package ec.edu.espe.rest_dotnet_bank.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.rest_dotnet_bank.data.models.OperacionResult
import ec.edu.espe.rest_dotnet_bank.data.repository.EurekaBankRepository
import kotlinx.coroutines.launch

class RetiroViewModel : ViewModel() {
    
    private val repository = EurekaBankRepository()
    
    private val _retiroResult = MutableLiveData<OperacionResult<String>>()
    val retiroResult: LiveData<OperacionResult<String>> = _retiroResult
    
    fun realizarRetiro(cuenta: String, monto: Double) {
        if (cuenta.isBlank()) {
            _retiroResult.value = OperacionResult.Error("Debe ingresar un número de cuenta")
            return
        }
        
        if (monto <= 0) {
            _retiroResult.value = OperacionResult.Error("El importe debe ser mayor que cero")
            return
        }
        
        _retiroResult.value = OperacionResult.Loading
        
        viewModelScope.launch {
            try {
                val result = repository.realizarRetiro(cuenta, monto)
                _retiroResult.value = result
            } catch (e: Exception) {
                _retiroResult.value = OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
