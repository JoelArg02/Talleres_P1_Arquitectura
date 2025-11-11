# Cliente de Consola REST - EurekaBank

## Estructura del Proyecto

```
TI1.7 REST_JAVA_CONDBB_EUREKABANK_GR03/
└── 02. CLICON/
    └── eurekabank_consola/
        ├── src/
        │   └── ec/edu/gr03/
        │       ├── Eurekabank_consola.java (Main)
        │       ├── view/
        │       │   ├── LoginView.java
        │       │   └── MenuView.java
        │       ├── controller/
        │       │   ├── MainController.java
        │       │   ├── EurekaBankClient.java
        │       │   └── EurekaBankController.java
        │       └── model/
        │           ├── Movimiento.java
        │           └── Cuenta.java
        ├── lib/ (dependencias descargadas)
        └── descargar_dependencias.bat
```

## Archivos Implementados

### 1. **Eurekabank_consola.java** (Clase Principal)
- Punto de entrada de la aplicación
- Inicia el `MainController`

### 2. **LoginView.java** (Vista de Login)
- Muestra pantalla de login
- Captura usuario y contraseña
- Muestra mensajes de error/bienvenida

### 3. **MenuView.java** (Vista del Menú)
- Muestra menú principal con 6 opciones:
  1. Realizar depósito
  2. Realizar retiro
  3. Realizar transferencia
  4. Ver movimientos
  5. Ver balances ✅ NUEVO
  0. Salir
- Captura datos para cada operación

### 4. **MainController.java** (Controlador Principal)
- Maneja el flujo de la aplicación
- Gestiona login y navegación del menú
- Implementa todas las operaciones:
  - ✅ Depósito
  - ✅ Retiro
  - ✅ Transferencia
  - ✅ Ver movimientos
  - ✅ Ver balances (NUEVO - exactamente igual que TI1.5)

### 5. **EurekaBankClient.java** (Cliente de Servicios)
- Métodos estáticos para consumir el servidor REST:
  - `login(username, password)` → boolean
  - `regDeposito(cuenta, importe)` → int
  - `regRetiro(cuenta, importe)` → int
  - `regTransferencia(origen, destino, importe)` → int
  - `traerMovimientos(cuenta)` → List<Movimiento>
  - `traerBalances()` → List<Cuenta> ✅ NUEVO

### 6. **EurekaBankController.java** (Controlador REST)
- Consume el servidor REST en `http://localhost:8080/eurekabank/api`
- Endpoints implementados:
  - POST `/eureka/login`
  - POST `/eureka/deposito`
  - POST `/eureka/retiro`
  - POST `/eureka/transferencia`
  - GET `/eureka/movimientos/{cuenta}`
  - GET `/eureka/balances` ✅ NUEVO

### 7. **Movimiento.java** (Modelo)
- Propiedades: nromov, fecha, tipo, accion, importe

### 8. **Cuenta.java** (Modelo) ✅ NUEVO
- Propiedades: numeroCuenta, nombreCliente, saldo, moneda, estado

## Dependencias (lib/)

Las siguientes dependencias se descargaron automáticamente:

1. jakarta.ws.rs-api-3.1.0.jar (151 KB)
2. jersey-client-3.1.3.jar (298 KB)
3. jersey-common-3.1.3.jar (1.2 MB)
4. jersey-hk2-3.1.3.jar (78 KB)
5. jersey-media-json-jackson-3.1.3.jar (80 KB)
6. jackson-databind-2.15.2.jar (1.6 MB)
7. jackson-core-2.15.2.jar (536 KB)
8. jackson-annotations-2.15.2.jar (75 KB)
9. hk2-api-3.0.4.jar (202 KB)
10. hk2-locator-3.0.4.jar (200 KB)

**Total: ~4.3 MB**

## Configuración en NetBeans

1. Abrir el proyecto `eurekabank_consola` en NetBeans
2. Click derecho en el proyecto → **Properties**
3. Ir a **Libraries** → **Compile** tab
4. Click en **Add JAR/Folder**
5. Navegar a la carpeta `lib/`
6. Seleccionar todos los JARs (Ctrl+A)
7. Click **Open** → **OK**
8. **Clean and Build** el proyecto

## Ejecución

1. Asegurarse que el servidor REST esté corriendo:
   - Servidor: `TI1.7 REST_JAVA_CONDBB_EUREKABANK_GR03/01. SERVER/eurekabank`
   - URL: `http://localhost:8080/eurekabank/api`

2. Ejecutar el proyecto en NetBeans:
   - Click derecho → **Run**
   - O presionar **F6**

## Funcionalidades Implementadas

### ✅ Login
- Usuario: `admin`
- Contraseña: `admin123`
- Validación contra el servidor REST

### ✅ Operaciones Bancarias
1. **Depósito**: Ingresa cuenta e importe
2. **Retiro**: Ingresa cuenta e importe
3. **Transferencia**: Ingresa cuenta origen, cuenta destino e importe
4. **Movimientos**: Ingresa cuenta y muestra tabla de movimientos
5. **Balances**: Muestra tabla con todas las cuentas (NUEVO - igual que TI1.5)

### ✅ Formato de Salida (Balances)
```
=== BALANCES DE TODAS LAS CUENTAS ===

N° Cuenta       Cliente                                         Saldo           Estado
--------------------------------------------------------------------------------------------------------------------
00010001        JUAN PEREZ                                      1500.00         ACTIVO
00010002        MARIA GARCIA                                    2300.50         ACTIVO
```

## Diferencias con TI1.5 (SOAP)

| Aspecto | TI1.5 (SOAP) | TI1.7 (REST) |
|---------|--------------|--------------|
| Protocolo | SOAP/XML | REST/JSON |
| Cliente | JAX-WS | JAX-RS (Jersey) |
| Clases Generadas | `WSEureka_Service`, `WSEureka` | `EurekaBankController` (manual) |
| Serialización | XML | JSON (Jackson) |
| Interfaz Usuario | **EXACTAMENTE IGUAL** ✅ | **EXACTAMENTE IGUAL** ✅ |
| Funcionalidades | Login, CRUD, Movimientos, Balances | Login, CRUD, Movimientos, Balances |

## Notas Importantes

- ✅ La interfaz de consola es **EXACTAMENTE IGUAL** a TI1.5 SOAP
- ✅ Todas las funcionalidades están implementadas (incluyendo balances)
- ✅ El formato de salida de tablas es idéntico
- ✅ El menú tiene las mismas opciones
- ✅ Los mensajes de usuario son los mismos
- ✅ La estructura del código sigue el mismo patrón MVC
