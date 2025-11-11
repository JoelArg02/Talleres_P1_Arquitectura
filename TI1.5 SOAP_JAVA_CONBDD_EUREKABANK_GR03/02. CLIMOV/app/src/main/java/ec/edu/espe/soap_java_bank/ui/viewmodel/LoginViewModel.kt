package ec.edu.espe.soap_java_bank.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.soap_java_bank.data.models.OperacionResult
import ec.edu.espe.soap_java_bank.data.repository.EurekaBankRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    
    private val repository = EurekaBankRepository()
    
    private val _loginResult = MutableLiveData<OperacionResult<Boolean>>()
    val loginResult: LiveData<OperacionResult<Boolean>> = _loginResult
    
    fun login(username: String, password: String) {
        Log.d("LoginViewModel", "Intentando login - Usuario: $username")
        
        if (username.isBlank() || password.isBlank()) {
            Log.w("LoginViewModel", "Campos vacíos")
            _loginResult.value = OperacionResult.Error("Ingrese usuario y contraseña")
            return
        }
        
        _loginResult.value = OperacionResult.Loading
        Log.d("LoginViewModel", "Estado: Loading")
        
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Llamando a repository.login()")
                val result = repository.login(username, password)
                Log.d("LoginViewModel", "Resultado login: $result")
                
                if (result) {
                    Log.d("LoginViewModel", "Login exitoso")
                    _loginResult.value = OperacionResult.Success(true)
                } else {
                    Log.w("LoginViewModel", "Credenciales incorrectas")
                    _loginResult.value = OperacionResult.Error("Usuario o contraseña incorrectos")
                }
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error en login", e)
                _loginResult.value = OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
