package ec.edu.espe.rest_dotnet_bank.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.rest_dotnet_bank.data.models.Cuenta
import ec.edu.espe.rest_dotnet_bank.data.models.OperacionResult
import ec.edu.espe.rest_dotnet_bank.data.repository.EurekaBankRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    
    private val repository = EurekaBankRepository()
    
    private val _loginResult = MutableLiveData<OperacionResult<Cuenta>>()
    val loginResult: LiveData<OperacionResult<Cuenta>> = _loginResult
    
    fun login(cuenta: String, clave: String) {
        Log.d("LoginViewModel", "Intentando login - Cuenta: $cuenta")
        
        if (cuenta.isBlank() || clave.isBlank()) {
            Log.w("LoginViewModel", "Campos vacíos")
            _loginResult.value = OperacionResult.Error("Ingrese cuenta y clave")
            return
        }
        
        _loginResult.value = OperacionResult.Loading
        Log.d("LoginViewModel", "Estado: Loading")
        
        viewModelScope.launch {
            try {
                Log.d("LoginViewModel", "Llamando a repository.login()")
                val result = repository.login(cuenta, clave)
                Log.d("LoginViewModel", "Resultado login: $result")
                _loginResult.value = result
            } catch (e: Exception) {
                Log.e("LoginViewModel", "Error en login", e)
                _loginResult.value = OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
