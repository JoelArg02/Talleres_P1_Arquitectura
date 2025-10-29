package ec.edu.arguello.ws;

import ec.edu.arguello.servicios.ConUniServicio;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;


@WebService(serviceName = "WSConUni")
public class WSConUni {
    private final ConUniServicio conUniService = new ConUniServicio();
    
  
    @WebMethod(operationName = "convertUnit")
    public double convertUnit(
            @WebParam(name = "value") double value,
            @WebParam(name = "inUnit") String inUnit,
            @WebParam(name = "outUnit") String outUnit) 
    {
        return conUniService.conversion(value, inUnit, outUnit);
    }
    
}
