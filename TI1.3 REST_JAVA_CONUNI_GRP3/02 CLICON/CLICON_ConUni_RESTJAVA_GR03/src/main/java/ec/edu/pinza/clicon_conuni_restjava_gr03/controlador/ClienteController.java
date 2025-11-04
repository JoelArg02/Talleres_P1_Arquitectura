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
        double valor = view.pedirValor("Ingrese el numero que desea convertir");
        String unidadOrigen = view.seleccionarUnidad("Seleccione la unidad de origen");
        String unidadDestino = view.seleccionarUnidad("Seleccione la unidad de destino");

        try {
            double resultado = conversionModel.conversionUnidades(valor, unidadOrigen, unidadDestino);
            String etiquetaOrigen = view.formatearUnidad(unidadOrigen);
            String etiquetaDestino = view.formatearUnidad(unidadDestino);
            String mensaje = String.format(Locale.US,
                    "Resultado: %.4f %s = %.4f %s",
                    valor, etiquetaOrigen, resultado, etiquetaDestino);
            view.mostrarMensaje(mensaje);
        } catch (IOException e) {
            view.mostrarMensaje("ADVERTENCIA: Error al conectarse al servicio REST de conversion.");
            view.mostrarMensaje("Detalle: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            view.mostrarMensaje("ADVERTENCIA: Operacion de conversion interrumpida.");
        }
        view.pausar();
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
