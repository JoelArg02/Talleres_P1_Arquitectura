package ec.edu.arguello.mavenproject2.model;
import java.util.Date;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "movimiento")
public class Movimiento {

    private String cuenta;
    private int nroMov;
    private Date fecha;
    private String tipo;
    private String accion;
    private double importe;

    public Movimiento() {
    }

    // Getters y Setters
    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public int getNroMov() {
        return nroMov;
    }

    public void setNroMov(int nroMov) {
        this.nroMov = nroMov;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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
