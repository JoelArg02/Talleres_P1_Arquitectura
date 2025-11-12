package ec.edu.gr03.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class Cuenta implements Serializable {
    
    @JsonProperty("NumeroCuenta")
    private String numeroCuenta;
    
    @JsonProperty("NombreCliente")
    private String nombreCliente;
    
    @JsonProperty("Saldo")
    private Double saldo;
    
    @JsonProperty("Moneda")
    private String moneda;
    
    @JsonProperty("Estado")
    private String estado;
    
    public Cuenta() {}
    
    // Getters y Setters
    
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
    
    public Double getSaldo() {
        return saldo;
    }
    
    public void setSaldo(Double saldo) {
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
