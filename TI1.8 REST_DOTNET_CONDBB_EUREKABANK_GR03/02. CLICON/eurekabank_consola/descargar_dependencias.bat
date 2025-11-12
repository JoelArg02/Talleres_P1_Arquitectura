@echo off
echo ========================================
echo Descargando dependencias JAR para TI1.8 CLICON
echo ========================================

REM Crear carpeta lib si no existe
if not exist "lib" mkdir lib
cd lib

echo.
echo Descargando Jakarta REST API...
curl -L -o jakarta.ws.rs-api-3.1.0.jar https://repo1.maven.org/maven2/jakarta/ws/rs/jakarta.ws.rs-api/3.1.0/jakarta.ws.rs-api-3.1.0.jar

echo.
echo Descargando Jersey Client...
curl -L -o jersey-client-3.1.3.jar https://repo1.maven.org/maven2/org/glassfish/jersey/core/jersey-client/3.1.3/jersey-client-3.1.3.jar

echo.
echo Descargando Jersey Common...
curl -L -o jersey-common-3.1.3.jar https://repo1.maven.org/maven2/org/glassfish/jersey/core/jersey-common/3.1.3/jersey-common-3.1.3.jar

echo.
echo Descargando Jersey HK2...
curl -L -o jersey-hk2-3.1.3.jar https://repo1.maven.org/maven2/org/glassfish/jersey/inject/jersey-hk2/3.1.3/jersey-hk2-3.1.3.jar

echo.
echo Descargando Jersey Media JSON Jackson...
curl -L -o jersey-media-json-jackson-3.1.3.jar https://repo1.maven.org/maven2/org/glassfish/jersey/media/jersey-media-json-jackson/3.1.3/jersey-media-json-jackson-3.1.3.jar

echo.
echo Descargando Jackson Core...
curl -L -o jackson-core-2.15.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-core/2.15.2/jackson-core-2.15.2.jar

echo.
echo Descargando Jackson Databind...
curl -L -o jackson-databind-2.15.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.15.2/jackson-databind-2.15.2.jar

echo.
echo Descargando Jackson Annotations...
curl -L -o jackson-annotations-2.15.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.15.2/jackson-annotations-2.15.2.jar

echo.
echo Descargando Jackson Jakarta RS JSON Provider...
curl -L -o jackson-jakarta-rs-json-provider-2.15.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/jakarta/rs/jackson-jakarta-rs-json-provider/2.15.2/jackson-jakarta-rs-json-provider-2.15.2.jar

echo.
echo Descargando Jackson Jakarta RS Base...
curl -L -o jackson-jakarta-rs-base-2.15.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/jakarta/rs/jackson-jakarta-rs-base/2.15.2/jackson-jakarta-rs-base-2.15.2.jar

echo.
echo Descargando Jackson Module Jakarta XMLBIND Annotations...
curl -L -o jackson-module-jakarta-xmlbind-annotations-2.15.2.jar https://repo1.maven.org/maven2/com/fasterxml/jackson/module/jackson-module-jakarta-xmlbind-annotations/2.15.2/jackson-module-jakarta-xmlbind-annotations-2.15.2.jar

echo.
echo Descargando HK2 API...
curl -L -o hk2-api-3.0.4.jar https://repo1.maven.org/maven2/org/glassfish/hk2/hk2-api/3.0.4/hk2-api-3.0.4.jar

echo.
echo Descargando HK2 Locator...
curl -L -o hk2-locator-3.0.4.jar https://repo1.maven.org/maven2/org/glassfish/hk2/hk2-locator/3.0.4/hk2-locator-3.0.4.jar

echo.
echo Descargando HK2 Utils...
curl -L -o hk2-utils-3.0.4.jar https://repo1.maven.org/maven2/org/glassfish/hk2/hk2-utils/3.0.4/hk2-utils-3.0.4.jar

echo.
echo Descargando Jakarta Inject API...
curl -L -o jakarta.inject-api-2.0.1.jar https://repo1.maven.org/maven2/jakarta/inject/jakarta.inject-api/2.0.1/jakarta.inject-api-2.0.1.jar

echo.
echo Descargando Jakarta Annotation API...
curl -L -o jakarta.annotation-api-2.1.1.jar https://repo1.maven.org/maven2/jakarta/annotation/jakarta.annotation-api/2.1.1/jakarta.annotation-api-2.1.1.jar

echo.
echo Descargando Jakarta Activation API...
curl -L -o jakarta.activation-api-2.1.2.jar https://repo1.maven.org/maven2/jakarta/activation/jakarta.activation-api/2.1.2/jakarta.activation-api-2.1.2.jar

echo.
echo Descargando OSGi Resource Locator...
curl -L -o osgi-resource-locator-1.0.3.jar https://repo1.maven.org/maven2/org/glassfish/hk2/osgi-resource-locator/1.0.3/osgi-resource-locator-1.0.3.jar

echo.
echo Descargando ASM...
curl -L -o asm-9.5.jar https://repo1.maven.org/maven2/org/ow2/asm/asm/9.5/asm-9.5.jar

echo.
echo Descargando Javassist...
curl -L -o javassist-3.29.0-GA.jar https://repo1.maven.org/maven2/org/javassist/javassist/3.29.0-GA/javassist-3.29.0-GA.jar

cd ..

echo.
echo ========================================
echo Descarga completada!
echo ========================================
echo Todos los JARs estan en la carpeta "lib"
echo.
pause
