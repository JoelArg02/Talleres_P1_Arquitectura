package ec.edu.gr03.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public class Movimiento implements Serializable {
    
    @JsonProperty("Cuenta")
    private String cuenta;
    
    @JsonProperty("NroMov")
    private Integer nroMov;
    
    @JsonProperty("Fecha")
    private String fecha;
    
    @JsonProperty("Tipo")
    private String tipo;
    
    @JsonProperty("Accion")
    private String accion;
    
    @JsonProperty("Importe")
    private Double importe;
    
    public Movimiento() {}
    
    // Getters y Setters
    
    public String getCuenta() {
        return cuenta;
    }
    
    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }
    
    public Integer getNroMov() {
        return nroMov;
    }
    
    public void setNroMov(Integer nroMov) {
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
    
    public Double getImporte() {
        return importe;
    }
    
    public void setImporte(Double importe) {
        this.importe = importe;
    }
}
