package ec.edu.espe.conv_rtfull.data.api

import ec.edu.espe.conv_rtfull.data.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Interfaz de la API REST para conversión de unidades
 */
interface ConversionApi {
    
    @POST("api/Length/convert")
    suspend fun convertLength(@Body request: LengthConversionRequest): Response<ConversionResponse>
    
    @POST("api/Weight/convert")
    suspend fun convertWeight(@Body request: WeightConversionRequest): Response<ConversionResponse>
    
    @POST("api/Temperature/convert")
    suspend fun convertTemperature(@Body request: TemperatureConversionRequest): Response<ConversionResponse>
}
