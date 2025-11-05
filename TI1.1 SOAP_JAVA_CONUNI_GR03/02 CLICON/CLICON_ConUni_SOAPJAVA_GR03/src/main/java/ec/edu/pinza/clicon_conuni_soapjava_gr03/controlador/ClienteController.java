package ec.edu.pinza.clicon_conuni_soapjava_gr03.controlador;

import ec.edu.pinza.clicon_conuni_soapjava_gr03.modelo.LoginModel;
import ec.edu.pinza.clicon_conuni_soapjava_gr03.vista.ConsolaView;
import java.util.Locale;

public class ClienteController {

    private final ConsolaView view;
    private final LoginModel loginModel;

    private boolean autenticado = false;
    private String usuarioAutenticado = "";

    public ClienteController() {
        this.view = new ConsolaView();
        this.loginModel = new LoginModel();
    }

    /**
     * Metodo principal que ejecuta el bucle del menu.
     */
    public void iniciar() {
        boolean salir = false;

        while (!salir) {
            int opcion = view.mostrarMenuPrincipal(autenticado, usuarioAutenticado);

            switch (opcion) {
                case 1 -> autenticarUsuario();
                case 2 -> convertirUnidades();
                case 3 -> {
                    view.mostrarMensaje("");
                    view.mostrarMensaje("Cerrando aplicacion... Hasta luego!");
                    salir = true;
                }
                default -> {
                    view.mostrarMensaje("ADVERTENCIA: Opcion invalida. Intente nuevamente.");
                    view.pausar();
                }
            }
        }
    }

    /**
     * Maneja el inicio de sesion del usuario.
     */
    private void autenticarUsuario() {
        view.mostrarMensaje("");
        view.mostrarMensaje("===== AUTENTICACION =====");
        String usuario = view.pedirUsuario();
        String contrasena = view.pedirContrasena();

        try {
            boolean ok = loginModel.autenticar(usuario, contrasena);
            if (ok) {
                autenticado = true;
                usuarioAutenticado = usuario;
                view.mostrarMensaje("[OK] Bienvenido, " + usuario + "!");
            } else {
                autenticado = false;
                usuarioAutenticado = "";
                view.mostrarMensaje("ADVERTENCIA: Credenciales incorrectas. Intente nuevamente.");
            }
        } catch (Exception e) {
            autenticado = false;
            usuarioAutenticado = "";
            view.mostrarMensaje("ADVERTENCIA: Error al conectarse al servicio SOAP de autenticacion.");
            e.printStackTrace();
        }
        view.pausar();
    }

    /**
     * Maneja la conversion de unidades (ahora local, sin SOAP).
     */
    private void convertirUnidades() {
        if (!autenticado) {
            view.mostrarMensaje("ADVERTENCIA: Debe iniciar sesion antes de convertir unidades.");
            view.pausar();
            return;
        }

        view.mostrarMensaje("");
        view.mostrarMensaje("===== CONVERSION DE UNIDADES =====");
        
        // Mostrar menú de selección de tipo de conversión
        view.mostrarMensaje("Seleccione el tipo de conversion:");
        view.mostrarMensaje("1) Masa (kg → lb, g)");
        view.mostrarMensaje("2) Temperatura (°C → °F, K)");
        view.mostrarMensaje("3) Longitud 2 (decimal → radianes)");
        view.mostrarMensaje("4) Convertir todos");
        
        int opcion = view.leerOpcionNumerica("Elija una opcion");
        
        view.mostrarMensaje("");
        view.mostrarMensaje("===== RESULTADOS DE CONVERSION =====");
        
        switch (opcion) {
            case 1 -> convertirMasa();
            case 2 -> convertirTemperatura();
            case 3 -> convertirLongitud2();
            case 4 -> convertirTodos();
            default -> view.mostrarMensaje("ADVERTENCIA: Opcion invalida.");
        }
        
        view.pausar();
    }
    
    private void convertirMasa() {
        String masaStr = view.pedirTexto("Ingrese la masa en kilogramos (kg)");
        
        if (masaStr != null && !masaStr.trim().isEmpty()) {
            try {
                double kg = Double.parseDouble(masaStr);
                double lb = kg * 2.20462;
                double g = kg * 1000;
                
                view.mostrarMensaje("");
                view.mostrarMensaje("MASA:");
                view.mostrarMensaje(String.format(Locale.US, "  Kilogramos: %.4f kg", kg));
                view.mostrarMensaje(String.format(Locale.US, "  Libras:     %.4f lb", lb));
                view.mostrarMensaje(String.format(Locale.US, "  Gramos:     %.4f g", g));
            } catch (NumberFormatException e) {
                view.mostrarMensaje("ADVERTENCIA: Valor de masa invalido.");
            }
        } else {
            view.mostrarMensaje("ADVERTENCIA: No se ingreso ningun valor.");
        }
    }
    
