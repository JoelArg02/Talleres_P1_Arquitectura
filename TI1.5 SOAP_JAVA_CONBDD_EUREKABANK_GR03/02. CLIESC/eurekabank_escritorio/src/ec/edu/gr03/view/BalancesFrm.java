package ec.edu.gr03.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class BalancesFrm extends javax.swing.JFrame {

    // --- Variables para la navegación ---
    private JButton btnMovimientos;
    private JButton btnRetiro;
    private JButton btnDeposito;
    private JButton btnTransferencia;
    private JButton btnBalances;
    private JButton btnCerrarSesion;
    
    // Tabla para mostrar los balances
    private JTable tblBalances;
    private DefaultTableModel modeloTabla;

    // Colores del diseño
    private final Color COLOR_PANEL_IZQUIERDO = Color.BLACK;
    private final Color COLOR_SECUNDARIO = new Color(52, 152, 219); // Azul brillante
    private final Color COLOR_TEXTO = new Color(236, 240, 241); // Blanco grisáceo
    private final Color COLOR_GRIS_FONDO = new Color(245, 245, 245); // Fondo claro
    private final Color COLOR_LOGOUT = new Color(160, 40, 40); // Rojo sutil

    public BalancesFrm() {
        // --- 1. Configuración del JFrame principal ---
        setTitle("EurekaBank - Balances");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null); // Centrar
        getContentPane().setLayout(new BorderLayout());

        // --- 2. Panel Izquierdo (Branding y Navegación) ---
        JPanel pnlLeft = new JPanel(new GridBagLayout());
        pnlLeft.setBackground(COLOR_PANEL_IZQUIERDO);
        pnlLeft.setPreferredSize(new Dimension(300, 600)); // Ancho fijo

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0;
        gbcLeft.fill = GridBagConstraints.HORIZONTAL;
        gbcLeft.insets = new Insets(10, 20, 10, 20); // Padding lateral
        gbcLeft.weightx = 1; // Ocupar todo el ancho

        // Logo
        gbcLeft.gridy = 0;
        gbcLeft.insets = new Insets(30, 20, 30, 20); // Padding superior e inferior
        JLabel lblLogo = new JLabel();
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/ec/edu/gr03/img/logo.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(250, 170, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaledImage));
            lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
        } catch (Exception e) {
            System.err.println("No se pudo cargar el logo.");
            lblLogo.setText("[LOGO]");
        }
        pnlLeft.add(lblLogo, gbcLeft);

        // --- Botones de Navegación ---
        gbcLeft.insets = new Insets(5, 0, 5, 0);

        // Rutas de iconos
        String iconMov = "/ec/edu/gr03/img/icon_movimientos.png";
        String iconRet = "/ec/edu/gr03/img/icon_retiro.png";
        String iconDep = "/ec/edu/gr03/img/icon_deposito.png";
        String iconTrans = "/ec/edu/gr03/img/icon_transferencia.png";
        String iconBal = "/ec/edu/gr03/img/icon_balances.png";
        String iconLogout = "/ec/edu/gr03/img/icon_logout.png";

        gbcLeft.gridy = 1;
        btnMovimientos = createNavButton("Movimientos", iconMov);
        pnlLeft.add(btnMovimientos, gbcLeft);

        gbcLeft.gridy = 2;
        btnRetiro = createNavButton("Retiro", iconRet);
        pnlLeft.add(btnRetiro, gbcLeft);

        gbcLeft.gridy = 3;
        btnDeposito = createNavButton("Deposito", iconDep);
        pnlLeft.add(btnDeposito, gbcLeft);

        gbcLeft.gridy = 4;
        btnTransferencia = createNavButton("Transferencia", iconTrans);
        pnlLeft.add(btnTransferencia, gbcLeft);
        
        gbcLeft.gridy = 5;
        btnBalances = createNavButton("Balances", iconBal);
        // --- ESTE ES EL BOTÓN ACTIVO ---
        btnBalances.setBackground(COLOR_SECUNDARIO);
        btnBalances.setForeground(Color.WHITE);
        pnlLeft.add(btnBalances, gbcLeft);

        // Botón de Cerrar Sesión (empujado al fondo)
        gbcLeft.gridy = 6;
        gbcLeft.weighty = 1; // ESTO EMPUJA EL BOTÓN HACIA ABAJO
        gbcLeft.anchor = GridBagConstraints.SOUTH; // Anclar al sur
        gbcLeft.insets = new Insets(5, 0, 20, 0); // Padding inferior
        btnCerrarSesion = createNavButton("Cerrar Sesión", iconLogout);
        btnCerrarSesion.setBackground(COLOR_LOGOUT); // Color rojo sutil
        pnlLeft.add(btnCerrarSesion, gbcLeft);

        // --- 3. Panel Derecho (Tabla de Balances) ---
        JPanel pnlRight = new JPanel(new GridBagLayout());
        pnlRight.setBackground(COLOR_GRIS_FONDO); // Fondo gris claro

        // Panel interno para la tabla
        JPanel pnlTabla = new JPanel(new BorderLayout());
        pnlTabla.setBackground(Color.WHITE); // El panel en sí es blanco
        pnlTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20) // Padding interno
        ));

        // Título "Balances de Cuentas"
        JLabel lblTitle = new JLabel("BALANCES DE CUENTAS");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(COLOR_PANEL_IZQUIERDO);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        pnlTabla.add(lblTitle, BorderLayout.NORTH);

        // Crear tabla
        String[] columnas = {"N° Cuenta", "Cliente", "Saldo", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };
        tblBalances = new JTable(modeloTabla);
        tblBalances.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tblBalances.setRowHeight(30);
        tblBalances.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblBalances.getTableHeader().setBackground(COLOR_SECUNDARIO);
        tblBalances.getTableHeader().setForeground(Color.WHITE);
        tblBalances.setSelectionBackground(new Color(173, 216, 230));
        
        // Alinear columnas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        tblBalances.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblBalances.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        
        // Alinear saldo a la derecha
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tblBalances.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        JScrollPane scrollPane = new JScrollPane(tblBalances);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        pnlTabla.add(scrollPane, BorderLayout.CENTER);

        // Añadir panel de tabla al panel derecho (para centrarlo)
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.fill = GridBagConstraints.BOTH;
        gbcRight.weightx = 1;
        gbcRight.weighty = 1;
        gbcRight.insets = new Insets(20, 20, 20, 20);
        pnlRight.add(pnlTabla, gbcRight);

        // --- 4. AÑADIR LOS LISTENERS ---
        btnMovimientos.addActionListener(e -> irAMovimientos());
        btnRetiro.addActionListener(e -> irARetiro());
        btnDeposito.addActionListener(e -> irADeposito());
        btnTransferencia.addActionListener(e -> irATransferencia());
        btnCerrarSesion.addActionListener(e -> irALogin());

        // --- 5. Ensamblaje Final ---
        getContentPane().add(pnlLeft, BorderLayout.WEST);
        getContentPane().add(pnlRight, BorderLayout.CENTER);

        pack(); // Ajusta el tamaño de la ventana
        
        // Cargar los balances
        cargarBalances();
    }

    /**
     * Carga los balances desde el servicio web y los muestra en la tabla.
     * NOTA: Descomentar después de recompilar el servidor y regenerar los Web Service References
     */
    private void cargarBalances() {
        try {
            // Limpiar la tabla
            modeloTabla.setRowCount(0);
            
            // Obtener los balances del servicio
            List<ec.edu.gr03.controller.Cuenta> balances = ec.edu.gr03.model.EurekaBankClient.traerBalances();
            
            // Formato para números
            DecimalFormat df = new DecimalFormat("#,##0.00");
            
            // Llenar la tabla
            for (ec.edu.gr03.controller.Cuenta cuenta : balances) {
                Object[] fila = new Object[4];
                fila[0] = cuenta.getNumeroCuenta();
                fila[1] = cuenta.getNombreCliente();
                fila[2] = df.format(cuenta.getSaldo());
                fila[3] = cuenta.getEstado();
                modeloTabla.addRow(fila);
            }
            
            if (balances.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "No hay cuentas activas para mostrar.", 
                    "Información", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al cargar los balances: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Crea un botón de navegación estilizado para el panel lateral.
     * @param text El texto del botón.
     * @param iconPath La ruta al icono (ej: /ec/edu/gr03/img/icon.png)
     */
    private JButton createNavButton(String text, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(COLOR_TEXTO);
        button.setBackground(COLOR_PANEL_IZQUIERDO); // Fondo negro
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));
        button.setHorizontalAlignment(SwingConstants.LEFT); // Alineado a la izquierda

        if (iconPath != null && !iconPath.isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
                Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(img));
                button.setIconTextGap(20); // Espacio entre icono y texto
            } catch (Exception e) {
                System.err.println("No se pudo cargar el icono: " + iconPath);
                button.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 30));
            }
        }

        // Efecto Hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.getBackground() != COLOR_SECUNDARIO && button.getBackground() != COLOR_LOGOUT) {
                    button.setBackground(new Color(50, 50, 50));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.getBackground() != COLOR_SECUNDARIO && button.getBackground() != COLOR_LOGOUT) {
                    button.setBackground(COLOR_PANEL_IZQUIERDO);
                }
            }
        });

        return button;
    }

    // =========================================================================
    //   MÉTODOS DE NAVEGACIÓN
    // =========================================================================

    private void irAMovimientos() {
        MovimientosFrm movFrm = new MovimientosFrm();
        movFrm.setVisible(true);
        this.dispose();
    }

    private void irARetiro() {
        RetiroFrm retiroFrm = new RetiroFrm();
        retiroFrm.setVisible(true);
        this.dispose();
    }

    private void irADeposito() {
        DepositoFrm depositoFrm = new DepositoFrm();
        depositoFrm.setVisible(true);
        this.dispose();
    }

    private void irATransferencia() {
        TransferenciasFrm transFrm = new TransferenciasFrm();
        transFrm.setVisible(true);
        this.dispose();
    }

    private void irALogin() {
        LoginFrm loginFrm = new LoginFrm();
        loginFrm.setVisible(true);
        this.dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BalancesFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BalancesFrm().setVisible(true);
            }
        });
    }
}
