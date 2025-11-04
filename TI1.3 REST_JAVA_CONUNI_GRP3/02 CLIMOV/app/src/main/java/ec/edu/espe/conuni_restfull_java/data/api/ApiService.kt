package ec.edu.espe.conuni_restfull_java.data.api

import ec.edu.espe.conuni_restfull_java.data.model.ConUni
import ec.edu.espe.conuni_restfull_java.data.model.User
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    
    // Login
    @POST("Auth")
    suspend fun login(@Body user: User): Response<Boolean>
    
    // Conversión de unidades
    @POST("ConUni/convertUnit")
    suspend fun convertUnit(@Body conUni: ConUni): Response<Double>
    
    // Obtener unidades soportadas por tipo
    @GET("ConUni/supportedUnits/{type}")
    suspend fun getSupportedUnits(@Path("type") type: String): Response<List<String>>
    
    // Obtener tipos soportados
    @GET("ConUni/supportedTypes")
    suspend fun getSupportedTypes(): Response<List<String>>
}
