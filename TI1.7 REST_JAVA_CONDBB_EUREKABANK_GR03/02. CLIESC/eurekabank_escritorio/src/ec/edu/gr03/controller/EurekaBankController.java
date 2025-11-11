package ec.edu.gr03.controller;

import jakarta.ws.rs.ClientErrorException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

public class EurekaBankController {

    private WebTarget webTarget;
    private Client client;
    private static final String BASE_URI = "http://localhost:8080/eurekabank/api";

    public EurekaBankController() {
        client = jakarta.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("eureka");
    }

    public Response login(String username, String password) throws ClientErrorException {
        return webTarget.path("login")
                .queryParam("username", username)
                .queryParam("password", password)
                .request()
                .post(null, Response.class);
    }

    public Response regDeposito(String cuenta, double importe) throws ClientErrorException {
        return webTarget.path("deposito")
                .queryParam("cuenta", cuenta)
                .queryParam("importe", importe)
                .request()
                .post(null, Response.class);
    }

    public Response regRetiro(String cuenta, double importe) throws ClientErrorException {
        return webTarget.path("retiro")
                .queryParam("cuenta", cuenta)
                .queryParam("importe", importe)
                .request()
                .post(null, Response.class);
    }

    public Response regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) throws ClientErrorException {
        return webTarget.path("transferencia")
                .queryParam("cuentaOrigen", cuentaOrigen)
                .queryParam("cuentaDestino", cuentaDestino)
                .queryParam("importe", importe)
                .request()
                .post(null, Response.class);
    }

    public <T> T traerMovimientos(Class<T> responseType, String cuenta) throws ClientErrorException {
        return webTarget.path("movimientos").path(cuenta)
                .request()
                .get(responseType);
    }

    public <T> T traerBalances(Class<T> responseType) throws ClientErrorException {
        return webTarget.path("balances")
                .request()
                .get(responseType);
    }

    public void close() {
        client.close();
    }
}
