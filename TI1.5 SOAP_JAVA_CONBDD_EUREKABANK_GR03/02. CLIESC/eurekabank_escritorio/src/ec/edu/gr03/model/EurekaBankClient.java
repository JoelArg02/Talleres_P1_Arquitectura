/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.gr03.model;

public class EurekaBankClient {

    public static boolean login(java.lang.String username, java.lang.String password) {
        ec.edu.eurekabank.controller.WSEureka_Service service = new ec.edu.eurekabank.controller.WSEureka_Service();
        ec.edu.eurekabank.controller.WSEureka port = service.getWSEurekaPort();
        return port.login(username, password);
    }

    public static int regDeposito(java.lang.String cuenta, double importe) {
        ec.edu.eurekabank.controller.WSEureka_Service service = new ec.edu.eurekabank.controller.WSEureka_Service();
        ec.edu.eurekabank.controller.WSEureka port = service.getWSEurekaPort();
        return port.regDeposito(cuenta, importe);
    }

    public static int regRetiro(java.lang.String cuenta, double importe) {
        ec.edu.eurekabank.controller.WSEureka_Service service = new ec.edu.eurekabank.controller.WSEureka_Service();
        ec.edu.eurekabank.controller.WSEureka port = service.getWSEurekaPort();
        return port.regRetiro(cuenta, importe);
    }

    public static int regTransferencia(java.lang.String cuentaOrigen, java.lang.String cuentaDestino, double importe) {
        ec.edu.eurekabank.controller.WSEureka_Service service = new ec.edu.eurekabank.controller.WSEureka_Service();
        ec.edu.eurekabank.controller.WSEureka port = service.getWSEurekaPort();
        return port.regTransferencia(cuentaOrigen, cuentaDestino, importe);
    }

    public static java.util.List<ec.edu.eurekabank.controller.Movimiento> traerMovimientos(java.lang.String cuenta) {
        ec.edu.eurekabank.controller.WSEureka_Service service = new ec.edu.eurekabank.controller.WSEureka_Service();
        ec.edu.eurekabank.controller.WSEureka port = service.getWSEurekaPort();
        return port.traerMovimientos(cuenta);
    }

    
}
