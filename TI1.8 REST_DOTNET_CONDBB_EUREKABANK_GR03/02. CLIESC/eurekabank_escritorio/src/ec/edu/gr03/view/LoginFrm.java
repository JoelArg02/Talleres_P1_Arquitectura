package ec.edu.gr03.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue; // Importación necesaria
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
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder; // Para la línea inferior de los campos

public class LoginFrm extends javax.swing.JFrame {

    // --- Variables de componentes (conservamos los nombres originales) ---
    private JButton btnLogin;
    private JPasswordField txtPassword;
    private JTextField txtUsername;

    // Colores para el nuevo diseño
    private final Color COLOR_PRINCIPAL = new Color(34, 49, 63); // Un azul oscuro
    private final Color COLOR_SECUNDARIO = new Color(52, 152, 219); // Un azul brillante
    private final Color COLOR_TEXTO = new Color(236, 240, 241); // Blanco grisáceo
    private final Color COLOR_GRIS_FONDO = new Color(245, 245, 245); // Fondo claro
    private final Color COLOR_HINT = Color.GRAY; // Para placeholders

    public LoginFrm() {
        // --- 1. Configuración del JFrame principal ---
        setTitle("Bienvenido a EurekaBank");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(700, 500)); // Tamaño total de la ventana
        setLocationRelativeTo(null); // Centrar
        getContentPane().setLayout(new BorderLayout());

        // --- 2. Panel Izquierdo (Branding) ---
        JPanel pnlLeft = new JPanel();
        pnlLeft.setLayout(new GridBagLayout()); // Para centrar el contenido fácilmente
        pnlLeft.setBackground(Color.BLACK);
        pnlLeft.setPreferredSize(new Dimension(300, 500)); // Ancho fijo

        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.gridx = 0;
        gbcLeft.gridy = 0;
        gbcLeft.insets = new Insets(10, 10, 10, 10);

        // Logo
        JLabel lblLogo = new JLabel();
        try {
            // Asegúrate de que logo.png esté en esta ruta
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/ec/edu/gr03/img/logo.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(250, 170, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.err.println("No se pudo cargar el logo.");
            lblLogo.setText("[LOGO]");
        }
        pnlLeft.add(lblLogo, gbcLeft);

        // --- 3. Panel Derecho (Formulario) ---
        JPanel pnlRight = new JPanel(new GridBagLayout());
        pnlRight.setBackground(COLOR_GRIS_FONDO); // Un fondo gris claro

        // Panel interno para centrar el formulario
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBackground(Color.WHITE); // El formulario en sí es blanco
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), // Borde ligero
                BorderFactory.createEmptyBorder(40, 40, 40, 40) // Padding interno
        ));

        GridBagConstraints gbcForm = new GridBagConstraints();
        gbcForm.gridx = 0;
        gbcForm.gridwidth = GridBagConstraints.REMAINDER;
        gbcForm.fill = GridBagConstraints.HORIZONTAL;
        gbcForm.insets = new Insets(10, 5, 10, 5); // Espaciado

        // Título "Iniciar Sesión"
        gbcForm.gridy = 0;
        gbcForm.insets = new Insets(0, 5, 25, 5); // Más espacio abajo
        JLabel lblLoginTitle = new JLabel("INICIAR SESIÓN");
        lblLoginTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblLoginTitle.setForeground(COLOR_PRINCIPAL);
        lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
        pnlForm.add(lblLoginTitle, gbcForm);

        // Resetear insets
        gbcForm.insets = new Insets(10, 5, 10, 5);

        // Campo de Usuario
        gbcForm.gridy = 1;
        JLabel lblUsername = new JLabel("Usuario");
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsername.setForeground(COLOR_PRINCIPAL);
        pnlForm.add(lblUsername, gbcForm);

        gbcForm.gridy = 2;
        txtUsername = new JTextField(20); // 20 columnas de ancho
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtUsername.setBorder(createFieldBorder()); // Borde de línea inferior
        txtUsername.setBackground(pnlForm.getBackground()); // Fondo igual al panel
        addPlaceholder(txtUsername, "Ingrese su usuario");
        pnlForm.add(txtUsername, gbcForm);

        // Campo de Contraseña
        gbcForm.gridy = 3;
        JLabel lblPassword = new JLabel("Contraseña");
        lblPassword.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblPassword.setForeground(COLOR_PRINCIPAL);
        pnlForm.add(lblPassword, gbcForm);

        gbcForm.gridy = 4;
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtPassword.setBorder(createFieldBorder()); // Borde de línea inferior
        txtPassword.setBackground(pnlForm.getBackground());
        addPlaceholder(txtPassword, "Ingrese su contraseña");
        pnlForm.add(txtPassword, gbcForm);

        // Botón de Login
        gbcForm.gridy = 5;
        gbcForm.insets = new Insets(30, 5, 10, 5); // Más espacio arriba
        btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setBackground(COLOR_SECUNDARIO);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        // Padding interno del botón
        btnLogin.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        pnlForm.add(btnLogin, gbcForm);

        // Añadir panel del formulario al panel derecho (para centrarlo)
        pnlRight.add(pnlForm, new GridBagConstraints());

        // --- 4. AÑADIR EL LISTENER AL BOTÓN (TU LÓGICA) ---
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        // --- 5. Ensamblaje Final ---
        getContentPane().add(pnlLeft, BorderLayout.WEST);
        getContentPane().add(pnlRight, BorderLayout.CENTER);

        pack(); // Ajusta el tamaño de la ventana
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
    //   TU LÓGICA DE NEGOCIO (traída de tu versión antigua)
    // =========================================================================
    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        // Corregir para la lógica del placeholder
        if (username.equals("Ingrese su usuario")) username = "";
        if (password.equals("Ingrese su contraseña")) password = "";

        if (username.isEmpty() || password.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Ingrese usuario y contraseña.", "Advertencia", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Llamar al cliente para hacer login
        boolean loginExitoso = ec.edu.gr03.model.EurekaBankClient.login(username, password);

        // Verificar el resultado
        if (loginExitoso) {
            javax.swing.JOptionPane.showMessageDialog(this, "Login exitoso.", "Información", javax.swing.JOptionPane.INFORMATION_MESSAGE);

            MovimientosFrm movFrm = new MovimientosFrm();
            movFrm.setVisible(true);
            this.dispose(); // Opcional: cierra el formulario actual

        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
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
            java.util.logging.Logger.getLogger(LoginFrm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() { // Usa EventQueue
            public void run() {
                new LoginFrm().setVisible(true);
            }
        });
    }

    // --- Las variables generadas por NetBeans (eliminadas) ---
    // private javax.swing.JButton btnLogin;
    // ...
    // private javax.swing.JTextField txtUsername;
}