    private void convertirTemperatura() {
        String temperaturaStr = view.pedirTexto("Ingrese la temperatura en Celsius (°C)");
        
        if (temperaturaStr != null && !temperaturaStr.trim().isEmpty()) {
            try {
                double celsius = Double.parseDouble(temperaturaStr);
                double fahrenheit = (celsius * 9.0 / 5.0) + 32;
                double kelvin = celsius + 273.15;
                
                view.mostrarMensaje("");
                view.mostrarMensaje("TEMPERATURA:");
                view.mostrarMensaje(String.format(Locale.US, "  Celsius:    %.4f °C", celsius));
                view.mostrarMensaje(String.format(Locale.US, "  Fahrenheit: %.4f °F", fahrenheit));
                view.mostrarMensaje(String.format(Locale.US, "  Kelvin:     %.4f K", kelvin));
            } catch (NumberFormatException e) {
                view.mostrarMensaje("ADVERTENCIA: Valor de temperatura invalido.");
            }
        } else {
            view.mostrarMensaje("ADVERTENCIA: No se ingreso ningun valor.");
        }
    }
    
    private void convertirLongitud2() {
        String longitud2Str = view.pedirTexto("Ingrese la longitud 2 en grados decimales");
        
        if (longitud2Str != null && !longitud2Str.trim().isEmpty()) {
            try {
                double decimal = Double.parseDouble(longitud2Str);
                double radianes = decimal * (Math.PI / 180.0);
                
                view.mostrarMensaje("");
                view.mostrarMensaje("COORDENADAS:");
                view.mostrarMensaje(String.format(Locale.US, "  Long2 Decimal:  %.4f°", decimal));
                view.mostrarMensaje(String.format(Locale.US, "  Long2 Radianes: %.6f rad", radianes));
            } catch (NumberFormatException e) {
                view.mostrarMensaje("ADVERTENCIA: Valor de longitud invalido.");
            }
        } else {
            view.mostrarMensaje("ADVERTENCIA: No se ingreso ningun valor.");
        }
    }
    
    private void convertirTodos() {
        view.mostrarMensaje("Ingrese los valores (deje en blanco para omitir):");
        view.mostrarMensaje("");
        
        // Pedir masa
        String masaStr = view.pedirTexto("Masa en kilogramos (kg)");
        
        // Pedir temperatura
        String temperaturaStr = view.pedirTexto("Temperatura en Celsius (°C)");
        
        // Pedir longitud2
        String longitud2Str = view.pedirTexto("Longitud 2 en grados decimales");
        
        // Convertir masa
        if (masaStr != null && !masaStr.trim().isEmpty()) {
            try {
                double kg = Double.parseDouble(masaStr);
                double lb = kg * 2.20462;
                double g = kg * 1000;
                
                view.mostrarMensaje("");
                view.mostrarMensaje("MASA:");
                view.mostrarMensaje(String.format(Locale.US, "  Kilogramos: %.4f kg", kg));
                view.mostrarMensaje(String.format(Locale.US, "  Libras:     %.4f lb", lb));
                view.mostrarMensaje(String.format(Locale.US, "  Gramos:     %.4f g", g));
            } catch (NumberFormatException e) {
                view.mostrarMensaje("ADVERTENCIA: Valor de masa invalido.");
            }
        }
        
        // Convertir temperatura
        if (temperaturaStr != null && !temperaturaStr.trim().isEmpty()) {
            try {
                double celsius = Double.parseDouble(temperaturaStr);
                double fahrenheit = (celsius * 9.0 / 5.0) + 32;
                double kelvin = celsius + 273.15;
                
                view.mostrarMensaje("");
                view.mostrarMensaje("TEMPERATURA:");
                view.mostrarMensaje(String.format(Locale.US, "  Celsius:    %.4f °C", celsius));
                view.mostrarMensaje(String.format(Locale.US, "  Fahrenheit: %.4f °F", fahrenheit));
                view.mostrarMensaje(String.format(Locale.US, "  Kelvin:     %.4f K", kelvin));
            } catch (NumberFormatException e) {
                view.mostrarMensaje("ADVERTENCIA: Valor de temperatura invalido.");
            }
        }
        
        // Convertir longitud2
        if (longitud2Str != null && !longitud2Str.trim().isEmpty()) {
            try {
                double decimal = Double.parseDouble(longitud2Str);
                double radianes = decimal * (Math.PI / 180.0);
                
                view.mostrarMensaje("");
                view.mostrarMensaje("COORDENADAS:");
                view.mostrarMensaje(String.format(Locale.US, "  Long2 Decimal:  %.4f°", decimal));
                view.mostrarMensaje(String.format(Locale.US, "  Long2 Radianes: %.6f rad", radianes));
            } catch (NumberFormatException e) {
                view.mostrarMensaje("ADVERTENCIA: Valor de longitud invalido.");
            }
        }
    }
}
