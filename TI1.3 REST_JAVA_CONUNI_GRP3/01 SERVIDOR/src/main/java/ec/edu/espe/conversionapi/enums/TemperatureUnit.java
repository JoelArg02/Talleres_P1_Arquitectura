package ec.edu.espe.conversionapi.enums;

/**
 * Unidades de temperatura
 */
public enum TemperatureUnit {
    CELSIUS("Celsius"),
    FAHRENHEIT("Fahrenheit"),
    KELVIN("Kelvin");

    private final String displayName;

    TemperatureUnit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
