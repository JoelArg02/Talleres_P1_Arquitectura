package ec.edu.pinza.clicon_conuni_restjava_gr03.modelo;

import ec.edu.pinza.clicon_conuni_restjava_gr03.modelo.util.JsonUtils;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginModel {

    private final HttpClient httpClient;
    private final String baseUrl;

    public LoginModel(HttpClient httpClient, String baseUrl) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
    }

    public boolean autenticar(String usuario, String contrasena) throws IOException, InterruptedException {
        String payload = """
            {
              "username": "%s",
              "password": "%s"
            }
            """.formatted(JsonUtils.escape(usuario), JsonUtils.escape(contrasena));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(buildUri("/Auth"))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return Boolean.parseBoolean(response.body().trim());
        }

        throw new IOException("Estado HTTP inesperado: " + response.statusCode());
    }

    private URI buildUri(String path) {
        String normalizedBase = baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
        String normalizedPath = path.startsWith("/") ? path : "/" + path;
        return URI.create(normalizedBase + normalizedPath);
    }
}
