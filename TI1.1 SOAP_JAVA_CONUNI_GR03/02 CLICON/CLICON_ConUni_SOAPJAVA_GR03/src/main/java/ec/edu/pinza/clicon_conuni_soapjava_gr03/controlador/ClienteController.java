package ec.edu.pinza.clicon_conuni_soapjava_gr03.controlador;

import ec.edu.pinza.clicon_conuni_soapjava_gr03.modelo.LoginModel;
import ec.edu.pinza.clicon_conuni_soapjava_gr03.modelo.ConversionModel;
import ec.edu.pinza.clicon_conuni_soapjava_gr03.vista.ConsolaView;
import java.util.Locale;

public class ClienteController {

    private final ConsolaView view;
    private final LoginModel loginModel;
    private final ConversionModel conversionModel;

    private boolean autenticado = false;
    private String usuarioAutenticado = "";

    public ClienteController() {
        this.view = new ConsolaView();
        this.loginModel = new LoginModel();
        this.conversionModel = new ConversionModel();
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
        
        view.mostrarMensaje("Seleccione el tipo de conversion:");
        view.mostrarMensaje("1) Masa (Gramo <-> Kilogramo <-> Libra)");
        view.mostrarMensaje("2) Temperatura (Celsius <-> Fahrenheit <-> Kelvin)");
        view.mostrarMensaje("3) Longitud (Metro <-> Centimetro <-> Pulgada)");
        
        int tipoConversion = view.leerOpcionNumerica("Elija una opcion");
        
        view.mostrarMensaje("");
        
        switch (tipoConversion) {
            case 1 -> convertirMasa();
            case 2 -> convertirTemperatura();
            case 3 -> convertirLongitud();
            default -> view.mostrarMensaje("ADVERTENCIA: Opcion invalida.");
        }
        
        view.pausar();
    }
    
    private void convertirMasa() {
        view.mostrarMensaje("=== CONVERSION DE MASA ===");
        double valor = view.pedirValor("Ingrese el valor");
        String unidadOrigen = view.seleccionarUnidadMasa("Seleccione unidad de origen");
        String unidadDestino = view.seleccionarUnidadMasa("Seleccione unidad de destino");
        
        try {
            double resultado = conversionModel.conversionUnidades(valor, unidadOrigen, unidadDestino);
            view.mostrarMensaje("");
            view.mostrarMensaje("===== RESULTADO =====");
            view.mostrarMensaje(String.format(Locale.US, "%s %s = %s %s", 
                formatearNumero(valor), view.formatearUnidadMasa(unidadOrigen), 
                formatearNumero(resultado), view.formatearUnidadMasa(unidadDestino)));
        } catch (Exception e) {
            view.mostrarMensaje("Error en la conversion: " + e.getMessage());
        }
    }
    
    private void convertirTemperatura() {
        view.mostrarMensaje("=== CONVERSION DE TEMPERATURA ===");
        double valor = view.pedirValor("Ingrese el valor");
        String unidadOrigen = view.seleccionarUnidadTemperatura("Seleccione unidad de origen");
        String unidadDestino = view.seleccionarUnidadTemperatura("Seleccione unidad de destino");
        
        try {
            double resultado = conversionModel.conversionUnidades(valor, unidadOrigen, unidadDestino);
            view.mostrarMensaje("");
            view.mostrarMensaje("===== RESULTADO =====");
            view.mostrarMensaje(String.format(Locale.US, "%s %s = %s %s", 
                formatearNumero(valor), view.formatearUnidadTemperatura(unidadOrigen), 
                formatearNumero(resultado), view.formatearUnidadTemperatura(unidadDestino)));
        } catch (Exception e) {
            view.mostrarMensaje("Error en la conversion: " + e.getMessage());
        }
    }
    
    private void convertirLongitud() {
        view.mostrarMensaje("=== CONVERSION DE LONGITUD ===");
        double valor = view.pedirValor("Ingrese el valor");
        String unidadOrigen = view.seleccionarUnidadLongitud("Seleccione unidad de origen");
        String unidadDestino = view.seleccionarUnidadLongitud("Seleccione unidad de destino");
        
        try {
            double resultado = conversionModel.conversionUnidades(valor, unidadOrigen, unidadDestino);
            view.mostrarMensaje("");
            view.mostrarMensaje("===== RESULTADO =====");
            view.mostrarMensaje(String.format(Locale.US, "%s %s = %s %s", 
                formatearNumero(valor), view.formatearUnidadLongitud(unidadOrigen), 
                formatearNumero(resultado), view.formatearUnidadLongitud(unidadDestino)));
        } catch (Exception e) {
            view.mostrarMensaje("Error en la conversion: " + e.getMessage());
        }
    }
    
    private String formatearNumero(double value) {
        double absValue = Math.abs(value);
        
        // Para números muy pequeños (menores a 0.001) o muy grandes (mayores a 1,000,000)
        if ((absValue > 0 && absValue < 0.001) || absValue >= 1_000_000) {
            return String.format(Locale.US, "%.6e", value);
        }
        // Para números normales, usar formato con 3 decimales
        else if (absValue >= 1000) {
            return String.format(Locale.US, "%,.3f", value);
        }
        // Para números menores a 1000, usar 3 decimales
        else {
            return String.format(Locale.US, "%.3f", value);
        }
    }
}
