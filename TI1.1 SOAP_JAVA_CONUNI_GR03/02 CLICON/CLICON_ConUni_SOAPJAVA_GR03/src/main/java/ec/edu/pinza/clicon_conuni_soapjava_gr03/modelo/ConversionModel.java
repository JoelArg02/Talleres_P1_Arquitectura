/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.pinza.clicon_conuni_soapjava_gr03.modelo;

import ec.edu.pinza.client.WSConUni_Service;
import ec.edu.pinza.client.WSConUni;

/**
 *
 * @author pinza
 */
public class ConversionModel {

    public double conversionUnidades(double valor, String entrada, String salida) {
        WSConUni_Service servicio = new WSConUni_Service();
        WSConUni op = servicio.getWSConUniPort();
        try {
            return op.convertUnit(valor, entrada, salida);
        } catch (Exception e) {
            System.err.println("Error al convertir" + e.getMessage());
            return 0;
        }
    };
}
