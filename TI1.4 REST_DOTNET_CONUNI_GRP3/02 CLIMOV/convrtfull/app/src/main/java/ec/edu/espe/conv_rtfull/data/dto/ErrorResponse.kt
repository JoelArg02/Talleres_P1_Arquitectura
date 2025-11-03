package ec.edu.espe.conv_rtfull.data.dto

import com.google.gson.annotations.SerializedName

/**
 * DTO para respuesta de error
 */
data class ErrorResponse(
    @SerializedName("message")
    val message: String,
    
    @SerializedName("details")
    val details: String?,
    
    @SerializedName("timestamp")
    val timestamp: String
)
