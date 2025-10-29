package ec.edu.arguello.servicios;

public class LoginServicio {

    private static final String USUARIO_FIJO = "MONSTER";
    private static final String CONTRASENA_FIJA = "MONSTER9";


    public boolean validarCredenciales(String username, String password) {
        
        boolean esUsuarioCorrecto = USUARIO_FIJO.equals(username);
        boolean esContrasenaCorrecta = CONTRASENA_FIJA.equals(password);
        
        return esUsuarioCorrecto && esContrasenaCorrecta;
    }
}