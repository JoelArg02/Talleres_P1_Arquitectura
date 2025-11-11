package ec.edu.espe.soap_java_bank.data.models

data class Movimiento(
    val idMovimiento: Int,
    val numeroCuenta: String,
    val tipoMovimiento: String,
    val importe: Double,
    val fecha: String
)
