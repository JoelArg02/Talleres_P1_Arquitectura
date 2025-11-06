package ec.edu.espe.conuni_restfull_java

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ec.edu.espe.conuni_restfull_java.data.api.RetrofitClient
import ec.edu.espe.conuni_restfull_java.data.model.ConUni
import ec.edu.espe.conuni_restfull_java.databinding.FragmentFirstBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    
    private val apiService = RetrofitClient.apiService
    private val TAG = "FirstFragment"
    
    private val conversionTypes = arrayOf("MASA", "TEMPERATURA", "LONGITUD")
    private val unitsByType = arrayOf(
        arrayOf("Miligramo (mg)", "Gramo (g)", "Kilogramo (kg)", "Libra (lb)", "Onza (oz)", "Tonelada (t)"),
        arrayOf("Celsius (°C)", "Fahrenheit (°F)", "Kelvin (K)", "Rankine (°R)"),
        arrayOf("Milímetro (mm)", "Centímetro (cm)", "Metro (m)", "Kilómetro (km)", "Pulgada (in)", "Pie (ft)")
    )
    private val unitCodesByType = arrayOf(
        arrayOf("mg", "g", "kg", "lb", "oz", "t"),
        arrayOf("c", "f", "k", "r"),
        arrayOf("mm", "cm", "m", "km", "in", "ft")
    )
    private val typeMapping = mapOf(
        0 to "masa",
        1 to "temperatura",
        2 to "longitud"
    )
    
    private var currentTypeIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupConversionTypeSpinner()
        setupUnitSpinners()
        
        binding.convertButton.setOnClickListener {
            performConversion()
        }
    }
    
    private fun setupConversionTypeSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            conversionTypes
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        binding.conversionTypeSpinner.adapter = adapter
        
        binding.conversionTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentTypeIndex = position
                updateUnitSpinners()
                binding.resultCard.visibility = View.GONE
            }
            
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
    
    private fun setupUnitSpinners() {
        updateUnitSpinners()
    }
    
    private fun updateUnitSpinners() {
        val units = unitsByType[currentTypeIndex]
        
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            units
        )
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        
        binding.unitFromSpinner.adapter = adapter
        binding.unitToSpinner.adapter = adapter
        
        if (units.size > 1) {
            binding.unitToSpinner.setSelection(1)
        }
    }
    
    private fun performConversion() {
        val valueStr = binding.valueInput.text.toString().trim()
        
        if (valueStr.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese un valor", Toast.LENGTH_SHORT).show()
            return
        }
        
        val value: Double
        try {
            value = valueStr.toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(requireContext(), "Valor inválido", Toast.LENGTH_SHORT).show()
            return
        }
        
        val fromIndex = binding.unitFromSpinner.selectedItemPosition
        val toIndex = binding.unitToSpinner.selectedItemPosition
        
        if (fromIndex == toIndex) {
            Toast.makeText(requireContext(), "Las unidades deben ser diferentes", Toast.LENGTH_SHORT).show()
            return
        }
        
        binding.convertButton.isEnabled = false
        binding.errorText.visibility = View.GONE
        
        val fromUnit = unitCodesByType[currentTypeIndex][fromIndex]
        val toUnit = unitCodesByType[currentTypeIndex][toIndex]
        val type = typeMapping[currentTypeIndex] ?: "masa"
        
        lifecycleScope.launch {
            try {
                val result = withContext(Dispatchers.IO) {
                    val conUni = ConUni(type, value, fromUnit, toUnit)
                    val response = apiService.convertUnit(conUni)
                    if (response.isSuccessful && response.body() != null) {
                        response.body()!!
                    } else {
                        throw Exception("Error en la conversión: ${response.code()}")
                    }
                }
                
                val fromUnitText = unitsByType[currentTypeIndex][fromIndex]
                val toUnitText = unitsByType[currentTypeIndex][toIndex]
                
                val resultText = "${formatNumber(value)} $fromUnitText\n=\n${formatNumber(result)} $toUnitText"
                
                binding.resultText.text = resultText
                binding.resultCard.visibility = View.VISIBLE
                binding.errorText.visibility = View.GONE
                Toast.makeText(requireContext(), "Conversión exitosa", Toast.LENGTH_SHORT).show()
                
            } catch (e: Exception) {
                Log.e(TAG, "Error en conversión", e)
                binding.errorText.text = "Error al conectar con el servidor: ${e.message}"
                binding.errorText.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                binding.resultCard.visibility = View.GONE
            } finally {
                binding.convertButton.isEnabled = true
            }
        }
    }
    
    private fun formatNumber(value: Double): String {
        val absValue = Math.abs(value)
        
        return when {
            (absValue > 0 && absValue < 0.001) || absValue >= 1_000_000 -> {
                String.format("%.6e", value)
            }
            absValue >= 1000 -> {
                String.format("%,.3f", value)
            }
            else -> {
                String.format("%.3f", value)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}