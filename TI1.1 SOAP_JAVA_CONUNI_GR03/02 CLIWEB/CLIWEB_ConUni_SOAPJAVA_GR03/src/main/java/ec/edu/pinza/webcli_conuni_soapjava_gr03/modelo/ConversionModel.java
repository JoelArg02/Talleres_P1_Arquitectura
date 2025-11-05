/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.pinza.webcli_conuni_soapjava_gr03.modelo;

/**
 *
 * @author pinza
 */
import ec.edu.pinza.client.WSConUni;
import ec.edu.pinza.client.WSConUni_Service;
import jakarta.xml.ws.BindingProvider;
import java.util.Map;

public class ConversionModel {

    private static final String ENDPOINT = "http://192.168.137.1:8080/WS_ConUni_SOAPJAVA_GR03/WSConUni";

    public double convertir(double valor, String inUnit, String outUnit) {
        try {
            WSConUni_Service service = new WSConUni_Service();
            WSConUni port = service.getWSConUniPort();

            Map<String, Object> ctx = ((BindingProvider) port).getRequestContext();
            ctx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, ENDPOINT);

            return port.convertUnit(valor, inUnit, outUnit);
        } catch (Exception e) {
            System.err.println("Error al conectar con servicio SOAP: " + e.getMessage());
            return 0.0;
        }
    }
}
