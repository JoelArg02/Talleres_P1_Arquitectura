package ec.edu.espe.rest_java_bank.data.models

import com.google.gson.annotations.SerializedName

data class Movimiento(
    @SerializedName("cuenta")
    val cuenta: String,
    @SerializedName("nromov")
    val nromov: Int,
    @SerializedName("fecha")
    val fecha: String,
    @SerializedName("tipo")
    val tipo: String,
    @SerializedName("accion")
    val accion: String,
    @SerializedName("importe")
    val importe: Double
)
