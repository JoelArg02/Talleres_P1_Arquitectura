package ec.edu.carrera.view;

import ec.edu.carrera.controller.LoginController;
import ec.edu.carrera.model.User;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.net.URL;

public class JLogin extends JFrame {

    private LoginController loginController;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    
    private JPanel pnlUserIcon; 
    
    private JLabel lblSubtitle;
    private JPanel mainPanel;

    private final Color BACKGROUND_PRIMARY = Color.decode("#F0F2F5");
    private final Color BACKGROUND_SECONDARY = Color.decode("#FFFFFF");
    private final Color ACCENT_BLUE = Color.decode("#4A90E2");
    private final Color ACCENT_BLUE_DARK = Color.decode("#357ABD");
    private final Color GRAY_BUTTON = Color.decode("#AAAAAA");
    private final Color TEXT_DARK = Color.decode("#333333");
    private final Color TEXT_MEDIUM = Color.decode("#666666");
    private final Color BORDER_GRAY = Color.decode("#D3D8DE");
    private final Color TEXT_LIGHT = Color.WHITE;
    private final Color ERROR_RED = Color.decode("#E74C3C");
    private final Color PLACEHOLDER_GRAY = new Color(180, 180, 180);

    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    private final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 18);
    private final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 18);
    private final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 16);
    private static final int CORNER_RADIUS = 12;
    private static final int AVATAR_SIZE = 70;
    private static final int FIELD_ICON_SIZE = 18;

    private final Icon AVATAR_INNER_ICON;
    private final Icon FIELD_USER_ICON;
    private final Icon FIELD_PASS_ICON;
    
    // Método de carga de íconos (centralizado)
    private ImageIcon loadIcon(String path, int size) {
        try {
            URL url = getClass().getClassLoader().getResource(path); 
            if (url != null) {
                Image originalImg = new ImageIcon(url).getImage();
                Image scaled = originalImg.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            } else {
                 System.err.println("Recurso no encontrado: " + path);
            }
        } catch (Exception e) {
             System.err.println("Error al cargar y escalar el icono: " + path);
        }
        return null;
    }


    static class RoundBorder extends AbstractBorder {
        private final Color color;
        private final int thickness;
        private final int radius;

        public RoundBorder(Color color, int thickness, int radius) {
            this.color = color;
            this.thickness = thickness;
            this.radius = radius;
        }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Stroke oldStroke = g2.getStroke();
            g2.setStroke(new BasicStroke(thickness));
            g2.setColor(color);
            g2.drawRoundRect(x + thickness / 2, y + thickness / 2,
                    width - thickness, height - thickness, radius, radius);
            g2.setStroke(oldStroke);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) { return new Insets(10, 12, 10, 12); } 
        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.top = insets.bottom = 10; insets.left = insets.right = 12; return insets;
        }
    }

    static class RoundedPanel extends JPanel {
        private final int radius;
        private final Color bgColor;
        public RoundedPanel(int radius, Color bgColor) {
            this.radius = radius; this.bgColor = bgColor; setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1,
                    getHeight() - 1, radius, radius));
            g2.dispose();
        }
    }
    
    // Panel circular para dibujar el avatar de la imagen
    static class CircularIconPanel extends JPanel {
        private final Color outerColor;
        private final Icon icon;
        private final int iconSize;

        public CircularIconPanel(Icon icon, Color outerColor, int size) {
            this.icon = icon;
            this.outerColor = outerColor;
            this.iconSize = size;
            setPreferredSize(new Dimension(size, size));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int size = Math.min(getWidth(), getHeight());
            
            // Dibuja el círculo azul
            g2.setColor(outerColor);
            g2.fillOval(0, 0, size, size);

            // Dibuja el ícono (el pequeño avatar blanco)
            if (icon != null) {
                int iconX = (size - icon.getIconWidth()) / 2;
                int iconY = (size - icon.getIconHeight()) / 2;
                icon.paintIcon(this, g2, iconX, iconY);
            }
            g2.dispose();
        }
    }

    // Subclase de JButton para pintar el fondo redondeado y azul
    private static class RoundedButton extends JButton {
        private final int radius;
        private Color baseColor;

        public RoundedButton(String text, Color baseColor, int radius) {
            super(text);
            this.baseColor = baseColor;
            this.radius = radius;
            setContentAreaFilled(false);
            setOpaque(false);
            super.setBackground(baseColor);
        }

        @Override
        public void setBackground(Color bg) {
            if (bg != null) {
                this.baseColor = bg;
            }
            super.setBackground(bg);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(this.baseColor);
            // Dibuja el fondo azul completo
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
            super.paintComponent(g); // Esto dibuja el texto
        }
    }


    private void setPlaceholder(JTextComponent field, String hint) {
        if (field instanceof JPasswordField) {
            ((JPasswordField) field).setEchoChar((char) 0); 
        }
        field.setForeground(PLACEHOLDER_GRAY);
        field.setText(hint);
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(hint)) {
                    field.setText("");
                    field.setForeground(TEXT_DARK);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar('*'); 
                    }
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(PLACEHOLDER_GRAY);
                    field.setText(hint);
                    if (field instanceof JPasswordField) {
                        ((JPasswordField) field).setEchoChar((char) 0); 
                    }
                }
            }
        });
    }

    public JLogin() {
        // CARGA DE ICONOS CON NOMBRES ESPECÍFICOS
        AVATAR_INNER_ICON = loadIcon("icons/userlogin.png", AVATAR_SIZE / 2);
        FIELD_USER_ICON = loadIcon("icons/userlogin2.png", FIELD_ICON_SIZE);
        FIELD_PASS_ICON = loadIcon("icons/password.png", FIELD_ICON_SIZE);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }
        loginController = new LoginController();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Login de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // MANTENEMOS EL TAMAÑO DEL FRAME
        setSize(550, 500); 
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_PRIMARY);

        // 1. Ícono Avatar Circular
        pnlUserIcon = new CircularIconPanel(AVATAR_INNER_ICON, ACCENT_BLUE, AVATAR_SIZE);
        
        // 2. Subtítulo
        lblSubtitle = new JLabel("User Login", SwingConstants.CENTER);
        lblSubtitle.setFont(FONT_SUBTITLE); lblSubtitle.setForeground(TEXT_MEDIUM);

        // 3. Campo Usuario
        txtUsername = new JTextField();
        txtUsername.setFont(FONT_INPUT); 
        txtUsername.setBackground(BACKGROUND_SECONDARY);
        
        // 4. Campo Contraseña
        txtPassword = new JPasswordField();
        txtPassword.setFont(FONT_INPUT); 
        txtPassword.setBackground(BACKGROUND_SECONDARY);

        // Aplicar placeholder y FocusListener
        setPlaceholder(txtUsername, "Username");
        setPlaceholder(txtPassword, "••••••••"); 

        // 5. Botón LOGIN (Usando la clase RoundedButton)
        btnLogin = new RoundedButton("LOGIN", ACCENT_BLUE, CORNER_RADIUS);
        btnLogin.setFont(FONT_BUTTON);
        btnLogin.setForeground(TEXT_LIGHT);
        btnLogin.setBorder(new EmptyBorder(10, 100, 10, 100)); 
        btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnLogin.setFocusPainted(false); 
        btnLogin.addActionListener(this::btnLoginActionPerformed);
        
        // Efecto hover 
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(ACCENT_BLUE_DARK);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(ACCENT_BLUE);
            }
        });

        // 6. Creación de Paneles Contenedores para Campos (para iconos y bordes)
        JPanel userContainer = createInputPanelWithIcons(txtUsername, FIELD_USER_ICON, null);
        JPanel passContainer = createInputPanelWithIcons(txtPassword, FIELD_PASS_ICON, null); 
        
        mainPanel = new RoundedPanel(CORNER_RADIUS + 5, BACKGROUND_SECONDARY);
        mainPanel.setLayout(new GridBagLayout());
        // *** AUMENTADO EL ESPACIADO HORIZONTAL DEL PANEL INTERNO AÚN MÁS (de 80 a 100) ***
        mainPanel.setBorder(new EmptyBorder(32, 100, 32, 100)); 
        
        setupLayout(userContainer, passContainer);
        
        // Centrar el mainPanel dentro del JFrame
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints outer = new GridBagConstraints();
        outer.anchor = GridBagConstraints.CENTER;
        outer.insets = new Insets(20, 20, 20, 20);
        getContentPane().add(mainPanel, outer);
    }
    
    // Método auxiliar para crear el campo de texto con íconos (usando Icon)
    private JPanel createInputPanelWithIcons(JTextComponent component, Icon leftIcon, String rightIconText) {
        component.setBorder(new EmptyBorder(0, 0, 0, 0)); 

        JPanel container = new JPanel(new BorderLayout(5, 0));
        container.setBackground(BACKGROUND_SECONDARY);
        container.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS));

        // 2. Icono izquierdo (Icon)
        if (leftIcon != null) {
            JLabel leftIconLabel = new JLabel(leftIcon);
            // *** REDUCIDO EL PADDING HORIZONTAL DEL ÍCONO (de 10 a 8) ***
            leftIconLabel.setBorder(new EmptyBorder(0, 8, 0, 0)); 
            container.add(leftIconLabel, BorderLayout.WEST);
        } else {

             component.setBorder(new EmptyBorder(0, 8, 0, 0));
        }

        // 3. Icono derecho (Ojo) - No debería tener efecto ya que se pasa null, pero se mantiene la lógica
        if (rightIconText != null) {
            JLabel rightIcon = new JLabel(rightIconText);
            rightIcon.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            rightIcon.setForeground(TEXT_MEDIUM);
            rightIcon.setBorder(new EmptyBorder(0, 5, 0, 8)); // También reducido
            rightIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            container.add(rightIcon, BorderLayout.EAST);
        }

        // 4. Campo de texto/contraseña en el centro
        container.add(component, BorderLayout.CENTER);
        
        return container;
    }


    private void setupLayout(JPanel userContainer, JPanel passContainer) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // 1. Avatar Circular
        gbc.gridx = 0; gbc.gridy = 0; 
        gbc.insets = new Insets(0, 0, 12, 0);
        gbc.fill = GridBagConstraints.NONE; 
        mainPanel.add(pnlUserIcon, gbc);
        
        gbc.fill = GridBagConstraints.HORIZONTAL; // Restablecer la expansión horizontal

        // 2. Subtítulo "User Log in"
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(lblSubtitle, gbc);

        // 3. Campo Usuario
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(userContainer, gbc);

        // 4. Campo Contraseña
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 40, 0); // Más espacio antes del botón
        mainPanel.add(passContainer, gbc);

        // 5. Botón Login
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 0, 0); 
        mainPanel.add(btnLogin, gbc);
    }


    private void btnLoginActionPerformed(ActionEvent evt) {
        String username = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        
        // Verifica si los campos contienen el placeholder
        if (username.equals("User ID") || password.equals("••••••••") || username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar usuario y contraseña.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        User user = new User(username, password);
        btnLogin.setEnabled(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                try { Thread.sleep(500); } catch (Exception ex) {}
                return loginController.login(user);
            }
            @Override
            protected void done() {
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                btnLogin.setEnabled(true);
                try {
                    boolean loginExitoso = get();
                    if (loginExitoso) {
                        JOptionPane.showMessageDialog(JLogin.this, "¡Bienvenido, " + username + "!", "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
                        JLogin.this.dispose();
                        
                        String finalUsername = username; 
                        java.awt.EventQueue.invokeLater(() -> new JApplication(finalUsername).setVisible(true)); 
                        
                    } else {
                        JOptionPane.showMessageDialog(JLogin.this, "Usuario o contraseña incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
                        txtPassword.setText("");
                        
                        JPanel userContainer = (JPanel) txtUsername.getParent();
                        JPanel passContainer = (JPanel) txtPassword.getParent();
                        
                        userContainer.setBorder(new RoundBorder(ERROR_RED, 2, CORNER_RADIUS));
                        passContainer.setBorder(new RoundBorder(ERROR_RED, 2, CORNER_RADIUS));
                        txtUsername.requestFocus();
                        
                        new Timer(1300, e -> {
                            userContainer.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS));
                            passContainer.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS));
                            ((Timer) e.getSource()).stop();
                        }).start();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(JLogin.this,
                            "Error al intentar conectar: " + ex.getMessage(),
                            "Error Crítico", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JLogin().setVisible(true));
    }
}