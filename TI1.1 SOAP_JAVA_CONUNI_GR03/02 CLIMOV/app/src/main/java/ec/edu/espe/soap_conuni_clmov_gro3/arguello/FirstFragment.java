package ec.edu.espe.soap_conuni_clmov_gro3.arguello;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ec.edu.espe.soap_conuni_clmov_gro3.arguello.servicios.ConUniServicio;
import ec.edu.espe.soap_conuni_clmov_gro3.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ConUniServicio conversionService;
    private static final String TAG = "FirstFragment";

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

        binding.convertButton.setOnClickListener(v -> performConversion());
    }

    private void performConversion() {
        String massStr = binding.massInput.getText().toString().trim();
        String tempStr = binding.tempInput.getText().toString().trim();
        String longStr = binding.longitude2Input.getText().toString().trim();
        
        if (massStr.isEmpty() && tempStr.isEmpty() && longStr.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese al menos un valor", Toast.LENGTH_SHORT).show();
            return;
        }

        binding.convertButton.setEnabled(false);
        binding.errorText.setVisibility(View.GONE);
        
        new ConversionTask().execute(massStr, tempStr, longStr);
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
                String massStr = params[0];
                String tempStr = params[1];
                String longStr = params[2];
                
                if (!massStr.isEmpty()) {
                    double massKg = Double.parseDouble(massStr);
                    result.massKg = massKg;
                    result.massMg = conversionService.conversion(massKg, "kg", "mg");
                    result.massG = conversionService.conversion(massKg, "kg", "g");
                    result.massLb = conversionService.conversion(massKg, "kg", "lb");
                    result.massOz = conversionService.conversion(massKg, "kg", "oz");
                    result.massT = conversionService.conversion(massKg, "kg", "t");
                    result.hasMass = true;
                }
                
                if (!tempStr.isEmpty()) {
                    double tempC = Double.parseDouble(tempStr);
                    result.tempC = tempC;
                    result.tempF = conversionService.conversion(tempC, "c", "f");
                    result.tempK = conversionService.conversion(tempC, "c", "k");
                    result.tempR = conversionService.conversion(tempC, "c", "r");
                    result.hasTemp = true;
                }
                
                if (!longStr.isEmpty()) {
                    double longM = Double.parseDouble(longStr);
                    result.longM = longM;
                    result.longMm = conversionService.conversion(longM, "m", "mm");
                    result.longCm = conversionService.conversion(longM, "m", "cm");
                    result.longKm = conversionService.conversion(longM, "m", "km");
                    result.longIn = conversionService.conversion(longM, "m", "in");
                    result.longFt = conversionService.conversion(longM, "m", "ft");
                    result.hasLong = true;
                }
                
                result.success = true;
                
            } catch (NumberFormatException e) {
                Log.e(TAG, "Error de formato de numero", e);
                result.error = "Valores invalidos";
            } catch (Exception e) {
                Log.e(TAG, "Error en conversion", e);
                result.error = "Error al conectar con el servidor";
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
                return;
            }
            
            if (result.hasMass) {
                binding.resultKg.setText(formatNumber(result.massKg) + " kg");
                binding.resultG.setText(formatNumber(result.massG) + " g");
                binding.resultLb.setText(formatNumber(result.massLb) + " lb");
            } else {
                binding.resultKg.setText("---");
                binding.resultG.setText("---");
                binding.resultLb.setText("---");
            }
            
            if (result.hasTemp) {
                binding.resultCelsius.setText(formatNumber(result.tempC) + " C");
                binding.resultFahrenheit.setText(formatNumber(result.tempF) + " F");
                binding.resultKelvin.setText(formatNumber(result.tempK) + " K");
            } else {
                binding.resultCelsius.setText("---");
                binding.resultFahrenheit.setText("---");
                binding.resultKelvin.setText("---");
            }
            
            if (result.hasLong) {
                binding.resultLong2Decimal.setText(formatNumber(result.longM) + " m");
                binding.resultLong2Radians.setText(formatNumber(result.longCm) + " cm");
            } else {
                binding.resultLong2Decimal.setText("---");
                binding.resultLong2Radians.setText("---");
            }
            
            binding.errorText.setVisibility(View.GONE);
            Toast.makeText(requireContext(), "Conversion exitosa", Toast.LENGTH_SHORT).show();
        }
    }
    
    private static class ConversionResult {
        boolean success = false;
        String error = "";
        
        boolean hasMass = false;
        double massMg, massG, massKg, massLb, massOz, massT;
        
        boolean hasTemp = false;
        double tempC, tempF, tempK, tempR;
        
        boolean hasLong = false;
        double longMm, longCm, longM, longKm, longIn, longFt;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
