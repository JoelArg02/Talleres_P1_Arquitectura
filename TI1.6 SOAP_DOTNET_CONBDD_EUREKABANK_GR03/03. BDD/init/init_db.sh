#!/bin/bash
echo "Esperando a que SQL Server se inicie..."
sleep 45
echo "Creando base de datos eurekabank..."
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P "YourStrong!Passw0rd" -Q "CREATE DATABASE eurekabank"
echo "Ejecutando script 1_crear_bd.sql..."
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P "YourStrong!Passw0rd" -d eurekabank -i /docker-entrypoint-initdb.d/1_crear_bd.sql
echo "Ejecutando script 2_cargar_datos.sql..."
/opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P "YourStrong!Passw0rd" -d eurekabank -i /docker-entrypoint-initdb.d/2_cargar_datos.sql
echo "Base de datos configurada exitosamente"
