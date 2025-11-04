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
                return value;
            case "kilometros":
            case "kilómetros":
            case "kilometers":
                return value * 1000.0;
            case "centimetros":
            case "centímetros":
            case "centimeters":
                return value / 100.0;
            case "milimetros":
            case "milímetros":
            case "millimeters":
                return value / 1000.0;
            case "millas":
            case "miles":
                return value * 1609.34;
            case "pulgadas":
            case "inches":
                return value * 0.0254;
            case "pies":
            case "feet":
                return value * 0.3048;
            default:
                return 0.0;
        }
    }

    private double convertFromBaseMeters(double value, String unit) {
        switch (unit.toLowerCase()) {
            case "metros":
            case "meters":
                return value;
            case "kilometros":
            case "kilómetros":
            case "kilometers":
                return value / 1000.0;
            case "centimetros":
            case "centímetros":
            case "centimeters":
                return value * 100.0;
            case "milimetros":
            case "milímetros":
            case "millimeters":
                return value * 1000.0;
            case "millas":
            case "miles":
                return value / 1609.34;
            case "pulgadas":
            case "inches":
                return value / 0.0254;
            case "pies":
            case "feet":
                return value / 0.3048;
            default:
                return 0.0;
        }
    }

    public String[] getSupportedUnits() {
        return new String[]{"metros", "kilometros", "centimetros", "milimetros", "millas", "pulgadas", "pies"};
    }
}
