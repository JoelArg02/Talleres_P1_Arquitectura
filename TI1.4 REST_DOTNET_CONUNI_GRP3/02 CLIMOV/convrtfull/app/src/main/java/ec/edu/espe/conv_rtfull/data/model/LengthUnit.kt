package ec.edu.espe.conv_rtfull.data.model

/**
 * Unidades de longitud
 */
enum class LengthUnit(val displayName: String, val code: Int) {
    MILLIMETERS("Milímetros", 3),
    CENTIMETERS("Centímetros", 2),
    METERS("Metros", 0),
    KILOMETERS("Kilómetros", 1),
    INCHES("Pulgadas", 7),
    FEET("Pies", 6)
}
