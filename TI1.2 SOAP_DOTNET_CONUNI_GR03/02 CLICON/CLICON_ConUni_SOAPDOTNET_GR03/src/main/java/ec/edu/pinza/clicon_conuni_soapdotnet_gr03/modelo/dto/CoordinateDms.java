package ec.edu.pinza.clicon_conuni_soapdotnet_gr03.modelo.dto;

/**
 * Coordenada en formato grados-minutos-segundos.
 *
 * @param degrees    grados
 * @param minutes    minutos
 * @param seconds    segundos
 * @param hemisphere hemisferio (N/S/E/W)
 */
public record CoordinateDms(Integer degrees, Integer minutes, Double seconds, Character hemisphere) {
}
