package ec.edu.gr03.test;

import ec.edu.gr03.model.EurekaBankClient;

public class TestLogin {
    public static void main(String[] args) {
        System.out.println("===== TEST LOGIN =====");
        System.out.println("URL Base: http://localhost:60245/api/movimiento");
        System.out.println();
        
        try {
            System.out.println("Probando login con MONSTER/MONSTER9...");
            boolean resultado = EurekaBankClient.login("MONSTER", "MONSTER9");
            
            if (resultado) {
                System.out.println("✓ Login exitoso");
            } else {
                System.out.println("✗ Login falló");
            }
            
            System.out.println("\nProbando login con credenciales incorrectas...");
            boolean resultado2 = EurekaBankClient.login("USUARIO_FALSO", "PASSWORD_FALSO");
            
            if (!resultado2) {
                System.out.println("✓ Correctamente rechazado");
            } else {
                System.out.println("✗ No debería haber aceptado credenciales incorrectas");
            }
            
        } catch (Exception e) {
            System.err.println("✗ Excepción: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n===== FIN TEST =====");
    }
}
