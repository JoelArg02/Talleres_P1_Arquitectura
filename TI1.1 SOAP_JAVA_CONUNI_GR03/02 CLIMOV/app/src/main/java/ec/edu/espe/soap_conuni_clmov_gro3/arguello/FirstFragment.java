package ec.edu.espe.soap_conuni_clmov_gro3.arguello;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;

import ec.edu.espe.soap_conuni_clmov_gro3.arguello.viewmodel.ConversionViewModel;
import ec.edu.espe.soap_conuni_clmov_gro3.databinding.FragmentFirstBinding;

/**
 * Vista (View en MVC) para la conversión de unidades.
 * Se conecta con el ViewModel para realizar las operaciones.
 */
public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ConversionViewModel viewModel;
    
    // Mapa para convertir nombres en español a inglés (según tu servicio SOAP)
    private final Map<String, String> unitMap = new HashMap<String, String>() {{
        put("Metros", "meters");
        put("Kilómetros", "kilometers");
        put("Centímetros", "centimeters");
    }};

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ConversionViewModel.class);

        // Mostrar nombres en español en la UI
        String[] units = {"Metros", "Kilómetros", "Centímetros"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                units
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        
        binding.fromUnitSpinner.setAdapter(adapter);
        binding.toUnitSpinner.setAdapter(adapter);

        binding.convertButton.setOnClickListener(v -> performConversion());

        observeViewModel();
    }

    private void performConversion() {
        String valueStr = binding.valueInput.getText().toString().trim();
        
        if (valueStr.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese un valor", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double value = Double.parseDouble(valueStr);
            String fromUnitSpanish = binding.fromUnitSpinner.getSelectedItem().toString();
            String toUnitSpanish = binding.toUnitSpinner.getSelectedItem().toString();

            // Convertir nombres en español a inglés para el servicio SOAP
            String fromUnit = unitMap.get(fromUnitSpanish);
            String toUnit = unitMap.get(toUnitSpanish);

            if (fromUnit == null || toUnit == null) {
                Toast.makeText(requireContext(), "Unidad no válida", Toast.LENGTH_SHORT).show();
                return;
            }

            // Llamar al ViewModel (Controlador) con los nombres en inglés
            viewModel.convertUnit(value, fromUnit, toUnit);
            
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Valor inválido", Toast.LENGTH_SHORT).show();
        }
    }

    private void observeViewModel() {
        // Observar resultado
        viewModel.getConversionResult().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                binding.resultText.setText(String.format("%.4f", result));
                binding.errorText.setVisibility(View.GONE);
            }
        });

        // Observar errores
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                binding.errorText.setText(error);
                binding.errorText.setVisibility(View.VISIBLE);
                binding.resultText.setText("---");
            }
        });

        // Observar estado de carga
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading != null) {
                binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                binding.convertButton.setEnabled(!isLoading);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}