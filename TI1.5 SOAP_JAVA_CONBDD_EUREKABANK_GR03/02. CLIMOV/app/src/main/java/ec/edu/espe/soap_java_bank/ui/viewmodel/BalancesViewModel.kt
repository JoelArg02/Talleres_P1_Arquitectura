package ec.edu.espe.soap_java_bank.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.soap_java_bank.data.models.Cuenta
import ec.edu.espe.soap_java_bank.data.models.OperacionResult
import ec.edu.espe.soap_java_bank.data.repository.EurekaBankRepository
import kotlinx.coroutines.launch

class BalancesViewModel : ViewModel() {
    
    private val repository = EurekaBankRepository()
    
    private val _balances = MutableLiveData<OperacionResult<List<Cuenta>>>()
    val balances: LiveData<OperacionResult<List<Cuenta>>> = _balances
    
    fun obtenerBalances() {
        _balances.value = OperacionResult.Loading
        
        viewModelScope.launch {
            try {
                val result = repository.obtenerBalances()
                if (result.isNotEmpty()) {
                    _balances.value = OperacionResult.Success(result)
                } else {
                    _balances.value = OperacionResult.Error("No hay cuentas activas para mostrar")
                }
            } catch (e: Exception) {
                _balances.value = OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
