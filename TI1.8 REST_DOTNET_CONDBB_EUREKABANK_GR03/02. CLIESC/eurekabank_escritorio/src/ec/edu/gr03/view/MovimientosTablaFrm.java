package ec.edu.gr03.view;

import ec.edu.gr03.controller.Movimiento;
import ec.edu.gr03.model.EurekaBankClient;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class MovimientosTablaFrm extends javax.swing.JFrame {

    // --- Variables de componentes (conservamos los nombres originales) ---
    private javax.swing.JLabel lblCuenta;
    private javax.swing.JTable tblMovimientos;
    private javax.swing.JLabel lblSaldo; // <--- VARIABLE AÑADIDA

    // Colores del diseño
    private final Color COLOR_HEADER = Color.BLACK;
    private final Color COLOR_SECUNDARIO = new Color(52, 152, 219); // Azul brillante
    private final Color COLOR_GRIS_FONDO = new Color(245, 245, 245); // Fondo claro

    /**
     * Creates new form MovimientosTablaFrm
     */
    public MovimientosTablaFrm(String numeroCuenta) {

        // 1. OBTENER DATOS
        List<Movimiento> lista = EurekaBankClient.traerMovimientos(numeroCuenta);

        // 2. CONFIGURAR EL JFRAME (del 1er archivo)
        setTitle("EurekaBank - Movimientos");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // Importante
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null); // Centrar
        getContentPane().setLayout(new BorderLayout());

        // 3. PANEL PRINCIPAL (del 1er archivo)
        JPanel pnlMain = new JPanel(new BorderLayout(10, 10));
        pnlMain.setBackground(COLOR_GRIS_FONDO);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 4. PANEL DE TÍTULO (Fusionado)
        JPanel pnlHeader = new JPanel(new GridBagLayout());
        pnlHeader.setBackground(Color.WHITE); // Fondo blanco
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1.0;

        JLabel lblTitle = new JLabel("Historial de Movimientos");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(COLOR_HEADER);
        pnlHeader.add(lblTitle, gbc);

        // Etiqueta para el número de cuenta
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        lblCuenta = new JLabel(); // Se inicializa aquí
        lblCuenta.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCuenta.setForeground(Color.GRAY);
        pnlHeader.add(lblCuenta, gbc);

        // --- ETIQUETA DE SALDO AÑADIDA ---
        gbc.gridy = 2;
        lblSaldo = new JLabel(); // Se inicializa aquí
        lblSaldo.setFont(new Font("Segoe UI", Font.BOLD, 16)); // En negrita
        lblSaldo.setForeground(COLOR_HEADER); // En negro
        pnlHeader.add(lblSaldo, gbc);

        pnlMain.add(pnlHeader, BorderLayout.NORTH);

        // 5. CONFIGURAR LA TABLA Y EL MODELO (Fusionado)

        // Crear el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("NroMov");
        modelo.addColumn("Fecha");
        modelo.addColumn("Tipo");
        modelo.addColumn("Acción");
        modelo.addColumn("Importe");

        // Formateador de fecha
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        // Llenar el modelo con los datos
        if (lista == null || lista.isEmpty()) {
            lblCuenta.setText("No se encontraron registros para la cuenta: " + numeroCuenta);
            lblSaldo.setText("Saldo: N/A");
        } else {
            lblCuenta.setText("Mostrando movimientos de la cuenta: " + numeroCuenta);
            // Calcular saldo
            String saldo = String.format("%.2f", EurekaBankClient.ObtenerSaldo(lista));
            lblSaldo.setText("Saldo Actual: $" + saldo);

            // Revertir la lista para mostrar del más reciente al más antiguo
            Collections.reverse(lista);

            for (Movimiento mov : lista) {
                modelo.addRow(new Object[]{
                        mov.getNroMov(),
                        // Formatear la fecha (ya viene como String del JSON)
                        mov.getFecha(),
                        mov.getTipo(),
                        mov.getAccion(),
                        // Formatear el importe a 2 decimales
                        String.format("%.2f", mov.getImporte())
                });
            }
        }

        // Crear y estilizar la JTable (del 1er archivo)
        tblMovimientos = new JTable(modelo);

        // --- ESTILO DE LA TABLA ---
        tblMovimientos.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblMovimientos.setRowHeight(30);
        tblMovimientos.setGridColor(new Color(220, 220, 220));
        tblMovimientos.setShowGrid(true);
        tblMovimientos.setShowHorizontalLines(true);
        tblMovimientos.setShowVerticalLines(false);
        tblMovimientos.setSelectionBackground(COLOR_SECUNDARIO);
        tblMovimientos.setSelectionForeground(Color.WHITE);
        tblMovimientos.setBorder(null);

        // --- ESTILO DEL ENCABEZADO (HEADER) ---
        JTableHeader header = tblMovimientos.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(COLOR_HEADER); // Fondo negro
        header.setForeground(Color.WHITE); // Texto blanco
        header.setOpaque(true);
        header.setPreferredSize(new Dimension(100, 40));
        header.setBorder(BorderFactory.createLineBorder(COLOR_HEADER));

        // 6. SCROLL PANE (del 1er archivo)
        JScrollPane jScrollPane1 = new JScrollPane(tblMovimientos);
        jScrollPane1.setBorder(BorderFactory.createEmptyBorder()); // Sin borde
        jScrollPane1.getViewport().setBackground(Color.WHITE); // Fondo blanco

        pnlMain.add(jScrollPane1, BorderLayout.CENTER);

        // 7. ENSAMBLAJE FINAL
        getContentPane().add(pnlMain, BorderLayout.CENTER);

        pack(); // Ajusta el tamaño
    }

    // El initComponents() generado por NetBeans se elimina.

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MovimientosTablaFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                // El constructor necesita una cuenta, pasamos una vacía para prueba
                new MovimientosTablaFrm("").setVisible(true);
            }
        });
    }

    // --- Variables generadas por NetBeans (eliminadas) ---
    // private javax.swing.JLabel jLabel1;
    // ...
    // private javax.swing.JTable tblMovimientos;
    // private javax.swing.JLabel lblSaldo;
}