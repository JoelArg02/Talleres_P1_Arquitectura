package ec.edu.pinza.clicon_conuni_soapdotnet_gr03.modelo.dto;

/**
 * Representa la respuesta del servicio de autenticacion.
 *
 * @param success indica si el inicio de sesion fue exitoso
 * @param message mensaje informativo proporcionado por el servicio
 * @param token   token emitido en caso de exito
 */
public record LoginResult(boolean success, String message, String token) {

    public static LoginResult failure(String message) {
        return new LoginResult(false, message, null);
    }

    public static LoginResult success(String message, String token) {
        return new LoginResult(true, message, token);
    }
}
