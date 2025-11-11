package ec.edu.gr03.service;

import ec.edu.gr03.ws.*;
import java.util.List;

/**
 * Cliente para consumir el servicio SOAP DOTNET de EurekaBank
 * Esta clase encapsula todas las operaciones disponibles en el Web Service
 * 
 * NOTA IMPORTANTE:
 * Antes de usar esta clase, debe:
 * 1. Asegurarse que el servidor SOAP DOTNET esté corriendo en http://localhost:55325/
 * 2. Ejecutar 'mvn clean compile' para generar las clases del WSDL
 * 3. Las clases generadas estarán en el paquete ec.edu.gr03.ws
 * 
 * WSDL: http://localhost:55325/ec.edu.monster.controlador/MovimientoController.svc?wsdl
 */
public class EurekaBankClient {
    
    private static IMovimientoController getPort() {
        MovimientoController service = new MovimientoController();
        return service.getBasicHttpBindingIMovimientoController();
    }
    
    /**
     * Realiza login de un usuario
     * @param username nombre de usuario
     * @param password contraseña
     * @return true si el login es exitoso, false en caso contrario
     */
    public static boolean login(String username, String password) {
        try {
            IMovimientoController port = getPort();
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
            IMovimientoController port = getPort();
            String result = port.registrarDeposito(cuenta, importe);
            return Integer.parseInt(result);
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
            IMovimientoController port = getPort();
            String result = port.registrarRetiro(cuenta, importe);
            return Integer.parseInt(result);
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
            IMovimientoController port = getPort();
            String result = port.registrarTransferencia(cuentaOrigen, cuentaDestino, importe);
            return Integer.parseInt(result);
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
            IMovimientoController port = getPort();
            ArrayOfMovimiento result = port.obtenerPorCuenta(cuenta);
            return (result != null) ? result.getMovimiento() : null;
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
            IMovimientoController port = getPort();
            ArrayOfCuenta result = port.traerBalances();
            return (result != null) ? result.getCuenta() : null;
        } catch (Exception e) {
            System.err.println("Error al traer balances: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
