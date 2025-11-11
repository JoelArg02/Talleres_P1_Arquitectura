package ec.edu.gr03.view;

import java.util.Scanner;

public class MenuView {
    private final Scanner scanner = new Scanner(System.in);

    public int mostrarMenu() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Realizar depósito");
        System.out.println("2. Realizar retiro");
        System.out.println("3. Realizar transferencia");
        System.out.println("4. Ver movimientos");
        System.out.println("5. Ver balances");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public String[] pedirDatosDeposito() {
        System.out.print("Cuenta destino: ");
        String cuenta = scanner.nextLine();
        System.out.print("Importe a depositar: ");
        double importe = Double.parseDouble(scanner.nextLine());
        return new String[]{cuenta, String.valueOf(importe)};
    }

    public String[] pedirDatosRetiro() {
        System.out.print("Cuenta: ");
        String cuenta = scanner.nextLine();
        System.out.print("Importe a retirar: ");
        double importe = Double.parseDouble(scanner.nextLine());
        return new String[]{cuenta, String.valueOf(importe)};
    }

    public String[] pedirDatosTransferencia() {
        System.out.print("Cuenta origen: ");
        String origen = scanner.nextLine();
        System.out.print("Cuenta destino: ");
        String destino = scanner.nextLine();
        System.out.print("Importe a transferir: ");
        double importe = Double.parseDouble(scanner.nextLine());
        return new String[]{origen, destino, String.valueOf(importe)};
    }

    public String pedirCuentaMovimientos() {
        System.out.print("Cuenta para ver movimientos: ");
        return scanner.nextLine();
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}
