/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.gr03.controller;

import ec.edu.eurekabank.model.Movimiento;
import ec.edu.eurekabank.model.Cuenta;
import ec.edu.eurekabank.service.EurekaService;
import java.util.ArrayList;
import java.util.List;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;

/**
 *
 * @author DELL
 */
@WebService(serviceName = "WSEureka")
public class WSEureka {

    /**
     * Web service operation
     * @param cuenta
     * @return Retorna la lista de movimientos de la cuenta
     */
    @WebMethod(operationName = "traerMovimientos")
    @WebResult(name = "movimiento")
    public List<Movimiento> traerMovimientos(@WebParam(name = "cuenta") String cuenta) {
        List<Movimiento> lista;
        try {
            EurekaService service = new EurekaService();
            lista = service.leerMovimientos(cuenta);
        } catch (Exception e) {
            lista = new ArrayList<>();
        }
        return lista;
    }

    /**
     * Web service operation
     * @param cuenta
     * @param importe
     * @return Estado 1 o -1
     */
    @WebMethod(operationName = "regDeposito")
    @WebResult(name = "estado")
    public int regDeposito(@WebParam(name = "cuenta") String cuenta, @WebParam(name = "importe") double importe) {
        int estado;
        String codEmp = "0001";
        try {
            EurekaService service = new EurekaService();
            service.registrarDeposito(cuenta, importe, codEmp);
            estado = 1;
        } catch (Exception e) {
            estado = -1;
        }
        return estado;
    }

    /**
     * Web service operation
     * @param cuenta
     * @param importe
     * @return Estado 1 o -1
     */
    @WebMethod(operationName = "regRetiro")
    @WebResult(name = "estado")
    public int regRetiro(@WebParam(name = "cuenta") String cuenta, @WebParam(name = "importe") double importe) {
        int estado;
        String codEmp = "0001";
        try {
            EurekaService service = new EurekaService();
            service.registrarRetiro(cuenta, importe, codEmp);
            estado = 1;
        } catch (Exception e) {
            estado = -1;
        }
        return estado;
    }

    /**
     * Web service operation
     * @param cuentaOrigen
     * @param cuentaDestino
     * @param importe
     * @return Estado 1 o -1
     */
    @WebMethod(operationName = "regTransferencia")
    @WebResult(name = "estado")
    public int regTransferencia(
        @WebParam(name = "cuentaOrigen") String cuentaOrigen,
        @WebParam(name = "cuentaDestino") String cuentaDestino,
        @WebParam(name = "importe") double importe) {
        int estado;
        String codEmp = "0001";
        try {
            EurekaService service = new EurekaService();
            service.registrarTransferencia(cuentaOrigen, cuentaDestino, importe, codEmp);
            estado = 1;
        } catch (Exception e) {
            estado = -1;
        }
        return estado;
    }
    
    
    @WebMethod(operationName = "login")
    public boolean login(@WebParam(name = "username") String username, @WebParam(name = "password") String password) {
        EurekaService service = new EurekaService();
        return service.login(username, password);
    }
    
    /**
     * Web service operation
     * @return Retorna la lista de todas las cuentas activas con sus balances
     */
    @WebMethod(operationName = "traerBalances")
    @WebResult(name = "cuenta")
    public List<Cuenta> traerBalances() {
        List<Cuenta> lista;
        try {
            EurekaService service = new EurekaService();
            lista = service.leerBalances();
        } catch (Exception e) {
            lista = new ArrayList<>();
        }
        return lista;
    }
}
