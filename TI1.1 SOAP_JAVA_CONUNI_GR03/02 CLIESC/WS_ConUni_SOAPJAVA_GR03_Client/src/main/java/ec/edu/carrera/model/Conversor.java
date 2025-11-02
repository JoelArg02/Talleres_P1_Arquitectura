package ec.edu.carrera.model;

import ec.edu.carrera.ws.WSConUni;
import ec.edu.carrera.ws.WSConUni_Service;

/**
 *
 * @author nahir
 */
public class Conversor {

    private final WSConUni port;
    public Conversor() {
        WSConUni_Service service = new WSConUni_Service();
        this.port = service.getWSConUniPort();
    }
    
    public double convertUnit(int number, String inUnit, String outUnit) {
        return port.convertUnit(number, inUnit, outUnit);
    }
}
