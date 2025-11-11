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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField; // Mantenido por si acaso
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

public class DepositoFrm extends javax.swing.JFrame {

    // --- Variables de componentes (conservamos los nombres originales) ---
    private JButton btnDepositar;
    private JTextField txtCuenta;
    private JTextField txtValor;

    // --- Variables para la nueva navegación ---
    private JButton btnMovimientos;
    private JButton btnRetiro;
    private JButton btnDeposito;
    private JButton btnTransferencia;
    private JButton btnBalances;
    private JButton btnCerrarSesion;

    // Colores del diseño
    private final Color COLOR_PANEL_IZQUIERDO = Color.BLACK;
    private final Color COLOR_SECUNDARIO = new Color(52, 152, 219); // Azul brillante
    private final Color COLOR_TEXTO = new Color(236, 240, 241); // Blanco grisáceo
    private final Color COLOR_GRIS_FONDO = new Color(245, 245, 245); // Fondo claro
    private final Color COLOR_HINT = Color.GRAY; // Para placeholders
    private final Color COLOR_LOGOUT = new Color(160, 40, 40); // Rojo sutil

    public DepositoFrm() {
        // --- 1. Configuración del JFrame principal ---
        setTitle("EurekaBank - Depósitos");
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
            // Usamos logo.png como en los Frms anteriores
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
        // --- ESTE ES EL BOTÓN ACTIVO ---
        btnDeposito.setBackground(COLOR_SECUNDARIO);
        btnDeposito.setForeground(Color.WHITE);
        pnlLeft.add(btnDeposito, gbcLeft);

        gbcLeft.gridy = 4;
        btnTransferencia = createNavButton("Transferencia", iconTrans);
        pnlLeft.add(btnTransferencia, gbcLeft);

        gbcLeft.gridy = 5;
        btnBalances = createNavButton("Balances", iconBal);
        pnlLeft.add(btnBalances, gbcLeft);

        // Botón de Cerrar Sesión (empujado al fondo)
        gbcLeft.gridy = 6;
        gbcLeft.weighty = 1; // ESTO EMPUJA EL BOTÓN HACIA ABAJO
        gbcLeft.anchor = GridBagConstraints.SOUTH; // Anclar al sur
        gbcLeft.insets = new Insets(5, 0, 20, 0); // Padding inferior
        btnCerrarSesion = createNavButton("Cerrar Sesión", iconLogout);
        btnCerrarSesion.setBackground(COLOR_LOGOUT); // Color rojo sutil
        pnlLeft.add(btnCerrarSesion, gbcLeft);

        // --- 3. Panel Derecho (Formulario de Depósito) ---
        JPanel pnlRight = new JPanel(new GridBagLayout());
        pnlRight.setBackground(COLOR_GRIS_FONDO); // Fondo gris claro

        // Panel interno para centrar el formulario
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE); // El formulario en sí es blanco
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(40, 40, 40, 40) // Padding interno
        ));

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.gridx = 0;
        gbcForm.gridwidth = GridBagConstraints.REMAINDER;
        gbcForm.fill = GridBagConstraints.HORIZONTAL;
        gbcForm.insets = new Insets(10, 5, 10, 5); // Espaciado

        // Título "Realizar Depósito"
        gbcForm.gridy = 0;
        gbcForm.insets = new Insets(0, 5, 25, 5); // Más espacio abajo
        JLabel lblLoginTitle = new JLabel("REALIZAR DEPÓSITO");
        lblLoginTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblLoginTitle.setForeground(COLOR_PANEL_IZQUIERDO); // Color negro
        lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pnlForm.add(lblLoginTitle, gbcForm);

        // Resetear insets
        gbcForm.insets = new Insets(10, 5, 10, 5);

        // --- Formulario ---
        // Cuenta
        gbcForm.gridy = 1;
        JLabel lblCuenta = new JLabel("Número de Cuenta");
        lblCuenta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnlForm.add(lblCuenta, gbcForm);

        gbcForm.gridy = 2;
        txtCuenta = new JTextField(20);
        txtCuenta.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCuenta.setBorder(createFieldBorder());
        txtCuenta.setBackground(pnlForm.getBackground());
        addPlaceholder(txtCuenta, "Ej: 000123456");
        pnlForm.add(txtCuenta, gbcForm);

        // Importe
        gbcForm.gridy = 3;
        JLabel lblValor = new JLabel("Importe ($)"); // Título de tu 'jLabel5' antiguo era "Deposito:"
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnlForm.add(lblValor, gbcForm);

        gbcForm.gridy = 4;
        txtValor = new JTextField(20);
        txtValor.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtValor.setBorder(createFieldBorder());
        txtValor.setBackground(pnlForm.getBackground());
        addPlaceholder(txtValor, "Ej: 100.00");
        pnlForm.add(txtValor, gbcForm);


        // Botón de Depositar
        gbcForm.gridy = 5;
        gbcForm.insets = new Insets(30, 5, 10, 5); // Más espacio arriba
        btnDepositar = new JButton("Realizar Depósito");
        btnDepositar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnDepositar.setBackground(COLOR_SECUNDARIO);
        btnDepositar.setForeground(Color.WHITE);
        btnDepositar.setFocusPainted(false);
        btnDepositar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDepositar.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        pnlForm.add(btnDepositar, gbcForm);

        // Añadir panel del formulario al panel derecho (para centrarlo)
        pnlRight.add(pnlForm, new GridBagConstraints());

        // --- 4. AÑADIR LOS LISTENERS (TU LÓGICA) ---

        // Lógica del formulario
        btnDepositar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDepositarActionPerformed(evt);
            }
        });

        // Lógica de Navegación
        btnMovimientos.addActionListener(e -> irAMovimientos());
        btnRetiro.addActionListener(e -> irARetiro());
        // El botón de Deposito no hace nada, ya estamos aquí.
        btnTransferencia.addActionListener(e -> irATransferencia());
        btnBalances.addActionListener(e -> irABalances());
        btnCerrarSesion.addActionListener(e -> irALogin());


        // --- 5. Ensamblaje Final ---
        getContentPane().add(pnlLeft, BorderLayout.WEST);
        getContentPane().add(pnlRight, BorderLayout.CENTER);

        pack(); // Ajusta el tamaño de la ventana
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

    /**
     * Crea un borde estilizado para los campos de texto (solo línea inferior).
     */
    private Border createFieldBorder() {
        Border line = new MatteBorder(0, 0, 2, 0, COLOR_SECUNDARIO.darker()); // Línea azul oscura
        Border padding = BorderFactory.createEmptyBorder(5, 5, 5, 5); // Padding
        return BorderFactory.createCompoundBorder(line, padding);
    }

    /**
     * Añade texto de placeholder (marcador de posición) a un campo de texto.
     */
    private void addPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(COLOR_HINT);

        if (field instanceof JPasswordField) {
            ((JPasswordField) field).setEchoChar((char) 0);
        }

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar('•');
                    }
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(COLOR_HINT);
                    field.setText(placeholder);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar((char) 0);
                    }
                }
            }
        });
    }

    // =========================================================================
    //   TUS MÉTODOS DE NAVEGACIÓN (adaptados de tu código original)
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

    private void irATransferencia() {
        TransferenciasFrm transFrm = new TransferenciasFrm();
        transFrm.setVisible(true);
        this.dispose();
    }

    private void irABalances() {
        BalancesFrm balancesFrm = new BalancesFrm();
        balancesFrm.setVisible(true);
        this.dispose();
    }

    private void irALogin() {
        LoginFrm transFrm = new LoginFrm();
        transFrm.setVisible(true);
        this.dispose();
    }

    // =========================================================================
    //   TU LÓGICA DE NEGOCIO (traída de tu versión antigua y adaptada)
    // =========================================================================

    private void btnDepositarActionPerformed(java.awt.event.ActionEvent evt) {
        // Obtener los datos del formulario
        String cuenta = txtCuenta.getText().trim();
        String textoImporte = txtValor.getText().trim();

        // Corregir para la lógica del placeholder
        if (cuenta.equals("Ej: 000123456")) cuenta = "";
        if (textoImporte.equals("Ej: 100.00")) textoImporte = "";

        try {
            double importe = Double.parseDouble(textoImporte);

            // Validar importe positivo
            if (importe <= 0) {
                JOptionPane.showMessageDialog(this, "El importe debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cuenta.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar un número de cuenta.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Llamar al cliente para registrar el depósito (usando la lógica de String)
            String resultado = ec.edu.gr03.model.EurekaBankClient.regDeposito(cuenta, importe);

            // Verificar el resultado (usando la lógica de String)
            if ("1".equals(resultado)) {
                JOptionPane.showMessageDialog(this, "Depósito realizado con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Limpiar campos
                addPlaceholder(txtCuenta, "Ej: 000123456");
                addPlaceholder(txtValor, "Ej: 100.00");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo realizar el depósito.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El importe debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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
            java.util.logging.Logger.getLogger(DepositoFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DepositoFrm().setVisible(true);
            }
        });
    }

    // --- Variables generadas por NetBeans (eliminadas) ---
    // private javax.swing.JButton btnDepositar;
    // ...
    // private javax.swing.JTextField txtValor;
}