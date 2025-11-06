package ec.edu.espe.soap_conuni_clmov_gro3.arguello;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ec.edu.espe.soap_conuni_clmov_gro3.R;
import ec.edu.espe.soap_conuni_clmov_gro3.arguello.servicios.ConUniServicio;
import ec.edu.espe.soap_conuni_clmov_gro3.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ConUniServicio conversionService;
    private static final String TAG = "FirstFragment";
    
    private String[] conversionTypes = {"MASA", "TEMPERATURA", "LONGITUD"};
    private String[][] unitsByType = {
        {"Miligramo (mg)", "Gramo (g)", "Kilogramo (kg)", "Libra (lb)", "Onza (oz)", "Tonelada (t)"},
        {"Celsius (c)", "Fahrenheit (f)", "Kelvin (k)", "Rankine (r)"},
        {"Milímetro (mm)", "Centímetro (cm)", "Metro (m)", "Kilómetro (km)", "Pulgada (in)", "Pie (ft)"}
    };
    private String[][] unitCodesByType = {
        {"mg", "g", "kg", "lb", "oz", "t"},
        {"c", "f", "k", "r"},
        {"mm", "cm", "m", "km", "in", "ft"}
    };
    
    private int currentTypeIndex = 0;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        conversionService = new ConUniServicio();
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupConversionTypeSpinner();
        setupUnitSpinners();
        
        binding.convertButton.setOnClickListener(v -> performConversion());
    }
    
    private void setupConversionTypeSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            requireContext(),
            R.layout.spinner_item,
            conversionTypes
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.conversionTypeSpinner.setAdapter(adapter);
        
        binding.conversionTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentTypeIndex = position;
                updateUnitSpinners();
                binding.resultCard.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    
    private void setupUnitSpinners() {
        updateUnitSpinners();
    }
    
    private void updateUnitSpinners() {
        String[] units = unitsByType[currentTypeIndex];
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
            requireContext(),
            R.layout.spinner_item,
            units
        );
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        
        binding.unitFromSpinner.setAdapter(adapter);
        binding.unitToSpinner.setAdapter(adapter);
        
        if (units.length > 1) {
            binding.unitToSpinner.setSelection(1);
        }
    }

    private void performConversion() {
        String valueStr = binding.valueInput.getText().toString().trim();
        
        if (valueStr.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese un valor", Toast.LENGTH_SHORT).show();
            return;
        }
        
        int fromIndex = binding.unitFromSpinner.getSelectedItemPosition();
        int toIndex = binding.unitToSpinner.getSelectedItemPosition();
        
        if (fromIndex == toIndex) {
            Toast.makeText(requireContext(), "Las unidades deben ser diferentes", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.convertButton.setEnabled(false);
        binding.errorText.setVisibility(View.GONE);
        
        String fromUnit = unitCodesByType[currentTypeIndex][fromIndex];
        String toUnit = unitCodesByType[currentTypeIndex][toIndex];
        
        new ConversionTask().execute(valueStr, fromUnit, toUnit);
    }
    
    private String formatNumber(double value) {
        double absValue = Math.abs(value);
        
        if ((absValue > 0 && absValue < 0.001) || absValue >= 1_000_000) {
            return String.format("%.6e", value);
        } else if (absValue >= 1000) {
            return String.format("%,.3f", value);
        } else {
            return String.format("%.3f", value);
        }
    }
    
    private class ConversionTask extends AsyncTask<String, Void, ConversionResult> {
        
        @Override
        protected ConversionResult doInBackground(String... params) {
            ConversionResult result = new ConversionResult();
            
            try {
                double value = Double.parseDouble(params[0]);
                String fromUnit = params[1];
                String toUnit = params[2];
                
                result.value = value;
                result.fromUnit = unitsByType[currentTypeIndex][binding.unitFromSpinner.getSelectedItemPosition()];
                result.toUnit = unitsByType[currentTypeIndex][binding.unitToSpinner.getSelectedItemPosition()];
                result.resultValue = conversionService.conversion(value, fromUnit, toUnit);
                result.success = true;
                
            } catch (NumberFormatException e) {
                Log.e(TAG, "Error de formato de numero", e);
                result.error = "Valor inválido";
            } catch (Exception e) {
                Log.e(TAG, "Error en conversion", e);
                result.error = "Error al conectar con el servidor: " + e.getMessage();
            }
            
            return result;
        }
        
        @Override
        protected void onPostExecute(ConversionResult result) {
            binding.convertButton.setEnabled(true);
            
            if (!result.success) {
                binding.errorText.setText(result.error);
                binding.errorText.setVisibility(View.VISIBLE);
                Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show();
                binding.resultCard.setVisibility(View.GONE);
                return;
            }
            
            String resultText = formatNumber(result.value) + " " + result.fromUnit + 
                              "\n=\n" + formatNumber(result.resultValue) + " " + result.toUnit;
            
            binding.resultText.setText(resultText);
            binding.resultCard.setVisibility(View.VISIBLE);
            binding.errorText.setVisibility(View.GONE);
            Toast.makeText(requireContext(), "Conversión exitosa", Toast.LENGTH_SHORT).show();
        }
    }
    
    private static class ConversionResult {
        boolean success = false;
        String error = "";
        double value;
        double resultValue;
        String fromUnit;
        String toUnit;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
