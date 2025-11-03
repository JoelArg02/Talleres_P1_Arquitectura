package ec.edu.espe.conv_rtfull.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.conv_rtfull.data.dto.ConversionResponse
import ec.edu.espe.conv_rtfull.data.repository.ConversionRepository
import kotlinx.coroutines.launch

/**
 * ViewModel para conversión de temperatura
 */
class TemperatureViewModel : ViewModel() {
    
    private val repository = ConversionRepository()
    
    private val _conversionResult = MutableLiveData<ConversionResponse?>()
    val conversionResult: LiveData<ConversionResponse?> = _conversionResult
    
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun convertTemperature(value: Double, fromUnit: Int, toUnit: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""
            
            val result = repository.convertTemperature(value, fromUnit, toUnit)
            
            result.onSuccess { response ->
                _conversionResult.value = response
            }.onFailure { error ->
                _errorMessage.value = error.message ?: "Error desconocido"
                _conversionResult.value = null
            }
            
            _isLoading.value = false
        }
    }
}
