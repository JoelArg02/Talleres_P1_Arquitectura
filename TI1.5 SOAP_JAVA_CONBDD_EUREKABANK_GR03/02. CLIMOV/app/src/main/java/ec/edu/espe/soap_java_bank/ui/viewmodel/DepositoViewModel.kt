package ec.edu.espe.soap_java_bank.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.soap_java_bank.data.models.OperacionResult
import ec.edu.espe.soap_java_bank.data.repository.EurekaBankRepository
import kotlinx.coroutines.launch

class DepositoViewModel : ViewModel() {
    
    private val repository = EurekaBankRepository()
    
    private val _depositoResult = MutableLiveData<OperacionResult<Boolean>>()
    val depositoResult: LiveData<OperacionResult<Boolean>> = _depositoResult
    
    fun realizarDeposito(cuenta: String, importe: Double) {
        if (cuenta.isBlank()) {
            _depositoResult.value = OperacionResult.Error("Debe ingresar un número de cuenta")
            return
        }
        
        if (importe <= 0) {
            _depositoResult.value = OperacionResult.Error("El importe debe ser mayor que cero")
            return
        }
        
        _depositoResult.value = OperacionResult.Loading
        
        viewModelScope.launch {
            try {
                val result = repository.realizarDeposito(cuenta, importe)
                if (result == 1) {
                    _depositoResult.value = OperacionResult.Success(true)
                } else {
                    _depositoResult.value = OperacionResult.Error("No se pudo realizar el depósito")
                }
            } catch (e: Exception) {
                _depositoResult.value = OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
