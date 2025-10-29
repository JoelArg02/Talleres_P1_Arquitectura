# Pruebas del Servicio SOAP de Conversión

## Endpoints Disponibles

- **Service.svc**: `http://localhost:5000/Service.svc`
- **Conversion.svc**: `http://localhost:5000/Conversion.svc`
- **WSDL**: `http://localhost:5000/Service.svc?wsdl` o `http://localhost:5000/Conversion.svc?wsdl`

## Iniciar el Servicio

```bash
cd "/Users/joelarguello/Documents/GitHub/Talleres/TI1.2 SOAP_DOTNET_CONUNI_GRP3/01 SERVIDOR/DOTNET_CONUNI_GRO3"
dotnet run
```

## Ejemplo 1: Lima, Perú

### Request SOAP

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:tem="http://tempuri.org/" 
                  xmlns:wcf="http://schemas.datacontract.org/2004/07/WCFService.Models">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:Convert>
         <tem:request>
            <wcf:Latitude>-12.046374</wcf:Latitude>
            <wcf:Longitude>-77.042793</wcf:Longitude>
            <wcf:MassKg>70</wcf:MassKg>
         </tem:request>
      </tem:Convert>
   </soapenv:Body>
</soapenv:Envelope>
```

### Response Esperada

```xml
<s:Envelope xmlns:s="http://schemas.xmlsoap.org/soap/envelope/">
   <s:Body>
      <ConvertResponse xmlns="http://tempuri.org/">
         <ConvertResult xmlns:a="http://schemas.datacontract.org/2004/07/WCFService.Models">
            <a:LatitudeDecimal>-12.046374</a:LatitudeDecimal>
            <a:LongitudeDecimal>-77.042793</a:LongitudeDecimal>
            <a:LatitudeRadians>-0.210240</a:LatitudeRadians>
            <a:LongitudeRadians>-1.344016</a:LongitudeRadians>
            <a:LatitudeDMS>
               <a:Degrees>12</a:Degrees>
               <a:Minutes>2</a:Minutes>
               <a:Seconds>46.9464</a:Seconds>
               <a:Hemisphere>S</a:Hemisphere>
            </a:LatitudeDMS>
            <a:LongitudeDMS>
               <a:Degrees>77</a:Degrees>
               <a:Minutes>2</a:Minutes>
               <a:Seconds>34.0548</a:Seconds>
               <a:Hemisphere>W</a:Hemisphere>
            </a:LongitudeDMS>
            <a:MassKg>70</a:MassKg>
            <a:MassG>70000</a:MassG>
            <a:MassLb>154.32358</a:MassLb>
         </ConvertResult>
      </ConvertResponse>
   </s:Body>
</s:Envelope>
```

## Ejemplo 2: Nueva York, USA

### Request SOAP

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:tem="http://tempuri.org/" 
                  xmlns:wcf="http://schemas.datacontract.org/2004/07/WCFService.Models">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:Convert>
         <tem:request>
            <wcf:Latitude>40.712776</wcf:Latitude>
            <wcf:Longitude>-74.005974</wcf:Longitude>
            <wcf:MassKg>85.5</wcf:MassKg>
         </tem:request>
      </tem:Convert>
   </soapenv:Body>
</soapenv:Envelope>
```

### Valores Esperados

- **Latitud DMS**: 40° 42' 46.0" N
- **Longitud DMS**: 74° 0' 21.5" W
- **Latitud Radianes**: 0.710593
- **Longitud Radianes**: -1.291544
- **Masa en gramos**: 85500 g
- **Masa en libras**: 188.495 lb

## Ejemplo 3: Tokyo, Japón

### Request SOAP

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:tem="http://tempuri.org/" 
                  xmlns:wcf="http://schemas.datacontract.org/2004/07/WCFService.Models">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:Convert>
         <tem:request>
            <wcf:Latitude>35.6762</wcf:Latitude>
            <wcf:Longitude>139.6503</wcf:Longitude>
            <wcf:MassKg>62</wcf:MassKg>
         </tem:request>
      </tem:Convert>
   </soapenv:Body>
</soapenv:Envelope>
```

### Valores Esperados

- **Latitud DMS**: 35° 40' 34.3" N
- **Longitud DMS**: 139° 39' 1.1" E
- **Latitud Radianes**: 0.622718
- **Longitud Radianes**: 2.437101
- **Masa en gramos**: 62000 g
- **Masa en libras**: 136.687 lb

## Ejemplo 4: Valores en cero

### Request SOAP

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:tem="http://tempuri.org/" 
                  xmlns:wcf="http://schemas.datacontract.org/2004/07/WCFService.Models">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:Convert>
         <tem:request>
            <wcf:Latitude>0</wcf:Latitude>
            <wcf:Longitude>0</wcf:Longitude>
            <wcf:MassKg>100</wcf:MassKg>
         </tem:request>
      </tem:Convert>
   </soapenv:Body>
</soapenv:Envelope>
```

### Valores Esperados

- **Latitud DMS**: 0° 0' 0.0" N
- **Longitud DMS**: 0° 0' 0.0" E
- **Latitud Radianes**: 0
- **Longitud Radianes**: 0
- **Masa en gramos**: 100000 g
- **Masa en libras**: 220.462 lb

## Probar con cURL

```bash
curl -X POST http://localhost:5000/Conversion.svc \
  -H "Content-Type: text/xml; charset=utf-8" \
  -H "SOAPAction: http://tempuri.org/IService/Convert" \
  -d '<?xml version="1.0" encoding="utf-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:tem="http://tempuri.org/" xmlns:wcf="http://schemas.datacontract.org/2004/07/WCFService.Models">
   <soapenv:Header/>
   <soapenv:Body>
      <tem:Convert>
         <tem:request>
            <wcf:Latitude>-12.046374</wcf:Latitude>
            <wcf:Longitude>-77.042793</wcf:Longitude>
            <wcf:MassKg>70</wcf:MassKg>
         </tem:request>
      </tem:Convert>
   </soapenv:Body>
</soapenv:Envelope>'
```

## Probar con Postman

1. Crear nueva request POST
2. URL: `http://localhost:5000/Conversion.svc`
3. Headers:
   - `Content-Type: text/xml; charset=utf-8`
   - `SOAPAction: http://tempuri.org/IService/Convert`
4. Body (raw): Copiar cualquiera de los XML de ejemplo de arriba

## Probar con SoapUI

1. File → New SOAP Project
2. Initial WSDL: `http://localhost:5000/Conversion.svc?wsdl`
3. Hacer clic en el endpoint "Convert"
4. Completar los valores en el request generado
5. Click en el botón verde de play

## Conversiones que Realiza

### Latitud y Longitud
- **Entrada**: Grados decimales (ej: -12.046374)
- **Salidas**:
  - Grados decimales (echo)
  - Radianes (deg × π / 180)
  - DMS (Grados, Minutos, Segundos, Hemisferio)

### Masa
- **Entrada**: Kilogramos (kg)
- **Salidas**:
  - Kilogramos (echo)
  - Gramos (kg × 1000)
  - Libras (kg × 2.20462262185)

## Estructura MVC

```
Models/
  - ConversionRequest.cs
  - ConversionResponse.cs

Controller/
  - ConversionController.cs

Services/
  - Service.cs
  - Interfaces/
    - IService.cs

Views/
  - README.md

Configuration/
  - ServiceConfiguration.cs
```
