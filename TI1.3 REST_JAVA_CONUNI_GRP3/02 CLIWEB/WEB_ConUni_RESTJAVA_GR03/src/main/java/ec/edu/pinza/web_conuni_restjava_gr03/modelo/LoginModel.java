package ec.edu.pinza.web_conuni_restjava_gr03.modelo;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import java.io.IOException;
import java.net.http.HttpResponse;

public class LoginModel {

    private final RestClient restClient = new RestClient();

    public boolean autenticar(String usuario, String contrasena) {
        JsonObject payload = Json.createObjectBuilder()
                .add("username", usuario)
                .add("password", contrasena)
                .build();

        try {
            HttpResponse<String> response = restClient.post("/Auth", payload.toString());

            if (response.statusCode() == 200) {
                return Boolean.parseBoolean(response.body().trim());
            }

            System.err.println("Autenticacion REST fallida, codigo: " + response.statusCode());
        } catch (IOException e) {
            System.err.println("Error al conectar con Auth REST: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Solicitud interrumpida hacia Auth REST");
        }

        return false;
    }
}
