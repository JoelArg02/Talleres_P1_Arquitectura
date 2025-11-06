package ec.edu.espe.conv_rtfull.data.model

/**
 * Unidades de temperatura
 */
enum class TemperatureUnit(val displayName: String, val code: Int) {
    CELSIUS("Celsius", 0),
    FAHRENHEIT("Fahrenheit", 1),
    KELVIN("Kelvin", 2),
    RANKINE("Rankine", 3)
}
