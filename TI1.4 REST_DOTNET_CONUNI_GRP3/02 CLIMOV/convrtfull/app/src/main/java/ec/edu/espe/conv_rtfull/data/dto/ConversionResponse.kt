package ec.edu.espe.conv_rtfull.data.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para respuesta de conversión
 */
data class ConversionResponse(
    @SerializedName("originalValue")
    val originalValue: Double,
    
    @SerializedName("fromUnit")
    val fromUnit: String,
    
    @SerializedName("convertedValue")
    val convertedValue: Double,
    
    @SerializedName("toUnit")
    val toUnit: String,
    
    @SerializedName("category")
    val category: String,
    
    @SerializedName("timestamp")
    val timestamp: String
)
