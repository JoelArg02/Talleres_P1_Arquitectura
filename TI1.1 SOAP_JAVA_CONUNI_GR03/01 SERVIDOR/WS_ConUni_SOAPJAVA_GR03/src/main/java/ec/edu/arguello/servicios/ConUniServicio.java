
package ec.edu.arguello.servicios;

public class ConUniServicio {

    public double conversion(double value, String inUnit, String outUnit) {
        
        double baseValue = convertToBaseMeters(value, inUnit);

        return convertFromBaseMeters(baseValue, outUnit);
    }
    
    private double convertToBaseMeters(double value, String unit) {
        switch (unit.toLowerCase()) {
            case "meters": 
                return value;
            case "kilometers": 
                return value * 1000.0;
            case "centimeters":
                return value / 100.0;
            default:
                return 0.0; 
        }
    }
    
    private double convertFromBaseMeters(double value, String unit) {
        switch (unit.toLowerCase()) {
            case "meters":
                return value;
            case "kilometers":
                return value / 1000.0;
            case "centimeters":
                return value * 100.0;
            default:
                return 0.0;
        }
    }
}
