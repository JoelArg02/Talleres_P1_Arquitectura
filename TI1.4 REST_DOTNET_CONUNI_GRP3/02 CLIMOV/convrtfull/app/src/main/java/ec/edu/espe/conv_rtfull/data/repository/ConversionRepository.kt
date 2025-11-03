package ec.edu.espe.conv_rtfull.data.repository

import ec.edu.espe.conv_rtfull.data.api.RetrofitClient
import ec.edu.espe.conv_rtfull.data.dto.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Repositorio para manejar las operaciones de conversión
 */
class ConversionRepository {
    
    private val api = RetrofitClient.api
    
    /**
     * Convierte unidades de longitud
     */
    suspend fun convertLength(value: Double, fromUnit: Int, toUnit: Int): Result<ConversionResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = LengthConversionRequest(value, fromUnit, toUnit)
                val response = api.convertLength(request)
                
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    /**
     * Convierte unidades de peso
     */
    suspend fun convertWeight(value: Double, fromUnit: Int, toUnit: Int): Result<ConversionResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = WeightConversionRequest(value, fromUnit, toUnit)
                val response = api.convertWeight(request)
                
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    /**
     * Convierte unidades de temperatura
     */
    suspend fun convertTemperature(value: Double, fromUnit: Int, toUnit: Int): Result<ConversionResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = TemperatureConversionRequest(value, fromUnit, toUnit)
                val response = api.convertTemperature(request)
                
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    /**
     * Convierte unidades de volumen
     */
    suspend fun convertVolume(value: Double, fromUnit: Int, toUnit: Int): Result<ConversionResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val request = VolumeConversionRequest(value, fromUnit, toUnit)
                val response = api.convertVolume(request)
                
                if (response.isSuccessful && response.body() != null) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error: ${response.code()} - ${response.message()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}
