package ec.edu.espe.soap_java_bank.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.soap_java_bank.data.models.OperacionResult
import ec.edu.espe.soap_java_bank.data.repository.EurekaBankRepository
import kotlinx.coroutines.launch

class TransferenciaViewModel : ViewModel() {
    
    private val repository = EurekaBankRepository()
    
    private val _transferenciaResult = MutableLiveData<OperacionResult<Boolean>>()
    val transferenciaResult: LiveData<OperacionResult<Boolean>> = _transferenciaResult
    
    fun realizarTransferencia(cuentaOrigen: String, cuentaDestino: String, importe: Double) {
        if (cuentaOrigen.isBlank() || cuentaDestino.isBlank()) {
            _transferenciaResult.value = OperacionResult.Error("Debe ingresar ambas cuentas")
            return
        }
        
        if (cuentaOrigen == cuentaDestino) {
            _transferenciaResult.value = OperacionResult.Error("La cuenta origen y destino no pueden ser iguales")
            return
        }
        
        if (importe <= 0) {
            _transferenciaResult.value = OperacionResult.Error("El importe debe ser mayor que cero")
            return
        }
        
        _transferenciaResult.value = OperacionResult.Loading
        
        viewModelScope.launch {
            try {
                val result = repository.realizarTransferencia(cuentaOrigen, cuentaDestino, importe)
                if (result == 1) {
                    _transferenciaResult.value = OperacionResult.Success(true)
                } else {
                    _transferenciaResult.value = OperacionResult.Error("No se pudo realizar la transferencia")
                }
            } catch (e: Exception) {
                _transferenciaResult.value = OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
