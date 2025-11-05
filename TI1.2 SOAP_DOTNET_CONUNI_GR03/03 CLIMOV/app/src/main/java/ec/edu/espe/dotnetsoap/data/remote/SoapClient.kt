package ec.edu.espe.dotnetsoap.data.remote

import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit

class SoapClient {
    
    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val xmlMediaType = "text/xml; charset=utf-8".toMediaType()
    
    companion object {
        private const val TAG = "SoapClient"
        private const val BASE_URL = "http://10.1.200.10:5001"
        const val LOGIN_ENDPOINT = "$BASE_URL/Login.svc"
        const val CONVERSION_ENDPOINT = "$BASE_URL/Conversion.svc"
    }
    
    fun callSoap(endpoint: String, soapAction: String, soapBody: String): String {
        Log.d(TAG, "=== SOAP REQUEST ===")
        Log.d(TAG, "Endpoint: $endpoint")
        Log.d(TAG, "SOAPAction: $soapAction")
        Log.d(TAG, "Body: $soapBody")
        
        try {
            val requestBody = soapBody.toRequestBody(xmlMediaType)
            val request = Request.Builder()
                .url(endpoint)
                .post(requestBody)
                .addHeader("Content-Type", "text/xml; charset=utf-8")
                .addHeader("SOAPAction", "\"$soapAction\"")
                .build()
            
            val response = client.newCall(request).execute()
            
            Log.d(TAG, "=== SOAP RESPONSE ===")
            Log.d(TAG, "HTTP Code: ${response.code}")
            
            if (!response.isSuccessful) {
                val error = "HTTP error: ${response.code} - ${response.message}"
                Log.e(TAG, error)
                throw RuntimeException(error)
            }
            
            val responseBody = response.body?.string() ?: ""
            Log.d(TAG, "Response Body: $responseBody")
            Log.d(TAG, "===================")
            
            return responseBody
        } catch (e: Exception) {
            Log.e(TAG, "=== SOAP ERROR ===")
            Log.e(TAG, "Endpoint: $endpoint")
            Log.e(TAG, "Error: ${e.message}", e)
            Log.e(TAG, "==================")
            throw RuntimeException("Error invoking SOAP service: $endpoint", e)
        }
    }
}
