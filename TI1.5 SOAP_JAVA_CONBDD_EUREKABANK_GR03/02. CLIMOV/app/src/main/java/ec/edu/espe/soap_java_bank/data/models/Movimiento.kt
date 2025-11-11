package ec.edu.espe.soap_java_bank.data.models

data class Movimiento(
    val nromov: String,
    val cuenta: String,
    val tipo: String,
    val accion: String,
    val importe: Double,
    val fecha: String
)
