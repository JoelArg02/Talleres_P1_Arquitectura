package ec.edu.arguello.servicios;

/**
 * Clase para manejar la lógica de negocio del login.
 * Ahora usa credenciales fijas (MONSTER / MONSTER9).
 */
public class LoginServicio {

    // Credenciales fijas para la validación
    private static final String USUARIO_FIJO = "MONSTER";
    private static final String CONTRASENA_FIJA = "MONSTER9";

    /**
     * Valida las credenciales de usuario contra los valores fijos.
     * NO BUSCA EN BASE DE DATOS.
     * * @param username El nombre de usuario.
     * @param password La contraseña.
     * @return true si las credenciales coinciden con MONSTER / MONSTER9, false en caso contrario.
     */
    public boolean validarCredenciales(String username, String password) {
        
        boolean esUsuarioCorrecto = USUARIO_FIJO.equals(username);
        boolean esContrasenaCorrecta = CONTRASENA_FIJA.equals(password);
        
        return esUsuarioCorrecto && esContrasenaCorrecta;
    }
}