package ec.edu.pinza.clicon_conuni_restdotnet_gr03.controlador;

import ec.edu.pinza.clicon_conuni_restdotnet_gr03.modelo.ConversionModel;
import ec.edu.pinza.clicon_conuni_restdotnet_gr03.modelo.ConversionResult;
import ec.edu.pinza.clicon_conuni_restdotnet_gr03.modelo.LoginModel;
import ec.edu.pinza.clicon_conuni_restdotnet_gr03.vista.ConsolaView;
import java.io.IOException;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Locale;

public class ClienteController {

    private static final String DEFAULT_BASE_URL = "http://localhost:5003/api";

    private final ConsolaView view;
    private final LoginModel loginModel;
    private final ConversionModel conversionModel;

    private boolean autenticado = false;
    private String usuarioAutenticado = "";

    public ClienteController() {
        this(resolveBaseUrl());
    }

    ClienteController(String baseUrl) {
        this.view = new ConsolaView();
        HttpClient httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.loginModel = new LoginModel();
        this.conversionModel = new ConversionModel(httpClient, baseUrl);
    }

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

    private void autenticarUsuario() {
        view.mostrarMensaje("");
        view.mostrarMensaje("===== AUTENTICACION =====");
        String usuario = view.pedirUsuario();
        String contrasena = view.pedirContrasena();

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
        view.pausar();
    }

    private void convertirUnidades() {
        if (!autenticado) {
            view.mostrarMensaje("ADVERTENCIA: Debe iniciar sesion antes de convertir unidades.");
            view.pausar();
            return;
        }

        view.mostrarMensaje("");
        view.mostrarMensaje("===== CONVERSION DE UNIDADES =====");
        
        view.mostrarMensaje("Seleccione el tipo de conversion:");
        view.mostrarMensaje("1) Masa (Miligramo <-> Gramo <-> Kilogramo <-> Libra <-> Onza <-> Tonelada)");
        view.mostrarMensaje("2) Temperatura (Celsius <-> Fahrenheit <-> Kelvin <-> Rankine)");
        view.mostrarMensaje("3) Longitud (Milimetro <-> Centimetro <-> Metro <-> Kilometro <-> Pulgada <-> Pie)");
        
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
            ConversionResult resultado = conversionModel.convertirMasa(valor, unidadOrigen, unidadDestino);
            view.mostrarMensaje("");
            view.mostrarMensaje("===== RESULTADO =====");
            view.mostrarMensaje(String.format(Locale.US, "%s %s = %s %s", 
                formatearNumero(valor), view.formatearUnidadMasa(unidadOrigen), 
                formatearNumero(resultado.convertedValue()), view.formatearUnidadMasa(unidadDestino)));
        } catch (IOException e) {
            view.mostrarMensaje("Error al conectarse al servicio: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            view.mostrarMensaje("Operacion interrumpida.");
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
            ConversionResult resultado = conversionModel.convertirTemperatura(valor, unidadOrigen, unidadDestino);
            view.mostrarMensaje("");
            view.mostrarMensaje("===== RESULTADO =====");
            view.mostrarMensaje(String.format(Locale.US, "%s %s = %s %s", 
                formatearNumero(valor), view.formatearUnidadTemperatura(unidadOrigen), 
                formatearNumero(resultado.convertedValue()), view.formatearUnidadTemperatura(unidadDestino)));
        } catch (IOException e) {
            view.mostrarMensaje("Error al conectarse al servicio: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            view.mostrarMensaje("Operacion interrumpida.");
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
            ConversionResult resultado = conversionModel.convertirLongitud(valor, unidadOrigen, unidadDestino);
            view.mostrarMensaje("");
            view.mostrarMensaje("===== RESULTADO =====");
            view.mostrarMensaje(String.format(Locale.US, "%s %s = %s %s", 
                formatearNumero(valor), view.formatearUnidadLongitud(unidadOrigen), 
                formatearNumero(resultado.convertedValue()), view.formatearUnidadLongitud(unidadDestino)));
        } catch (IOException e) {
            view.mostrarMensaje("Error al conectarse al servicio: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            view.mostrarMensaje("Operacion interrumpida.");
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

    private static String resolveBaseUrl() {
        String fromEnv = System.getenv("CONUNI_API_BASE");
        if (fromEnv != null && !fromEnv.isBlank()) {
            return normalizeUrl(fromEnv.trim());
        }
        String fromProperty = System.getProperty("conuni.api.base");
        if (fromProperty != null && !fromProperty.isBlank()) {
            return normalizeUrl(fromProperty.trim());
        }
        return DEFAULT_BASE_URL;
    }

    private static String normalizeUrl(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }
}
