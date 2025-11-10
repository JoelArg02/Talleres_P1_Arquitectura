package ec.edu.gr03.controller;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EurekaBankController {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:61210/ec.edu.monster.controlador";

    public EurekaBankController() {
        client = jakarta.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("MovimientoController.svc");
    }

    public Response login(String username, String password) throws ClientErrorException {
        return webTarget.path("login")
                .queryParam("username", username)
                .queryParam("password", password)
                .request()
                .post(Entity.text(""), Response.class);
    }

    public Response regDeposito(String cuenta, double importe) throws ClientErrorException {
        return webTarget.path("deposito")
                .queryParam("cuenta", cuenta)
                .queryParam("importe", importe)
                .request()
                .post(Entity.text(""), Response.class);
    }

    public Response regRetiro(String cuenta, double importe) throws ClientErrorException {
        return webTarget.path("retiro")
                .queryParam("cuenta", cuenta)
                .queryParam("importe", importe)
                .request()
                .post(Entity.text(""), Response.class);
    }

    public Response regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) throws ClientErrorException {
        return webTarget.path("transferencia")
                .queryParam("cuentaOrigen", cuentaOrigen)
                .queryParam("cuentaDestino", cuentaDestino)
                .queryParam("importe", importe)
                .request()
                .post(Entity.text(""), Response.class);
    }

    public <T> T traerMovimientos(Class<T> responseType, String cuenta) throws ClientErrorException {
        return webTarget.path("movimientos").path(cuenta)
                .request()
                .get(responseType);
    }

    public void close() {
        client.close();
    }
    
    public static LocalDateTime parseFechaDotNet(String fechaDotNet) {
        Pattern pattern = Pattern.compile("/Date\\((\\d+)([+-]\\d{4})?\\)/");
        Matcher matcher = pattern.matcher(fechaDotNet);

        if (matcher.matches()) {
            long millis = Long.parseLong(matcher.group(1));
            Instant instant = Instant.ofEpochMilli(millis);
            return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        }

        throw new IllegalArgumentException("Formato de fecha no válido: " + fechaDotNet);
    }
}
