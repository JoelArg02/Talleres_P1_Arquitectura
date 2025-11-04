package ec.edu.espe.conuni_restfull_java.ui.conversion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ec.edu.espe.conuni_restfull_java.databinding.FragmentConversionBinding

class LongitudFragment : Fragment() {
    
    private var _binding: FragmentConversionBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ConversionViewModel by viewModels()
    private val conversionType = "longitud"
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentConversionBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.cardResult.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        
        setupListeners()
        observeViewModel()
        loadUnits()
    }
    
    private fun setupListeners() {
        binding.btnConvert.setOnClickListener {
            performConversion()
        }
    }
    
    private fun observeViewModel() {
        viewModel.supportedUnits.observe(viewLifecycleOwner) { units ->
            if (units.isNotEmpty()) {
                setupSpinners(units)
            }
        }
        
        viewModel.conversionResult.observe(viewLifecycleOwner) { result ->
            binding.cardResult.visibility = View.VISIBLE
            val formattedResult = String.format("%.4f", result)
            binding.tvResult.text = formattedResult
        }
        
        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            binding.tvError.text = message
            binding.tvError.visibility = View.VISIBLE
        }
        
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnConvert.isEnabled = !isLoading
            if (isLoading) {
                binding.tvError.visibility = View.GONE
            }
        }
    }
    
    private fun loadUnits() {
        viewModel.loadSupportedUnits(conversionType)
    }
    
    private fun setupSpinners(units: List<String>) {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            units
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        
        binding.spinnerFromUnit.adapter = adapter
        binding.spinnerToUnit.adapter = adapter
        
        if (units.size > 1) {
            binding.spinnerToUnit.setSelection(1)
        }
    }
    
    private fun performConversion() {
        val valueText = binding.etValue.text.toString()
        
        if (valueText.isEmpty()) {
            binding.tvError.text = "Ingresa un valor"
            binding.tvError.visibility = View.VISIBLE
            return
        }
        
        val value = valueText.toDoubleOrNull()
        if (value == null || value <= 0) {
            binding.tvError.text = "Ingresa un valor válido mayor a 0"
            binding.tvError.visibility = View.VISIBLE
            return
        }
        
        val fromUnit = binding.spinnerFromUnit.selectedItem?.toString() ?: ""
        val toUnit = binding.spinnerToUnit.selectedItem?.toString() ?: ""
        
        if (fromUnit.isEmpty() || toUnit.isEmpty()) {
            binding.tvError.text = "Selecciona las unidades"
            binding.tvError.visibility = View.VISIBLE
            return
        }
        
        binding.tvError.visibility = View.GONE
        viewModel.convertUnit(conversionType, value, fromUnit, toUnit)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
