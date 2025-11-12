package ec.edu.espe.rest_dotnet_bank.data.models

import com.google.gson.annotations.SerializedName

data class Cuenta(
    @SerializedName("NumeroCuenta")
    val numeroCuenta: String = "",
    
    @SerializedName("NombreCliente")
    val nombreCliente: String = "",
    
    @SerializedName("Saldo")
    val saldo: Double = 0.0,
    
    @SerializedName("Moneda")
    val moneda: String = "",
    
    @SerializedName("Estado")
    val estado: String = ""
)
