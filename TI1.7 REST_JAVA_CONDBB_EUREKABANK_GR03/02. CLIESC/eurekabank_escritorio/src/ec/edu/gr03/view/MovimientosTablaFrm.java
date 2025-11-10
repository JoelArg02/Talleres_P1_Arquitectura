package ec.edu.gr03.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
// Importación de tu modelo local
import ec.edu.gr03.model.Movimiento;

public class MovimientosTablaFrm extends javax.swing.JFrame {

    // --- Variables de componentes (conservamos los nombres originales) ---
    private javax.swing.JLabel lblCuenta;
    private javax.swing.JTable tblMovimientos;
    // Se eliminó lblSaldo para que coincida con tu nuevo archivo

    // Colores del diseño
    private final Color COLOR_HEADER = Color.BLACK;
    private final Color COLOR_SECUNDARIO = new Color(52, 152, 219); // Azul brillante
    private final Color COLOR_GRIS_FONDO = new Color(245, 245, 245); // Fondo claro

    /**
     * Creates new form MovimientosTablaFrm
     */
    public MovimientosTablaFrm(String numeroCuenta) {

        // 1. OBTENER DATOS (lógica de tu nuevo proyecto)
        List<Movimiento> lista = ec.edu.gr03.controller.EurekaBankClient.traerMovimientos(numeroCuenta);

        // 2. CONFIGURAR EL JFRAME
        setTitle("EurekaBank - Movimientos");
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE); // Importante
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null); // Centrar
        getContentPane().setLayout(new BorderLayout());

        // 3. PANEL PRINCIPAL
        JPanel pnlMain = new JPanel(new BorderLayout(10, 10));
        pnlMain.setBackground(COLOR_GRIS_FONDO);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 4. PANEL DE TÍTULO
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

        // Se eliminó la etiqueta de Saldo

        pnlMain.add(pnlHeader, BorderLayout.NORTH);

        // 5. CONFIGURAR LA TABLA Y EL MODELO

        // Crear el modelo de la tabla
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("NroMov");
        modelo.addColumn("Fecha");
        modelo.addColumn("Tipo");
        modelo.addColumn("Acción");
        modelo.addColumn("Importe");

        // Llenar el modelo con los datos (lógica de tu nuevo proyecto)
        if (lista.isEmpty()) {
            lblCuenta.setText("No se encontraron registros para la cuenta: " + numeroCuenta);
        } else {
            lblCuenta.setText("Mostrando movimientos de la cuenta: " + numeroCuenta);

            // Llenar el modelo
            for (Movimiento mov : lista) {
                modelo.addRow(new Object[]{
                        mov.getNromov(),
                        mov.getFecha().toString(), // Usando toString() como en tu archivo
                        mov.getTipo(),     // Acceso directo
                        mov.getAccion(),   // Acceso directo
                        // --- CORRECCIÓN AQUÍ ---
                        mov.getImporte() // Se pasa directamente (ya es String)
                });
            }
        }

        // Crear y estilizar la JTable
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

        // 6. SCROLL PANE
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
}