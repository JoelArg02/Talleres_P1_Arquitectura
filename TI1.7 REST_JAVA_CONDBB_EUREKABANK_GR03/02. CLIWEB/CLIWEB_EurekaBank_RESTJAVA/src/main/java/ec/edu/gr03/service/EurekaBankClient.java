package ec.edu.gr03.service;

import ec.edu.gr03.model.Cuenta;
import ec.edu.gr03.model.Movimiento;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Cliente para consumir el servicio REST de EurekaBank
 * Esta clase encapsula todas las operaciones disponibles en el Web Service REST
 * 
 * NOTA IMPORTANTE:
 * Antes de usar esta clase, debe:
 * 1. Asegurarse que el servidor REST esté corriendo en http://localhost:8080/eurekabank/
 * 2. El servidor debe tener todos los endpoints REST configurados
 */
public class EurekaBankClient {
    
    private static final String BASE_URI = "http://localhost:8080/eurekabank/api/eureka";
    
    private static WebTarget getWebTarget() {
        Client client = ClientBuilder.newClient();
        return client.target(BASE_URI);
    }
    
    /**
     * Realiza login de un usuario
     * @param username nombre de usuario
     * @param password contraseña
     * @return true si el login es exitoso, false en caso contrario
     */
    public static boolean login(String username, String password) {
        try {
            WebTarget target = getWebTarget();
            Response response = target.path("login")
                    .queryParam("username", username)
                    .queryParam("password", password)
                    .request(MediaType.APPLICATION_JSON)
                    .post(null);
            
            boolean result = response.getStatus() == 200;
            response.close();
            return result;
        } catch (Exception e) {
            System.err.println("Error en login: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Registra un depósito en una cuenta
     * @param cuenta número de cuenta
     * @param importe monto a depositar
     * @return 1 si fue exitoso, -1 en caso de error
     */
    public static int regDeposito(String cuenta, double importe) {
        try {
            WebTarget target = getWebTarget();
            Response response = target.path("deposito")
                    .queryParam("cuenta", cuenta)
                    .queryParam("importe", importe)
                    .request(MediaType.APPLICATION_JSON)
                    .post(null);
            
            int status = response.getStatus();
            response.close();
            return status == 200 ? 1 : -1;
        } catch (Exception e) {
            System.err.println("Error en depósito: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    
    /**
     * Registra un retiro de una cuenta
     * @param cuenta número de cuenta
     * @param importe monto a retirar
     * @return 1 si fue exitoso, -1 en caso de error
     */
    public static int regRetiro(String cuenta, double importe) {
        try {
            WebTarget target = getWebTarget();
            Response response = target.path("retiro")
                    .queryParam("cuenta", cuenta)
                    .queryParam("importe", importe)
                    .request(MediaType.APPLICATION_JSON)
                    .post(null);
            
            int status = response.getStatus();
            response.close();
            return status == 200 ? 1 : -1;
        } catch (Exception e) {
            System.err.println("Error en retiro: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    
    /**
     * Registra una transferencia entre dos cuentas
     * @param cuentaOrigen cuenta desde donde se transfiere
     * @param cuentaDestino cuenta hacia donde se transfiere
     * @param importe monto a transferir
     * @return 1 si fue exitoso, -1 en caso de error
     */
    public static int regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) {
        try {
            WebTarget target = getWebTarget();
            Response response = target.path("transferencia")
                    .queryParam("cuentaOrigen", cuentaOrigen)
                    .queryParam("cuentaDestino", cuentaDestino)
                    .queryParam("importe", importe)
                    .request(MediaType.APPLICATION_JSON)
                    .post(null);
            
            int status = response.getStatus();
            response.close();
            return status == 200 ? 1 : -1;
        } catch (Exception e) {
            System.err.println("Error en transferencia: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
    
    /**
     * Obtiene todos los movimientos de una cuenta
     * @param cuenta número de cuenta
     * @return lista de movimientos
     */
    public static List<Movimiento> traerMovimientos(String cuenta) {
        try {
            WebTarget target = getWebTarget();
            Response response = target.path("movimientos").path(cuenta)
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            
            if (response.getStatus() == 200) {
                List<Movimiento> movimientos = response.readEntity(new GenericType<List<Movimiento>>() {});
                response.close();
                return movimientos;
            }
            response.close();
            return null;
        } catch (Exception e) {
            System.err.println("Error al traer movimientos: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Obtiene los balances de todas las cuentas activas
     * @return lista de cuentas con sus balances
     */
    public static List<Cuenta> traerBalances() {
        try {
            WebTarget target = getWebTarget();
            Response response = target.path("balances")
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            
            if (response.getStatus() == 200) {
                List<Cuenta> balances = response.readEntity(new GenericType<List<Cuenta>>() {});
                response.close();
                return balances;
            }
            response.close();
            return null;
        } catch (Exception e) {
            System.err.println("Error al traer balances: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
