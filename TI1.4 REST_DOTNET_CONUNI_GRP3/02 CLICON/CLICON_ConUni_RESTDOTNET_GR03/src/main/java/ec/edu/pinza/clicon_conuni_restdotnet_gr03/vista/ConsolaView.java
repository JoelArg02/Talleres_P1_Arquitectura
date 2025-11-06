package ec.edu.pinza.clicon_conuni_restdotnet_gr03.vista;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class ConsolaView {

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

    public String seleccionarUnidadMasa(String mensaje) {
        System.out.println();
        System.out.println(mensaje);
        System.out.println("  1) Miligramo (mg)");
        System.out.println("  2) Gramo (g)");
        System.out.println("  3) Kilogramo (kg)");
        System.out.println("  4) Libra (lb)");
        System.out.println("  5) Onza (oz)");
        System.out.println("  6) Tonelada (t)");
        
        int opcion = leerOpcionNumerica("Elija una opcion");
        return switch (opcion) {
            case 1 -> "Milligrams";
            case 2 -> "Grams";
            case 3 -> "Kilograms";
            case 4 -> "Pounds";
            case 5 -> "Ounces";
            case 6 -> "Tons";
            default -> {
                System.out.println("ADVERTENCIA: Opcion invalida, se usara Kilogramos por defecto.");
                yield "Kilograms";
            }
        };
    }
    
    public String seleccionarUnidadTemperatura(String mensaje) {
        System.out.println();
        System.out.println(mensaje);
        System.out.println("  1) Celsius (C)");
        System.out.println("  2) Fahrenheit (F)");
        System.out.println("  3) Kelvin (K)");
        System.out.println("  4) Rankine (R)");
        
        int opcion = leerOpcionNumerica("Elija una opcion");
        return switch (opcion) {
            case 1 -> "Celsius";
            case 2 -> "Fahrenheit";
            case 3 -> "Kelvin";
            case 4 -> "Rankine";
            default -> {
                System.out.println("ADVERTENCIA: Opcion invalida, se usara Celsius por defecto.");
                yield "Celsius";
            }
        };
    }
    
    public String seleccionarUnidadLongitud(String mensaje) {
        System.out.println();
        System.out.println(mensaje);
        System.out.println("  1) Milimetro (mm)");
        System.out.println("  2) Centimetro (cm)");
        System.out.println("  3) Metro (m)");
        System.out.println("  4) Kilometro (km)");
        System.out.println("  5) Pulgada (in)");
        System.out.println("  6) Pie (ft)");
        
        int opcion = leerOpcionNumerica("Elija una opcion");
        return switch (opcion) {
            case 1 -> "Millimeters";
            case 2 -> "Centimeters";
            case 3 -> "Meters";
            case 4 -> "Kilometers";
            case 5 -> "Inches";
            case 6 -> "Feet";
            default -> {
                System.out.println("ADVERTENCIA: Opcion invalida, se usara Metros por defecto.");
                yield "Meters";
            }
        };
    }
    
    public String formatearUnidadMasa(String codigo) {
        return switch (codigo) {
            case "Milligrams" -> "Miligramo (mg)";
            case "Grams" -> "Gramo (g)";
            case "Kilograms" -> "Kilogramo (kg)";
            case "Pounds" -> "Libra (lb)";
            case "Ounces" -> "Onza (oz)";
            case "Tons" -> "Tonelada (t)";
            default -> codigo;
        };
    }
    
    public String formatearUnidadTemperatura(String codigo) {
        return switch (codigo) {
            case "Celsius" -> "Celsius (C)";
            case "Fahrenheit" -> "Fahrenheit (F)";
            case "Kelvin" -> "Kelvin (K)";
            case "Rankine" -> "Rankine (R)";
            default -> codigo;
        };
    }
    
    public String formatearUnidadLongitud(String codigo) {
        return switch (codigo) {
            case "Millimeters" -> "Milimetro (mm)";
            case "Centimeters" -> "Centimetro (cm)";
            case "Meters" -> "Metro (m)";
            case "Kilometers" -> "Kilometro (km)";
            case "Inches" -> "Pulgada (in)";
            case "Feet" -> "Pie (ft)";
            default -> codigo;
        };
    }

    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }

    public void pausar() {
        System.out.println();
        System.out.print("Presione ENTER para continuar...");
        scanner.nextLine();
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

    public int leerOpcionNumerica(String mensaje) {
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
}
