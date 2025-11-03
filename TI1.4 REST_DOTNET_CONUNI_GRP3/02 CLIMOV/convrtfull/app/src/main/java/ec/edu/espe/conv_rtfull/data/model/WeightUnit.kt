package ec.edu.espe.conv_rtfull.data.model

/**
 * Unidades de peso
 */
enum class WeightUnit(val displayName: String, val code: Int) {
    KILOGRAMS("Kilogramos", 0),
    GRAMS("Gramos", 1),
    MILLIGRAMS("Miligramos", 2),
    POUNDS("Libras", 3),
    OUNCES("Onzas", 4),
    TONS("Toneladas", 5)
}
