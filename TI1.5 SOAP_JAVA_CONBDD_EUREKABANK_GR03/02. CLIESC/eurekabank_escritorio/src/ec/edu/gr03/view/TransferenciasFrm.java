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

public class TransferenciasFrm extends javax.swing.JFrame {

    // --- Variables de componentes (conservamos los nombres originales) ---
    private JButton btnTransferir;
    private JTextField txtCuentaDestino;
    private JTextField txtCuentaOrigen;
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
    // --- NUEVO COLOR ---
    private final Color COLOR_LOGOUT = new Color(160, 40, 40); // Rojo sutil

    public TransferenciasFrm() {
        // --- 1. Configuración del JFrame principal ---
        setTitle("EurekaBank - Transferencias");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 600)); // Un poco más grande
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

        // Logo (con más espacio)
        gbcLeft.gridy = 0;
        gbcLeft.insets = new Insets(30, 20, 30, 20); // Padding superior e inferior aumentado
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

        // --- TÍTULO ELIMINADO ---
        // El título "EurekaBank" que estaba aquí se ha quitado.

        // --- Botones de Navegación ---
        gbcLeft.insets = new Insets(5, 0, 5, 0); // Sin padding lateral para que los botones se estiren

        // Rutas de iconos (puedes cambiarlas o ponerlas null si no tienes)
        String iconMov = "/ec/edu/gr03/img/icon_movimientos.png";
        String iconRet = "/ec/edu/gr03/img/icon_retiro.png";
        String iconDep = "/ec/edu/gr03/img/icon_deposito.png";
        String iconTrans = "/ec/edu/gr03/img/icon_transferencia.png";
        String iconBal = "/ec/edu/gr03/img/icon_balances.png";
        String iconLogout = "/ec/edu/gr03/img/icon_logout.png";

        gbcLeft.gridy = 1; // Re-indexado (antes era 2)
        btnMovimientos = createNavButton("Movimientos", iconMov);
        pnlLeft.add(btnMovimientos, gbcLeft);

        gbcLeft.gridy = 2; // Re-indexado
        btnRetiro = createNavButton("Retiro", iconRet);
        pnlLeft.add(btnRetiro, gbcLeft);

        gbcLeft.gridy = 3; // Re-indexado
        btnDeposito = createNavButton("Deposito", iconDep);
        pnlLeft.add(btnDeposito, gbcLeft);

        gbcLeft.gridy = 4; // Re-indexado
        btnTransferencia = createNavButton("Transferencia", iconTrans);
        // ESTE ES EL BOTÓN ACTIVO
        btnTransferencia.setBackground(COLOR_SECUNDARIO);
        btnTransferencia.setForeground(Color.WHITE);
        pnlLeft.add(btnTransferencia, gbcLeft);
        
        gbcLeft.gridy = 5;
        btnBalances = createNavButton("Balances", iconBal);
        pnlLeft.add(btnBalances, gbcLeft);

        // Botón de Cerrar Sesión (empujado al fondo)
        gbcLeft.gridy = 6; // Re-indexado
        gbcLeft.weighty = 1; // ESTO EMPUJA EL BOTÓN HACIA ABAJO
        gbcLeft.anchor = GridBagConstraints.SOUTH; // Anclar al sur
        gbcLeft.insets = new Insets(5, 0, 20, 0); // Padding inferior
        btnCerrarSesion = createNavButton("Cerrar Sesión", iconLogout);
        // --- CAMBIO AQUÍ ---
        btnCerrarSesion.setBackground(COLOR_LOGOUT); // Asignar el color rojo sutil
        pnlLeft.add(btnCerrarSesion, gbcLeft);

        // --- 3. Panel Derecho (Formulario de Transferencia) ---
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

        // Título "Transferencias"
        gbcForm.gridy = 0;
        gbcForm.insets = new Insets(0, 5, 25, 5); // Más espacio abajo
        JLabel lblLoginTitle = new JLabel("REALIZAR TRANSFERENCIA");
        lblLoginTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblLoginTitle.setForeground(COLOR_PANEL_IZQUIERDO); // Color negro
        lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pnlForm.add(lblLoginTitle, gbcForm);

        // Resetear insets
        gbcForm.insets = new Insets(10, 5, 10, 5);

        // --- Formulario ---
        // Cuenta Origen
        gbcForm.gridy = 1;
        JLabel lblCuentaOrigen = new JLabel("Cuenta Origen");
        lblCuentaOrigen.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnlForm.add(lblCuentaOrigen, gbcForm);

        gbcForm.gridy = 2;
        txtCuentaOrigen = new JTextField(20);
        txtCuentaOrigen.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCuentaOrigen.setBorder(createFieldBorder());
        txtCuentaOrigen.setBackground(pnlForm.getBackground());
        addPlaceholder(txtCuentaOrigen, "Ej: 000123456");
        pnlForm.add(txtCuentaOrigen, gbcForm);

        // Cuenta Destino
        gbcForm.gridy = 3;
        JLabel lblCuentaDestino = new JLabel("Cuenta Destino");
        lblCuentaDestino.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnlForm.add(lblCuentaDestino, gbcForm);

        gbcForm.gridy = 4;
        txtCuentaDestino = new JTextField(20);
        txtCuentaDestino.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCuentaDestino.setBorder(createFieldBorder());
        txtCuentaDestino.setBackground(pnlForm.getBackground());
        addPlaceholder(txtCuentaDestino, "Ej: 000987654");
        pnlForm.add(txtCuentaDestino, gbcForm);

        // Importe
        gbcForm.gridy = 5;
        JLabel lblValor = new JLabel("Importe ($)");
        lblValor.setFont(new Font("Segoe UI", Font.BOLD, 14));
        pnlForm.add(lblValor, gbcForm);

        gbcForm.gridy = 6;
        txtValor = new JTextField(20);
        txtValor.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtValor.setBorder(createFieldBorder());
        txtValor.setBackground(pnlForm.getBackground());
        addPlaceholder(txtValor, "Ej: 100.00");
        pnlForm.add(txtValor, gbcForm);

        // Botón de Transferir
        gbcForm.gridy = 7;
        gbcForm.insets = new Insets(30, 5, 10, 5); // Más espacio arriba
        btnTransferir = new JButton("Transferir");
        btnTransferir.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnTransferir.setBackground(COLOR_SECUNDARIO);
        btnTransferir.setForeground(Color.WHITE);
        btnTransferir.setFocusPainted(false);
        btnTransferir.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTransferir.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        pnlForm.add(btnTransferir, gbcForm);

        // Añadir panel del formulario al panel derecho (para centrarlo)
        pnlRight.add(pnlForm, new GridBagConstraints());

        // --- 4. AÑADIR LOS LISTENERS (TU LÓGICA) ---

        // Lógica del formulario
        btnTransferir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTransferirActionPerformed(evt);
            }
        });

        // Lógica de Navegación
        btnMovimientos.addActionListener(e -> irAMovimientos());
        btnRetiro.addActionListener(e -> irARetiro());
        btnDeposito.addActionListener(e -> irADeposito());
        btnBalances.addActionListener(e -> irABalances());
        btnCerrarSesion.addActionListener(e -> irALogin());
        // El botón de Transferencia no hace nada, ya estamos aquí.

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

        // --- NUEVO: Añadir icono ---
        if (iconPath != null && !iconPath.isEmpty()) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
                // Escalar el icono a un tamaño uniforme
                Image img = icon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(img));
                button.setIconTextGap(20); // Espacio entre icono y texto
            } catch (Exception e) {
                System.err.println("No se pudo cargar el icono: " + iconPath);
                button.setBorder(BorderFactory.createEmptyBorder(15, 50, 15, 30)); // Padding si no hay icono
            }
        }

        // --- NUEVO: Efecto Hover (MODIFICADO) ---
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Solo cambia el fondo si NO es el botón activo (azul)
                // Y NO es el botón de logout (rojo)
                if (button.getBackground() != COLOR_SECUNDARIO && button.getBackground() != COLOR_LOGOUT) {
                    button.setBackground(new Color(50, 50, 50)); // Gris oscuro al pasar el mouse
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Solo vuelve al negro si NO es el botón activo (azul)
                // Y NO es el botón de logout (rojo)
                if (button.getBackground() != COLOR_SECUNDARIO && button.getBackground() != COLOR_LOGOUT) {
                    button.setBackground(COLOR_PANEL_IZQUIERDO); // Vuelve a negro
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

        // Lógica para JPasswordField
        if (field instanceof JPasswordField) {
            ((JPasswordField) field).setEchoChar((char) 0); // Muestra el texto
        }

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar('•'); // Oculta el texto
                    }
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(COLOR_HINT);
                    field.setText(placeholder);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar((char) 0); // Muestra placeholder
                    }
                }
            }
        });
    }

    // =========================================================================
    //   TUS MÉTODOS DE NAVEGACIÓN (adaptados)
    // =========================================================================

    private void irAMovimientos() {
        MovimientosFrm movFrm = new MovimientosFrm();
        movFrm.setVisible(true);
        this.dispose(); // Opcional: cierra el formulario actual
    }

    private void irARetiro() {
        RetiroFrm retiroFrm = new RetiroFrm();
        retiroFrm.setVisible(true);
        this.dispose(); // Opcional
    }

    private void irADeposito() {
        DepositoFrm depositoFrm = new DepositoFrm();
        depositoFrm.setVisible(true);
        this.dispose(); // Opcional
    }

    private void irABalances() {
        BalancesFrm balancesFrm = new BalancesFrm();
        balancesFrm.setVisible(true);
        this.dispose();
    }

    private void irALogin() {
        LoginFrm transFrm = new LoginFrm();
        transFrm.setVisible(true);
        this.dispose(); // Opcional
    }

    // =========================================================================
    //   TU LÓGICA DE NEGOCIO ORIGINAL (COPIADA EXACTAMENTE)
    // =========================================================================

    private void btnTransferirActionPerformed(java.awt.event.ActionEvent evt) {
        String cuentaOrigen = txtCuentaOrigen.getText();
        String cuentaDestino = txtCuentaDestino.getText();
        String textoImporte = txtValor.getText();

        // Corregir para la lógica del placeholder
        if (cuentaOrigen.equals("Ej: 000123456")) cuentaOrigen = "";
        if (cuentaDestino.equals("Ej: 000987654")) cuentaDestino = "";
        if (textoImporte.equals("Ej: 100.00")) textoImporte = "";

        try {
            double importe = Double.parseDouble(textoImporte);

            // Validar campos
            if (cuentaOrigen.isEmpty() || cuentaDestino.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe ingresar ambas cuentas.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cuentaOrigen.equals(cuentaDestino)) {
                JOptionPane.showMessageDialog(this, "La cuenta origen y destino no pueden ser iguales.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (importe <= 0) {
                JOptionPane.showMessageDialog(this, "El importe debe ser mayor que cero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Ejecutar transferencia
            int resultado = ec.edu.gr03.model.EurekaBankClient.regTransferencia(cuentaOrigen, cuentaDestino, importe);

            // Verificar resultado
            if (resultado == 1) {
                JOptionPane.showMessageDialog(this, "Transferencia realizada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                // Limpiar campos
                addPlaceholder(txtCuentaOrigen, "Ej: 000123456");
                addPlaceholder(txtCuentaDestino, "Ej: 000987654");
                addPlaceholder(txtValor, "Ej: 100.00");
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo realizar la transferencia.", "Error", JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(TransferenciasFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TransferenciasFrm().setVisible(true);
            }
        });
    }

    // --- Variables generadas por NetBeans (eliminadas) ---
}