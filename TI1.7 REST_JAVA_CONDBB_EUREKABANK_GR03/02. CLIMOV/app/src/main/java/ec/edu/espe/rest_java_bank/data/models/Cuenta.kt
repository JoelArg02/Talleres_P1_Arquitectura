package ec.edu.espe.rest_java_bank.data.models

import com.google.gson.annotations.SerializedName

data class Cuenta(
    @SerializedName("numeroCuenta")
    val numeroCuenta: String,
    @SerializedName("nombreCliente")
    val nombreCliente: String,
    @SerializedName("saldo")
    val saldo: Double,
    @SerializedName("moneda")
    val moneda: String,
    @SerializedName("estado")
    val estado: String
)
