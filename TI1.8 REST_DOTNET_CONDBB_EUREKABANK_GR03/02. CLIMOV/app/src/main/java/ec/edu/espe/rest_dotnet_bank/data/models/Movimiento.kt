package ec.edu.espe.rest_dotnet_bank.data.models

import com.google.gson.annotations.SerializedName

data class Movimiento(
    @SerializedName("NroMov")
    val nroMov: Int = 0,
    
    @SerializedName("Cuenta")
    val cuenta: String = "",
    
    @SerializedName("Fecha")
    val fecha: String = "",
    
    @SerializedName("Tipo")
    val tipo: String = "",
    
    @SerializedName("Accion")
    val accion: String = "",
    
    @SerializedName("Importe")
    val importe: Double = 0.0
)
