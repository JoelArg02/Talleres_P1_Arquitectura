package ec.edu.espe.conv_rtfull.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ec.edu.espe.conv_rtfull.data.model.TemperatureUnit
import ec.edu.espe.conv_rtfull.databinding.FragmentTemperatureBinding
import ec.edu.espe.conv_rtfull.ui.viewmodel.TemperatureViewModel

/**
 * Fragment para conversión de temperatura
 */
class TemperatureFragment : Fragment() {
    
    private var _binding: FragmentTemperatureBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: TemperatureViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTemperatureBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupSpinners()
        setupListeners()
        observeViewModel()
    }
    
    private fun setupSpinners() {
        val units = TemperatureUnit.values().map { it.displayName }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, units)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        
        binding.spinnerFromUnit.adapter = adapter
        binding.spinnerToUnit.adapter = adapter
        binding.spinnerToUnit.setSelection(1) // Fahrenheit por defecto
    }
    
    private fun setupListeners() {
        binding.btnConvert.setOnClickListener {
            performConversion()
        }
    }
    
    private fun performConversion() {
        val valueStr = binding.etValue.text.toString()
        
        if (valueStr.isEmpty()) {
            binding.etValue.error = "Ingrese un valor"
            return
        }
        
        try {
            val value = valueStr.toDouble()
            val fromUnit = TemperatureUnit.values()[binding.spinnerFromUnit.selectedItemPosition]
            val toUnit = TemperatureUnit.values()[binding.spinnerToUnit.selectedItemPosition]
            
            viewModel.convertTemperature(value, fromUnit.code, toUnit.code)
        } catch (e: NumberFormatException) {
            Toast.makeText(requireContext(), "Valor inválido", Toast.LENGTH_SHORT).show()
        }
    }
    
    private fun observeViewModel() {
        viewModel.conversionResult.observe(viewLifecycleOwner) { result ->
            result?.let {
                binding.tvResult.text = String.format("%.2f %s", it.convertedValue, it.toUnit)
                binding.tvError.visibility = View.GONE
            }
        }
        
        viewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            if (error.isNotEmpty()) {
                binding.tvError.text = error
                binding.tvError.visibility = View.VISIBLE
                binding.tvResult.text = "---"
            }
        }
        
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnConvert.isEnabled = !isLoading
        }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
