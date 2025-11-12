# Documentación API REST - Eurekabank

**Base URL:** `http://localhost:8080/eurekabank/api/eureka`

---

## 📋 Índice
1. [Autenticación](#autenticación)
2. [Consultas](#consultas)
3. [Transacciones](#transacciones)
4. [Modelos de Datos](#modelos-de-datos)

---

## 🔐 Autenticación

### Login
Valida las credenciales de un usuario.

**Endpoint:** `POST /login`

**Query Parameters:**
- `username` (String): Nombre de usuario
- `password` (String): Contraseña del usuario

**Ejemplo de Request:**
```
POST /api/eureka/login?username=admin&password=123456
```

**Respuesta Exitosa (200 OK):**
```json
true
```

**Respuesta Error (401 UNAUTHORIZED):**
```json
false
```

---

## 📊 Consultas

### 1. Obtener Movimientos de una Cuenta
Lista todos los movimientos de una cuenta específica.

**Endpoint:** `GET /movimientos/{cuenta}`

**Path Parameters:**
- `cuenta` (String): Código de la cuenta

**Ejemplo de Request:**
```
GET /api/eureka/movimientos/00100001
```

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "cuenta": "00100001",
    "nromov": 5,
    "fecha": "2024-11-10T00:00:00.000+00:00",
    "tipo": "Depósito en Efectivo",
    "accion": "++",
    "importe": 500.00
  },
  {
    "cuenta": "00100001",
    "nromov": 4,
    "fecha": "2024-11-09T00:00:00.000+00:00",
    "tipo": "Retiro en Efectivo",
    "accion": "--",
    "importe": 200.00
  }
]
```

**Respuesta Error (500 INTERNAL SERVER ERROR):**
```json
"Error al obtener movimientos."
```

---

### 2. Obtener Balances de Todas las Cuentas
Lista todas las cuentas activas con sus balances.

**Endpoint:** `GET /balances`

**Ejemplo de Request:**
```
GET /api/eureka/balances
```

**Respuesta Exitosa (200 OK):**
```json
[
  {
    "numeroCuenta": "00100001",
    "nombreCliente": "Juan Pérez García",
    "saldo": 5000.00,
    "moneda": "SOLES",
    "estado": "ACTIVO"
  },
  {
    "numeroCuenta": "00100002",
    "nombreCliente": "María López Torres",
    "saldo": 3500.50,
    "moneda": "DOLARES",
    "estado": "ACTIVO"
  }
]
```

**Respuesta Error (500 INTERNAL SERVER ERROR):**
```json
"Error al obtener balances."
```

---

## 💰 Transacciones

### 1. Registrar Depósito
Registra un depósito en una cuenta.

**Endpoint:** `POST /deposito`

**Query Parameters:**
- `cuenta` (String): Código de la cuenta
- `importe` (Double): Monto a depositar

**Ejemplo de Request:**
```
POST /api/eureka/deposito?cuenta=00100001&importe=500.00
```

**Respuesta Exitosa (200 OK):**
```json
"Depósito registrado con éxito."
```

**Respuesta Error (500 INTERNAL SERVER ERROR):**
```json
"Error al registrar el depósito."
```

**Notas:**
- El importe debe ser un número positivo
- La cuenta debe estar activa
- Se utiliza el código de empleado "0001" por defecto
- Tipo de movimiento: "003"

---

### 2. Registrar Retiro
Registra un retiro de una cuenta.

**Endpoint:** `POST /retiro`

**Query Parameters:**
- `cuenta` (String): Código de la cuenta
- `importe` (Double): Monto a retirar

**Ejemplo de Request:**
```
POST /api/eureka/retiro?cuenta=00100001&importe=200.00
```

**Respuesta Exitosa (200 OK):**
```json
"Retiro registrado con éxito."
```

**Respuesta Error (500 INTERNAL SERVER ERROR):**
```json
"Error al registrar el retiro."
```

**Errores Posibles:**
- `ERROR: Saldo insuficiente.` - Si la cuenta no tiene fondos suficientes
- `ERROR: Cuenta no existe o no está activa.` - Si la cuenta no es válida

**Notas:**
- El importe debe ser un número positivo
- La cuenta debe tener saldo suficiente
- Se utiliza el código de empleado "0001" por defecto
- Tipo de movimiento: "004"

---

### 3. Registrar Transferencia
Registra una transferencia entre dos cuentas.

**Endpoint:** `POST /transferencia`

**Query Parameters:**
- `cuentaOrigen` (String): Código de la cuenta origen
- `cuentaDestino` (String): Código de la cuenta destino
- `importe` (Double): Monto a transferir

**Ejemplo de Request:**
```
POST /api/eureka/transferencia?cuentaOrigen=00100001&cuentaDestino=00100002&importe=300.00
```

**Respuesta Exitosa (200 OK):**
```json
"Transferencia registrada con éxito."
```

**Respuesta Error (500 INTERNAL SERVER ERROR):**
```json
"Error al registrar la transferencia."
```

**Errores Posibles:**
- `ERROR: Saldo insuficiente.` - Si la cuenta origen no tiene fondos suficientes
- `ERROR: Cuenta no existe o no está activa.` - Si alguna de las cuentas no es válida

**Notas:**
- Se realiza como una transacción atómica (se revierte si falla)
- Se utiliza el código de empleado "0001" por defecto
- Tipo de movimiento origen: "009" (retiro por transferencia)
- Tipo de movimiento destino: "008" (depósito por transferencia)

---

## 📦 Modelos de Datos

### Movimiento
Representa un movimiento bancario.

```json
{
  "cuenta": "String",
  "nromov": "Integer",
  "fecha": "Date",
  "tipo": "String",
  "accion": "String",
  "importe": "Double"
}
```

**Campos:**
- `cuenta`: Código de la cuenta (ej: "00100001")
- `nromov`: Número secuencial del movimiento
- `fecha`: Fecha del movimiento en formato ISO 8601
- `tipo`: Descripción del tipo de movimiento (ej: "Depósito en Efectivo", "Retiro en Efectivo")
- `accion`: Indicador de suma o resta ("++" para aumentos, "--" para disminuciones)
- `importe`: Monto del movimiento

---

### Cuenta
Representa una cuenta bancaria con su balance.

```json
{
  "numeroCuenta": "String",
  "nombreCliente": "String",
  "saldo": "Double",
  "moneda": "String",
  "estado": "String"
}
```

**Campos:**
- `numeroCuenta`: Código único de la cuenta (ej: "00100001")
- `nombreCliente`: Nombre completo del titular (ej: "Juan Pérez García")
- `saldo`: Balance actual de la cuenta
- `moneda`: Tipo de moneda ("SOLES", "DOLARES")
- `estado`: Estado de la cuenta ("ACTIVO", "INACTIVO")

---

## 📝 Códigos de Estado HTTP

| Código | Descripción |
|--------|-------------|
| 200 | OK - Operación exitosa |
| 401 | UNAUTHORIZED - Credenciales inválidas |
| 500 | INTERNAL SERVER ERROR - Error en el servidor |

---

## 🔧 Configuración

### Headers Requeridos
```
Content-Type: application/json
Accept: application/json
```

### CORS
El servidor tiene configurado CORS para aceptar peticiones desde cualquier origen.

---

## 💾 Base de Datos

### Tablas Principales
- `cuenta`: Información de cuentas bancarias
- `movimiento`: Registro de transacciones
- `tipomovimiento`: Catálogo de tipos de movimientos
- `cliente`: Información de clientes
- `modena`: Catálogo de monedas
- `usuario`: Usuarios del sistema

### Tipos de Movimiento
- `003`: Depósito en efectivo
- `004`: Retiro en efectivo
- `008`: Depósito por transferencia
- `009`: Retiro por transferencia

---

## ⚠️ Consideraciones Importantes

1. **Transacciones**: Las operaciones de depósito, retiro y transferencia utilizan transacciones para garantizar la consistencia de datos.

2. **Bloqueos**: Las operaciones que modifican saldos utilizan `FOR UPDATE` para evitar condiciones de carrera.

3. **Seguridad**: Las contraseñas se validan usando SHA en la base de datos.

4. **Validaciones**:
   - Las cuentas deben estar en estado "ACTIVO"
   - Los saldos no pueden ser negativos (excepto en casos especiales)
   - Los importes deben ser valores numéricos válidos

5. **Auditoría**: Cada movimiento registra:
   - Fecha y hora automática (SYSDATE())
   - Código de empleado que realiza la operación
   - Número secuencial de movimiento

---

## 📞 Contacto y Soporte

Para reportar problemas o solicitar nuevas funcionalidades, contactar al equipo de desarrollo.

**Versión:** 1.0  
**Última actualización:** Noviembre 2024
