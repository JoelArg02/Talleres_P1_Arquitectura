package ec.edu.espe.dotnetsoap.data.models

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val token: String?
)
