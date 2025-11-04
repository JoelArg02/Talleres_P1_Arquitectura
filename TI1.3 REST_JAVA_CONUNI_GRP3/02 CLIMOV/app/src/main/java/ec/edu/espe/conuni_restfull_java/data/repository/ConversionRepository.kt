package ec.edu.espe.conuni_restfull_java.data.repository

import ec.edu.espe.conuni_restfull_java.data.api.RetrofitClient
import ec.edu.espe.conuni_restfull_java.data.model.ConUni
import ec.edu.espe.conuni_restfull_java.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ConversionRepository {
    
    private val apiService = RetrofitClient.apiService
    
    /**
     * Realiza el login del usuario
     */
    suspend fun login(user: User): Result<Boolean> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.login(user)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Credenciales incorrectas"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Convierte unidades
     */
    suspend fun convertUnit(conUni: ConUni): Result<Double> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.convertUnit(conUni)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error en la conversión"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Obtiene las unidades soportadas para un tipo
     */
    suspend fun getSupportedUnits(type: String): Result<List<String>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getSupportedUnits(type)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al obtener unidades"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Obtiene los tipos de conversión soportados
     */
    suspend fun getSupportedTypes(): Result<List<String>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getSupportedTypes()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al obtener tipos"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
