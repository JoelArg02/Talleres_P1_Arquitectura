package ec.edu.pinza.clicon_conuni_restjava_gr03.controlador;

import ec.edu.pinza.clicon_conuni_restjava_gr03.modelo.ConversionModel;
import ec.edu.pinza.clicon_conuni_restjava_gr03.modelo.LoginModel;
import ec.edu.pinza.clicon_conuni_restjava_gr03.vista.ConsolaView;
import java.io.IOException;
import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Locale;

public class ClienteController {

    private static final String DEFAULT_BASE_URL = "http://localhost:8080/WS_ConUni_RESTJAVA_GR03/api";

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
        this.loginModel = new LoginModel(httpClient, baseUrl);
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
        } catch (IOException e) {
            autenticado = false;
            usuarioAutenticado = "";
            view.mostrarMensaje("ADVERTENCIA: Error al conectarse al servicio REST de autenticacion.");
            view.mostrarMensaje("Detalle: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            autenticado = false;
            usuarioAutenticado = "";
            view.mostrarMensaje("ADVERTENCIA: Operacion de autenticacion interrumpida.");
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
        view.mostrarMensaje("1) Masa");
        view.mostrarMensaje("2) Temperatura");
        view.mostrarMensaje("3) Longitud");
        
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
        } catch (IOException e) {
            view.mostrarMensaje("Error de conexion: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            view.mostrarMensaje("Operacion interrumpida.");
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
        } catch (IOException e) {
            view.mostrarMensaje("Error de conexion: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            view.mostrarMensaje("Operacion interrumpida.");
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
        } catch (IOException e) {
            view.mostrarMensaje("Error de conexion: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            view.mostrarMensaje("Operacion interrumpida.");
        }
    }
    
    private String formatearNumero(double value) {
        double absValue = Math.abs(value);
        
        if ((absValue > 0 && absValue < 0.001) || absValue >= 1_000_000) {
            return String.format(Locale.US, "%.6e", value);
        } else if (absValue >= 1000) {
            return String.format(Locale.US, "%,.3f", value);
        } else {
            return String.format(Locale.US, "%.3f", value);
        }
    }

    private static String resolveBaseUrl() {
        String fromEnv = System.getenv("CONUNI_API_BASE");
        if (fromEnv != null && !fromEnv.isBlank()) {
            return fromEnv.trim();
        }
        String fromProperty = System.getProperty("conuni.api.base");
        if (fromProperty != null && !fromProperty.isBlank()) {
            return fromProperty.trim();
        }
        return DEFAULT_BASE_URL;
    }
}
