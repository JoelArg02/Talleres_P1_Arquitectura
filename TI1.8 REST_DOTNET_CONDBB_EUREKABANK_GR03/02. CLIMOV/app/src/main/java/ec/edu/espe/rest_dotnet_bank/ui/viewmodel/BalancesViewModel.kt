package ec.edu.espe.rest_dotnet_bank.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.rest_dotnet_bank.data.models.Cuenta
import ec.edu.espe.rest_dotnet_bank.data.models.OperacionResult
import ec.edu.espe.rest_dotnet_bank.data.repository.EurekaBankRepository
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
                _balances.value = result
            } catch (e: Exception) {
                _balances.value = OperacionResult.Error("Error de conexión: ${e.message}")
            }
        }
    }
}
