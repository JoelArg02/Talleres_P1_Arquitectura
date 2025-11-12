package ec.edu.espe.rest_dotnet_bank.data.models

data class OperacionResultData(
    val resultado: Int = 0,
    val mensaje: String = ""
)

sealed class OperacionResult<out T> {
    data class Success<T>(val data: T) : OperacionResult<T>()
    data class Error(val message: String) : OperacionResult<Nothing>()
    object Loading : OperacionResult<Nothing>()
}
