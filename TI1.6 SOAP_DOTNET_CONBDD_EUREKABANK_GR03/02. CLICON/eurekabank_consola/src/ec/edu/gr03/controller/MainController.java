package ec.edu.gr03.controller;

import ec.edu.gr03.model.EurekaBankClient;
import ec.edu.gr03.view.LoginView;
import ec.edu.gr03.view.MenuView;
import java.util.Collections; // <--- AÑADIR ESTA LÍNEA
import java.util.List; // <--- AÑADIR ESTA LÍNEA
import org.datacontract.schemas._2004._07.ec_edu_monster.ArrayOfMovimiento;
import org.datacontract.schemas._2004._07.ec_edu_monster.Movimiento;

public class MainController {
    private final LoginView loginView = new LoginView();
    private final MenuView menuView = new MenuView();

    public void iniciarAplicacion() {
        boolean autenticado = false;
        String usuario = "";

        while (!autenticado) {
            String[] datos = loginView.mostrarLogin();
            autenticado = EurekaBankClient.login(datos[0], datos[1]);
            if (!autenticado) {
                loginView.mostrarErrorLogin();
            } else {
                usuario = datos[0];
                loginView.mostrarBienvenida(usuario);
            }
        }

        boolean salir = false;
        while (!salir) {
            int opcion = menuView.mostrarMenu();
            switch (opcion) {
                case 1 -> {
                    String[] datos = menuView.pedirDatosDeposito();
                    String r = EurekaBankClient.registrarDeposito(datos[0], Double.parseDouble(datos[1]));
                    menuView.mostrarMensaje("Depósito realizado: " + ("1".equals(r) ? "Éxito" : "Fallo"));
                }
                case 2 -> {
                    String[] datos = menuView.pedirDatosRetiro();
                    String r = EurekaBankClient.registrarRetiro(datos[0], Double.parseDouble(datos[1]));
                    menuView.mostrarMensaje("Retiro realizado: " + ("1".equals(r) ? "Éxito" : "Fallo"));
                }
                case 3 -> {
                    String[] datos = menuView.pedirDatosTransferencia();
                    String r = EurekaBankClient.registrarTransferencia(datos[0], datos[1], Double.parseDouble(datos[2]));
                    menuView.mostrarMensaje("Transferencia realizada: " + ("1".equals(r) ? "Éxito" : "Fallo"));
                }
                case 4 -> {
                    String cuenta = menuView.pedirCuentaMovimientos();
                    var lista = EurekaBankClient.obtenerPorCuenta(cuenta);

                    // Título y cuenta
                    menuView.mostrarMensaje("\n=== MOVIMIENTOS DE LA CUENTA ===");
                    menuView.mostrarMensaje("Cuenta: " + cuenta);
                    menuView.mostrarMensaje("Saldo: " + EurekaBankClient.ObtenerSaldo(lista.getMovimiento())+ "\n");

                    // Encabezado
                    menuView.mostrarMensaje("NroMov\tFecha\t\t\t\tTipo\t\t\tAcción\t\tImporte");
                    menuView.mostrarMensaje("--------------------------------------------------------------------------------------------------------------------");

                    // --- INICIO DE LA CORRECCIÓN ---
                    // Obtenemos la lista primero
                    List<Movimiento> movimientos = lista.getMovimiento();
                    // La revertimos usando Collections
                    Collections.reverse(movimientos);

                    // Cuerpo (iteramos sobre la lista ya revertida)
                    for (Movimiento mov : movimientos) {
                    // --- FIN DE LA CORRECCIÓN ---
                        System.out.printf("%-8d%-32s%-24s%-16s%-10.2f\n",
                        mov.getNroMov(), mov.getFecha().toString(), mov.getTipo().getValue(), mov.getAccion().getValue(), mov.getImporte());
                    }
                }

                case 0 -> {
                    salir = true;
                    menuView.mostrarMensaje("Gracias por usar el sistema.");
                }
                default -> menuView.mostrarMensaje("Opción no válida.");
            }
        }
    }
}