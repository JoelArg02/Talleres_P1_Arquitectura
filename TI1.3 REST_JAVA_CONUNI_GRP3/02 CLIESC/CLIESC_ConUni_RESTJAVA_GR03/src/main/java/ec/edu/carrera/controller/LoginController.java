package ec.edu.carrera.controller;

import ec.edu.carrera.model.ApiService;
import ec.edu.carrera.view.JConversionWindowNew;
import ec.edu.carrera.view.JLoginWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingWorker;

public class LoginController implements ActionListener {
    
    private final JLoginWindow view;
    private final ApiService model;

    public LoginController(JLoginWindow view, ApiService model) {
        this.view = view;
        this.model = model;
        this.view.addLoginListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String username = view.getUsername();
        String password = view.getPassword();
        view.setLoading(true);

        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return model.login(username, password);
            }

            @Override
            protected void done() {
                try {
                    if (get()) { 
                        // CAMBIO: Pasa el username a la nueva ventana
                        abrirVentanaConversion(username);
                        view.dispose();
                    } else {
                        view.showErrorMessage("Credenciales inválidas.");
                        view.setLoading(false);
                    }
                } catch (Exception ex) {
                    view.showErrorMessage("Error de conexión con el servidor.");
                    view.setLoading(false);
                }
            }
        }.execute();
    }
    
    // CAMBIO: Acepta el username y pasa ApiService al constructor
    private void abrirVentanaConversion(String username) {
        JConversionWindowNew convView = new JConversionWindowNew(username, model); // Pasa username y apiService
        convView.setVisible(true);
    }
}