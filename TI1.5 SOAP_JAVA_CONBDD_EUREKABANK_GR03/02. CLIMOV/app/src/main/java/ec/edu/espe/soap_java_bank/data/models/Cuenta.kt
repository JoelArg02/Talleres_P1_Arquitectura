package ec.edu.espe.soap_java_bank.data.models

data class Cuenta(
    val numeroCuenta: String,
    val nombreCliente: String,
    val saldo: Double,
    val estado: String
)
