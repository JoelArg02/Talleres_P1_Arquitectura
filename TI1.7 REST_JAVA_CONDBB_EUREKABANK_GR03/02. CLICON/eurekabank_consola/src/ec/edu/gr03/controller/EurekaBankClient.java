/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.gr03.controller;

import ec.edu.gr03.model.Movimiento;
import ec.edu.gr03.model.Cuenta;
import jakarta.ws.rs.core.Response;
import java.util.List;

public class EurekaBankClient {

    public static boolean login(String username, String password) {
        EurekaBankController ebc = new EurekaBankController();
        try {
            Response response = ebc.login(username, password);
            return response.getStatus() == 200;
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
            return List.of(array);
        } finally {
            ebc.close();
        }
    }

    public static List<Cuenta> traerBalances() {
        EurekaBankController ebc = new EurekaBankController();
        try {
            Cuenta[] array = ebc.traerBalances(Cuenta[].class);
            return List.of(array);
        } finally {
            ebc.close();
        }
    }
}
