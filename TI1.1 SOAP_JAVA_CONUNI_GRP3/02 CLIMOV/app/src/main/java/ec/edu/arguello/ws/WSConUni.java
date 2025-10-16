package ec.edu.arguello.ws;

import ec.edu.arguello.servicios.ConUniServicio;

/**
 * Clase adaptadora que expone métodos para la UI y delega en ConUniServicio.
 * No utiliza anotaciones @WebService en Android; simplemente actúa como fachada.
 */
public class WSConUni {

    public String hello(String txt) {
        return "Hello " + txt + " !";
    }

    private final ConUniServicio conUniService = new ConUniServicio();

    public double convertUnit(double value, String inUnit, String outUnit) {
        return conUniService.conversion(value, inUnit, outUnit);
    }

}
