package ec.edu.pinza.clicon_conuni_soapdotnet_gr03.modelo.dto;

/**
 * Resultado devuelto por el servicio de conversion.
 *
 * @param massKg           masa en kilogramos
 * @param massLb           masa en libras
 * @param massG            masa en gramos
 * @param latitudeDecimal  latitud en decimal
 * @param latitudeRadians  latitud en radianes
 * @param latitudeDms      latitud en formato DMS
 * @param longitudeDecimal longitud en decimal
 * @param longitudeRadians longitud en radianes
 * @param longitudeDms     longitud en formato DMS
 */
public record ConversionResult(
    Double massKg,
    Double massLb,
    Double massG,
    Double latitudeDecimal,
    Double latitudeRadians,
    CoordinateDms latitudeDms,
    Double longitudeDecimal,
    Double longitudeRadians,
    CoordinateDms longitudeDms
) {
}
