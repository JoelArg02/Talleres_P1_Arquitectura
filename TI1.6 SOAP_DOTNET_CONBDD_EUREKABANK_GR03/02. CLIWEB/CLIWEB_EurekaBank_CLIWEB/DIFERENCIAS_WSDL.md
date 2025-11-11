# Diferencias entre WSDL Java y WSDL DOTNET

Este documento describe las diferencias clave entre las clases generadas desde el servicio SOAP Java y el servicio SOAP DOTNET.

## 🔄 Nombres de Métodos

### Servicio (IMovimientoController)

| Método Java | Método DOTNET (generado) | Descripción |
|-------------|--------------------------|-------------|
| `login(username, password)` | `login(username, password)` | ✅ Igual |
| `regDeposito(cuenta, importe)` | `registrarDeposito(cuenta, importe)` | ❌ Diferente |
| `regRetiro(cuenta, importe)` | `registrarRetiro(cuenta, importe)` | ❌ Diferente |
| `regTransferencia(origen, destino, importe)` | `registrarTransferencia(origen, destino, importe)` | ❌ Diferente |
| `traerMovimientos(cuenta)` | `obtenerPorCuenta(cuenta)` | ❌ Diferente |
| `traerBalances()` | `traerBalances()` | ✅ Igual |

### Tipos de Retorno

| Método | Java | DOTNET |
|--------|------|--------|
| `regDeposito/registrarDeposito` | `int` | `String` |
| `regRetiro/registrarRetiro` | `int` | `String` |
| `regTransferencia/registrarTransferencia` | `int` | `String` |
| `traerMovimientos/obtenerPorCuenta` | `List<Movimiento>` | `ArrayOfMovimiento` |
| `traerBalances` | `List<Cuenta>` | `ArrayOfCuenta` |

## 📦 Clase Movimiento

### Propiedades y Getters

| Propiedad | Java | DOTNET | Tipo de Retorno DOTNET |
|-----------|------|--------|------------------------|
| Número de Movimiento | `getNromov()` | `getNroMov()` | `Integer` |
| Fecha | `getFecha()` | `getFecha()` | `XMLGregorianCalendar` |
| Tipo | `getTipo()` | `getTipo()` | `JAXBElement<String>` ⚠️ |
| Acción | `getAccion()` | `getAccion()` | `JAXBElement<String>` ⚠️ |
| Importe | `getImporte()` | `getImporte()` | `Double` |
| Cuenta | `getCuenta()` | `getCuenta()` | `JAXBElement<String>` ⚠️ |

### ⚠️ Importante: JAXBElement

Algunas propiedades en DOTNET retornan `JAXBElement<String>` en lugar de `String` directamente.

**Para obtener el valor:**
```java
// Java (servicio Java)
String tipo = mov.getTipo();

// DOTNET (servicio DOTNET)
String tipo = mov.getTipo() != null ? mov.getTipo().getValue() : "N/A";
```

## 📦 Clase Cuenta

### Propiedades y Getters

| Propiedad | Java | DOTNET | Tipo de Retorno DOTNET |
|-----------|------|--------|------------------------|
| Número de Cuenta | `getNumeroCuenta()` | `getNumeroCuenta()` | `JAXBElement<String>` ⚠️ |
| Nombre Cliente | `getNombreCliente()` | `getNombreCliente()` | `JAXBElement<String>` ⚠️ |
| Saldo | `getSaldo()` | `getSaldo()` | `Double` |
| Moneda | `getMoneda()` | `getMoneda()` | `JAXBElement<String>` ⚠️ |
| Estado | `getEstado()` | `getEstado()` | `JAXBElement<String>` ⚠️ |

### ⚠️ Importante: JAXBElement

**Para obtener el valor:**
```java
// Java (servicio Java)
String numCuenta = cuenta.getNumeroCuenta();
String nombreCliente = cuenta.getNombreCliente();
String estado = cuenta.getEstado();

// DOTNET (servicio DOTNET)
String numCuenta = cuenta.getNumeroCuenta() != null ? cuenta.getNumeroCuenta().getValue() : "N/A";
String nombreCliente = cuenta.getNombreCliente() != null ? cuenta.getNombreCliente().getValue() : "N/A";
String estado = cuenta.getEstado() != null ? cuenta.getEstado().getValue() : "N/A";
```

## 🔧 Correcciones Aplicadas

### 1. EurekaBankClient.java

✅ Se actualizaron todos los métodos para usar los nombres correctos del servicio DOTNET:
```java
// Antes (esperado del servicio Java)
port.regDeposito(cuenta, importe);

// Después (servicio DOTNET)
String result = port.registrarDeposito(cuenta, importe);
return Integer.parseInt(result);
```

✅ Se agregó conversión de `String` a `int` para mantener compatibilidad con los servlets.

✅ Se agregó manejo de `ArrayOfMovimiento` y `ArrayOfCuenta`:
```java
ArrayOfMovimiento result = port.obtenerPorCuenta(cuenta);
return (result != null) ? result.getMovimiento() : null;
```

### 2. movimientos.jsp

✅ Corregido `getNromov()` → `getNroMov()`

✅ Agregado `.getValue()` para propiedades `JAXBElement`:
```jsp
<!-- Antes -->
<td><%= mov.getTipo() %></td>
<td><%= mov.getAccion() %></td>

<!-- Después -->
<td><%= mov.getTipo() != null ? mov.getTipo().getValue() : "N/A" %></td>
<td><%= mov.getAccion() != null ? mov.getAccion().getValue() : "N/A" %></td>
```

### 3. balances.jsp

✅ Agregado extracción de valores con `.getValue()`:
```jsp
<%
String numCuenta = cuenta.getNumeroCuenta() != null ? cuenta.getNumeroCuenta().getValue() : "N/A";
String nombreCliente = cuenta.getNombreCliente() != null ? cuenta.getNombreCliente().getValue() : "N/A";
String estado = cuenta.getEstado() != null ? cuenta.getEstado().getValue() : "N/A";
Double saldo = cuenta.getSaldo() != null ? cuenta.getSaldo() : 0.0;
%>
```

## 📚 Referencias

- **WSDL DOTNET**: `http://localhost:55325/ec.edu.monster.controlador/MovimientoController.svc?wsdl`
- **Clases generadas**: `target/generated-sources/wsimport/ec/edu/gr03/ws/`
- **Documentación JAXBElement**: Los elementos opcionales (minOccurs="0" en WSDL) se generan como `JAXBElement<T>`

## 🎯 Buenas Prácticas

1. **Siempre verificar null** antes de llamar a `.getValue()` en `JAXBElement`
2. **Usar valores por defecto** ("N/A", 0.0, etc.) cuando el valor sea null
3. **Regenerar clases** con `mvn clean compile` si cambia el WSDL del servicio DOTNET
4. **No modificar clases generadas** en `target/generated-sources/` - se sobrescriben en cada compilación

## 🔍 Debugging

Si encuentras errores de compilación tipo "cannot find symbol":
1. Verifica que el servidor DOTNET esté corriendo
2. Ejecuta `mvn clean compile` para regenerar las clases
3. Revisa los nombres de métodos en `target/generated-sources/wsimport/ec/edu/gr03/ws/IMovimientoController.java`
4. Revisa los tipos de retorno en `Movimiento.java` y `Cuenta.java`
