package ec.edu.espe.conuni_restfull_java.data.model

import com.google.gson.annotations.SerializedName

data class ConUni(
    @SerializedName("type")
    val type: String,           // longitud, masa, temperatura
    
    @SerializedName("value")
    val value: Double,
    
    @SerializedName("inUnit")
    val inUnit: String,         // Unidad de entrada
    
    @SerializedName("outUnit")
    val outUnit: String         // Unidad de salida
)
