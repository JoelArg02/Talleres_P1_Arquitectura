# API REST - EurekaBank .NET

**BASE URL:** `http://localhost:5000/api/movimiento`

---

## POST /login

**Payload:**
```json
{
  "username": "string",
  "password": "string"
}
```

**Response 200:**
```json
{
  "mensaje": "Login exitoso.",
  "autenticado": true
}
```

**Response 200 (error):**
```json
{
  "mensaje": "Credenciales inválidas.",
  "autenticado": false
}
```

---

## GET /listar

**Query Parameter:**
- `cuenta` (string)

**Example:** `/api/movimiento/listar?cuenta=00100001`

**Response 200:**
```json
[
  {
    "Cuenta": "00100001",
    "NroMov": 5,
    "Fecha": "2024-11-10T00:00:00",
    "Tipo": "Depósito en Efectivo",
    "Accion": "++",
    "Importe": 500.00
  }
]
```

---

## GET /balances

**Response 200:**
```json
[
  {
    "NumeroCuenta": "00100001",
    "NombreCliente": "Juan Pérez García",
    "Saldo": 5000.00,
    "Moneda": "SOLES",
    "Estado": "ACTIVO"
  }
]
```

---

## POST /deposito

**Payload:**
```json
{
  "cuenta": "string",
  "importe": 0.0
}
```

**Response 200:**
```json
{
  "mensaje": "Depósito registrado exitosamente.",
  "resultado": "1"
}
```

---

## POST /retiro

**Payload:**
```json
{
  "cuenta": "string",
  "importe": 0.0
}
```

**Response 200:**
```json
{
  "mensaje": "Retiro registrado exitosamente.",
  "resultado": "1"
}
```

---

## POST /transferencia

**Payload:**
```json
{
  "cuentaOrigen": "string",
  "cuentaDestino": "string",
  "importe": 0.0
}
```

**Response 200:**
```json
{
  "mensaje": "Transferencia registrada exitosamente.",
  "resultado": "1"
}
```

---

## Error Responses

**400 Bad Request:**
```json
{
  "Message": "Datos inválidos."
}
```

**500 Internal Server Error:**
```json
{
  "Message": "Error al procesar solicitud: [detalle]"
}
```
