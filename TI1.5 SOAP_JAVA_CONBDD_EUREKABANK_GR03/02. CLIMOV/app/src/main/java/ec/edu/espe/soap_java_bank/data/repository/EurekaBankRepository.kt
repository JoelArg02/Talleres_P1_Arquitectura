package ec.edu.espe.soap_java_bank.data.repository

import ec.edu.espe.soap_java_bank.data.models.Cuenta
import ec.edu.espe.soap_java_bank.data.models.Movimiento
import ec.edu.espe.soap_java_bank.data.remote.services.AuthService
import ec.edu.espe.soap_java_bank.data.remote.services.DepositoService
import ec.edu.espe.soap_java_bank.data.remote.services.RetiroService
import ec.edu.espe.soap_java_bank.data.remote.services.TransferenciaService
import ec.edu.espe.soap_java_bank.data.remote.services.MovimientosService
import ec.edu.espe.soap_java_bank.data.remote.services.BalancesService

class EurekaBankRepository {
    
    private val authService = AuthService()
    private val depositoService = DepositoService()
    private val retiroService = RetiroService()
    private val transferenciaService = TransferenciaService()
    private val movimientosService = MovimientosService()
    private val balancesService = BalancesService()
    
    suspend fun login(username: String, password: String): Boolean {
        return authService.login(username, password)
    }
    
    suspend fun realizarDeposito(cuenta: String, importe: Double): Int {
        return depositoService.registrarDeposito(cuenta, importe)
    }
    
    suspend fun realizarRetiro(cuenta: String, importe: Double): Int {
        return retiroService.registrarRetiro(cuenta, importe)
    }
    
    suspend fun realizarTransferencia(cuentaOrigen: String, cuentaDestino: String, importe: Double): Int {
        return transferenciaService.registrarTransferencia(cuentaOrigen, cuentaDestino, importe)
    }
    
    suspend fun obtenerMovimientos(cuenta: String): List<Movimiento> {
        val data = movimientosService.obtenerMovimientos(cuenta)
        return data.map { 
            Movimiento(
                nromov = it["nromov"] ?: "0",
                cuenta = it["cuenta"] ?: "",
                tipo = it["tipo"] ?: "",
                accion = it["accion"] ?: "",
                importe = it["importe"]?.toDoubleOrNull() ?: 0.0,
                fecha = it["fecha"] ?: ""
            )
        }
    }
    
    suspend fun obtenerBalances(): List<Cuenta> {
        val data = balancesService.obtenerBalances()
        return data.map {
            Cuenta(
                numeroCuenta = it["numeroCuenta"] ?: "",
                nombreCliente = it["nombreCliente"] ?: "",
                saldo = it["saldo"]?.toDoubleOrNull() ?: 0.0,
                estado = it["estado"] ?: ""
            )
        }
    }
}
