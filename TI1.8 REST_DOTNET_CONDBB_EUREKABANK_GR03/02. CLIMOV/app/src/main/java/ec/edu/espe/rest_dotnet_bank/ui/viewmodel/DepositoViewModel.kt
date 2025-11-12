package ec.edu.espe.rest_dotnet_bank.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.rest_dotnet_bank.data.models.OperacionResult
import ec.edu.espe.rest_dotnet_bank.data.repository.EurekaBankRepository
import kotlinx.coroutines.launch

class DepositoViewModel : ViewModel() {
    
    private val repository = EurekaBankRepository()
    
    private val _depositoResult = MutableLiveData<OperacionResult<String>>()
    val depositoResult: LiveData<OperacionResult<String>> = _depositoResult
    
    fun realizarDeposito(cuenta: String, monto: Double) {
        if (cuenta.isBlank()) {
            _depositoResult.value = OperacionResult.Error("Debe ingresar un número de cuenta")
            return
        }
        
        if (monto <= 0) {
            _depositoResult.value = OperacionResult.Error("El importe debe ser mayor que cero")
            return
        }
        
        _depositoResult.value = OperacionResult.Loading
        
        viewModelScope.launch {
            try {
                val result = repository.realizarDeposito(cuenta, monto)
                _depositoResult.value = result
            } catch (e: Exception) {
                _depositoResult.value = OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
