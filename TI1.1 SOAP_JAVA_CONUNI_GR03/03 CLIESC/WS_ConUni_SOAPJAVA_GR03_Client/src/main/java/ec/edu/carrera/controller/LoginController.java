package ec.edu.carrera.controller;

import ec.edu.carrera.model.User;

// Importar las clases generadas por Maven/CXF en el paquete ec.edu.carrera.ws
// AJUSTE CRÍTICO: Usamos el paquete definido en el pom.xml
import ec.edu.carrera.ws.login.WSLogin; 
import ec.edu.carrera.ws.login.WSLogin_Service; 
import javax.swing.JOptionPane; 

/**
 * Controlador para la vista de Login.
 */
public class LoginController {

    private WSLogin webServicePort; // El puerto (proxy) del Web Service

    public LoginController() {
        // Inicializar la conexión al Web Service
        try {
            // Instanciar el servicio generado (por ejemplo, WSLogin_Service)
            WSLogin_Service service = new WSLogin_Service();
            webServicePort = service.getWSLoginPort();
            System.out.println("Conexión con WSLogin establecida.");
        } catch (Exception e) {
            // Si el servicio no está corriendo o la URL es inaccesible
            System.err.println("Error al conectar con el Web Service: " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Error de conexión con el servidor. Intente más tarde.", 
                "Error de Conexión", 
                JOptionPane.ERROR_MESSAGE);
            webServicePort = null; // Para asegurar que no se intenta usar
        }
    }

    /**
     * Intenta autenticar al usuario usando el Web Service.
     * @param user El objeto User con las credenciales.
     * @return true si el login es exitoso, false en caso contrario.
     */
    public boolean login(User user) {
        if (webServicePort == null) {
            JOptionPane.showMessageDialog(null, "El servicio de login no está disponible.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        try {
            // Llamada al método 'autenticar' del Web Service
            // NOTA: Asumimos que el WSDL tiene el método 'autenticar'
            boolean autenticado = webServicePort.autenticar(user.getUsername(), user.getPassword());
            
            if (autenticado) {
                System.out.println("Login exitoso para: " + user.getUsername());
            } else {
                System.out.println("Credenciales incorrectas para: " + user.getUsername());
            }
            
            return autenticado;
            
        } catch (Exception e) {
            // Manejo de cualquier excepción durante la llamada al WS (timeout, error SOAP)
            System.err.println("Error durante la comunicación con el Web Service: " + e.getMessage());
            JOptionPane.showMessageDialog(null, 
                "Error al procesar la solicitud de login.", 
                "Error de Comunicación", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
