package ec.edu.arguello.servicios;

public class ConUniServicio {

    public double conversion(double value, String inUnit, String outUnit) {
        if (inUnit == null || outUnit == null) {
            System.err.println("Error: inUnit o outUnit es null");
            return 0.0;
        }
        
        String inUnitLower = inUnit.trim().toLowerCase();
        String outUnitLower = outUnit.trim().toLowerCase();
        
        System.out.println("Conversion solicitada: " + value + " " + inUnitLower + " -> " + outUnitLower);
        
        if (isMassUnit(inUnitLower) && isMassUnit(outUnitLower)) {
            double baseValue = convertToBaseKg(value, inUnitLower);
            double result = convertFromBaseKg(baseValue, outUnitLower);
            System.out.println("Resultado masa: " + result);
            return result;
        }
        
        if (isTemperatureUnit(inUnitLower) && isTemperatureUnit(outUnitLower)) {
            double baseValue = convertToCelsius(value, inUnitLower);
            double result = convertFromCelsius(baseValue, outUnitLower);
            System.out.println("Resultado temperatura: " + result);
            return result;
        }
        
        if (isLengthUnit(inUnitLower) && isLengthUnit(outUnitLower)) {
            double baseValue = convertToBaseMeters(value, inUnitLower);
            double result = convertFromBaseMeters(baseValue, outUnitLower);
            System.out.println("Resultado longitud: " + result);
            return result;
        }
        
        System.err.println("Error: Unidades incompatibles o no reconocidas");
        return 0.0;
    }
    
    private boolean isMassUnit(String unit) {
        return unit.equals("mg") || unit.equals("g") || unit.equals("kg") || 
               unit.equals("lb") || unit.equals("oz") || unit.equals("t");
    }
    
    private boolean isTemperatureUnit(String unit) {
        return unit.equals("c") || unit.equals("f") || unit.equals("k") || unit.equals("r");
    }
    
    private boolean isLengthUnit(String unit) {
        return unit.equals("mm") || unit.equals("cm") || unit.equals("m") || 
               unit.equals("km") || unit.equals("in") || unit.equals("ft");
    }
    
    private double convertToBaseKg(double value, String unit) {
        switch (unit) {
            case "kg":
                return value;
            case "mg":
                return value / 1000000.0;
            case "g":
                return value / 1000.0;
            case "lb":
                return value / 2.20462262185;
            case "oz":
                return value / 35.27396195;
            case "t":
                return value * 1000.0;
            default:
                return 0.0;
        }
    }
    
    private double convertFromBaseKg(double value, String unit) {
        switch (unit) {
            case "kg":
                return value;
            case "mg":
                return value * 1000000.0;
            case "g":
                return value * 1000.0;
            case "lb":
                return value * 2.20462262185;
            case "oz":
                return value * 35.27396195;
            case "t":
                return value / 1000.0;
            default:
                return 0.0;
        }
    }
    
    private double convertToCelsius(double value, String unit) {
        switch (unit) {
            case "c":
                return value;
            case "f":
                return (value - 32.0) * 5.0 / 9.0;
            case "k":
                return value - 273.15;
            case "r":
                return (value - 491.67) * 5.0 / 9.0;
            default:
                return 0.0;
        }
    }
    
    private double convertFromCelsius(double value, String unit) {
        switch (unit) {
            case "c":
                return value;
            case "f":
                return (value * 9.0 / 5.0) + 32.0;
            case "k":
                return value + 273.15;
            case "r":
                return (value * 9.0 / 5.0) + 491.67;
            default:
                return 0.0;
        }
    }
    
    private double convertToBaseMeters(double value, String unit) {
        switch (unit) {
            case "m": 
                return value;
            case "mm":
                return value / 1000.0;
            case "cm":
                return value / 100.0;
            case "km":
                return value * 1000.0;
            case "in":
                return value * 0.0254;
            case "ft":
                return value * 0.3048;
            default:
                return 0.0; 
        }
    }
    
    private double convertFromBaseMeters(double value, String unit) {
        switch (unit) {
            case "m":
                return value;
            case "mm":
                return value * 1000.0;
            case "cm":
                return value * 100.0;
            case "km":
                return value / 1000.0;
            case "in":
                return value / 0.0254;
            case "ft":
                return value / 0.3048;
            default:
                return 0.0;
        }
    }
}
