package ec.edu.espe.soap_conuni_clmov_gro3.arguello.ws;

import ec.edu.espe.soap_conuni_clmov_gro3.arguello.servicios.ConUniServicio;

public class WSConUni {

    public String hello(String txt) {
        return "Hello " + txt + " !";
    }

    private final ConUniServicio conUniService = new ConUniServicio();

    public double convertUnit(double value, String inUnit, String outUnit) {
        return conUniService.conversion(value, inUnit, outUnit);
    }

}
