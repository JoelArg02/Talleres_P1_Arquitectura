package ec.edu.espe.soap_dotnet_bank.data.repository

import ec.edu.espe.soap_dotnet_bank.data.models.Cuenta
import ec.edu.espe.soap_dotnet_bank.data.models.Movimiento
import ec.edu.espe.soap_dotnet_bank.data.models.OperacionResultData
import ec.edu.espe.soap_dotnet_bank.data.remote.services.AuthService
import ec.edu.espe.soap_dotnet_bank.data.remote.services.BalancesService
import ec.edu.espe.soap_dotnet_bank.data.remote.services.DepositoService
import ec.edu.espe.soap_dotnet_bank.data.remote.services.MovimientosService
import ec.edu.espe.soap_dotnet_bank.data.remote.services.RetiroService
import ec.edu.espe.soap_dotnet_bank.data.remote.services.TransferenciaService

class EurekaBankRepository {
    
    private val authService = AuthService()
    private val balancesService = BalancesService()
    private val movimientosService = MovimientosService()
    private val depositoService = DepositoService()
    private val retiroService = RetiroService()
    private val transferenciaService = TransferenciaService()
    
    suspend fun login(usuario: String, clave: String): Boolean {
        return authService.login(usuario, clave)
    }
    
    suspend fun traerBalances(): List<Cuenta> {
        return balancesService.traerBalances()
    }
    
    suspend fun obtenerMovimientos(cuenta: String): List<Movimiento> {
        return movimientosService.obtenerPorCuenta(cuenta)
    }
    
    suspend fun registrarDeposito(cuenta: String, importe: Double): OperacionResultData {
        return depositoService.registrarDeposito(cuenta, importe)
    }
    
    suspend fun registrarRetiro(cuenta: String, importe: Double): OperacionResultData {
        return retiroService.registrarRetiro(cuenta, importe)
    }
    
    suspend fun registrarTransferencia(cuentaOrigen: String, cuentaDestino: String, importe: Double): OperacionResultData {
        return transferenciaService.registrarTransferencia(cuentaOrigen, cuentaDestino, importe)
    }
}
