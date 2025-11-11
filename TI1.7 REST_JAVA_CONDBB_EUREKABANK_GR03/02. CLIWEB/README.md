# Cliente Web REST - EurekaBank

## Descripción
Cliente web Jakarta EE que consume servicios REST de EurekaBank. Interfaz **EXACTAMENTE IGUAL** al cliente SOAP (TI1.5) pero consumiendo servicios REST.

## Estructura del Proyecto

```
CLIWEB_EurekaBank_RESTJAVA/
├── src/main/
│   ├── java/ec/edu/gr03/
│   │   ├── model/
│   │   │   ├── Movimiento.java
│   │   │   └── Cuenta.java
│   │   ├── service/
│   │   │   └── EurekaBankClient.java (Cliente REST)
│   │   └── controller/
│   │       ├── LoginServlet.java
│   │       ├── LogoutServlet.java
│   │       ├── DepositoServlet.java
│   │       ├── RetiroServlet.java
│   │       └── TransferenciaServlet.java
│   └── webapp/
│       ├── css/
│       │   └── styles.css (IDÉNTICO a TI1.5)
│       ├── js/
│       │   └── scripts.js (IDÉNTICO a TI1.5)
│       ├── images/
│       │   ├── logo.png
│       │   ├── icon_movimientos.png
│       │   ├── icon_retiro.png
│       │   ├── icon_deposito.png
│       │   ├── icon_transferencia.png
│       │   ├── icon_balances.png
│       │   └── icon_logout.png
│       ├── WEB-INF/
│       │   └── navbar.jsp (Menú lateral)
│       ├── login.jsp
│       ├── movimientos.jsp
│       ├── retiro.jsp
│       ├── deposito.jsp
│       ├── transferencia.jsp
│       ├── balances.jsp
│       └── index.html
└── pom.xml
```

## Tecnologías Utilizadas

- **Jakarta EE 10**: Plataforma empresarial Java
- **Jakarta Servlet 6.0**: Servlets para manejar peticiones HTTP
- **JSP (JavaServer Pages)**: Páginas dinámicas
- **JSTL 3.0**: Librería de etiquetas estándar para JSP
- **JAX-RS Client 3.1**: Cliente para consumir servicios REST
- **Jersey Client 3.1.3**: Implementación de referencia de JAX-RS
- **Jackson 2.15.2**: Procesamiento JSON
- **Maven**: Gestión de dependencias y construcción

## Dependencias Principales

```xml
<!-- JAX-RS Client API -->
<dependency>
    <groupId>jakarta.ws.rs</groupId>
    <artifactId>jakarta.ws.rs-api</artifactId>
    <version>3.1.0</version>
</dependency>

<!-- Jersey Client -->
<dependency>
    <groupId>org.glassfish.jersey.core</groupId>
    <artifactId>jersey-client</artifactId>
    <version>3.1.3</version>
</dependency>

<!-- Jersey Media JSON Jackson -->
<dependency>
    <groupId>org.glassfish.jersey.media</groupId>
    <artifactId>jersey-media-json-jackson</artifactId>
    <version>3.1.3</version>
</dependency>

<!-- Jackson -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.15.2</version>
</dependency>
```

## Configuración del Servidor REST

El cliente consume el servidor REST en:
```
http://localhost:8080/eurekabank/api/eureka
```

Si el servidor REST está en otra URL, modificar en:
`src/main/java/ec/edu/gr03/service/EurekaBankClient.java`
```java
private static final String BASE_URI = "http://localhost:8080/eurekabank/api/eureka";
```

## Endpoints Consumidos

| Operación | Método | Endpoint | Parámetros |
|-----------|--------|----------|------------|
| Login | POST | `/login` | username, password |
| Depósito | POST | `/deposito` | cuenta, importe |
| Retiro | POST | `/retiro` | cuenta, importe |
| Transferencia | POST | `/transferencia` | cuentaOrigen, cuentaDestino, importe |
| Movimientos | GET | `/movimientos/{cuenta}` | cuenta (path param) |
| Balances | GET | `/balances` | - |

## Compilación y Despliegue

### Requisitos Previos
1. JDK 11 o superior
2. Maven 3.6+
3. GlassFish 7.0+ o Payara 6.0+
4. Servidor REST corriendo en `http://localhost:8080/eurekabank/`

### Pasos de Instalación

1. **Compilar el proyecto**:
   ```bash
   cd CLIWEB_EurekaBank_RESTJAVA
   mvn clean package
   ```

2. **Desplegar en GlassFish/Payara**:
   - Copiar el WAR generado: `target/eurekabank-web-rest.war`
   - A la carpeta `autodeploy` del servidor
   - O usar la consola de administración

3. **Acceder a la aplicación**:
   ```
   http://localhost:8080/eurekabank-web-rest/
   ```

## Credenciales de Prueba

- **Usuario**: `admin`
- **Contraseña**: `admin123`

## Funcionalidades Implementadas

### ✅ Login/Logout
- Autenticación contra el servidor REST
- Gestión de sesión HTTP
- Redirección automática si no hay sesión

### ✅ Operaciones Bancarias

1. **Movimientos**
   - Consulta de movimientos por cuenta
   - Tabla con: N° Movimiento, Fecha, Tipo, Acción, Importe
   - Formato de moneda con 2 decimales

2. **Retiro**
   - Formulario con cuenta e importe
   - Validación de importe positivo
   - Mensaje de éxito/error

