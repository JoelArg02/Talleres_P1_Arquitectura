/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.gr03.model;

import java.util.List;
import org.datacontract.schemas._2004._07.ec_edu_monster.ArrayOfMovimiento;
import org.datacontract.schemas._2004._07.ec_edu_monster.Movimiento;

public class EurekaBankClient {
   
    public static ArrayOfMovimiento traerMovimientos(java.lang.String cuenta) {
        org.tempuri.MovimientoController service = new org.tempuri.MovimientoController();
        org.tempuri.IMovimientoController port = service.getBasicHttpBindingIMovimientoController();
        return port.obtenerPorCuenta(cuenta);
    }

    public static String regDeposito(java.lang.String cuenta, java.lang.Double importe) {
        org.tempuri.MovimientoController service = new org.tempuri.MovimientoController();
        org.tempuri.IMovimientoController port = service.getBasicHttpBindingIMovimientoController();
        return port.registrarDeposito(cuenta, importe);
    }

    public static String regRetiro(java.lang.String cuenta, java.lang.Double importe) {
        org.tempuri.MovimientoController service = new org.tempuri.MovimientoController();
        org.tempuri.IMovimientoController port = service.getBasicHttpBindingIMovimientoController();
        return port.registrarRetiro(cuenta, importe);
    }

    public static String regTransferencia(java.lang.String cuentaOrigen, java.lang.String cuentaDestino, java.lang.Double importe) {
        org.tempuri.MovimientoController service = new org.tempuri.MovimientoController();
        org.tempuri.IMovimientoController port = service.getBasicHttpBindingIMovimientoController();
        return port.registrarTransferencia(cuentaOrigen, cuentaDestino, importe);
    }

    public static Boolean login(java.lang.String username, java.lang.String password) {
        org.tempuri.MovimientoController service = new org.tempuri.MovimientoController();
        org.tempuri.IMovimientoController port = service.getBasicHttpBindingIMovimientoController();
        return port.login(username, password);
    }
    
    public static org.datacontract.schemas._2004._07.ec_edu_monster.ArrayOfCuenta traerBalances() {
        org.tempuri.MovimientoController service = new org.tempuri.MovimientoController();
        org.tempuri.IMovimientoController port = service.getBasicHttpBindingIMovimientoController();
        return port.traerBalances();
    }
    
    public static double ObtenerSaldo(List<Movimiento> movimientos) {
        double saldo = 0;
        for (Movimiento movimiento : movimientos) {
            if(movimiento.getAccion().getValue().equals("INGRESO")) {
                saldo += movimiento.getImporte();
            } else {
                saldo -= movimiento.getImporte();
            }
        }
        return saldo;
    }

    
}
