package ec.edu.espe.rest_dotnet_bank.data.repository

import ec.edu.espe.rest_dotnet_bank.data.models.Cuenta
import ec.edu.espe.rest_dotnet_bank.data.models.Movimiento
import ec.edu.espe.rest_dotnet_bank.data.models.OperacionResult
import ec.edu.espe.rest_dotnet_bank.data.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EurekaBankRepository {
    private val apiService = RetrofitClient.apiService

    suspend fun login(cuenta: String, clave: String): OperacionResult<Cuenta> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.login(LoginRequest(cuenta, clave))
                if (response.isSuccessful) {
                    response.body()?.let {
                        OperacionResult.Success(it)
                    } ?: OperacionResult.Error("Respuesta vacía del servidor")
                } else {
                    OperacionResult.Error("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }

    suspend fun listarMovimientos(cuenta: String): OperacionResult<List<Movimiento>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.listarMovimientos(cuenta)
                if (response.isSuccessful) {
                    response.body()?.let {
                        OperacionResult.Success(it)
                    } ?: OperacionResult.Error("Respuesta vacía del servidor")
                } else {
                    OperacionResult.Error("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }

    suspend fun obtenerBalances(): OperacionResult<List<Cuenta>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.obtenerBalances()
                if (response.isSuccessful) {
                    response.body()?.let {
                        OperacionResult.Success(it)
                    } ?: OperacionResult.Error("Respuesta vacía del servidor")
                } else {
                    OperacionResult.Error("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }

    suspend fun realizarDeposito(cuenta: String, monto: Double): OperacionResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.realizarDeposito(DepositoRequest(cuenta, monto))
                if (response.isSuccessful) {
                    val body = response.body()?.string()?.trim()?.removeSurrounding("\"") ?: ""
                    
                    // Si el mensaje contiene "Error" o "error", tratarlo como error
                    if (body.contains("Error", ignoreCase = true)) {
                        OperacionResult.Error(body)
                    } else {
                        OperacionResult.Success(body)
                    }
                } else {
                    OperacionResult.Error("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }

    suspend fun realizarRetiro(cuenta: String, monto: Double): OperacionResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.realizarRetiro(RetiroRequest(cuenta, monto))
                if (response.isSuccessful) {
                    val body = response.body()?.string()?.trim()?.removeSurrounding("\"") ?: ""
                    
                    // Si el mensaje contiene "Error" o "error", tratarlo como error
                    if (body.contains("Error", ignoreCase = true)) {
                        OperacionResult.Error(body)
                    } else {
                        OperacionResult.Success(body)
                    }
                } else {
                    OperacionResult.Error("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }

    suspend fun realizarTransferencia(cuentaOrigen: String, cuentaDestino: String, monto: Double): OperacionResult<String> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.realizarTransferencia(
                    TransferenciaRequest(cuentaOrigen, cuentaDestino, monto)
                )
                if (response.isSuccessful) {
                    val body = response.body()?.string()?.trim()?.removeSurrounding("\"") ?: ""
                    
                    // Si el mensaje contiene "Error" o "error", tratarlo como error
                    if (body.contains("Error", ignoreCase = true)) {
                        OperacionResult.Error(body)
                    } else {
                        OperacionResult.Success(body)
                    }
                } else {
                    OperacionResult.Error("Error: ${response.code()} - ${response.message()}")
                }
            } catch (e: Exception) {
                OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
