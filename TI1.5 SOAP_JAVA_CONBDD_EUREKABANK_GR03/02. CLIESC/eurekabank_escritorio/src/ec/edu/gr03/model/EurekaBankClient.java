/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.gr03.model;
import ec.edu.gr03.controller.*;

public class EurekaBankClient {

    public static boolean login(java.lang.String username, java.lang.String password) {
        ec.edu.gr03.controller.WSEureka_Service service = new ec.edu.gr03.controller.WSEureka_Service();
        ec.edu.gr03.controller.WSEureka port = service.getWSEurekaPort();
        return port.login(username, password);
    }

    public static int regDeposito(java.lang.String cuenta, double importe) {
        ec.edu.gr03.controller.WSEureka_Service service = new ec.edu.gr03.controller.WSEureka_Service();
        ec.edu.gr03.controller.WSEureka port = service.getWSEurekaPort();
        return port.regDeposito(cuenta, importe);
    }

    public static int regRetiro(java.lang.String cuenta, double importe) {
        ec.edu.gr03.controller.WSEureka_Service service = new ec.edu.gr03.controller.WSEureka_Service();
        ec.edu.gr03.controller.WSEureka port = service.getWSEurekaPort();
        return port.regRetiro(cuenta, importe);
    }

    public static int regTransferencia(java.lang.String cuentaOrigen, java.lang.String cuentaDestino, double importe) {
        ec.edu.gr03.controller.WSEureka_Service service = new ec.edu.gr03.controller.WSEureka_Service();
        ec.edu.gr03.controller.WSEureka port = service.getWSEurekaPort();
        return port.regTransferencia(cuentaOrigen, cuentaDestino, importe);
    }

    public static java.util.List<ec.edu.gr03.controller.Movimiento> traerMovimientos(java.lang.String cuenta) {
        ec.edu.gr03.controller.WSEureka_Service service = new ec.edu.gr03.controller.WSEureka_Service();
        ec.edu.gr03.controller.WSEureka port = service.getWSEurekaPort();
        return port.traerMovimientos(cuenta);
    }

    public static java.util.List<ec.edu.gr03.controller.Cuenta> traerBalances() {
        ec.edu.gr03.controller.WSEureka_Service service = new ec.edu.gr03.controller.WSEureka_Service();
        ec.edu.gr03.controller.WSEureka port = service.getWSEurekaPort();
        return port.traerBalances();
    }
    
}
