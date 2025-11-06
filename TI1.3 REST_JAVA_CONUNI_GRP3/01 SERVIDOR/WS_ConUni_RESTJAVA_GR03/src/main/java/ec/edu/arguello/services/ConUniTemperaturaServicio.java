package ec.edu.arguello.services;
import ec.edu.arguello.models.ConUni;

public class ConUniTemperaturaServicio {
    public double conversion(ConUni conUni) {
        double baseValue = convertToCelsius(conUni.getValue(), conUni.getInUnit());
        return convertFromCelsius(baseValue, conUni.getOutUnit());
    }
    
    private double convertToCelsius(double value, String unit) {
        switch (unit.toLowerCase()) {
            case "celsius":
            case "centigrados":
            case "c": 
                return value;
            case "fahrenheit":
            case "f": 
                return (value - 32.0) * 5.0 / 9.0;
            case "kelvin":
            case "k": 
                return value - 273.15;
            case "rankine":
            case "r":
                return (value - 491.67) * 5.0 / 9.0;
            default: 
                return 0.0;
        }
    }
    
    private double convertFromCelsius(double value, String unit) {
        switch (unit.toLowerCase()) {
            case "celsius":
            case "centigrados":
            case "c": 
                return value;
            case "fahrenheit":
            case "f": 
                return value * 9.0 / 5.0 + 32.0;
            case "kelvin":
            case "k": 
                return value + 273.15;
            case "rankine":
            case "r":
                return (value + 273.15) * 9.0 / 5.0;
            default: 
                return 0.0;
        }
    }
    
    public String[] getSupportedUnits() {
        return new String[]{"c", "f", "k", "r"};
    }
}
