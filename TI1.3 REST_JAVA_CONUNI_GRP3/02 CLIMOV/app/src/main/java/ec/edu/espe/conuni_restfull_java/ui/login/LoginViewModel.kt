package ec.edu.espe.conuni_restfull_java.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.conuni_restfull_java.data.model.User
import ec.edu.espe.conuni_restfull_java.data.repository.ConversionRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    
    private val repository = ConversionRepository()
    
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult
    
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun login(username: String, password: String) {
        if (username.isEmpty() || password.isEmpty()) {
            _errorMessage.value = "Por favor completa todos los campos"
            return
        }
        
        _isLoading.value = true
        
        viewModelScope.launch {
            val user = User(username, password)
            repository.login(user)
                .onSuccess { success ->
                    _isLoading.value = false
                    _loginResult.value = success
                    if (!success) {
                        _errorMessage.value = "Credenciales incorrectas"
                    }
                }
                .onFailure { exception ->
                    _isLoading.value = false
                    _errorMessage.value = exception.message ?: "Error de conexión"
                }
        }
    }
}
