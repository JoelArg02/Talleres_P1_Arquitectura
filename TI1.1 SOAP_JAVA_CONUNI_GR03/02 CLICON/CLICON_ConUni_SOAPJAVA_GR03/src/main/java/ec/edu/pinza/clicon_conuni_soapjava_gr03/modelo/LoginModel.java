/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.pinza.clicon_conuni_soapjava_gr03.modelo;

import ec.edu.pinza.client.WSLogin;
import ec.edu.pinza.client.WSLogin_Service;

/**
 *
 * @author pinza
 */
public class LoginModel {

    public boolean autenticar(String usuario, String contrasena) {

        //1. Creo el servicio
        WSLogin_Service servicio = new WSLogin_Service();
        //2. Instancio el puerto que implementa interfaz de operaciones.
        WSLogin op = servicio.getWSLoginPort();

        try {
            if (op.autenticar(usuario, contrasena)) {
                return true;
            }
        } catch (Exception e) {
            System.err.println("Error al autenticar con el servicio SOAP: " + e.getMessage());
            return false;
        }
        return false;
    }
;
}
