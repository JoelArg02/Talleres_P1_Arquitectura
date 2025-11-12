package ec.edu.gr03.controller;

import ec.edu.gr03.model.EurekaBankClient;
import ec.edu.gr03.view.LoginView;
import ec.edu.gr03.view.MenuView;

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
                    int r = EurekaBankClient.regDeposito(datos[0], Double.parseDouble(datos[1]));
                    menuView.mostrarMensaje("Depósito realizado: " + (r == 1 ? "Éxito" : "Fallo"));
                }
                case 2 -> {
                    String[] datos = menuView.pedirDatosRetiro();
                    int r = EurekaBankClient.regRetiro(datos[0], Double.parseDouble(datos[1]));
                    menuView.mostrarMensaje("Retiro realizado: " + (r == 1 ? "Éxito" : "Fallo"));
                }
                case 3 -> {
                    String[] datos = menuView.pedirDatosTransferencia();
                    int r = EurekaBankClient.regTransferencia(datos[0], datos[1], Double.parseDouble(datos[2]));
                    menuView.mostrarMensaje("Transferencia realizada: " + (r == 1 ? "Éxito" : "Fallo"));
                }
                case 4 -> {
                    String cuenta = menuView.pedirCuentaMovimientos();
                    var lista = EurekaBankClient.traerMovimientos(cuenta);

                    // Título y cuenta
                    menuView.mostrarMensaje("\n=== MOVIMIENTOS DE LA CUENTA ===");
                    menuView.mostrarMensaje("Cuenta: " + cuenta + "\n");

                    // Encabezado
                    menuView.mostrarMensaje("NroMov\tFecha\t\t\t\t\tTipo\t\t\tAcción\t\tImporte");
                    menuView.mostrarMensaje("--------------------------------------------------------------------------------------------------------------------");

                    // Cuerpo
                    for (Movimiento mov : lista) {
                        System.out.printf("%-8s%-32s%-24s%-16s%-10s\n",
                            mov.getNromov(), 
                            mov.getFecha(), 
                            mov.getTipo(), 
                            mov.getAccion(), 
                            mov.getImporte());
                    }
                }

                case 5 -> {
                    var lista = EurekaBankClient.traerBalances();

                    // Título
                    menuView.mostrarMensaje("\n=== BALANCES DE TODAS LAS CUENTAS ===\n");

                    // Encabezado
                    menuView.mostrarMensaje("N° Cuenta\tCliente\t\t\t\t\t\tSaldo\t\tEstado");
                    menuView.mostrarMensaje("--------------------------------------------------------------------------------------------------------------------");

                    // Cuerpo
                    for (Cuenta cuenta : lista) {
                        System.out.printf("%-16s%-48s%-16.2f%-10s\n",
                            cuenta.getNumeroCuenta(), 
                            cuenta.getNombreCliente(), 
                            cuenta.getSaldo(), 
                            cuenta.getEstado());
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
