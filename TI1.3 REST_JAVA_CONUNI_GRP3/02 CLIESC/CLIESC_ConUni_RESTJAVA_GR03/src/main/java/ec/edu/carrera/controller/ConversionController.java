package ec.edu.carrera.controller;

import ec.edu.carrera.model.ApiService;
import ec.edu.carrera.model.ConUni;
import ec.edu.carrera.view.JConversionWindow;
import ec.edu.carrera.view.JLoginWindow; // Importar JLoginWindow

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.SwingWorker;

public class ConversionController implements ActionListener, ItemListener {

    private final JConversionWindow view;
    private final ApiService model;

    public ConversionController(JConversionWindow view, ApiService model) {
        this.view = view;
        this.model = model;
        
        // Suscribir el controlador a TODOS los eventos de la vista
        this.view.addTypeChangeListener(this);
        this.view.addConvertListener(this);
        this.view.addClearListener(this);
        this.view.addSwapListener(this); // Nuevo
        this.view.addLogoutListener(this); // Nuevo
        
        cargarTipos();
    }

    // Manejador de eventos de botones
    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtenemos el texto del botón o comando
        String command = e.getActionCommand();
        
        if (null != command) switch (command) {
            case "CONVERTIR":
                realizarConversion();
                break;
            case "Limpiar":
                limpiarCampos();
                break;
            case "↔": // Botón de Swap
                intercambiarUnidades();
                break;
            case "Cerrar Sesión": // Opción de Logout
                realizarLogout();
                break;
            default:
                break;
        }
    }

    // (El resto del código (itemStateChanged, cargarTipos, cargarUnidades, realizarConversion) 
    // es idéntico al de la respuesta anterior, no cambia.)
    
    private void intercambiarUnidades() {
        String from = view.getFromUnit();
        String to = view.getToUnit();
        view.setUnits(to, from); // Llama al nuevo método en la vista
    }
    
    private void realizarLogout() {
        // 1. Abrir una nueva ventana de Login
        JLoginWindow.main(new String[0]); // Llama al main para reiniciar
        // 2. Cerrar esta ventana de Conversión
        view.dispose();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            cargarUnidades();
        }
    }

    private void cargarTipos() {
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                return model.getSupportedTypes();
            }
            @Override
            protected void done() {
                try {
                    view.setTypes(get());
                    cargarUnidades(); 
                } catch (Exception e) { view.setResult("Error al cargar tipos."); }
            }
        }.execute();
    }

    private void cargarUnidades() {
        String selectedType = view.getSelectedType();
        if (selectedType == null) return;
        new SwingWorker<List<String>, Void>() {
            @Override
            protected List<String> doInBackground() throws Exception {
                return model.getSupportedUnits(selectedType);
            }
            @Override
            protected void done() {
                try {
                    view.setUnits(get());
                } catch (Exception e) { view.setResult("Error al cargar unidades."); }
            }
        }.execute();
    }

    private void realizarConversion() {
        try {
            ConUni conversionRequest = new ConUni(
                view.getSelectedType(),
                Double.parseDouble(view.getInputValue()),
                view.getFromUnit(),
                view.getToUnit()
            );
            view.setResult("Convirtiendo...");
            new SwingWorker<Double, Void>() {
                @Override
                protected Double doInBackground() throws Exception {
                    return model.convert(conversionRequest);
                }
                @Override
                protected void done() {
                    try {
                        view.setResult(String.format("%.2f", get()));
                    } catch (Exception e) { view.setResult("Error."); }
                }
            }.execute();
        } catch (NumberFormatException e) {
            view.setResult("Valor inválido.");
        }
    }

    private void limpiarCampos() {
        view.setResult("0.00");
        // (Deberíamos añadir un 'view.setInputValue("")' a la vista)
        if (view.getSelectedType() != null) {
            cargarUnidades();
        }
    }
}