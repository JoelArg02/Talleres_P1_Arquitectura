/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.gr03.controller;

import ec.edu.gr03.model.Movimiento;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList; 
import java.util.Arrays;    
import java.util.Collections; 
import java.util.List;

public class EurekaBankClient {

    public static boolean login(String username, String password) {
        EurekaBankController ebc = new EurekaBankController();
        try {
            Response response = ebc.login(username, password);
            
            if (response.getStatus() == 200) {
                String result = response.readEntity(String.class);
                return Boolean.parseBoolean(result); // convierte "true"/"false" a boolean
            } else {
                System.err.println("Error: " + response.getStatus());
                return false;
            }
        } finally {
            ebc.close();
        }
    }

    public static int regDeposito(String cuenta, double importe) {
        EurekaBankController ebc = new EurekaBankController();
        try {
            Response response = ebc.regDeposito(cuenta, importe);
            return response.getStatus() == 200 ? 1 : 0;
        } finally {
            ebc.close();
        }
    }

    public static int regRetiro(String cuenta, double importe) {
        EurekaBankController ebc = new EurekaBankController();
        try {
            Response response = ebc.regRetiro(cuenta, importe);
            return response.getStatus() == 200 ? 1 : 0;
        } finally {
            ebc.close();
        }
    }

    public static int regTransferencia(String cuentaOrigen, String cuentaDestino, double importe) {
        EurekaBankController ebc = new EurekaBankController();
        try {
            Response response = ebc.regTransferencia(cuentaOrigen, cuentaDestino, importe);
            return response.getStatus() == 200 ? 1 : 0;
        } finally {
            ebc.close();
        }
    }

    public static List<Movimiento> traerMovimientos(String cuenta) {
        EurekaBankController ebc = new EurekaBankController();
        try {
            Movimiento[] array = ebc.traerMovimientos(Movimiento[].class, cuenta);
            
            // --- INICIO DE LA CORRECCIÓN ---
            // 1. Convertir el array a una lista mutable (ArrayList)
            List<Movimiento> movimientos = new ArrayList<>(Arrays.asList(array));
            
            // 2. Revertir la lista mutable usando Collections
            Collections.reverse(movimientos);
            
            // 3. Devolver la lista ya revertida
            return movimientos;
            // --- FIN DE LA CORRECCIÓN ---
            
        } finally {
            ebc.close();
        }
    }
    
    public static double ObtenerSaldo(List<Movimiento> movimientos) {
        double saldo = 0;
        for (Movimiento movimiento : movimientos) {
            if(movimiento.getAccion().equals("INGRESO")) {
                saldo += Double.parseDouble(movimiento.getImporte());
            } else {
                saldo -= Double.parseDouble(movimiento.getImporte());
            }
        }
        return saldo;
    }
    
    
}