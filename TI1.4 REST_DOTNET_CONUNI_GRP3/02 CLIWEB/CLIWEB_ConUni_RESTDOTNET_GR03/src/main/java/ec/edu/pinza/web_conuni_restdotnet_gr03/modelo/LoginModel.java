package ec.edu.pinza.web_conuni_restdotnet_gr03.modelo;

public class LoginModel {

    private static final String USUARIO = "MONSTER";
    private static final String CONTRASENA = "MONSTER9";

    public boolean autenticar(String usuario, String contrasena) {
        if (usuario == null || contrasena == null) {
            return false;
        }
        return USUARIO.equalsIgnoreCase(usuario.trim()) && CONTRASENA.equals(contrasena.trim());
    }
}
