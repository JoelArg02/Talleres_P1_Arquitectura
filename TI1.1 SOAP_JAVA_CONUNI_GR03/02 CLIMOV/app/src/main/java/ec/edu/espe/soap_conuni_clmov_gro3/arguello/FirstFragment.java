package ec.edu.espe.soap_conuni_clmov_gro3.arguello;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ec.edu.espe.soap_conuni_clmov_gro3.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

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

        binding.convertButton.setOnClickListener(v -> performConversion());
    }

    private void performConversion() {
        String massStr = binding.massInput.getText().toString().trim();
        String tempStr = binding.tempInput.getText().toString().trim();
        String long2Str = binding.longitude2Input.getText().toString().trim();
        
        if (massStr.isEmpty() || tempStr.isEmpty() || long2Str.isEmpty()) {
            Toast.makeText(requireContext(), "Ingrese todos los valores", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double massKg = Double.parseDouble(massStr);
            double tempC = Double.parseDouble(tempStr);
            double long2 = Double.parseDouble(long2Str);
            
            double massLb = massKg * 2.20462262185;
            double massG = massKg * 1000.0;
            
            double tempF = (tempC * 9.0 / 5.0) + 32.0;
            double tempK = tempC + 273.15;
            
            double long2Rad = long2 * Math.PI / 180.0;
            
            binding.resultKg.setText(String.format("%.4f kg", massKg));
            binding.resultLb.setText(String.format("%.4f lb", massLb));
            binding.resultG.setText(String.format("%.4f g", massG));
            
            binding.resultCelsius.setText(String.format("%.2f °C", tempC));
            binding.resultFahrenheit.setText(String.format("%.2f °F", tempF));
            binding.resultKelvin.setText(String.format("%.2f K", tempK));
            
            binding.resultLong2Decimal.setText(String.format("%.6f°", long2));
            binding.resultLong2Radians.setText(String.format("%.6f rad", long2Rad));
            
            binding.errorText.setVisibility(View.GONE);
            
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "Valores inválidos", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
