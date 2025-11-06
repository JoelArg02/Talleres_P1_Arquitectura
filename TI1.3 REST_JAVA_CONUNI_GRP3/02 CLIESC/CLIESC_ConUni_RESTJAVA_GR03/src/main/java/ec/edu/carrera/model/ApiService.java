/**
 *
 * @author nahir
 */
package ec.edu.carrera.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;

public class ApiService {
    
    // URL Base corregida con la información de tu servidor
    private static final String BASE_URL = "http://localhost:8080/WS_ConUni_RESTJAVA_GR03/api";

    private final HttpClient httpClient;
    private final Gson gson;

    public ApiService() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
    }

    // Los métodos (login, getSupportedTypes, etc.) son los mismos de la respuesta anterior
    
    public boolean login(String username, String password) throws Exception {
        User user = new User(username, password);
        String jsonBody = gson.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .uri(URI.create(BASE_URL + "/Auth"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), boolean.class);
    }

    public List<String> getSupportedTypes() throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + "/ConUni/supportedTypes"))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        java.lang.reflect.Type listType = new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public List<String> getSupportedUnits(String type) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL + "/ConUni/supportedUnits/" + type))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        java.lang.reflect.Type listType = new TypeToken<List<String>>(){}.getType();
        return gson.fromJson(response.body(), listType);
    }

    public double convert(ConUni conUni) throws Exception {
        String jsonBody = gson.toJson(conUni);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .uri(URI.create(BASE_URL + "/ConUni/convertUnit"))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(response.body(), Double.class);
    }
}
