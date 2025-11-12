@echo off
echo ======================================
echo Descargando dependencias JAR...
echo ======================================

if not exist "lib" mkdir lib

echo.
echo [1/20] Descargando Jakarta WS RS API 3.1.0...
curl -L -o lib\jakarta.ws.rs-api-3.1.0.jar https://repo1.maven.org/maven2/jakarta/ws/rs/jakarta.ws.rs-api/3.1.0/jakarta.ws.rs-api-3.1.0.jar

echo [2/20] Descargando Jersey Client 3.1.3...
curl -L -o lib\jersey-client-3.1.3.jar https://repo1.maven.org/maven2/org/glassfish/jersey/core/jersey-client/3.1.3/jersey-client-3.1.3.jar

echo [3/20] Descargando Jersey Common 3.1.3...
curl -L -o lib\jersey-common-3.1.3.jar https://repo1.maven.org/maven2/org/glassfish/jersey/core/jersey-common/3.1.3/jersey-common-3.1.3.jar

echo [4/20] Descargando Jersey HK2 3.1.3...
curl -L -o lib\jersey-hk2-3.1.3.jar https://repo1.maven.org/maven2/org/glassfish/jersey/inject/jersey-hk2/3.1.3/jersey-hk2-3.1.3.jar

echo [5/20] Descargando Jersey Media JSON Jackson 3.1.3...
curl -L -o lib\jersey-media-json-jackson-3.1.3.jar https://repo1.maven.org/maven2/org/glassfish/jersey/media/jersey-media-json-jackson/3.1.3/jersey-media-json-jackson-3.1.3.jar

echo [6/20] Descargando Jackson Core 2.15.2...
curl -L -o lib\jackson-core-2.15.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.15.2/jackson-core-2.15.2.jar

echo [7/20] Descargando Jackson Databind 2.15.2...
curl -L -o lib\jackson-databind-2.15.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.15.2/jackson-databind-2.15.2.jar

echo [8/20] Descargando Jackson Annotations 2.15.2...
curl -L -o lib\jackson-annotations-2.15.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.15.2/jackson-annotations-2.15.2.jar

echo [9/20] Descargando Jackson Jakarta RS JSON Provider 2.15.2...
curl -L -o lib\jackson-jakarta-rs-json-provider-2.15.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/jakarta/rs/jackson-jakarta-rs-json-provider/2.15.2/jackson-jakarta-rs-json-provider-2.15.2.jar

echo [10/20] Descargando Jackson Jakarta RS Base 2.15.2...
curl -L -o lib\jackson-jakarta-rs-base-2.15.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/jakarta/rs/jackson-jakarta-rs-base/2.15.2/jackson-jakarta-rs-base-2.15.2.jar

echo [11/20] Descargando Jackson Module Jakarta XMLBind Annotations 2.15.2...
curl -L -o lib\jackson-module-jakarta-xmlbind-annotations-2.15.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/module/jackson-module-jakarta-xmlbind-annotations/2.15.2/jackson-module-jakarta-xmlbind-annotations-2.15.2.jar

echo [12/20] Descargando HK2 API 3.0.4...
curl -L -o lib\hk2-api-3.0.4.jar https://repo1.maven.org/maven2/org/glassfish/hk2/hk2-api/3.0.4/hk2-api-3.0.4.jar

echo [13/20] Descargando HK2 Locator 3.0.4...
curl -L -o lib\hk2-locator-3.0.4.jar https://repo1.maven.org/maven2/org/glassfish/hk2/hk2-locator/3.0.4/hk2-locator-3.0.4.jar

echo [14/20] Descargando HK2 Utils 3.0.4...
curl -L -o lib\hk2-utils-3.0.4.jar https://repo1.maven.org/maven2/org/glassfish/hk2/hk2-utils/3.0.4/hk2-utils-3.0.4.jar

echo [15/20] Descargando Jakarta Inject API 2.0.1...
curl -L -o lib\jakarta.inject-api-2.0.1.jar https://repo1.maven.org/maven2/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar

echo [16/20] Descargando Jakarta Annotation API 2.1.1...
curl -L -o lib\jakarta.annotation-api-2.1.1.jar https://repo1.maven.org/maven2/jakarta/annotation/jakarta.annotation-api/2.1.1/jakarta.annotation-api-2.1.1.jar

echo [17/20] Descargando Jakarta Activation API 2.1.2...
curl -L -o lib\jakarta.activation-api-2.1.2.jar https://repo1.maven.org/maven2/jakarta/activation/jakarta.activation-api/2.1.2/jakarta.activation-api-2.1.2.jar

echo [18/20] Descargando OSGi Resource Locator 1.0.3...
curl -L -o lib\osgi-resource-locator-1.0.3.jar https://repo1.maven.org/maven2/org/glassfish/hk2/osgi-resource-locator/1.0.3/osgi-resource-locator-1.0.3.jar

echo [19/20] Descargando ASM 9.5...
curl -L -o lib\asm-9.5.jar https://repo1.maven.org/maven2/org/ow2/asm/asm/9.5/asm-9.5.jar

echo [20/20] Descargando Javassist 3.29.0-GA...
curl -L -o lib\javassist-3.29.0-GA.jar https://repo1.maven.org/maven2/org/javassist/javassist/3.29.0-GA/javassist-3.29.0-GA.jar

echo.
echo ======================================
echo Descarga completada!
echo ======================================
echo Todas las dependencias se han descargado en la carpeta 'lib'
echo Ahora puedes agregar estos JARs a tu proyecto en NetBeans:
echo   1. Abre el proyecto en NetBeans
echo   2. Click derecho en Libraries ^> Add JAR/Folder
echo   3. Selecciona todos los archivos de la carpeta 'lib'
echo ======================================
pause
