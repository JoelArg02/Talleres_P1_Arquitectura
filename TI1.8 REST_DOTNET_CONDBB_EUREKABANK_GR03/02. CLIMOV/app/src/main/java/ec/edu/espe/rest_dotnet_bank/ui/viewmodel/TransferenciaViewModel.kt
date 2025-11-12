package ec.edu.espe.rest_dotnet_bank.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.rest_dotnet_bank.data.models.OperacionResult
import ec.edu.espe.rest_dotnet_bank.data.repository.EurekaBankRepository
import kotlinx.coroutines.launch

class TransferenciaViewModel : ViewModel() {
    
    private val repository = EurekaBankRepository()
    
    private val _transferenciaResult = MutableLiveData<OperacionResult<String>>()
    val transferenciaResult: LiveData<OperacionResult<String>> = _transferenciaResult
    
    fun realizarTransferencia(cuentaOrigen: String, cuentaDestino: String, monto: Double) {
        if (cuentaOrigen.isBlank() || cuentaDestino.isBlank()) {
            _transferenciaResult.value = OperacionResult.Error("Debe ingresar ambas cuentas")
            return
        }
        
        if (cuentaOrigen == cuentaDestino) {
            _transferenciaResult.value = OperacionResult.Error("La cuenta origen y destino no pueden ser iguales")
            return
        }
        
        if (monto <= 0) {
            _transferenciaResult.value = OperacionResult.Error("El importe debe ser mayor que cero")
            return
        }
        
        _transferenciaResult.value = OperacionResult.Loading
        
        viewModelScope.launch {
            try {
                val result = repository.realizarTransferencia(cuentaOrigen, cuentaDestino, monto)
                _transferenciaResult.value = result
            } catch (e: Exception) {
                _transferenciaResult.value = OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
