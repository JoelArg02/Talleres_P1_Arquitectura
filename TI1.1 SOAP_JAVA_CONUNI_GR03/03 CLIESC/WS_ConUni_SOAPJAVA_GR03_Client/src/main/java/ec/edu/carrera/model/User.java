package ec.edu.carrera.model;

/**
 * Representación del usuario en el cliente.
 * Se podría expandir para guardar el token de sesión o más datos.
 */
public class User {
    private String username;
    private String password;
    
    // Constructor, Getters y Setters
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}