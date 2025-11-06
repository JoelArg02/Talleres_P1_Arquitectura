/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.pinza.clicon_conuni_soapjava_gr03.modelo;

import ec.edu.pinza.client.WSConUni_Service;
import ec.edu.pinza.client.WSConUni;
import jakarta.xml.ws.BindingProvider;

/**
 *
 * @author pinza
 */
public class ConversionModel {

    public double conversionUnidades(double valor, String entrada, String salida) {
        WSConUni_Service servicio = new WSConUni_Service();
        WSConUni op = servicio.getWSConUniPort();
        String newEndpoint = "http://localhost:8080/WS_ConUni_SOAPJAVA_GR03/WSConUni";
        ((BindingProvider) op).getRequestContext().put(
                BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                newEndpoint
        );
        try {
            System.out.println("Enviando conversion: " + valor + " " + entrada + " -> " + salida);
            double resultado = op.convertUnit(valor, entrada, salida);
            System.out.println("Resultado recibido: " + resultado);
            return resultado;
        } catch (Exception e) {
            System.err.println("Error al convertir: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    };
}
