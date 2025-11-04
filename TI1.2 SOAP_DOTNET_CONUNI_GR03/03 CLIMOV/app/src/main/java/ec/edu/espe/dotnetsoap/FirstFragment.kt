package ec.edu.espe.dotnetsoap

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import ec.edu.espe.dotnetsoap.data.repository.ConversionRepository
import ec.edu.espe.dotnetsoap.databinding.FragmentFirstBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private val conversionRepository = ConversionRepository()

    companion object {
        private const val TAG = "FirstFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.convertButton.setOnClickListener {
            performConversion()
        }
    }

    private fun performConversion() {
        Log.d(TAG, "=== INICIO CONVERSION ===")
        
        val massStr = binding.massInput.text.toString().trim()
        val tempStr = binding.tempInput.text.toString().trim()
        val long2Str = binding.longitude2Input.text.toString().trim()
        
        Log.d(TAG, "Valores ingresados - Mass: '$massStr', Temp: '$tempStr', Long2: '$long2Str'")
        
        if (massStr.isEmpty() || tempStr.isEmpty() || long2Str.isEmpty()) {
            Log.w(TAG, "ERROR: Campos vacíos detectados")
            Toast.makeText(requireContext(), "Ingrese todos los valores", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val massKg = massStr.toDouble()
            val tempC = tempStr.toDouble()
            val long2 = long2Str.toDouble()
            
            Log.d(TAG, "Valores parseados - Mass: $massKg kg, Temp: $tempC °C, Long2: $long2")
            
            showLoading(true)
            
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    Log.d(TAG, "Llamando al repositorio...")
                    val result = conversionRepository.convertMass(massKg, tempC, long2)
                    Log.d(TAG, "Resultado recibido del repositorio")
                    
                    withContext(Dispatchers.Main) {
                        showLoading(false)
                        Log.d(TAG, "Actualizando UI con resultados...")
                        binding.resultKg.text = String.format("%.4f kg", result.massKg)
                        binding.resultLb.text = String.format("%.4f lb", result.massLb)
                        binding.resultG.text = String.format("%.4f g", result.massG)
                        binding.resultCelsius.text = String.format("%.2f °C", result.temperatureCelsius)
                        binding.resultFahrenheit.text = String.format("%.2f °F", result.temperatureFahrenheit)
                        binding.resultKelvin.text = String.format("%.2f K", result.temperatureKelvin)
                        binding.resultLong2Decimal.text = String.format("%.6f°", result.longitude2Decimal)
                        binding.resultLong2Radians.text = String.format("%.6f rad", result.longitude2Radians)
                        binding.errorText.visibility = View.GONE
                        Log.d(TAG, "UI actualizada exitosamente")
                        Log.d(TAG, "=== FIN CONVERSION EXITOSA ===")
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "ERROR en conversión: ${e.message}", e)
                    withContext(Dispatchers.Main) {
                        showLoading(false)
                        binding.errorText.text = "Error: ${e.message}"
                        binding.errorText.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Error al convertir: ${e.message}", Toast.LENGTH_LONG).show()
                        Log.d(TAG, "=== FIN CONVERSION CON ERROR ===")
                    }
                }
            }
            
        } catch (e: NumberFormatException) {
            Log.e(TAG, "ERROR: Formato de número inválido - ${e.message}")
            Toast.makeText(requireContext(), "Valores inválidos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.convertButton.isEnabled = !show
        binding.massInput.isEnabled = !show
        binding.tempInput.isEnabled = !show
        binding.longitude2Input.isEnabled = !show
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}