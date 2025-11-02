package ec.edu.pinza.clicon_conuni_soapjava_gr03.controlador;

import ec.edu.pinza.clicon_conuni_soapjava_gr03.modelo.ConversionModel;
import ec.edu.pinza.clicon_conuni_soapjava_gr03.modelo.LoginModel;
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
     * Maneja la conversion de unidades.
     */
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
        } catch (Exception e) {
            view.mostrarMensaje("ADVERTENCIA: Error al conectar con el servicio de conversion.");
            e.printStackTrace();
        }
        view.pausar();
    }
}
