# Script para ejecutar el servidor REST en todas las interfaces
# Debe ejecutarse como ADMINISTRADOR

# Obtener la IP local actual
$localIP = (Get-NetIPAddress -AddressFamily IPv4 | Where-Object {$_.InterfaceAlias -notlike "*Loopback*" -and $_.IPAddress -notlike "169.254.*"} | Select-Object -First 1).IPAddress

# Puerto para este servidor REST (diferente al SOAP)
$port = 55326

Write-Host "======================================" -ForegroundColor Cyan
Write-Host "Iniciando servidor REST..." -ForegroundColor Green
Write-Host "======================================" -ForegroundColor Cyan
Write-Host "Puerto: $port" -ForegroundColor Yellow
Write-Host "IP Local: $localIP" -ForegroundColor Yellow
Write-Host "" -ForegroundColor Yellow
Write-Host "El servidor estará disponible en:" -ForegroundColor Green
Write-Host "  - http://localhost:${port}/" -ForegroundColor White
Write-Host "  - http://${localIP}:${port}/" -ForegroundColor White
Write-Host "" -ForegroundColor Yellow
Write-Host "Presiona 'Q' para detener el servidor" -ForegroundColor Cyan
Write-Host "======================================" -ForegroundColor Cyan
Write-Host ""

# Verificar y agregar reserva de URL si no existe
$urlReservation = netsh http show urlacl | Select-String "$localIP:$port"
if (-not $urlReservation) {
    Write-Host "Agregando reserva de URL para $localIP..." -ForegroundColor Yellow
    netsh http add urlacl url="http://${localIP}:${port}/" user=everyone
}

# Ejecutar IIS Express
$projectPath = Split-Path -Parent $MyInvocation.MyCommand.Path
& "C:\Program Files\IIS Express\iisexpress.exe" /path:"$projectPath" /port:$port /hostname:$localIP
