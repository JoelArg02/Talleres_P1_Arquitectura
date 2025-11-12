package ec.edu.espe.rest_java_bank.data.repository

import ec.edu.espe.rest_java_bank.data.models.Cuenta
import ec.edu.espe.rest_java_bank.data.models.Movimiento
import ec.edu.espe.rest_java_bank.data.models.OperacionResult
import ec.edu.espe.rest_java_bank.data.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EurekaBankRepository {
    private val apiService = RetrofitClient.apiService
    
    suspend fun login(username: String, password: String): OperacionResult<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(username, password)
                if (response.isSuccessful && response.body() == true) {
                    OperacionResult.Success(true)
                } else {
                    OperacionResult.Error("Credenciales inválidas")
                }
            } catch (e: Exception) {
                OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
    
    suspend fun getMovimientos(cuenta: String): OperacionResult<List<Movimiento>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getMovimientos(cuenta)
                if (response.isSuccessful) {
                    OperacionResult.Success(response.body() ?: emptyList())
                } else {
                    OperacionResult.Error("Error al obtener movimientos")
                }
            } catch (e: Exception) {
                OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
    
    suspend fun getBalances(): OperacionResult<List<Cuenta>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getBalances()
                if (response.isSuccessful) {
                    OperacionResult.Success(response.body() ?: emptyList())
                } else {
                    OperacionResult.Error("Error al obtener balances")
                }
            } catch (e: Exception) {
                OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
    
    suspend fun registrarDeposito(cuenta: String, importe: Double): OperacionResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.registrarDeposito(cuenta, importe)
                if (response.isSuccessful) {
                    val message = response.body()?.string()?.trim()?.removeSurrounding("\"") ?: "Depósito exitoso"
                    when {
                        message.contains("ERROR:", ignoreCase = true) -> OperacionResult.Error(message)
                        message.contains("no existe", ignoreCase = true) -> OperacionResult.Error(message)
                        message.contains("no está activa", ignoreCase = true) -> OperacionResult.Error(message)
                        else -> OperacionResult.Success(message)
                    }
                } else {
                    OperacionResult.Error("Error al registrar depósito")
                }
            } catch (e: Exception) {
                OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }

    suspend fun registrarRetiro(cuenta: String, importe: Double): OperacionResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.registrarRetiro(cuenta, importe)
                if (response.isSuccessful) {
                    val message = response.body()?.string()?.trim()?.removeSurrounding("\"") ?: "Retiro exitoso"
                    when {
                        message.contains("ERROR:", ignoreCase = true) -> OperacionResult.Error(message)
                        message.contains("Saldo insuficiente", ignoreCase = true) -> OperacionResult.Error(message)
                        message.contains("no existe", ignoreCase = true) -> OperacionResult.Error(message)
                        message.contains("no está activa", ignoreCase = true) -> OperacionResult.Error(message)
                        else -> OperacionResult.Success(message)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    OperacionResult.Error(errorBody ?: "Error al registrar retiro")
                }
            } catch (e: Exception) {
                OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }


    suspend fun registrarTransferencia(cuentaOrigen: String, cuentaDestino: String, importe: Double): OperacionResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.registrarTransferencia(cuentaOrigen, cuentaDestino, importe)
                if (response.isSuccessful) {
                    val message = response.body()?.string()?.trim()?.removeSurrounding("\"") ?: "Transferencia exitosa"
                    when {
                        message.contains("ERROR:", ignoreCase = true) -> OperacionResult.Error(message)
                        message.contains("Saldo insuficiente", ignoreCase = true) -> OperacionResult.Error(message)
                        message.contains("no existe", ignoreCase = true) -> OperacionResult.Error(message)
                        message.contains("no está activa", ignoreCase = true) -> OperacionResult.Error(message)
                        else -> OperacionResult.Success(message)
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    OperacionResult.Error(errorBody ?: "Error al registrar transferencia")
                }
            } catch (e: Exception) {
                OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}

