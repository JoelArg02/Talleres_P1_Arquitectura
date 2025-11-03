# ConversionAPI - Ejemplos de Payloads

API REST para conversión de unidades. Todos los endpoints usan método **POST**.

## 🌐 URL Base
```
http://localhost:5000/api
https://localhost:5001/api
```

---

## 📏 Conversión de Longitud
**Endpoint:** `POST /api/Length/convert`

### Metros a Kilómetros
```json
{
  "value": 1500,
  "fromUnit": 0,
  "toUnit": 1
}
```

### Kilómetros a Millas
```json
{
  "value": 10,
  "fromUnit": 1,
  "toUnit": 4
}
```

### Pies a Metros
```json
{
  "value": 100,
  "fromUnit": 6,
  "toUnit": 0
}
```

### Pulgadas a Centímetros
```json
{
  "value": 12,
  "fromUnit": 7,
  "toUnit": 2
}
```

**Códigos de Unidades:**
- `0` = Meters (Metros)
- `1` = Kilometers (Kilómetros)
- `2` = Centimeters (Centímetros)
- `3` = Millimeters (Milímetros)
- `4` = Miles (Millas)
- `5` = Yards (Yardas)
- `6` = Feet (Pies)
- `7` = Inches (Pulgadas)

---

## ⚖️ Conversión de Peso
**Endpoint:** `POST /api/Weight/convert`

### Kilogramos a Libras
```json
{
  "value": 10,
  "fromUnit": 0,
  "toUnit": 3
}
```

### Libras a Kilogramos
```json
{
  "value": 150,
  "fromUnit": 3,
  "toUnit": 0
}
```

### Gramos a Onzas
```json
{
  "value": 500,
  "fromUnit": 1,
  "toUnit": 4
}
```

### Toneladas a Kilogramos
```json
{
  "value": 2,
  "fromUnit": 5,
  "toUnit": 0
}
```

**Códigos de Unidades:**
- `0` = Kilograms (Kilogramos)
- `1` = Grams (Gramos)
- `2` = Milligrams (Miligramos)
- `3` = Pounds (Libras)
- `4` = Ounces (Onzas)
- `5` = Tons (Toneladas)

---

## 🌡️ Conversión de Temperatura
**Endpoint:** `POST /api/Temperature/convert`

### Celsius a Fahrenheit
```json
{
  "value": 25,
  "fromUnit": 0,
  "toUnit": 1
}
```

### Fahrenheit a Celsius
```json
{
  "value": 77,
  "fromUnit": 1,
  "toUnit": 0
}
```

### Celsius a Kelvin
```json
{
  "value": 100,
  "fromUnit": 0,
  "toUnit": 2
}
```

### Kelvin a Celsius
```json
{
  "value": 273.15,
  "fromUnit": 2,
  "toUnit": 0
}
```

**Códigos de Unidades:**
- `0` = Celsius (Celsius)
- `1` = Fahrenheit (Fahrenheit)
- `2` = Kelvin (Kelvin)

---

## 🧪 Conversión de Volumen
**Endpoint:** `POST /api/Volume/convert`

### Litros a Galones
```json
{
  "value": 10,
  "fromUnit": 0,
  "toUnit": 3
}
```

### Galones a Litros
```json
{
  "value": 5,
  "fromUnit": 3,
  "toUnit": 0
}
```

### Mililitros a Litros
```json
{
  "value": 2500,
  "fromUnit": 1,
  "toUnit": 0
}
```

### Metros Cúbicos a Litros
```json
{
  "value": 1,
  "fromUnit": 2,
  "toUnit": 0
}
```

**Códigos de Unidades:**
- `0` = Liters (Litros)
- `1` = Milliliters (Mililitros)
- `2` = CubicMeters (Metros Cúbicos)
- `3` = Gallons (Galones)
- `4` = Quarts (Cuartos)
- `5` = Pints (Pintas)
- `6` = Cups (Tazas)

---

## 📤 Ejemplo de Respuesta Exitosa

```json
{
  "originalValue": 100,
  "fromUnit": "Meters",
  "convertedValue": 0.1,
  "toUnit": "Kilometers",
  "category": "Length",
  "timestamp": "2025-11-03T12:00:00Z"
}
```

---

## ❌ Ejemplo de Respuesta de Error

```json
{
  "message": "Error en la conversión",
  "details": "Unidad de origen no válida",
  "timestamp": "2025-11-03T12:00:00Z"
}
```

---

## 🔧 Ejemplos con cURL

### Convertir 100 Metros a Kilómetros
```bash
curl -X POST "http://localhost:5000/api/Length/convert" \
  -H "Content-Type: application/json" \
  -d '{"value": 100, "fromUnit": 0, "toUnit": 1}'
```

### Convertir 25°C a Fahrenheit
```bash
curl -X POST "http://localhost:5000/api/Temperature/convert" \
  -H "Content-Type: application/json" \
  -d '{"value": 25, "fromUnit": 0, "toUnit": 1}'
```

### Convertir 10 Kg a Libras
```bash
curl -X POST "http://localhost:5000/api/Weight/convert" \
  -H "Content-Type: application/json" \
  -d '{"value": 10, "fromUnit": 0, "toUnit": 3}'
```

### Convertir 5 Litros a Galones
```bash
curl -X POST "http://localhost:5000/api/Volume/convert" \
  -H "Content-Type: application/json" \
  -d '{"value": 5, "fromUnit": 0, "toUnit": 3}'
```

---

## 🚀 Ejecutar el Proyecto

```bash
cd "01 SERVIDOR/ConversionAPI"
dotnet run
```

Acceder a Swagger: `http://localhost:5000/swagger`
