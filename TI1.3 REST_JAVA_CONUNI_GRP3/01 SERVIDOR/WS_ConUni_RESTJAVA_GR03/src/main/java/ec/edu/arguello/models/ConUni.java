package ec.edu.arguello.models;

public class ConUni {

    private String type;
    private double value;
    private String inUnit;
    private String outUnit;

    public ConUni() {
    }

    public ConUni(String type, double value, String inUnit, String outUnit) {
        this.type = type;
        this.value = value;
        this.inUnit = inUnit;
        this.outUnit = outUnit;
    }
        
    public String getType(){
        return type;
    }
    
    public void setType(String type){
        this.type = type;
    }
    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * @return the inUnit
     */
    public String getInUnit() {
        return inUnit;
    }

    /**
     * @param inUnit the inUnit to set
     */
    public void setInUnit(String inUnit) {
        this.inUnit = inUnit;
    }

    /**
     * @return the outUnit
     */
    public String getOutUnit() {
        return outUnit;
    }

    /**
     * @param outUnit the outUnit to set
     */
    public void setOutUnit(String outUnit) {
        this.outUnit = outUnit;
    }

}
