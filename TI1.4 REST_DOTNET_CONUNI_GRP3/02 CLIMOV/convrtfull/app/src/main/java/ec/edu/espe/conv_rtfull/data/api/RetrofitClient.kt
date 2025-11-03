package ec.edu.espe.conv_rtfull.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Cliente de Retrofit para la API de conversión
 */
object RetrofitClient {
    
    // Cambiar esta URL a la IP de tu máquina si estás usando un dispositivo físico
    // Para emulador usa: http://10.0.2.2:5000/
    // Para dispositivo físico usa: http://TU_IP_LOCAL:5000/
    private const val BASE_URL = "http://10.0.2.2:5000/"
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val api: ConversionApi = retrofit.create(ConversionApi::class.java)
}
