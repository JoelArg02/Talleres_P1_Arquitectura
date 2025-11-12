package ec.edu.espe.rest_java_bank.data.remote

import ec.edu.espe.rest_java_bank.data.models.Cuenta
import ec.edu.espe.rest_java_bank.data.models.Movimiento
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface EurekaBankApiService {
    
    @POST("login")
    suspend fun login(
        @Query("username") username: String,
        @Query("password") password: String
    ): Response<Boolean>
    
    @GET("movimientos/{cuenta}")
    suspend fun getMovimientos(
        @Path("cuenta") cuenta: String
    ): Response<List<Movimiento>>
    
    @GET("balances")
    suspend fun getBalances(): Response<List<Cuenta>>
    
    @POST("deposito")
    suspend fun registrarDeposito(
        @Query("cuenta") cuenta: String,
        @Query("importe") importe: Double
    ): Response<ResponseBody>
    
    @POST("retiro")
    suspend fun registrarRetiro(
        @Query("cuenta") cuenta: String,
        @Query("importe") importe: Double
    ): Response<ResponseBody>
    
    @POST("transferencia")
    suspend fun registrarTransferencia(
        @Query("cuentaOrigen") cuentaOrigen: String,
        @Query("cuentaDestino") cuentaDestino: String,
        @Query("importe") importe: Double
    ): Response<ResponseBody>
}
