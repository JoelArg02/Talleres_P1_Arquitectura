package ec.edu.espe.conversionapi.enums;

/**
 * Unidades de volumen
 */
public enum VolumeUnit {
    LITERS("Litros"),
    MILLILITERS("Mililitros"),
    CUBIC_METERS("Metros Cúbicos"),
    GALLONS("Galones"),
    QUARTS("Cuartos"),
    PINTS("Pintas"),
    CUPS("Tazas");

    private final String displayName;

    VolumeUnit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
