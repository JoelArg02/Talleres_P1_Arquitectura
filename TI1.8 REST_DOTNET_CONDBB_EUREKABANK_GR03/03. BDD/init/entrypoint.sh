#!/bin/bash

/opt/mssql/bin/sqlservr &

echo "Esperando a que SQL Server esté listo..."
sleep 50

echo "Creando base de datos eurekabank..."
/opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P YourStrong!Passw0rd -C -Q "CREATE DATABASE eurekabank"

echo "Ejecutando 1_crear_bd.sql..."
/opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P YourStrong!Passw0rd -C -d eurekabank -i /docker-entrypoint-initdb.d/1_crear_bd.sql

echo "Ejecutando 2_cargar_datos.sql..."
/opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P YourStrong!Passw0rd -C -d eurekabank -i /docker-entrypoint-initdb.d/2_cargar_datos.sql

echo "✅ Base de datos eurekabank configurada correctamente"

wait
