package ec.edu.espe.conversionapi.enums;

/**
 * Unidades de peso
 */
public enum WeightUnit {
    KILOGRAMS("Kilogramos"),
    GRAMS("Gramos"),
    MILLIGRAMS("Miligramos"),
    POUNDS("Libras"),
    OUNCES("Onzas"),
    TONS("Toneladas");

    private final String displayName;

    WeightUnit(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
