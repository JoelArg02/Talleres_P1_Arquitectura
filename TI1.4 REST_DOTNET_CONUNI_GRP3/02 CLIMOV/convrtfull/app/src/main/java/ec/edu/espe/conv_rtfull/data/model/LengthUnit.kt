package ec.edu.espe.conv_rtfull.data.model

/**
 * Unidades de longitud
 */
enum class LengthUnit(val displayName: String, val code: Int) {
    METERS("Metros", 0),
    KILOMETERS("Kilómetros", 1),
    CENTIMETERS("Centímetros", 2),
    MILLIMETERS("Milímetros", 3),
    MILES("Millas", 4),
    YARDS("Yardas", 5),
    FEET("Pies", 6),
    INCHES("Pulgadas", 7)
}
