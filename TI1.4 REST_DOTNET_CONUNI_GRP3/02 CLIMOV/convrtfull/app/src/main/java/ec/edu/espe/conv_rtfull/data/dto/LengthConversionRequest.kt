package ec.edu.espe.conv_rtfull.data.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para solicitud de conversión de longitud
 */
data class LengthConversionRequest(
    @SerializedName("value")
    val value: Double,
    
    @SerializedName("fromUnit")
    val fromUnit: Int,
    
    @SerializedName("toUnit")
    val toUnit: Int
)
