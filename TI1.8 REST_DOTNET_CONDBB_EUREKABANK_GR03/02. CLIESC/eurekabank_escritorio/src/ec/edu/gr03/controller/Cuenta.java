package ec.edu.gr03.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Cuenta {
    @JsonProperty("NumeroCuenta")
    private String numeroCuenta;
    
    @JsonProperty("NombreCliente")
    private String nombreCliente;
    
    @JsonProperty("Saldo")
    private double saldo;
    
    @JsonProperty("Moneda")
    private String moneda;
    
    @JsonProperty("Estado")
    private String estado;

    public Cuenta() {
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
