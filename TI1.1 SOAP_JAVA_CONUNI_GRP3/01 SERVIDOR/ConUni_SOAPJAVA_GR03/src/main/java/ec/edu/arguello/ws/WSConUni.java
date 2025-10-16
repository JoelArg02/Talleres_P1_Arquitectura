/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package ec.edu.arguello.ws;

import ec.edu.arguello.servicios.ConUniServicio;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;


@WebService(serviceName = "WSConUni")
public class WSConUni {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
     // Instanciar o inyectar la clase de servicio
    private final ConUniServicio conUniService = new ConUniServicio();
    
    /**
     * Operacion para convertir un valor de una unidad a otra.
     * @param value El valor a convertir.
     * @param inUnit La unidad inicial.
     * @param outUnit La unidad final.
     * @return El resultado de la conversion.
     */
    @WebMethod(operationName = "convertUnit")
    public double convertUnit(
            @WebParam(name = "value") double value,
            @WebParam(name = "inUnit") String inUnit,
            @WebParam(name = "outUnit") String outUnit) 
    {
        // Llama al método de la lógica de negocio
        return conUniService.conversion(value, inUnit, outUnit);
    }
    
}
