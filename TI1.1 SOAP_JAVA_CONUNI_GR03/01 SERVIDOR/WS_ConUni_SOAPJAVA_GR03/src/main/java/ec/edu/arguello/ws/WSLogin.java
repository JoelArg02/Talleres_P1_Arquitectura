package ec.edu.arguello.ws;

import ec.edu.arguello.servicios.LoginServicio;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;

@WebService(serviceName = "WSLogin")
public class WSLogin {
    
    private LoginServicio loginServicio = new LoginServicio();

    @WebMethod(operationName = "autenticar")
    public boolean autenticar(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password) {
        
        System.out.println("Intento de login para usuario: " + username);
        return loginServicio.validarCredenciales(username, password);
    }
}