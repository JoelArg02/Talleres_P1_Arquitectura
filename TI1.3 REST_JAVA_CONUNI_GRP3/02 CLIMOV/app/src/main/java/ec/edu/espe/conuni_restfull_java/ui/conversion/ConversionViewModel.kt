package ec.edu.espe.conuni_restfull_java.ui.conversion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.espe.conuni_restfull_java.data.model.ConUni
import ec.edu.espe.conuni_restfull_java.data.repository.ConversionRepository
import kotlinx.coroutines.launch

class ConversionViewModel : ViewModel() {
    
    private val repository = ConversionRepository()
    
    private val _conversionResult = MutableLiveData<Double>()
    val conversionResult: LiveData<Double> = _conversionResult
    
    private val _supportedUnits = MutableLiveData<List<String>>()
    val supportedUnits: LiveData<List<String>> = _supportedUnits
    
    private val _supportedTypes = MutableLiveData<List<String>>()
    val supportedTypes: LiveData<List<String>> = _supportedTypes
    
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage
    
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    /**
     * Realiza la conversión de unidades
     */
    fun convertUnit(type: String, value: Double, inUnit: String, outUnit: String) {
        if (value <= 0) {
            _errorMessage.value = "Ingresa un valor válido"
            return
        }
        
        _isLoading.value = true
        
        viewModelScope.launch {
            val conUni = ConUni(type, value, inUnit, outUnit)
            repository.convertUnit(conUni)
                .onSuccess { result ->
                    _isLoading.value = false
                    _conversionResult.value = result
                }
                .onFailure { exception ->
                    _isLoading.value = false
                    _errorMessage.value = exception.message ?: "Error en la conversión"
                }
        }
    }
    
    /**
     * Obtiene las unidades soportadas para un tipo
     */
    fun loadSupportedUnits(type: String) {
        viewModelScope.launch {
            repository.getSupportedUnits(type)
                .onSuccess { units ->
                    _supportedUnits.value = units
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message ?: "Error al cargar unidades"
                }
        }
    }
    
    /**
     * Obtiene los tipos de conversión soportados
     */
    fun loadSupportedTypes() {
        viewModelScope.launch {
            repository.getSupportedTypes()
                .onSuccess { types ->
                    _supportedTypes.value = types
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.message ?: "Error al cargar tipos"
                }
        }
    }
}
