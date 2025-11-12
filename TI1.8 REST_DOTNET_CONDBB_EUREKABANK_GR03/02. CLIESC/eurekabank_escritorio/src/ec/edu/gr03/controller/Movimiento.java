package ec.edu.gr03.controller;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movimiento {
    @JsonProperty("Cuenta")
    private String cuenta;
    
    @JsonProperty("NroMov")
    private String nroMov;
    
    @JsonProperty("Fecha")
    private String fecha;
    
    @JsonProperty("Tipo")
    private String tipo;
    
    @JsonProperty("Accion")
    private String accion;
    
    @JsonProperty("Importe")
    private double importe;

    public Movimiento() {
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNroMov() {
        return nroMov;
    }

    public void setNroMov(String nroMov) {
        this.nroMov = nroMov;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }
}
