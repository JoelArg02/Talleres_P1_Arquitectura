package ec.edu.espe.soap_dotnet_bank.data.models

data class Movimiento(
    val nroMov: Int = 0,
    val cuenta: String = "",
    val fecha: String = "",
    val tipo: String = "",
    val accion: String = "",
    val importe: Double = 0.0
)
