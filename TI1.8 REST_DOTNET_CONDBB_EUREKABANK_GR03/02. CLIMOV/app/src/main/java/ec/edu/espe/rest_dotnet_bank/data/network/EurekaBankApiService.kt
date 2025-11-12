package ec.edu.espe.rest_dotnet_bank.data.network

import ec.edu.espe.rest_dotnet_bank.data.models.Cuenta
import ec.edu.espe.rest_dotnet_bank.data.models.Movimiento
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface EurekaBankApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<Cuenta>

    @GET("listar")
    suspend fun listarMovimientos(@Query("cuenta") cuenta: String): Response<List<Movimiento>>

    @GET("balances")
    suspend fun obtenerBalances(): Response<List<Cuenta>>

    @POST("deposito")
    suspend fun realizarDeposito(@Body request: DepositoRequest): Response<ResponseBody>

    @POST("retiro")
    suspend fun realizarRetiro(@Body request: RetiroRequest): Response<ResponseBody>

    @POST("transferencia")
    suspend fun realizarTransferencia(@Body request: TransferenciaRequest): Response<ResponseBody>
}

// Request models que coinciden con la API .NET
data class LoginRequest(
    val username: String,
    val password: String
)

data class DepositoRequest(
    val cuenta: String,
    val importe: Double
)

data class RetiroRequest(
    val cuenta: String,
    val importe: Double
)

data class TransferenciaRequest(
    val cuentaOrigen: String,
    val cuentaDestino: String,
    val importe: Double
)
