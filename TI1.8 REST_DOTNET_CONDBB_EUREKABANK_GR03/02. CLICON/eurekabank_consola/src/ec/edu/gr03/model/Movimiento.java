package ec.edu.gr03.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

public class Movimiento {
    @JsonProperty("Cuenta")
    private String cuenta;
    @JsonProperty("NroMov")
    private String nromov;
    @JsonProperty("Fecha")
    private String fecha;
    @JsonProperty("Tipo")
    private String tipo;
    @JsonProperty("Accion")
    private String accion;
    @JsonProperty("Importe")
    private String importe;

    public Movimiento() {
    }

    public Movimiento(String cuenta, String nromov, String fecha, String tipo, String accion, String importe) {
        this.cuenta = cuenta;
        this.nromov = nromov;
        this.fecha = fecha;
        this.tipo = tipo;
        this.accion = accion;
        this.importe = importe;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNromov() {
        return nromov;
    }

    public void setNromov(String nromov) {
        this.nromov = nromov;
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

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    
}
