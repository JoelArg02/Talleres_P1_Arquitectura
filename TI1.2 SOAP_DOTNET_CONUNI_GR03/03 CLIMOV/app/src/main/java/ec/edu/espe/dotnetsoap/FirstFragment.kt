package ec.edu.espe.dotnetsoap

import android.os.Bundle
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
        val massStr = binding.massInput.text.toString().trim()
        val tempStr = binding.tempInput.text.toString().trim()
        
        if (massStr.isEmpty() || tempStr.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese ambos valores", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val massKg = massStr.toDouble()
            val tempC = tempStr.toDouble()
            
            showLoading(true)
            
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = conversionRepository.convertMass(massKg, tempC)
                    
                    withContext(Dispatchers.Main) {
                        showLoading(false)
                        binding.resultKg.text = String.format("%.4f kg", result.massKg)
                        binding.resultLb.text = String.format("%.4f lb", result.massLb)
                        binding.resultG.text = String.format("%.4f g", result.massG)
                        binding.resultCelsius.text = String.format("%.2f °C", result.temperatureCelsius)
                        binding.resultFahrenheit.text = String.format("%.2f °F", result.temperatureFahrenheit)
                        binding.resultKelvin.text = String.format("%.2f K", result.temperatureKelvin)
                        binding.errorText.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        showLoading(false)
                        binding.errorText.text = "Error: ${e.message}"
                        binding.errorText.visibility = View.VISIBLE
                        Toast.makeText(requireContext(), "Error al convertir", Toast.LENGTH_LONG).show()
                    }
                }
            }
            
        } catch (e: NumberFormatException) {
            Toast.makeText(requireContext(), "Valores inválidos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.convertButton.isEnabled = !show
        binding.massInput.isEnabled = !show
        binding.tempInput.isEnabled = !show
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}