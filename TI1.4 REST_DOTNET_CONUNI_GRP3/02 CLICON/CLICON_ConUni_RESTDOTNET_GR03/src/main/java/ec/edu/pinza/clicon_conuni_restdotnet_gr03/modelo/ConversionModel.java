package ec.edu.pinza.clicon_conuni_restdotnet_gr03.modelo;

import ec.edu.pinza.clicon_conuni_restdotnet_gr03.modelo.util.JsonUtils;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversionModel {

    private final HttpClient httpClient;
    private final String baseUrl;

    public ConversionModel(HttpClient httpClient, String baseUrl) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
    }

    public ConversionResult convertirLongitud(double valor, int unidadOrigen, int unidadDestino)
            throws IOException, InterruptedException {
        String payload = """
            {
              "value": %s,
              "fromUnit": %d,
              "toUnit": %d
            }
            """.formatted(
                Double.toString(valor),
                unidadOrigen,
                unidadDestino
            );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(buildUri("/Length/convert"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        String body = response.body();

        if (status == 200) {
            return parseConversionResponse(body);
        }

        throw buildHttpException(status, body);
    }

    private URI buildUri(String path) {
        String normalizedBase = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        String normalizedPath = path.startsWith("/") ? path : "/" + path;
        return URI.create(normalizedBase + normalizedPath);
    }

    private ConversionResult parseConversionResponse(String body) throws IOException {
        if (body == null || body.isBlank()) {
            throw new IOException("Respuesta vacia del servicio de conversion.");
        }
        Double originalValue = JsonUtils.readDouble(body, "originalValue");
        Double convertedValue = JsonUtils.readDouble(body, "convertedValue");
        String fromUnit = JsonUtils.readString(body, "fromUnit");
        String toUnit = JsonUtils.readString(body, "toUnit");
        String category = JsonUtils.readString(body, "category");
        String timestamp = JsonUtils.readString(body, "timestamp");

        if (originalValue == null || convertedValue == null) {
            throw new IOException("Respuesta invalida del servicio de conversion.");
        }
        return new ConversionResult(originalValue, fromUnit, convertedValue, toUnit, category, timestamp);
    }

    private IOException buildHttpException(int status, String body) {
        StringBuilder mensaje = new StringBuilder("Estado HTTP inesperado: ").append(status);
        if (body != null && !body.isBlank()) {
            String mensajeError = JsonUtils.readString(body, "message");
            String detalle = JsonUtils.readString(body, "details");
            if (mensajeError != null && !mensajeError.isBlank()) {
                mensaje.append(" - ").append(mensajeError);
            }
            if (detalle != null && !detalle.isBlank()) {
                mensaje.append(" (").append(detalle).append(")");
            }
        }
        return new IOException(mensaje.toString());
    }
}
