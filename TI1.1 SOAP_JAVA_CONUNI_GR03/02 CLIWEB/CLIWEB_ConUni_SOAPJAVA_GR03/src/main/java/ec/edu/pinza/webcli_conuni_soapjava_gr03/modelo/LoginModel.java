package ec.edu.pinza.webcli_conuni_soapjava_gr03.modelo;

import ec.edu.pinza.client.WSLogin;
import ec.edu.pinza.client.WSLogin_Service;
import jakarta.xml.ws.BindingProvider;
import java.util.Map;

public class LoginModel {

    private static final String ENDPOINT = "http://localhost:8080/WS_ConUni_SOAPJAVA_GR03/WSLogin";

    public boolean autenticar(String usuario, String contrasena) {
        try {
            WSLogin_Service service = new WSLogin_Service();
            WSLogin port = service.getWSLoginPort();

            Map<String, Object> ctx = ((BindingProvider) port).getRequestContext();
            ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ENDPOINT);

            return port.autenticar(usuario, contrasena);
        } catch (Exception e) {
            System.err.println("Error al conectar con servicio SOAP: " + e.getMessage());
            return false;
        }
    }
}
