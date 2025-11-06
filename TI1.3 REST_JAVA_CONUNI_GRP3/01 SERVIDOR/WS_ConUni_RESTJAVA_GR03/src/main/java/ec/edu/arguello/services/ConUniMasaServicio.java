package ec.edu.arguello.services;
import ec.edu.arguello.models.ConUni;

public class ConUniMasaServicio {
    public double conversion(ConUni conUni) {
        double baseValue = convertToBaseKilograms(conUni.getValue(), conUni.getInUnit());
        return convertFromBaseKilograms(baseValue, conUni.getOutUnit());
    }
    
    private double convertToBaseKilograms(double value, String unit) {
        switch (unit.toLowerCase()) {
            case "kilogramos":
            case "kilograms":
            case "kg": 
                return value;
            case "miligramos":
            case "milligrams":
            case "mg": 
                return value / 1_000_000.0;
            case "gramos":
            case "grams":
            case "g": 
                return value / 1000.0;
            case "libras":
            case "pounds":
            case "lb": 
                return value * 0.453592;
            case "onzas":
            case "ounces":
            case "oz": 
                return value * 0.0283495;
            case "toneladas":
            case "tons":
            case "metric tons":
            case "t": 
                return value * 1000.0;
            default: 
                return 0.0;
        }
    }
    
    private double convertFromBaseKilograms(double value, String unit) {
        switch (unit.toLowerCase()) {
            case "kilogramos":
            case "kilograms":
            case "kg": 
                return value;
            case "miligramos":
            case "milligrams":
            case "mg": 
                return value * 1_000_000.0;
            case "gramos":
            case "grams":
            case "g": 
                return value * 1000.0;
            case "libras":
            case "pounds":
            case "lb": 
                return value / 0.453592;
            case "onzas":
            case "ounces":
            case "oz": 
                return value / 0.0283495;
            case "toneladas":
            case "tons":
            case "metric tons":
            case "t": 
                return value / 1000.0;
            default: 
                return 0.0;
        }
    }
    
    public String[] getSupportedUnits() {
        return new String[]{"mg", "g", "kg", "lb", "oz", "t"};
    }
}
