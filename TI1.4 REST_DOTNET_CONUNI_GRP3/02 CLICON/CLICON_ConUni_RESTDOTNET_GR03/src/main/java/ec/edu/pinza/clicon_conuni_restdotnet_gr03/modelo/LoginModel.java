package ec.edu.pinza.clicon_conuni_restdotnet_gr03.modelo;

public class LoginModel {

    private static final String USUARIO_PERMITIDO = "MONSTER";
    private static final String CONTRASENA_PERMITIDA = "MONSTER9";

    public boolean autenticar(String usuario, String contrasena) {
        if (usuario == null || contrasena == null) {
            return false;
        }
        return USUARIO_PERMITIDO.equalsIgnoreCase(usuario.trim())
                && CONTRASENA_PERMITIDA.equals(contrasena.trim());
    }
}
