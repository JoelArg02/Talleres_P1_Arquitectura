@echo off
REM Script para descargar todas las dependencias necesarias para el cliente REST

echo Descargando dependencias de Jakarta y Jersey...
echo.

set LIB_DIR=lib
if not exist %LIB_DIR% mkdir %LIB_DIR%

echo [1/10] Descargando jakarta.ws.rs-api...
curl -L -o "%LIB_DIR%/jakarta.ws.rs-api-3.1.0.jar" "https://repo1.maven.org/maven2/jakarta/ws/rs/jakarta.ws.rs-api/3.1.0/jakarta.ws.rs-api-3.1.0.jar"

echo [2/10] Descargando jersey-client...
curl -L -o "%LIB_DIR%/jersey-client-3.1.3.jar" "https://repo1.maven.org/maven2/org/glassfish/jersey/core/jersey-client/3.1.3/jersey-client-3.1.3.jar"

echo [3/10] Descargando jersey-common...
curl -L -o "%LIB_DIR%/jersey-common-3.1.3.jar" "https://repo1.maven.org/maven2/org/glassfish/jersey/core/jersey-common/3.1.3/jersey-common-3.1.3.jar"

echo [4/10] Descargando jersey-hk2...
curl -L -o "%LIB_DIR%/jersey-hk2-3.1.3.jar" "https://repo1.maven.org/maven2/org/glassfish/jersey/inject/jersey-hk2/3.1.3/jersey-hk2-3.1.3.jar"

echo [5/10] Descargando jersey-media-json-jackson...
curl -L -o "%LIB_DIR%/jersey-media-json-jackson-3.1.3.jar" "https://repo1.maven.org/maven2/org/glassfish/jersey/media/jersey-media-json-jackson/3.1.3/jersey-media-json-jackson-3.1.3.jar"

echo [6/10] Descargando jackson-databind...
curl -L -o "%LIB_DIR%/jackson-databind-2.15.2.jar" "https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.15.2/jackson-databind-2.15.2.jar"

echo [7/10] Descargando jackson-core...
curl -L -o "%LIB_DIR%/jackson-core-2.15.2.jar" "https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.15.2/jackson-core-2.15.2.jar"

echo [8/10] Descargando jackson-annotations...
curl -L -o "%LIB_DIR%/jackson-annotations-2.15.2.jar" "https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.15.2/jackson-annotations-2.15.2.jar"

echo [9/10] Descargando hk2-api...
curl -L -o "%LIB_DIR%/hk2-api-3.0.4.jar" "https://repo1.maven.org/maven2/org/glassfish/hk2/hk2-api/3.0.4/hk2-api-3.0.4.jar"

echo [10/10] Descargando hk2-locator...
curl -L -o "%LIB_DIR%/hk2-locator-3.0.4.jar" "https://repo1.maven.org/maven2/org/glassfish/hk2/hk2-locator/3.0.4/hk2-locator-3.0.4.jar"

echo.
echo ============================================
echo Descarga completada!
echo Los JARs estan en el directorio: %LIB_DIR%
echo ============================================
echo.
echo Ahora puedes:
echo 1. Abrir NetBeans
echo 2. Ir a Properties del proyecto
echo 3. En Libraries, Add JAR/Folder
echo 4. Seleccionar todos los JARs del directorio lib
echo.
pause
