package ec.edu.espe.conv_rtfull.data.model

/**
 * Unidades de volumen
 */
enum class VolumeUnit(val displayName: String, val code: Int) {
    LITERS("Litros", 0),
    MILLILITERS("Mililitros", 1),
    CUBIC_METERS("Metros Cúbicos", 2),
    GALLONS("Galones", 3),
    QUARTS("Cuartos", 4),
    PINTS("Pintas", 5),
    CUPS("Tazas", 6)
}
