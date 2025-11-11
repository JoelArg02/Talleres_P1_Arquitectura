package ec.edu.espe.soap_java_bank.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.soap_java_bank.data.models.Movimiento
import ec.edu.espe.soap_java_bank.data.models.OperacionResult
import ec.edu.espe.soap_java_bank.data.repository.EurekaBankRepository
import kotlinx.coroutines.launch

class MovimientosViewModel : ViewModel() {
    
    private val repository = EurekaBankRepository()
    
    private val _movimientos = MutableLiveData<OperacionResult<List<Movimiento>>>()
    val movimientos: LiveData<OperacionResult<List<Movimiento>>> = _movimientos
    
    fun obtenerMovimientos(cuenta: String) {
        if (cuenta.isBlank()) {
            _movimientos.value = OperacionResult.Error("Debe ingresar un número de cuenta")
            return
        }
        
        _movimientos.value = OperacionResult.Loading
        
        viewModelScope.launch {
            try {
                val result = repository.obtenerMovimientos(cuenta)
                if (result.isNotEmpty()) {
                    _movimientos.value = OperacionResult.Success(result)
                } else {
                    _movimientos.value = OperacionResult.Error("No hay movimientos para esta cuenta")
                }
            } catch (e: Exception) {
                _movimientos.value = OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
