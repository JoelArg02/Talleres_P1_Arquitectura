# Guía rápida de integración con los servicios SOAP .NET (Login & Conversion)

Este proyecto de consola en Java consume dos servicios SOAP expuestos por un backend .NET (WCF). Si necesitas portar la integración a otro cliente (otro lenguaje, framework o interfaz), estos son los puntos clave que debes considerar.

## 1. Endpoints y WSDL relevantes

- **Login**
  - WSDL: `http://localhost:5001/Login.svc?wsdl`
  - Endpoint SOAP 1.1: `http://localhost:5001/Login.svc`
  - Acción (`SOAPAction`): `http://tempuri.org/ILoginService/ValidarCredenciales`

- **Conversion**
  - WSDL: `http://localhost:5001/Conversion.svc?wsdl`
  - Endpoint SOAP 1.1: `http://localhost:5001/Conversion.svc`
  - Acción (`SOAPAction`): `http://tempuri.org/IService/Convert`

Ambos servicios exponen un binding básico (`basicHttpBinding`), sin seguridad adicional (HTTP plano) y trabajan con mensajes document/literal.

## 2. Estructura de los mensajes

### 2.1. Autenticación (`ValidarCredenciales`)

**Petición**
```xml
POST /Login.svc HTTP/1.1
Host: localhost:5001
Content-Type: text/xml; charset=utf-8
SOAPAction: "http://tempuri.org/ILoginService/ValidarCredenciales"

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:tem="http://tempuri.org/">
  <soapenv:Header/>
  <soapenv:Body>
    <tem:ValidarCredenciales>
      <tem:username>USUARIO</tem:username>
      <tem:password>CLAVE</tem:password>
    </tem:ValidarCredenciales>
  </soapenv:Body>
</soapenv:Envelope>
```

**Respuesta**
```xml
<s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/">
  <s:Body>
    <ValidarCredencialesResponse xmlns="http://tempuri.org/">
      <ValidarCredencialesResult xmlns:a="http://schemas.datacontract.org/2004/07/WCFService.Models"
                                  xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
        <a:Message>Autenticacion correcta.</a:Message>
        <a:Success>true</a:Success>
        <a:Token>...token emitido...</a:Token>
      </ValidarCredencialesResult>
    </ValidarCredencialesResponse>
  </s:Body>
</s:Envelope>
```

### 2.2. Conversión (`Convert`)

**Petición**
```xml
POST /Conversion.svc HTTP/1.1
Host: localhost:5001
Content-Type: text/xml; charset=utf-8
SOAPAction: "http://tempuri.org/IService/Convert"

<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:tem="http://tempuri.org/"
                  xmlns:mod="http://schemas.datacontract.org/2004/07/WCFService.Models">
  <soapenv:Header/>
  <soapenv:Body>
    <tem:Convert>
      <tem:request>
        <mod:MassKg>10.0</mod:MassKg>
      </tem:request>
    </tem:Convert>
  </soapenv:Body>
</soapenv:Envelope>
```

**Respuesta (extracto)**
```xml
<s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/">
  <s:Body>
    <ConvertResponse xmlns="http://tempuri.org/">
      <ConvertResult xmlns:a="http://schemas.datacontract.org/2004/07/WCFService.Models"
                     xmlns:i="http://www.w3.org/2001/XMLSchema-instance">
        <a:MassKg>10</a:MassKg>
        <a:MassLb>22.0462262184878</a:MassLb>
        <a:MassG>10000</a:MassG>
        <a:LatitudeDecimal i:nil="true"/>
        ...
      </ConvertResult>
    </ConvertResponse>
  </s:Body>
</s:Envelope>
```

## 3. Reglas indispensables

1. **SOAPAction obligatorio**: WCF valida el encabezado `SOAPAction`. Si falta o es incorrecto, devolverá _ContractFilter mismatch_.
2. **Namespaces exactos**: El backend utiliza los espacios de nombres `http://tempuri.org/` para operaciones y `http://schemas.datacontract.org/2004/07/WCFService.Models` para los modelos. Si tu cliente genera elementos con otros prefijos/URIs o sin el namespace correcto, la petición será inválida.
3. **SOAP 1.1**: Usa `text/xml` y sobre SOAP 1.1 (no SOAP 1.2). Es el binding expuesto por defecto.
4. **Literal document**: Envía un único elemento raíz dentro del `<Body>` coincidiendo con la operación (`ValidarCredenciales`, `Convert`, etc.).

## 4. Estrategias de implementación

- **Java**: Puedes usar SAAJ (como en este cliente) o bibliotecas más declarativas (JAX-WS, Spring Web Services). Si optas por generar stubs con `wsimport`, verifica que la herramienta soporte Java 17 y SOAP 1.1 sin forzar `tools.jar`.
- **.NET**: Usa `Add Service Reference` / `Connected Services` y asegúrate de establecer `BasicHttpBinding` apuntando a los endpoints anteriores.
- **JavaScript / Node**: Utiliza librerías como `soap` que permitan especificar action, namespaces y el request completo.
- **Otros lenguajes**: Enviar el XML “a mano” sobre HTTP funciona, siempre que respetes los headers y la estructura.

## 5. Problemas resueltos y lecciones

- **Conflicto con `webservices-rt`**: Se retiró la dependencia `org.glassfish.metro:webservices-rt:2.3`. Su POM exige `tools.jar`, inexistente desde Java 9. La solución fue migrar a una integración manual (SAAJ) y quitar `wsimport` del build.
- **Error `ContractFilter mismatch`**: Se producía porque la cabecera `SOAPAction` no se enviaba. Se corrigió agregándola manualmente en cada solicitud.
- **Respuesta inesperada**: La estructura de la respuesta WCF no coincidía con la esperada por el parser. Se cambió a búsquedas por nombre local (ignorando prefijos) y se añadieron comprobaciones de `nil`.
- **Catalog WSDL heredado**: Se eliminaron referencias a los WSDL del servicio Java anterior (Metro). Asegúrate de usar únicamente los nuevos WSDL de `localhost:5001`.

## 6. Datos útiles de la respuesta

- **Login**
  - `Success` (`boolean`)
  - `Message` (`string`, opcional)
  - `Token` (`string`, opcional)

- **Conversion**
  - `MassKg`, `MassLb`, `MassG` (`double`)
  - `LatitudeDecimal`, `LatitudeRadians`, `LatitudeDMS` (`CoordinateDMS`)
  - `LongitudeDecimal`, `LongitudeRadians`, `LongitudeDMS`
  - Cada `CoordinateDMS` contiene `Degrees`, `Minutes`, `Seconds`, `Hemisphere`.

## 7. Pasos para nuevos clientes

1. Define URLs, acción y namespaces (ver secciones 1 y 3).
2. Construye el envelope SOAP con la operación correspondiente.
3. Envía la petición sobre HTTP POST (`Content-Type: text/xml; charset=utf-8`).
4. Parsear la respuesta respetando namespaces y valores `nil`.
5. Manejar faults (`SOAP Fault`) y errores HTTP.

Con estas pautas puedes replicar la integración en cualquier plataforma manteniendo compatibilidad con el backend .NET expuesto en `http://localhost:5001`.*** End Patch
