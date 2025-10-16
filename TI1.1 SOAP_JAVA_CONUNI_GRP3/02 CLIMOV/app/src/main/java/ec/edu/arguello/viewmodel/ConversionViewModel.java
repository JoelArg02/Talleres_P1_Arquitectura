package ec.edu.arguello.viewmodel;

import android.os.Handler;
import android.os.Looper;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ec.edu.arguello.ws.WSConUni;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ViewModel (Controlador en MVC) que maneja la lógica de conversión de unidades.
 * Realiza las llamadas al servicio SOAP en background thread y actualiza la UI mediante LiveData.
 */
public class ConversionViewModel extends ViewModel {

    private final WSConUni wsConUni = new WSConUni();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    // LiveData para el resultado de la conversión
    private final MutableLiveData<Double> conversionResult = new MutableLiveData<>();
    
    // LiveData para mensajes de error
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
    
    // LiveData para estado de carga
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public LiveData<Double> getConversionResult() {
        return conversionResult;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    /**
     * Realiza la conversión de unidades llamando al servicio SOAP en un thread secundario.
     */
    public void convertUnit(double value, String fromUnit, String toUnit) {
        isLoading.setValue(true);
        errorMessage.setValue(null);
        
        executorService.execute(() -> {
            try {
                // Llamada al servicio SOAP (puede tardar)
                double result = wsConUni.convertUnit(value, fromUnit, toUnit);
                
                // Actualizar UI en el thread principal
                mainHandler.post(() -> {
                    conversionResult.setValue(result);
                    isLoading.setValue(false);
                });
                
            } catch (Exception e) {
                // Manejar error y actualizar UI
                mainHandler.post(() -> {
                    errorMessage.setValue("Error: " + e.getMessage());
                    isLoading.setValue(false);
                });
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdown();
    }
}
