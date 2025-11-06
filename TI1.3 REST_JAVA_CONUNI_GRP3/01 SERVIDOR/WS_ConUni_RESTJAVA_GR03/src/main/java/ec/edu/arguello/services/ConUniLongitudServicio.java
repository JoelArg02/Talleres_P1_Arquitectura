package ec.edu.arguello.services;

import ec.edu.arguello.models.ConUni;

public class ConUniLongitudServicio {

    public double conversion(ConUni conUni) {
        double baseValue = convertToBaseMeters(conUni.getValue(), conUni.getInUnit());
        return convertFromBaseMeters(baseValue, conUni.getOutUnit());
    }

    private double convertToBaseMeters(double value, String unit) {
        switch (unit.toLowerCase()) {
            case "metros":
            case "meters":
            case "m":
                return value;
            case "milimetros":
            case "milímetros":
            case "millimeters":
            case "mm":
                return value / 1000.0;
            case "centimetros":
            case "centímetros":
            case "centimeters":
            case "cm":
                return value / 100.0;
            case "kilometros":
            case "kilómetros":
            case "kilometers":
            case "km":
                return value * 1000.0;
            case "pulgadas":
            case "inches":
            case "in":
                return value * 0.0254;
            case "pies":
            case "feet":
            case "ft":
                return value * 0.3048;
            default:
                return 0.0;
        }
    }

    private double convertFromBaseMeters(double value, String unit) {
        switch (unit.toLowerCase()) {
            case "metros":
            case "meters":
            case "m":
                return value;
            case "milimetros":
            case "milímetros":
            case "millimeters":
            case "mm":
                return value * 1000.0;
            case "centimetros":
            case "centímetros":
            case "centimeters":
            case "cm":
                return value * 100.0;
            case "kilometros":
            case "kilómetros":
            case "kilometers":
            case "km":
                return value / 1000.0;
            case "pulgadas":
            case "inches":
            case "in":
                return value / 0.0254;
            case "pies":
            case "feet":
            case "ft":
                return value / 0.3048;
            default:
                return 0.0;
        }
    }

    public String[] getSupportedUnits() {
        return new String[]{"mm", "cm", "m", "km", "in", "ft"};
    }
}
