package ec.edu.espe.conversionapi.enums;

/**
 * Unidades de longitud
 */
public enum LengthUnit {
    METERS("Metros"),
    KILOMETERS("Kilómetros"),
    CENTIMETERS("Centímetros"),
    MILLIMETERS("Milímetros"),
    MILES("Millas"),
    YARDS("Yardas"),
    FEET("Pies"),
    INCHES("Pulgadas");

    private final String displayName;

    LengthUnit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
