package ec.edu.espe.soap_dotnet_bank.data.models

data class Cuenta(
    val numeroCuenta: String = "",
    val nombreCliente: String = "",
    val saldo: Double = 0.0,
    val moneda: String = "",
    val estado: String = ""
)
