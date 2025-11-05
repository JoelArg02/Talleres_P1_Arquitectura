package ec.edu.espe.soap_conuni_clmov_gro3.arguello.Config;

import android.util.Log;
import okhttp3.*;

public class SoapClientConfig {

    private static final String TAG = "SoapClientConfig";
    private static final MediaType XML = MediaType.get("text/xml; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

//    private static final String BASE_URI = "http://10.0.2.2:8080/WS_ConUni_SOAPJAVA_GR03/";

    private static final String BASE_URI = "http://10.1.200.12:8080/WS_ConUni_SOAPJAVA_GR03/";
    private final String endpoint;

    public SoapClientConfig(String servicePath) {
        this.endpoint = BASE_URI + servicePath;
    }

    public String call(String soapBody) {
        Log.d(TAG, "=== SOAP REQUEST ===");
        Log.d(TAG, "Endpoint: " + endpoint);
        Log.d(TAG, "Request Body: " + soapBody);
        
        try {
            RequestBody body = RequestBody.create(soapBody, XML);
            Request request = new Request.Builder()
                    .url(endpoint)
                    .post(body)
                    .addHeader("Content-Type", "text/xml; charset=utf-8")
                    .build();

            Log.d(TAG, "Enviando petición...");
            
            try (Response response = client.newCall(request).execute()) {
                Log.d(TAG, "=== SOAP RESPONSE ===");
                Log.d(TAG, "HTTP Code: " + response.code());
                Log.d(TAG, "HTTP Message: " + response.message());
                
                if (!response.isSuccessful()) {
                    String errorMsg = "HTTP error: " + response.code() + " - " + response.message();
                    Log.e(TAG, errorMsg);
                    throw new RuntimeException(errorMsg);
                }
                
                String responseBody = response.body() != null ? response.body().string() : "";
                Log.d(TAG, "Response Body: " + responseBody);
                Log.d(TAG, "===================");
                
                return responseBody;
            }
        } catch (Exception ex) {
            Log.e(TAG, "=== SOAP ERROR ===");
            Log.e(TAG, "Endpoint: " + endpoint);
            Log.e(TAG, "Error: " + ex.getMessage(), ex);
            Log.e(TAG, "==================");
            throw new RuntimeException("Error al invocar el servicio SOAP: " + endpoint, ex);
        }
    }

    public String getEndpoint() {
        return endpoint;
    }
}
