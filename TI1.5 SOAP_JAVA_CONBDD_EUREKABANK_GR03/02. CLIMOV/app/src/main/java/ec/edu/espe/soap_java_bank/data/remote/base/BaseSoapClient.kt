package ec.edu.espe.soap_java_bank.data.remote.base

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

abstract class BaseSoapClient {
    
//    protected val baseUrl = "http://10.0.2.2:8080/eurekabank/WSEureka"
    protected val baseUrl = "http://10.40.16.227:8080/eurekabank/WSEureka"


    protected val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    protected suspend fun executeRequest(soapRequest: String): String = withContext(Dispatchers.IO) {
        Log.d("SOAP_REQUEST", "URL: $baseUrl")
        Log.d("SOAP_REQUEST", "Body: $soapRequest")
        
        val request = Request.Builder()
            .url(baseUrl)
            .post(soapRequest.toRequestBody("text/xml; charset=utf-8".toMediaType()))
            .addHeader("SOAPAction", "")
            .build()
        
        try {
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string() ?: ""
            
            Log.d("SOAP_RESPONSE", "Code: ${response.code}")
            Log.d("SOAP_RESPONSE", "Body: $responseBody")
            
            responseBody
        } catch (e: Exception) {
            Log.e("SOAP_ERROR", "Error en petición SOAP", e)
            throw e
        }
    }
    
    protected fun extractValue(xml: String, tag: String): String? {
        val pattern = """<$tag>(.*?)</$tag>""".toRegex()
        return pattern.find(xml)?.groupValues?.get(1)
    }
}
