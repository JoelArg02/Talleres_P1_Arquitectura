/**
 *
 * @author nahir
 */
package ec.edu.carrera.model;

public class ConUni {
    String type;
    double value;
    String inUnit;
    String outUnit;

    // Getters públicos son útiles para el controlador
    public String getType() { return type; }
    public double getValue() { return value; }
    public String getInUnit() { return inUnit; }
    public String getOutUnit() { return outUnit; }

    public ConUni(String type, double value, String inUnit, String outUnit) {
        this.type = type;
        this.value = value;
        this.inUnit = inUnit;
        this.outUnit = outUnit;
    }
}
