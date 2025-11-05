package ec.edu.carrera.view;

import ec.edu.carrera.controller.LoginController;
import ec.edu.carrera.model.ApiService;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

public class JLoginWindow extends JFrame {

    // --- CONSTANTES DE ESTILO COPIADAS DE JLogin.java ---
    private final Color BACKGROUND_PRIMARY = Color.decode("#F0F2F5");
    private final Color BACKGROUND_SECONDARY = Color.decode("#FFFFFF");
    private final Color ACCENT_BLUE = Color.decode("#4A90E2");
    private final Color ACCENT_BLUE_DARK = Color.decode("#357ABD");
    private final Color BORDER_GRAY = Color.decode("#D3D8DE");
    private final Color ERROR_RED = Color.decode("#E74C3C");
    private final Color TEXT_DARK = Color.decode("#333333");
    private final Color TEXT_MEDIUM = Color.decode("#666666");
    private final Color PLACEHOLDER_GRAY = new Color(180, 180, 180);
    private final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 18);
    private final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 18);
    private final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 16);
    private static final int CORNER_RADIUS = 12;
    private static final int AVATAR_SIZE = 70;
    private static final int FIELD_ICON_SIZE = 18;

    // --- ÍCONOS ---
    private Icon AVATAR_INNER_ICON;
    private Icon FIELD_USER_ICON;
    private Icon FIELD_PASS_ICON;

    // --- Componentes de la Vista ---
    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JLabel lblMensaje;
    private JPanel userContainer;
    private JPanel passContainer;
    private JPanel pnlUserIcon;

    // --- MÉTODO 'main' (Punto de entrada) requerido por LoginController ---
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        java.awt.EventQueue.invokeLater(() -> {
            ApiService model = new ApiService();
            JLoginWindow view = new JLoginWindow();
            new LoginController(view, model);
            view.setVisible(true);
        });
    }

    public JLoginWindow() {
        // Carga de Íconos
        AVATAR_INNER_ICON = loadIcon("/icons/userlogin.png", AVATAR_SIZE / 2);
        FIELD_USER_ICON = loadIcon("/icons/userlogin2.png", FIELD_ICON_SIZE);
        FIELD_PASS_ICON = loadIcon("/icons/password.png", FIELD_ICON_SIZE);

        initComponents();
        setLocationRelativeTo(null);
        setPlaceholder(txtUsuario, "Username");
        setPlaceholder(txtPassword, "Password");
    }

    // --- Métodos públicos para el Controlador ---
    public String getUsername() {
        return txtUsuario.getText().equals("Username") ? "" : txtUsuario.getText();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword()).equals("Password") ? "" : new String(txtPassword.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    public void showErrorMessage(String message) {
        lblMensaje.setText(message);
        userContainer.setBorder(new RoundBorder(ERROR_RED, 2, CORNER_RADIUS));
        passContainer.setBorder(new RoundBorder(ERROR_RED, 2, CORNER_RADIUS));

        new Timer(1300, e -> {
            resetBorders();
            ((Timer) e.getSource()).stop();
        }).start();
    }
    
    public void setLoading(boolean isLoading) {
        btnLogin.setEnabled(!isLoading);
        btnLogin.setText(isLoading ? "VALIDANDO..." : "LOG IN");
        lblMensaje.setText(isLoading ? "Validando..." : "");
        setCursor(Cursor.getPredefinedCursor(isLoading ? Cursor.WAIT_CURSOR : Cursor.DEFAULT_CURSOR));
    }
    // --- Fin de métodos públicos ---

    private void initComponents() {
        setTitle("Acceso al Sistema");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 520);
        setLayout(new BorderLayout());
        getContentPane().setBackground(BACKGROUND_PRIMARY);

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new GridBagLayout());
        wrapperPanel.setBackground(BACKGROUND_PRIMARY);
        add(wrapperPanel, BorderLayout.CENTER);

        RoundPanel contentCard = new RoundPanel(CORNER_RADIUS, BACKGROUND_SECONDARY);
        contentCard.setLayout(new BoxLayout(contentCard, BoxLayout.Y_AXIS));
        contentCard.setBorder(new EmptyBorder(40, 30, 40, 30));
        contentCard.setPreferredSize(new Dimension(340, 450));
        wrapperPanel.add(contentCard);

        pnlUserIcon = new CircularIconPanel(AVATAR_SIZE, ACCENT_BLUE, AVATAR_INNER_ICON);
        pnlUserIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentCard.add(pnlUserIcon);
        contentCard.add(Box.createVerticalStrut(15));

        JLabel lblSubtitle = new JLabel("User Login");
        lblSubtitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblSubtitle.setForeground(TEXT_DARK);
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentCard.add(lblSubtitle);
        contentCard.add(Box.createVerticalStrut(30));

        userContainer = createTextFieldContainer("Username", FIELD_USER_ICON, false);
        userContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentCard.add(userContainer);
        contentCard.add(Box.createVerticalStrut(15));

        passContainer = createTextFieldContainer("Password", FIELD_PASS_ICON, true);
        passContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentCard.add(passContainer);
        contentCard.add(Box.createVerticalStrut(30));

        btnLogin = new RoundedButton("LOG IN", ACCENT_BLUE, ACCENT_BLUE_DARK, Color.WHITE, FONT_BUTTON, CORNER_RADIUS);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogin.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btnLogin.setPreferredSize(new Dimension(280, 50));
        contentCard.add(btnLogin);
        
        // *******************************************************************
        // ********** AJUSTE: Añadir espacio antes del lblMensaje ************
        // *******************************************************************
        contentCard.add(Box.createVerticalStrut(10)); 

        // 6. Mensaje de error (Aseguramos que tenga un espacio de una línea)
        lblMensaje = new JLabel(" "); 
        lblMensaje.setForeground(ERROR_RED);
        lblMensaje.setFont(FONT_SUBTITLE.deriveFont(Font.ITALIC, 14f));
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentCard.add(lblMensaje);
        // *******************************************************************
        
    }

    private JPanel createTextFieldContainer(String hint, Icon leftIcon, boolean isPassword) {
        JPanel container = new JPanel(new GridBagLayout());
        container.setBackground(BACKGROUND_SECONDARY);
        container.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS));
        container.setMaximumSize(new Dimension(300, 50));
        container.setPreferredSize(new Dimension(280, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 10, 0, 0);

        JLabel icon = new JLabel(leftIcon);
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.0;
        container.add(icon, gbc);

        JTextComponent textField;
        if (isPassword) {
            txtPassword = new JPasswordField(hint, 20);
            textField = txtPassword;
        } else {
            txtUsuario = new JTextField(hint, 20);
            textField = txtUsuario;
        }

        textField.setBorder(null);
        textField.setBackground(BACKGROUND_SECONDARY);
        textField.setFont(FONT_INPUT);
        textField.setForeground(PLACEHOLDER_GRAY);

        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                container.setBorder(new RoundBorder(ACCENT_BLUE, 2, CORNER_RADIUS));
            }
            @Override
            public void focusLost(FocusEvent e) {
                resetBorders();
            }
        });

        gbc.gridx = 1; gbc.weightx = 1.0; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 10, 0, 15);
        container.add(textField, gbc);

        return container;
    }

    private void resetBorders() {
        if (!txtUsuario.isFocusOwner()) {
            userContainer.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS));
        }
        if (!txtPassword.isFocusOwner()) {
            passContainer.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS));
        }
    }

    // --- Métodos de Utilidad (loadIcon, setPlaceholder) ---
    private ImageIcon loadIcon(String path, int size) {
        try {
            URL url = getClass().getResource(path);
            if (url != null) {
                Image originalImg = new ImageIcon(url).getImage();
                Image scaled = originalImg.getScaledInstance(size, size, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            } else { System.err.println("Recurso no encontrado: " + path); }
        } catch (Exception e) { System.err.println("Error al cargar y escalar el icono: " + path); }
        return null;
    }

    private void setPlaceholder(JTextComponent field, String hint) {
        field.setText(hint);
        field.setForeground(PLACEHOLDER_GRAY);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                String currentText = field instanceof JPasswordField ? new String(((JPasswordField) field).getPassword()) : field.getText();
                if (currentText.equals(hint)) {
                    field.setText("");
                    field.setForeground(TEXT_DARK);
                }
                if (field instanceof JPasswordField) ((JPasswordField) field).setEchoChar('•');
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(hint);
                    field.setForeground(PLACEHOLDER_GRAY);
                    if (field instanceof JPasswordField) ((JPasswordField) field).setEchoChar((char) 0);
                }
            }
        });
        if (field instanceof JPasswordField) {
            ((JPasswordField) field).setEchoChar((char) 0);
        }
    }

    // --- Clases de Estilo ---
    private static class RoundPanel extends JPanel {
        private int radius; private Color bgColor;
        public RoundPanel(int radius, Color bgColor) { this.radius = radius; this.bgColor = bgColor; setOpaque(false); setLayout(new BorderLayout()); }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
        }
    }
    private static class RoundBorder extends AbstractBorder {
        private Color color; private int thickness; private int radius; private Insets insets;
        public RoundBorder(Color color, int thickness, int radius) {
            this.color = color; this.thickness = thickness; this.radius = radius;
            this.insets = new Insets(thickness + radius/4, thickness + radius/4, thickness + radius/4, thickness + radius/4);
        }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color); g2.setStroke(new BasicStroke(thickness));
            g2.draw(new RoundRectangle2D.Double(x + thickness / 2.0, y + thickness / 2.0, width - thickness, height - thickness, radius, radius));
            g2.dispose();
        }
        public Insets getBorderInsets(Component c) { return insets; }
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = thickness + radius/4; return insets;
        }
    }
    private static class CircularIconPanel extends JPanel {
        private int diameter; private Color outerColor; private Icon innerIcon;
        public CircularIconPanel(int diameter, Color outerColor, Icon innerIcon) {
            this.diameter = diameter; this.outerColor = outerColor; this.innerIcon = innerIcon;
            setPreferredSize(new Dimension(diameter, diameter)); setMinimumSize(new Dimension(diameter, diameter));
            setMaximumSize(new Dimension(diameter, diameter)); setOpaque(false); setLayout(new GridBagLayout());
            if (innerIcon != null) { add(new JLabel(innerIcon)); }
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(outerColor); g2.fillOval(0, 0, diameter, diameter); g2.dispose();
        }
    }
    private class RoundedButton extends JButton {
        private Color baseColor; private Color hoverColor; private int radius;
        public RoundedButton(String text, Color base, Color hover, Color foreground, Font font, int radius) {
            super(text); this.baseColor = base; this.hoverColor = hover; this.radius = radius; setFont(font);
            setForeground(foreground); setFocusPainted(false); setContentAreaFilled(false); setCursor(new Cursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { setBackground(hoverColor); }
                public void mouseExited(MouseEvent e) { setBackground(baseColor); }
                public void mousePressed(MouseEvent e) { setBackground(hoverColor.darker()); }
                public void mouseReleased(MouseEvent e) { setBackground(hoverColor); }
            });
        }
        public void setBackground(Color bg) { this.baseColor = bg; super.setBackground(bg); }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius)); g2.dispose();
            super.paintComponent(g);
        }
    }
}