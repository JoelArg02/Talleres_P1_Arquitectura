package ec.edu.espe.soap_java_bank.data.models

sealed class OperacionResult<out T> {
    data class Success<T>(val data: T) : OperacionResult<T>()
    data class Error(val message: String) : OperacionResult<Nothing>()
    object Loading : OperacionResult<Nothing>()
}
