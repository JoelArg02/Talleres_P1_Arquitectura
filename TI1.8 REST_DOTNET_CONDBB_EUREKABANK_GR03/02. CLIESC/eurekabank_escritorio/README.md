# Cliente de Escritorio - EurekaBank REST API

Cliente de escritorio Java Swing para consumir el servidor REST .NET de EurekaBank.

## 🚀 Configuración del Proyecto

### 1. Descargar Dependencias

Ejecuta el script BAT para descargar todas las librerías JAR necesarias:

```bash
descargar_dependencias.bat
```

Este script descargará automáticamente 20 archivos JAR en la carpeta `lib/`:
- Jakarta REST API 3.1.0
- Jersey Client 3.1.3 (cliente JAX-RS)
- Jackson 2.15.2 (procesamiento JSON)
- HK2 3.0.4 (inyección de dependencias)
- Y otras dependencias requeridas

### 2. Agregar Dependencias al Proyecto en NetBeans

1. Abre el proyecto `eurekabank_escritorio` en NetBeans
2. Click derecho en **Libraries** → **Add JAR/Folder**
3. Navega a la carpeta `lib/`
4. Selecciona **todos** los archivos JAR (Ctrl+A)
5. Click en **Open**

### 3. Verificar el Servidor REST

Asegúrate que el servidor REST .NET esté ejecutándose en:
```
http://localhost:60245/api/movimiento
```

Puedes verificarlo abriendo en el navegador o con Postman.

### 4. Ejecutar el Cliente

1. En NetBeans: **Run** → **Run Project** (F6)
2. Ingresa credenciales de prueba:
   - Usuario: `MONSTER`
   - Contraseña: `MONSTER9`

## 📁 Estructura del Proyecto

```
eurekabank_escritorio/
├── src/
│   └── ec/edu/gr03/
│       ├── controller/
│       │   ├── Cuenta.java          (Modelo REST)
│       │   └── Movimiento.java      (Modelo REST)
│       ├── model/
│       │   └── EurekaBankClient.java (Cliente REST API)
│       ├── view/
│       │   ├── LoginFrm.java         (Pantalla de login)
│       │   ├── MovimientosFrm.java   (Menú principal)
│       │   ├── DepositoFrm.java      (Realizar depósito)
│       │   ├── RetiroFrm.java        (Realizar retiro)
│       │   ├── TransferenciasFrm.java (Realizar transferencia)
│       │   ├── MovimientosTablaFrm.java (Ver movimientos)
│       │   └── BalancesFrm.java      (Ver balances)
│       └── img/
│           └── *.png                 (Iconos e imágenes)
├── lib/                              (Dependencias JAR)
├── nbproject/                        (Configuración NetBeans)
├── build.xml                         (Ant build)
├── manifest.mf                       (Manifest)
└── descargar_dependencias.bat       (Script de descarga)
```

## 🎨 Características de la Interfaz

- ✅ **Mismos colores** que TI1.6 SOAP
- ✅ **Mismas imágenes** e iconos
- ✅ **Mismo diseño** de formularios
- ✅ **Misma funcionalidad** completa

## 🔧 Funcionalidades

1. **Login** - Autenticación de usuarios
2. **Depósito** - Registrar depósitos en cuentas
3. **Retiro** - Registrar retiros de cuentas
4. **Transferencia** - Transferir entre cuentas
5. **Movimientos** - Ver historial de transacciones
6. **Balances** - Ver saldo de todas las cuentas

## 🌐 Endpoints REST Consumidos

```
POST http://localhost:60245/api/movimiento/login
POST http://localhost:60245/api/movimiento/deposito
POST http://localhost:60245/api/movimiento/retiro
POST http://localhost:60245/api/movimiento/transferencia
GET  http://localhost:60245/api/movimiento/listar?cuenta={cuenta}
GET  http://localhost:60245/api/movimiento/balances
```

## 📝 Diferencia con TI1.6 SOAP

| Aspecto | TI1.6 SOAP | TI1.8 REST |
|---------|------------|------------|
| Protocolo | SOAP/XML | REST/JSON |
| Cliente | WSDL generado | JAX-RS Jersey |
| Serialización | XML | JSON (Jackson) |
| Transporte | HTTP POST | HTTP GET/POST |
| Interfaz | ✅ Idéntica | ✅ Idéntica |

## ⚙️ Configuración del Servidor

Si necesitas cambiar la URL del servidor, edita la constante en `EurekaBankClient.java`:

```java
private static final String BASE_URI = "http://localhost:60245/api/movimiento";
```

## 🐛 Solución de Problemas

### Error: "No se puede conectar al servidor"
- Verifica que el servidor REST .NET esté ejecutándose
- Verifica la URL en `EurekaBankClient.java`
- Verifica que no haya firewall bloqueando el puerto 60245

### Error: "ClassNotFoundException"
- Asegúrate de haber agregado TODOS los JARs de la carpeta `lib/` al proyecto
- Limpia y reconstruye el proyecto (Clean and Build)

### Error de autenticación
- Verifica que las credenciales sean correctas
- Verifica que la base de datos EurekaBank esté accesible desde el servidor

## 📦 Tecnologías Utilizadas

- **Java Swing** - Interfaz gráfica
- **JAX-RS Jersey 3.1.3** - Cliente REST
- **Jackson 2.15.2** - Procesamiento JSON
- **NetBeans** - IDE de desarrollo

---

**Desarrollado para:** Taller de Integración 1 - Arquitectura de Aplicaciones  
**Protocolo:** REST con JSON  
**Servidor:** .NET Web API  
**Cliente:** Java Swing Desktop
