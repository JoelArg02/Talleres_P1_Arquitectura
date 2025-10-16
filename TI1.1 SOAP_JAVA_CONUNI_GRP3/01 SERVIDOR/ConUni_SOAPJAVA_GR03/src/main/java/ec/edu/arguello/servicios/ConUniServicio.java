
package ec.edu.arguello.servicios;

public class ConUniServicio {

    public double conversion(double value, String inUnit, String outUnit) {
        
        // 1. Convertir la unidad de entrada a una unidad base (ej: metros)
        double baseValue = convertToBaseMeters(value, inUnit);

        // 2. Convertir de la unidad base a la unidad de salida final
        return convertFromBaseMeters(baseValue, outUnit);
    }
    
    // Método auxiliar para convertir cualquier unidad a Metros
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
