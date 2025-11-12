package ec.edu.espe.rest_java_bank.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.rest_java_bank.data.models.OperacionResult
import ec.edu.espe.rest_java_bank.data.repository.EurekaBankRepository
import kotlinx.coroutines.launch

class RetiroViewModel : ViewModel() {
    
    private val repository = EurekaBankRepository()
    
    private val _retiroResult = MutableLiveData<OperacionResult<String>>()
    val retiroResult: LiveData<OperacionResult<String>> = _retiroResult
    
    fun realizarRetiro(cuenta: String, importe: Double) {
        if (cuenta.isBlank()) {
            _retiroResult.value = OperacionResult.Error("Debe ingresar un número de cuenta")
            return
        }
        
        if (importe <= 0) {
            _retiroResult.value = OperacionResult.Error("El importe debe ser mayor que cero")
            return
        }
        
        _retiroResult.value = OperacionResult.Loading
        
        viewModelScope.launch {
            try {
                val result = repository.registrarRetiro(cuenta, importe)
                _retiroResult.value = result
            } catch (e: Exception) {
                _retiroResult.value = OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
