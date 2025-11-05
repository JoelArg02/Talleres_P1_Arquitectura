package ec.edu.carrera.view;

import ec.edu.carrera.controller.ConversionController;
import ec.edu.carrera.model.ApiService;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.net.URL;
import java.util.List;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

public class JConversionWindow extends JFrame {

    // --- CONSTANTES DE ESTILO COPIADAS DE JApplication.java ---
    private final Color BACKGROUND_PRIMARY = Color.decode("#F0F2F5");
    private final Color BACKGROUND_SECONDARY = Color.decode("#FFFFFF");
    private final Color ACCENT_BLUE = Color.decode("#4A90E2");
    private final Color ACCENT_BLUE_DARK = Color.decode("#357ABD");
    private final Color GRAY_BUTTON = Color.decode("#AAAAAA");
    private final Color GRAY_BUTTON_DARK = Color.decode("#666666");
    private final Color TEXT_DARK = Color.decode("#333333");
    private final Color TEXT_MEDIUM = Color.decode("#666666");
    private final Color TEXT_LIGHT_GRAY = Color.decode("#AAAAAA");
    private final Color BORDER_GRAY = Color.decode("#D3D8DE");
    private final Color TEXT_LIGHT = Color.WHITE;
    private final Color ERROR_RED = Color.decode("#E74C3C");
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    private final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 20); 
    private final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_COMBO = new Font("Segoe UI", Font.PLAIN, 16);
    private final Font FONT_RESULT = new Font("Segoe UI", Font.BOLD, 36);
    private final Font FONT_STATUS = new Font("Segoe UI", Font.ITALIC, 12);
    private final Font FONT_USER_MENU = new Font("Segoe UI", Font.PLAIN, 14);
    private static final int CORNER_RADIUS = 12;
    private static final int INPUT_PADDING = 8; 

    // --- ÍCONOS ---
    private Icon USER_ICON;
    private Icon RETURN_ICON;
    private Icon EXCHANGE_ICON;

    // --- Componentes ---
    private JComboBox<String> cmbTipo, cmbUnidadDe, cmbUnidadA;
    private JTextField txtValor;
    private JButton btnConvertir, btnLimpiar, btnSwap;
    private JLabel lblResultado;
    private JLabel lblStatus; // Etiqueta de estado
    private JPanel userPanel;

    private String username;
    private JMenuItem itemLogout;
    private JPopupMenu userMenu;

    // --- MÉTODO 'main' (Punto de entrada) ---
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) { ex.printStackTrace(); }

        java.awt.EventQueue.invokeLater(() -> {
            JConversionWindow view = new JConversionWindow("MONSTER");
            // Aquí se conectaría con tu ApiService y ConversionController
            // new ConversionController(view, new ApiService()); 
            view.setVisible(true);
        });
    }

    public JConversionWindow(String username) {
        this.username = username;
        // Carga de Íconos
        USER_ICON = loadIcon("/icons/user.png", 24, 24);
        RETURN_ICON = loadIcon("/icons/return.png", 16, 16);
        EXCHANGE_ICON = loadIcon("/icons/exchange.png", 28, 28);

        initComponents();
        setLocationRelativeTo(null);
    }

    // --- Métodos públicos para el Controlador (getters/setters/listeners) ---
    public String getSelectedType() { return (String) cmbTipo.getSelectedItem(); }
    public String getInputValue() { return txtValor.getText(); }
    public String getFromUnit() { return (String) cmbUnidadDe.getSelectedItem(); }
    public String getToUnit() { return (String) cmbUnidadA.getSelectedItem(); }
    public void setTypes(List<String> types) { cmbTipo.setModel(new DefaultComboBoxModel<>(types.toArray(new String[0]))); }
    public void setUnits(List<String> units) {
        DefaultComboBoxModel<String> modelDe = new DefaultComboBoxModel<>(units.toArray(new String[0]));
        DefaultComboBoxModel<String> modelA = new DefaultComboBoxModel<>(units.toArray(new String[0]));
        cmbUnidadDe.setModel(modelDe);
        cmbUnidadA.setModel(modelA);
    }
    public void setResult(String result) { lblResultado.setText(result); }
    public void setStatus(String statusText, Color color) { lblStatus.setText(statusText); lblStatus.setForeground(color); }
    public void addTypeChangeListener(ItemListener listener) { cmbTipo.addItemListener(listener); }
    public void addConvertListener(ActionListener listener) { btnConvertir.addActionListener(listener); }
    public void addClearListener(ActionListener listener) { btnLimpiar.addActionListener(listener); }
    public void addSwapListener(ActionListener listener) { btnSwap.addActionListener(listener); }
    public void addLogoutListener(ActionListener listener) { itemLogout.addActionListener(listener); }
    public void setUnits(String fromUnit, String toUnit) {
        cmbUnidadDe.setSelectedItem(fromUnit);
        cmbUnidadA.setSelectedItem(toUnit);
    }
    // --- Fin de métodos públicos ---

    private void initComponents() {
        setTitle("Conversor de Unidades");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 680);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_PRIMARY);
        setLayout(new BorderLayout());

        createComponents();
    }

    private void createComponents() {
        // --- Header Panel (Arriba) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(BACKGROUND_PRIMARY);
        headerPanel.setBorder(new EmptyBorder(20, 30, 0, 30));

        JLabel lblTitle = new JLabel("Conversor Universal");
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(TEXT_DARK);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        // Panel de Usuario (Icono + Nombre)
        userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        userPanel.setOpaque(false);
        userPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblUsername = new JLabel(username);
        lblUsername.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblUsername.setForeground(TEXT_DARK);

        userPanel.add(new JLabel(USER_ICON));
        userPanel.add(lblUsername);
        headerPanel.add(userPanel, BorderLayout.EAST);
        add(headerPanel, BorderLayout.NORTH);

        // --- User Menu (Pop-up de Logout) ---
        userMenu = new JPopupMenu();
        userMenu.setBorder(new RoundBorder(BORDER_GRAY, 1, 8)); // Se usa el constructor de 3 params (padding por defecto)
        itemLogout = new JMenuItem("Cerrar Sesión", RETURN_ICON);
        itemLogout.setActionCommand("Cerrar Sesión");
        itemLogout.setFont(FONT_USER_MENU);
        userMenu.add(itemLogout);
        
        // Listener para mostrar el menú
        userPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Muestra el menú alineado a la derecha del panel de usuario
                userMenu.show(userPanel, userPanel.getWidth() - userMenu.getPreferredSize().width, userPanel.getHeight());
            }
        });

        // --- Contenido Principal (Centro) ---
        RoundPanel mainPanel = new RoundPanel(CORNER_RADIUS, BACKGROUND_SECONDARY);
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(25, 30, 25, 30)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // 1. Tipo de Conversión (ComboBox)
        JLabel lblTipo = new JLabel("Tipo de Conversión");
        lblTipo.setFont(FONT_SUBTITLE); lblTipo.setForeground(TEXT_MEDIUM);
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10, 0, 5, 0);
        mainPanel.add(lblTipo, gbc);

        cmbTipo = createStyledComboBox(new String[]{"Cargando..."});
        cmbTipo.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS, INPUT_PADDING));
        gbc.gridy = 1; gbc.insets = new Insets(5, 0, 20, 0);
        mainPanel.add(cmbTipo, gbc);

        // 2. Título de Entrada y Campo de Valor
        JLabel lblInputTitle = new JLabel("Valor a Convertir");
        lblInputTitle.setFont(FONT_SUBTITLE); lblInputTitle.setForeground(TEXT_MEDIUM);
        gbc.gridy = 2; gbc.insets = new Insets(10, 0, 5, 0);
        mainPanel.add(lblInputTitle, gbc);

        txtValor = new JTextField("1.0");
        txtValor.setFont(FONT_INPUT); txtValor.setHorizontalAlignment(SwingConstants.RIGHT);
        txtValor.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS, INPUT_PADDING));
        txtValor.setBackground(BACKGROUND_PRIMARY);
        gbc.gridy = 3; gbc.insets = new Insets(5, 0, 25, 0);
        mainPanel.add(txtValor, gbc);
        
        // Listener de Foco para txtValor
        txtValor.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtValor.setBorder(new RoundBorder(ACCENT_BLUE, 2, CORNER_RADIUS, INPUT_PADDING));
            }
            @Override
            public void focusLost(FocusEvent e) {
                 txtValor.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS, INPUT_PADDING));
            }
        });

        // 3. Secciones "De" y "A" con Swap
        JPanel unitsPanel = new JPanel(new GridBagLayout());
        unitsPanel.setOpaque(false);
        GridBagConstraints gbcUnits = new GridBagConstraints();
        gbcUnits.fill = GridBagConstraints.HORIZONTAL; 

        // 3.1 Unidad De: (Label)
        JLabel lblUnidadDe = new JLabel("Unidad De:");
        lblUnidadDe.setFont(FONT_SUBTITLE.deriveFont(Font.PLAIN)); lblUnidadDe.setForeground(TEXT_MEDIUM);
        gbcUnits.gridx = 0; gbcUnits.gridy = 0; gbcUnits.weightx = 0.5; gbcUnits.insets = new Insets(0, 0, 5, 5);
        unitsPanel.add(lblUnidadDe, gbcUnits);

        // 3.2 Unidad A: (Label) **SEPARADO**
        JLabel lblUnidadA = new JLabel("Unidad A:");
        lblUnidadA.setFont(FONT_SUBTITLE.deriveFont(Font.PLAIN)); lblUnidadA.setForeground(TEXT_MEDIUM);
        gbcUnits.gridx = 2; gbcUnits.gridy = 0; gbcUnits.weightx = 0.5; gbcUnits.insets = new Insets(0, 5, 5, 0);
        unitsPanel.add(lblUnidadA, gbcUnits);
        
        // 3.3 ComboBox Unidad De
        cmbUnidadDe = createStyledComboBox(new String[]{"Cargando..."});
        cmbUnidadDe.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS, INPUT_PADDING));
        gbcUnits.gridx = 0; gbcUnits.gridy = 1; gbcUnits.weightx = 0.5; gbcUnits.insets = new Insets(0, 0, 15, 5);
        unitsPanel.add(cmbUnidadDe, gbcUnits);

        // 3.4 Botón de Intercambio (SWAP)
        btnSwap = createIconButton(EXCHANGE_ICON, "↔", ACCENT_BLUE, ACCENT_BLUE_DARK);
        btnSwap.setActionCommand("↔"); 
        gbcUnits.gridx = 1; gbcUnits.gridy = 1; gbcUnits.gridheight = 1;
        gbcUnits.weightx = 0.0; gbcUnits.fill = GridBagConstraints.NONE;
        gbcUnits.insets = new Insets(0, 5, 15, 5); gbcUnits.anchor = GridBagConstraints.CENTER;
        unitsPanel.add(btnSwap, gbcUnits);

        // 3.5 ComboBox Unidad A
        cmbUnidadA = createStyledComboBox(new String[]{"Cargando..."});
        cmbUnidadA.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS, INPUT_PADDING));
        gbcUnits.gridx = 2; gbcUnits.gridy = 1; gbcUnits.weightx = 0.5; gbcUnits.fill = GridBagConstraints.HORIZONTAL;
        gbcUnits.insets = new Insets(0, 5, 15, 0);
        unitsPanel.add(cmbUnidadA, gbcUnits);

        gbc.gridy = 4; gbc.insets = new Insets(10, 0, 20, 0);
        mainPanel.add(unitsPanel, gbc);

        // 4. Resultado
        JLabel lblResultTitle = new JLabel("Resultado de la Conversión");
        lblResultTitle.setFont(FONT_SUBTITLE); lblResultTitle.setForeground(TEXT_MEDIUM);
        gbc.gridy = 5; gbc.insets = new Insets(10, 0, 5, 0);
        mainPanel.add(lblResultTitle, gbc);

        lblResultado = new JLabel("0.00");
        lblResultado.setFont(FONT_RESULT); lblResultado.setForeground(ACCENT_BLUE_DARK);
        lblResultado.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 6; gbc.insets = new Insets(5, 0, 0, 0); // Espacio reducido

        mainPanel.add(lblResultado, gbc);

        // 5. Status Bar
        lblStatus = new JLabel("Listo para convertir"); // Texto inicial
        lblStatus.setFont(FONT_STATUS); lblStatus.setForeground(TEXT_LIGHT_GRAY);
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 7; gbc.insets = new Insets(0, 0, 10, 0); // Margen para separar de los botones
        mainPanel.add(lblStatus, gbc);

        // 6. Botones de Acción
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);

        btnConvertir = createStyledButton("CONVERTIR", ACCENT_BLUE, ACCENT_BLUE_DARK, TEXT_LIGHT, FONT_BUTTON);
        btnConvertir.setActionCommand("CONVERTIR"); 
        buttonPanel.add(btnConvertir);

        btnLimpiar = createStyledButton("Limpiar", GRAY_BUTTON, GRAY_BUTTON_DARK, TEXT_DARK, FONT_BUTTON);
        btnLimpiar.setActionCommand("Limpiar"); 
        buttonPanel.add(btnLimpiar);

        gbc.gridy = 8; gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(buttonPanel, gbc);
        
        // Wrapper para centrar
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(BACKGROUND_PRIMARY);
        centerWrapper.add(mainPanel);
        add(centerWrapper, BorderLayout.CENTER);
        
        // 7. Footer (Copyright)
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(BACKGROUND_PRIMARY);
        JLabel footerLabel = new JLabel("© 2025 Conversor de Unidades | GR03", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        footerLabel.setForeground(TEXT_LIGHT_GRAY);
        footerPanel.add(footerLabel);
        footerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    // --- Métodos de Utilidad y Clases de Estilo ---
    
    private Icon loadIcon(String path, int width, int height) {
        try {
            URL url = getClass().getResource(path);
            if (url != null) {
                Image originalImg = new ImageIcon(url).getImage();
                Image scaled = originalImg.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            } else { System.err.println("Recurso no encontrado: " + path); }
        } catch (Exception e) { System.err.println("Error al cargar y escalar el icono: " + path); }
        return null;
    }
    
    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> cmb = new JComboBox<>(items);
        cmb.setFont(FONT_COMBO); cmb.setBackground(BACKGROUND_PRIMARY); cmb.setForeground(TEXT_DARK);
        cmb.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS, INPUT_PADDING));
        cmb.setUI(new CustomComboBoxUI());
        return cmb;
    }
    
    private JButton createStyledButton(String text, Color baseColor, Color hoverColor, Color foreground, Font font) {
        RoundedButton btn = new RoundedButton(text, baseColor, hoverColor, foreground, font, CORNER_RADIUS);
        btn.setPreferredSize(new Dimension(150, 45));
        return btn;
    }

    private JButton createIconButton(Icon icon, String fallbackText, Color baseColor, Color hoverColor) {
        CircularButton btn = new CircularButton(fallbackText, icon, baseColor);
        btn.setPreferredSize(new Dimension(45, 45)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(hoverColor); }
            public void mouseExited(MouseEvent e) { btn.setBackground(baseColor); }
            public void mousePressed(MouseEvent e) { btn.setBackground(hoverColor.darker()); }
            public void mouseReleased(MouseEvent e) { btn.setBackground(hoverColor); }
        });
        return btn;
    }
    
    /** Borde redondeado personalizado. */
    private static class RoundBorder extends AbstractBorder {
        private Color color; private int thickness; private int radius; private Insets insets;
        
        // Constructor modificado para aceptar un padding interno
        public RoundBorder(Color color, int thickness, int radius, int padding) {
            this.color = color; this.thickness = thickness; this.radius = radius;
            this.insets = new Insets(padding + thickness, padding + thickness, padding + thickness, padding + thickness);
        }
        
        // Constructor para elementos sin padding grande (como el menú pop-up)
        public RoundBorder(Color color, int thickness, int radius) {
            this(color, thickness, radius, 2); 
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
            insets.left = this.insets.left;
            insets.top = this.insets.top;
            insets.right = this.insets.right;
            insets.bottom = this.insets.bottom;
            return insets;
        }
    }
    
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
    private class RoundedButton extends JButton {
        private Color baseColor; private Color hoverColor; private int radius;
        public RoundedButton(String text, Color base, Color hover, Color foreground, Font font, int radius) {
            super(text); this.baseColor = base; this.hoverColor = hover; this.radius = radius; setFont(font);
            setForeground(foreground); setFocusPainted(false); setContentAreaFilled(false);
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
    private static class CircularButton extends JButton {
        private Color baseColor;
        public CircularButton(String text, Icon icon, Color baseColor) {
            super(text, icon); this.baseColor = baseColor; setContentAreaFilled(false); setOpaque(false);
            if (icon != null) { super.setText(null); } super.setBackground(baseColor);
        }
        public void setBackground(Color bg) { if (bg != null) { this.baseColor = bg; } super.setBackground(bg); }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground()); g2.fillOval(0, 0, getWidth(), getHeight()); g2.dispose();
            super.paintComponent(g);
        }
        public Insets getInsets() { int p = 4; return new Insets(p, p, p, p); }
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize(); int max = Math.max(size.width, size.height);
            return new Dimension(max, max);
        }
    }
    private class CustomComboBoxUI extends BasicComboBoxUI {
        protected JButton createArrowButton() {
            JButton button = new JButton(UIManager.getIcon("ComboBox.arrowIcon"));
            button.setBackground(BACKGROUND_PRIMARY); button.setOpaque(true);
            button.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
            return button;
        }
        public void installUI(JComponent c) {
            super.installUI(c); JComboBox comboBox = (JComboBox) c;
            comboBox.setOpaque(false); comboBox.setBorder(BorderFactory.createEmptyBorder());
            comboBox.setBackground(BACKGROUND_PRIMARY);
        }
        protected ComboPopup createPopup() {
            BasicComboPopup popup = (BasicComboPopup) super.createPopup();
            popup.setBorder(new RoundBorder(BORDER_GRAY, 1, 8)); return popup;
        }
    }
}