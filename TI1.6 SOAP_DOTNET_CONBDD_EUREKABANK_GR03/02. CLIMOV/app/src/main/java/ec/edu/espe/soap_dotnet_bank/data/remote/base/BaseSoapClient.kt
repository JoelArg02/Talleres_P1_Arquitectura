package ec.edu.espe.soap_dotnet_bank.data.remote.base

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

open class BaseSoapClient {
    
    protected val baseUrl = "http://10.0.2.2:8080/ec.edu.monster.controlador/MovimientoController.svc"
    
    protected val client: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    protected suspend fun executeRequest(soapAction: String, soapEnvelope: String): String {
        val mediaType = "text/xml; charset=utf-8".toMediaType()
        val requestBody = soapEnvelope.toRequestBody(mediaType)
        
        val request = Request.Builder()
            .url(baseUrl)
            .post(requestBody)
            .addHeader("Content-Type", "text/xml; charset=utf-8")
            .addHeader("SOAPAction", "http://tempuri.org/IMovimientoController/$soapAction")
            .build()
        
        val response = client.newCall(request).execute()
        
        if (!response.isSuccessful) {
            throw Exception("Error en la petición SOAP: ${response.code}")
        }
        
        return response.body?.string() ?: throw Exception("Respuesta vacía del servidor")
    }
}
