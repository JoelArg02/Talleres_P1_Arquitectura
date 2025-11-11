package ec.edu.gr03.service;

import ec.edu.gr03.ws.*;
import java.util.List;

/**
 * Cliente para consumir el servicio SOAP de EurekaBank
 * Esta clase encapsula todas las operaciones disponibles en el Web Service
 * 
 * NOTA IMPORTANTE:
 * Antes de usar esta clase, debe:
 * 1. Asegurarse que el servidor SOAP esté corriendo en http://localhost:8080/eurekabank-1/
 * 2. Ejecutar 'mvn clean compile' para generar las clases del WSDL
 * 3. Las clases generadas estarán en el paquete ec.edu.gr03.ws
 */
public class EurekaBankClient {
    
    private static WSEureka getPort() {
        WSEureka_Service service = new WSEureka_Service();
        return service.getWSEurekaPort();
    }
    
    /**
     * Realiza login de un usuario
     * @param username nombre de usuario
     * @param password contraseña
     * @return true si el login es exitoso, false en caso contrario
     */
    public static boolean login(String username, String password) {
        try {
            WSEureka port = getPort();
            return port.login(username, password);
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
            WSEureka port = getPort();
            return port.regDeposito(cuenta, importe);
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
            WSEureka port = getPort();
            return port.regRetiro(cuenta, importe);
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
            WSEureka port = getPort();
            return port.regTransferencia(cuentaOrigen, cuentaDestino, importe);
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
            WSEureka port = getPort();
            return port.traerMovimientos(cuenta);
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
            WSEureka port = getPort();
            return port.traerBalances();
        } catch (Exception e) {
            System.err.println("Error al traer balances: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
