package ec.edu.espe.soap_conuni_clmov_gro3.arguello.Config;

import okhttp3.*;

public class SoapClientConfig {

    private static final MediaType XML = MediaType.get("text/xml; charset=utf-8");
    private static final OkHttpClient client = new OkHttpClient();

    private static final String BASE_URI = "http://10.0.2.2:8080/WS_ConUni_SOAPJAVA_GR03";

    private final String endpoint;

    public SoapClientConfig(String servicePath) {
        this.endpoint = BASE_URI + servicePath;
    }

    public String call(String soapBody) {
        try {
            RequestBody body = RequestBody.create(soapBody, XML);
            Request request = new Request.Builder()
                    .url(endpoint)
                    .post(body)
                    .addHeader("Content-Type", "text/xml; charset=utf-8")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("HTTP error: " + response.code() + " - " + response.message());
                }
                return response.body() != null ? response.body().string() : "";
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error al invocar el servicio SOAP: " + endpoint, ex);
        }
    }

    public String getEndpoint() {
        return endpoint;
    }
}
