# Usar Ubuntu con repositorios Mono funcionales
FROM ubuntu:22.04

ENV DEBIAN_FRONTEND=noninteractive

# Instalar Mono y XSP
RUN apt-get update && \
    apt-get install -y gnupg ca-certificates wget && \
    apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys 3FA7E0328081BFF6A14DA29AA6A19B38D3D831EF && \
    echo "deb https://download.mono-project.com/repo/ubuntu stable-focal main" | tee /etc/apt/sources.list.d/mono-official-stable.list && \
    apt-get update && \
    apt-get install -y mono-complete mono-xsp4 && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copiar archivos del proyecto
COPY . .

# Compilar el proyecto
RUN msbuild "01. SERVIDOR.csproj" /p:Configuration=Release /p:OutputPath=./bin

# Exponer el puerto 8080
EXPOSE 8080

# Ejecutar la aplicación con XSP
CMD ["xsp4", "--port", "8080", "--applications", "/:.", "--nonstop", "--address", "0.0.0.0"]