package ec.edu.espe.dotnetsoap.data.models

data class ConversionResponse(
    val massKg: Double,
    val massLb: Double,
    val massG: Double,
    val temperatureCelsius: Double,
    val temperatureFahrenheit: Double,
    val temperatureKelvin: Double
)
