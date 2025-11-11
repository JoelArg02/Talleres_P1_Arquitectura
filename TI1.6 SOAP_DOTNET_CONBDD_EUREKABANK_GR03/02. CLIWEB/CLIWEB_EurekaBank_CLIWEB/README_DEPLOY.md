# Cliente Web EurekaBank - SOAP DOTNET

Este es el cliente web que consume el servicio SOAP DOTNET de EurekaBank.

## Requisitos Previos

1. **Servidor SOAP DOTNET** debe estar corriendo en `http://localhost:55325/`
2. **Java 11** o superior instalado
3. **Maven** instalado
4. **Apache Tomcat 10** o servidor compatible con Jakarta EE 10

## Configuración del Proyecto

### 1. Generar las clases del WSDL

Antes de ejecutar el proyecto, debe generar las clases Java desde el WSDL del servicio DOTNET:

```bash
cd "d:\ReposGitHub\Talleres_P1_Arquitectura\TI1.6 SOAP_DOTNET_CONBDD_EUREKABANK_GR03\02. CLIWEB\CLIWEB_EurekaBank_CLIWEB"
mvn clean compile
```

Este comando:
- Limpiará el proyecto
- Descargará el WSDL desde: `http://localhost:55325/ec.edu.monster.controlador/MovimientoController.svc?wsdl`
- Generará las clases Java en el paquete `ec.edu.gr03.ws`
- Compilará todo el proyecto

### 2. Empaquetar el proyecto

Para crear el archivo WAR:

```bash
mvn clean package
```

El archivo WAR se generará en: `target/CLIWEB_EurekaBank_CLIWEB-SNAPSHOT1.war`

### 3. Desplegar en Tomcat

**Opción A - Usando Maven (si tienes el plugin configurado):**
```bash
mvn tomcat10:deploy
```

**Opción B - Manual:**
1. Copiar el archivo WAR generado al directorio `webapps` de Tomcat
2. Iniciar Tomcat
3. El contexto será: `http://localhost:8080/CLIWEB_EurekaBank_CLIWEB-SNAPSHOT1/`

## Estructura del Proyecto

```
CLIWEB_EurekaBank_CLIWEB/
├── src/
│   └── main/
│       ├── java/
│       │   └── ec/edu/gr03/
│       │       ├── controller/          # Servlets
│       │       │   ├── LoginServlet.java
│       │       │   ├── LogoutServlet.java
│       │       │   ├── DepositoServlet.java
│       │       │   ├── RetiroServlet.java
│       │       │   └── TransferenciaServlet.java
│       │       └── service/
│       │           └── EurekaBankClient.java  # Cliente SOAP
│       └── webapp/
│           ├── css/
│           │   └── styles.css          # Estilos personalizados
│           ├── images/                 # Íconos e imágenes
│           ├── js/
│           │   └── scripts.js          # Scripts JavaScript
│           ├── WEB-INF/
│           │   ├── navbar.jsp          # Barra de navegación
│           │   └── web.xml             # Configuración web
│           ├── login.jsp               # Página de login
│           ├── index.jsp               # Página principal
│           ├── movimientos.jsp         # Ver movimientos
│           ├── deposito.jsp            # Realizar depósitos
│           ├── retiro.jsp              # Realizar retiros
│           ├── transferencia.jsp       # Realizar transferencias
│           ├── balances.jsp            # Ver balances
│           └── error.jsp               # Página de errores
└── pom.xml                             # Configuración Maven
```

## Funcionalidades

### 1. Login
- **URL**: `/login.jsp`
- **Usuarios de prueba**:
  - Usuario: `cromero` / Contraseña: `chicho`
  - Usuario: `lcastro` / Contraseña: `flaca`
  - Usuario: `internet` / Contraseña: `internet`

### 2. Operaciones Bancarias
- **Ver Movimientos**: Consultar todos los movimientos de una cuenta
- **Depósito**: Registrar un depósito en una cuenta
- **Retiro**: Registrar un retiro de una cuenta
- **Transferencia**: Transferir dinero entre dos cuentas
- **Ver Balances**: Consultar los balances de todas las cuentas activas

## Interfaz de Usuario

La interfaz web replica **EXACTAMENTE** la misma apariencia y funcionalidad del cliente web Java:
- Mismos colores y estilos CSS
- Mismas imágenes e íconos
- Misma distribución de elementos
- Misma experiencia de usuario

## Consumo del Servicio SOAP

El cliente web consume el servicio SOAP DOTNET a través de la clase `EurekaBankClient.java`:

```java
// Métodos disponibles:
- login(username, password)          // Autenticación
- regDeposito(cuenta, importe)       // Registrar depósito → llama a registrarDeposito()
- regRetiro(cuenta, importe)         // Registrar retiro → llama a registrarRetiro()
- regTransferencia(origen, destino, importe) // Registrar transferencia → llama a registrarTransferencia()
- traerMovimientos(cuenta)           // Obtener movimientos → llama a obtenerPorCuenta()
- traerBalances()                    // Obtener balances → llama a traerBalances()
```

**Nota**: Los nombres de los métodos del servicio DOTNET siguen la convención PascalCase:
- `RegistrarDeposito` (C#) → `registrarDeposito` (Java generado)
- `RegistrarRetiro` (C#) → `registrarRetiro` (Java generado)
- `RegistrarTransferencia` (C#) → `registrarTransferencia` (Java generado)
- `ObtenerPorCuenta` (C#) → `obtenerPorCuenta` (Java generado)
- `TraerBalances` (C#) → `traerBalances` (Java generado)

## Configuración del Servicio

El servicio SOAP se consume desde:
- **WSDL**: `http://localhost:55325/ec.edu.monster.controlador/MovimientoController.svc?wsdl`
- **Endpoint**: `http://localhost:55325/ec.edu.monster.controlador/MovimientoController.svc`

Esta configuración está en el `pom.xml` en la sección del plugin `jaxws-maven-plugin`.

## Solución de Problemas

### Error: "No se puede conectar al servicio"
- Verifique que el servidor SOAP DOTNET esté corriendo
- Verifique que la URL del WSDL sea accesible

### Error: "Clases del paquete ec.edu.gr03.ws no encontradas"
- Ejecute `mvn clean compile` para generar las clases desde el WSDL

### Error de compilación
- Asegúrese de tener Java 11 o superior
- Verifique que Maven esté correctamente instalado

## Notas Importantes

1. **El servidor DOTNET DEBE estar corriendo** antes de compilar el proyecto, ya que Maven necesita acceder al WSDL
2. Las clases generadas automáticamente están en `target/generated-sources/wsimport/`
3. Si cambia el servicio DOTNET, debe regenerar las clases con `mvn clean compile`
4. La sesión HTTP maneja la autenticación del usuario

## Desarrollo

Para desarrollo con hot-reload, puede usar:
```bash
mvn tomcat10:run
```

Esto iniciará un servidor Tomcat embebido en `http://localhost:8080/`
