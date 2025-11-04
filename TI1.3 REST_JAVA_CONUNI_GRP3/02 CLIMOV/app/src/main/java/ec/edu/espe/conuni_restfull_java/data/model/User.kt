package ec.edu.espe.conuni_restfull_java.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    val username: String,
    
    @SerializedName("password")
    val password: String
)