3. **Depósito**
   - Formulario con cuenta e importe
   - Validación de importe positivo
   - Mensaje de éxito/error

4. **Transferencia**
   - Formulario con cuenta origen, destino e importe
   - Validación de cuentas diferentes
   - Validación de importe positivo
   - Mensaje de éxito/error

5. **Balances** ✅ NUEVO
   - Tabla con todas las cuentas activas
   - Columnas: N° Cuenta, Cliente, Saldo, Estado
   - Estado con color (verde=ACTIVO, rojo=INACTIVO)
   - Contador de cuentas totales

## Diseño y Estilos

### Colores (IDÉNTICOS a TI1.5)

- **Color Principal**: `#22313f` (Azul oscuro - 34, 49, 63)
- **Color Secundario**: `#3498db` (Azul brillante - 52, 152, 219)
- **Color Texto**: `#ecf0f1` (Blanco grisáceo)
- **Fondo**: `#f5f5f5` (Gris claro)
- **Panel Lateral**: `#000000` (Negro)
- **Logout**: `#a02828` (Rojo sutil)
- **Hover**: `#323232` (Gris oscuro)

### Componentes de UI

- **Panel Lateral Izquierdo (Sidebar)**:
  - Logo de EurekaBank
  - Menú de navegación con iconos
  - Botón de cerrar sesión al fondo
  - Fondo negro

- **Panel Principal (Main Content)**:
  - Formularios centrados con borde
  - Inputs con borde inferior azul
  - Botones con color secundario
  - Tablas con cabecera azul

- **Alertas**:
  - Success (verde)
  - Error (rojo)
  - Warning (amarillo)
  - Info (azul)

## Diferencias con TI1.5 (SOAP)

| Aspecto | TI1.5 (SOAP) | TI1.7 (REST) |
|---------|--------------|--------------|
| **Protocolo** | SOAP/XML | REST/JSON |
| **Cliente** | JAX-WS | JAX-RS (Jersey) |
| **Serialización** | XML | JSON |
| **Generación de Código** | wsimport (automático) | Manual |
| **Modelos** | Generados por WSDL | Creados manualmente |
| **Interfaz de Usuario** | **IDÉNTICA** ✅ | **IDÉNTICA** ✅ |
| **Colores** | **IDÉNTICOS** ✅ | **IDÉNTICOS** ✅ |
| **Iconos** | **IDÉNTICOS** ✅ | **IDÉNTICOS** ✅ |
| **Funcionalidades** | Login, CRUD, Movimientos, Balances | Login, CRUD, Movimientos, Balances |

## Estructura de Clases

### EurekaBankClient.java (Servicio)
```java
public class EurekaBankClient {
    private static final String BASE_URI = "http://localhost:8080/eurekabank/api/eureka";
    
    public static boolean login(String username, String password);
    public static int regDeposito(String cuenta, double importe);
    public static int regRetiro(String cuenta, double importe);
    public static int regTransferencia(String cuentaOrigen, String cuentaDestino, double importe);
    public static List<Movimiento> traerMovimientos(String cuenta);
    public static List<Cuenta> traerBalances();
}
```

### Modelos
```java
// Movimiento.java
public class Movimiento {
    private int nromov;
    private String fecha;
    private String tipo;
    private String accion;
    private double importe;
}

// Cuenta.java
public class Cuenta {
    private String numeroCuenta;
    private String nombreCliente;
    private double saldo;
    private String moneda;
    private String estado;
}
```

### Servlets
- **LoginServlet**: Maneja autenticación
- **LogoutServlet**: Cierra sesión
- **DepositoServlet**: Procesa depósitos
- **RetiroServlet**: Procesa retiros
- **TransferenciaServlet**: Procesa transferencias

## Flujo de Navegación

1. **Inicio** → `index.html` → Redirige a `login.jsp`
2. **Login** → Si exitoso → `movimientos.jsp`
3. **Menú Lateral** → Navegación a cualquier página
4. **Operaciones** → Formulario → Servlet → Respuesta
5. **Logout** → Cierra sesión → `login.jsp`

## Validaciones

### Cliente (JavaScript)
- Campos requeridos
- Formato de números
- Confirmación de logout

### Servidor (Java)
- Sesión válida
- Campos no vacíos
- Importes positivos
- Cuentas diferentes en transferencias
- Respuestas del servidor REST

## Manejo de Errores

- **Sin sesión**: Redirige a login
- **Error de red**: Mensaje amigable
- **Error del servidor**: Muestra mensaje de error
- **Datos inválidos**: Validación y mensaje

## Testing

1. **Verificar servidor REST**: `http://localhost:8080/eurekabank/`
2. **Login**: Probar credenciales válidas/inválidas
3. **Operaciones**: Probar cada operación bancaria
4. **Sesión**: Verificar timeout y logout
5. **Balances**: Verificar tabla y formato

## Notas Importantes

✅ **La interfaz es EXACTAMENTE IGUAL a TI1.5 SOAP**
- Mismos colores
- Mismos íconos
- Mismo layout
- Misma funcionalidad
- Mismos mensajes

✅ **Diferencia principal**: Consume REST en lugar de SOAP

✅ **Ventajas del REST**:
- Más ligero (JSON vs XML)
- Más fácil de consumir
- Mejor rendimiento
- Más escalable

## Soporte

Para problemas o dudas:
1. Verificar que el servidor REST esté corriendo
2. Revisar logs de GlassFish/Payara
3. Verificar la configuración de BASE_URI
4. Revisar la consola del navegador (F12)
