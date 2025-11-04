package ec.edu.pinza.clicon_conuni_restjava_gr03.modelo;

import ec.edu.pinza.clicon_conuni_restjava_gr03.modelo.util.JsonUtils;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversionModel {

    private static final String DEFAULT_TYPE = "longitud";

    private final HttpClient httpClient;
    private final String baseUrl;

    public ConversionModel(HttpClient httpClient, String baseUrl) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
    }

    public double conversionUnidades(double valor, String unidadOrigen, String unidadDestino) throws IOException, InterruptedException {
        String payload = """
            {
              "type": "%s",
              "value": %s,
              "inUnit": "%s",
              "outUnit": "%s"
            }
            """.formatted(
                JsonUtils.escape(resolveType()),
                Double.toString(valor),
                JsonUtils.escape(unidadOrigen),
                JsonUtils.escape(unidadDestino)
            );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(buildUri("/ConUni/convertUnit"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            String body = response.body().trim();
            try {
                return Double.parseDouble(body);
            } catch (NumberFormatException e) {
                throw new IOException("No se pudo interpretar la respuesta: " + body, e);
            }
        }

        throw new IOException("Estado HTTP inesperado: " + response.statusCode());
    }

    private String resolveType() {
        String configured = System.getenv("CONUNI_CONVERSION_TYPE");
        if (configured != null && !configured.isBlank()) {
            return configured.trim();
        }
        String property = System.getProperty("conuni.conversion.type");
        if (property != null && !property.isBlank()) {
            return property.trim();
        }
        return DEFAULT_TYPE;
    }

    private URI buildUri(String path) {
        String normalizedBase = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        String normalizedPath = path.startsWith("/") ? path : "/" + path;
        return URI.create(normalizedBase + normalizedPath);
    }
}
