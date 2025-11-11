# CLIWEB - EurekaBank Web Client

Cliente web para el sistema bancario EurekaBank que consume servicios SOAP.

## 🏗️ Arquitectura

Este proyecto es un cliente web desarrollado con:
- **Jakarta EE 10** (Servlets, JSP, JSTL)
- **JAX-WS** para consumo de servicios SOAP
- **Maven** para gestión de dependencias
- **CSS3 y JavaScript** para la interfaz de usuario

## 📋 Prerrequisitos

1. **JDK 11** o superior
2. **Maven 3.6+**
3. **Servidor de aplicaciones compatible con Jakarta EE 10**:
   - GlassFish 7.x (recomendado)
   - WildFly 27+
   - TomEE 9.x
4. **Servidor SOAP en ejecución** (01. SERVER debe estar corriendo)

## 🚀 Instalación y Configuración

### Paso 1: Copiar las Imágenes

Copie todas las imágenes desde el cliente escritorio:

```bash
# Desde la carpeta raíz del proyecto
cd "02. CLIWEB\CLIWEB_EurekaBank_SOAPJAVA\src\main\webapp\images"

# En Windows
copy "..\..\..\..\..\..\02. CLIESC\eurekabank_escritorio\src\ec\edu\gr03\img\*.*" .

# En Linux/Mac
cp ../../../../../../"02. CLIESC"/eurekabank_escritorio/src/ec/edu/gr03/img/* .
```

O copie manualmente estos archivos:
- logo.png
- icon_movimientos.png
- icon_retiro.png
- icon_deposito.png
- icon_transferencia.png
- icon_balances.png
- icon_logout.png

### Paso 2: Configurar la URL del WSDL

Edite el archivo `pom.xml` y verifique la URL del WSDL (línea 69):

```xml
<wsdlUrl>http://localhost:8080/eurekabank/WSEureka?WSDL</wsdlUrl>
```

Ajuste el puerto y contexto si su servidor usa configuración diferente.

### Paso 3: Generar las Clases del Cliente SOAP

```bash
# Desde la carpeta del proyecto CLIWEB
cd "02. CLIWEB\CLIWEB_EurekaBank_SOAPJAVA"

# Compilar y generar las clases SOAP
mvn clean compile
```

Esto generará las clases cliente en `target/generated-sources/jaxws/ec/edu/gr03/ws/`

### Paso 4: Compilar el Proyecto

```bash
mvn clean package
```

Esto creará el archivo WAR en `target/eurekabank-web.war`

## 📦 Despliegue

### GlassFish

1. Inicie GlassFish:
   ```bash
   asadmin start-domain
   ```

2. Despliegue el WAR:
   ```bash
   asadmin deploy target/eurekabank-web.war
   ```

3. O copie manualmente el WAR a `glassfish/domains/domain1/autodeploy/`

### Otros Servidores

- **WildFly**: Copie el WAR a `standalone/deployments/`
- **TomEE**: Copie el WAR a `webapps/`

## 🌐 Acceso a la Aplicación

Una vez desplegado, acceda a:

```
http://localhost:8080/eurekabank-web/
```

O el puerto que use su servidor de aplicaciones.

## 👤 Credenciales de Prueba

Las credenciales dependen de la base de datos configurada en el servidor. Consulte:
- `03. BDD/init/2_cargar_datos.sql` para ver usuarios de prueba

## 🎨 Funcionalidades

El cliente web replica EXACTAMENTE las funcionalidades del cliente escritorio:

1. **Login** - Autenticación de usuarios
2. **Movimientos** - Consulta de transacciones por cuenta
3. **Depósito** - Registro de depósitos
4. **Retiro** - Registro de retiros
5. **Transferencia** - Transferencias entre cuentas
6. **Balances** - Visualización de todas las cuentas y saldos

## 🎭 Diseño

La interfaz usa los mismos colores del cliente escritorio:
- **Negro** (#000000) - Panel lateral
- **Azul brillante** (#3498db) - Elementos activos y botones
- **Gris claro** (#f5f5f5) - Fondo principal
- **Rojo sutil** (#a02828) - Botón de cerrar sesión

## 🔧 Solución de Problemas

### Error al generar clases SOAP

**Problema**: `mvn compile` falla al generar las clases

**Solución**:
1. Verifique que el servidor SOAP esté corriendo
2. Pruebe el WSDL en el navegador: `http://localhost:8080/eurekabank/WSEureka?WSDL`
3. Ajuste la URL en `pom.xml` si es necesario

### Error 404 al acceder

**Problema**: La página no se encuentra

**Solución**:
1. Verifique que el WAR esté desplegado correctamente
2. Revise los logs del servidor
3. Acceda a `http://localhost:8080/eurekabank-web/login.jsp` directamente

### Error de conexión SOAP

**Problema**: "Error al procesar operación"

**Solución**:
1. Verifique que el servidor SOAP esté corriendo
2. Revise que la base de datos esté activa
3. Compruebe los logs del servidor en `01. SERVER`

## 📁 Estructura del Proyecto

```
CLIWEB_EurekaBank_SOAPJAVA/
├── src/
│   └── main/
│       ├── java/
│       │   └── ec/edu/gr03/
│       │       ├── controller/      # Servlets
│       │       └── service/         # Cliente SOAP
│       └── webapp/
│           ├── css/                 # Estilos
│           ├── js/                  # JavaScript
│           ├── images/              # Imágenes e íconos
│           ├── WEB-INF/
│           │   ├── navbar.jsp       # Menú de navegación
│           │   └── web.xml          # Configuración
│           ├── login.jsp            # Página de login
│           ├── movimientos.jsp      # Consulta movimientos
│           ├── deposito.jsp         # Realizar depósitos
│           ├── retiro.jsp           # Realizar retiros
│           ├── transferencia.jsp    # Transferencias
│           ├── balances.jsp         # Ver balances
│           ├── index.jsp            # Página inicial
│           └── error.jsp            # Página de error
└── pom.xml                          # Configuración Maven
```

## 🔐 Seguridad

- Las páginas verifican la sesión del usuario
- Timeout de sesión: 30 minutos
- Validaciones en cliente (JavaScript) y servidor (Servlets)

## 👥 Autores

Grupo 03 - Arquitectura de Aplicaciones

## 📝 Notas Adicionales

- El diseño es completamente responsive
- Incluye validaciones de formularios
- Manejo de errores consistente
- Mensajes de éxito/error visibles

## 🆘 Soporte

Para problemas o preguntas:
1. Revise los logs del servidor
2. Verifique la consola del navegador (F12)
3. Consulte la documentación del servidor SOAP
