package ec.edu.gr03.model;

import ec.edu.gr03.controller.Cuenta;
import ec.edu.gr03.controller.Movimiento;
import ec.edu.gr03.controller.WSEureka;
import ec.edu.gr03.controller.WSEureka_Service;
import java.util.List;

public class EurekaBankClient {

    public static boolean login(String username, String password) {
        WSEureka_Service service = new WSEureka_Service();
    WSEureka port = service.getWSEurekaPort();
        return port.login(username, password);
    }

    public static int regDeposito(String cuenta, double importe) {
        WSEureka_Service service = new WSEureka_Service();
        WSEureka port = service.getWSEurekaPort();
        return port.regDeposito(cuenta, importe);
    }

    public static int regRetiro(String cuenta, double importe) {
        WSEureka_Service service = new WSEureka_Service();
        WSEureka port = service.getWSEurekaPort();
        return port.regRetiro(cuenta, importe);
    }

    public static int regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) {
        WSEureka_Service service = new WSEureka_Service();
        WSEureka port = service.getWSEurekaPort();
        return port.regTransferencia(cuentaOrigen, cuentaDestino, importe);
    }

    public static List<Movimiento> traerMovimientos(String cuenta) {
        WSEureka_Service service = new WSEureka_Service();
        WSEureka port = service.getWSEurekaPort();
        return port.traerMovimientos(cuenta);
    }

    public static List<Cuenta> traerBalances() {
        WSEureka_Service service = new WSEureka_Service();
        WSEureka port = service.getWSEurekaPort();
        return port.traerBalances();
    }
}
