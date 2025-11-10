package ec.edu.gr03.view;

import java.util.Scanner;

public class LoginView {
    private final Scanner scanner = new Scanner(System.in);

    public String[] mostrarLogin() {
        System.out.println("===== LOGIN =====");
        System.out.print("Usuario: ");
        String usuario = scanner.nextLine();
        System.out.print("Contraseña: ");
        String clave = scanner.nextLine();
        return new String[]{usuario, clave};
    }

    public void mostrarErrorLogin() {
        System.out.println("Error: Usuario o contraseña incorrectos.");
    }

    public void mostrarBienvenida(String usuario) {
        System.out.println("Bienvenido, " + usuario + "!");
    }
}
