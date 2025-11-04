package ec.edu.pinza.clicon_conuni_restdotnet_gr03.vista;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class ConsolaView {

    private static final UnitOption[] UNIDADES_DISPONIBLES = {
        new UnitOption(1, 0, "Metros (m)"),
        new UnitOption(2, 1, "Kilometros (km)"),
        new UnitOption(3, 2, "Centimetros (cm)"),
        new UnitOption(4, 3, "Milimetros (mm)"),
        new UnitOption(5, 4, "Millas (mi)"),
        new UnitOption(6, 5, "Yardas (yd)"),
        new UnitOption(7, 6, "Pies (ft)"),
        new UnitOption(8, 7, "Pulgadas (in)")
    };

    private final Scanner scanner = new Scanner(System.in);

    public int mostrarMenuPrincipal(boolean autenticado, String usuario) {
        limpiarConsola();
        mostrarEncabezado("CLIENTE CONUNI - REST DOTNET (MVC)");

        if (autenticado) {
            System.out.println("Usuario conectado : " + usuario);
        } else {
            System.out.println("Usuario conectado : (ninguno)");
            System.out.println("ADVERTENCIA: Debe iniciar sesion antes de convertir unidades.");
        }

        System.out.println("------------------------------------");
        System.out.println("1) Iniciar sesion");
        System.out.println("2) Convertir unidades");
        System.out.println("3) Salir");
        System.out.println("------------------------------------");
        return leerOpcionNumerica("Seleccione una opcion");
    }

    public String pedirUsuario() {
        System.out.print("Usuario: ");
        return scanner.nextLine().trim();
    }

    public String pedirContrasena() {
        System.out.print("Contrasena: ");
        return scanner.nextLine().trim();
    }

    public double pedirValor(String mensaje) {
        while (true) {
            System.out.print(mensaje + ": ");
            String entrada = scanner.nextLine().trim();
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("ADVERTENCIA: Valor invalido. Intente nuevamente usando numeros.");
            }
        }
    }

    public int seleccionarUnidad(String mensaje) {
        System.out.println();
        System.out.println(mensaje);
        for (UnitOption unidad : UNIDADES_DISPONIBLES) {
            System.out.println("  " + unidad.opcionMenu() + ") " + unidad.etiqueta());
        }
        while (true) {
            int opcion = leerOpcionNumerica("Elija una opcion");
            for (UnitOption unidad : UNIDADES_DISPONIBLES) {
                if (unidad.opcionMenu() == opcion) {
                    return unidad.codigoApi();
                }
            }
            System.out.println("ADVERTENCIA: Opcion invalida. Intente nuevamente.");
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void pausar() {
        System.out.println();
        System.out.print("Presione ENTER para continuar...");
        scanner.nextLine();
    }

    public String formatearUnidad(int codigo) {
        for (UnitOption unidad : UNIDADES_DISPONIBLES) {
            if (unidad.codigoApi() == codigo) {
                return unidad.etiqueta();
            }
        }
        return null;
    }

    private void mostrarEncabezado(String titulo) {
        System.out.println("+----------------------------------+");
        System.out.printf(Locale.US, "| %-32s |%n", titulo);
        System.out.println("+----------------------------------+");
    }

    private void limpiarConsola() {
        if (ejecutarLimpiadoDeConsola()) {
            return;
        }
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    private boolean ejecutarLimpiadoDeConsola() {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        if (os.contains("windows")) {
            return ejecutarComando("cmd", "/c", "cls");
        } else {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            return true;
        }
    }

    private boolean ejecutarComando(String... comando) {
        try {
            Process proceso = new ProcessBuilder(comando).inheritIO().start();
            proceso.waitFor();
            return proceso.exitValue() == 0;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    private int leerOpcionNumerica(String mensaje) {
        while (true) {
            String entrada = leerLinea(mensaje);
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("ADVERTENCIA: Ingrese solo numeros.");
            }
        }
    }

    private String leerLinea(String mensaje) {
        System.out.print(mensaje + ": ");
        return scanner.nextLine().trim();
    }

    private record UnitOption(int opcionMenu, int codigoApi, String etiqueta) {
    }
}
