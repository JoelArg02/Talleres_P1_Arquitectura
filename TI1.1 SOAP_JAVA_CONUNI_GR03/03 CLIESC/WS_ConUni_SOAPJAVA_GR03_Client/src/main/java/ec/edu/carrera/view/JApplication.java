package ec.edu.carrera.view;

import ec.edu.carrera.controller.ConversorController;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

public class JApplication extends JFrame {

    private JTextField txtNumber;
    private JComboBox<String> cmbInUnit, cmbOutUnit;
    private JButton btnConvert, btnClear, btnSwap;
    private JLabel lblResult, statusLabel, lblTitle;

    // Username
    private String username;
    // Panel usuario compuesto (icono + nombre)
    private JPanel userPanel; 

    // Iconos
    private final Icon USER_ICON;
    private final Icon RETURN_ICON;
    private final Icon EXCHANGE_ICON;

    private final ConversorController controller;
    private final Font FONT_USER_MENU = new Font("Segoe UI", Font.PLAIN, 14);

    // Colores y estilos
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
    private final Color ERROR_RED = Color.decode("#E74C3C");
    private final Color SUCCESS_GREEN = Color.decode("#2ECC71");
    private final Color TEXT_LIGHT = Color.WHITE;
    private final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 28);
    private final Font FONT_SUBTITLE = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_INPUT = new Font("Segoe UI", Font.PLAIN, 24);
    private final Font FONT_BUTTON = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_COMBO = new Font("Segoe UI", Font.PLAIN, 16);
    private final Font FONT_RESULT = new Font("Segoe UI", Font.BOLD, 36);
    private final Font FONT_STATUS = new Font("Segoe UI", Font.ITALIC, 12);
    private static final int CORNER_RADIUS = 12;

    private final String[] units = {"meters", "kilometers", "centimeters"};

    public JApplication(String username) {
        this.username = username;
        USER_ICON = loadIcon("/icons/user.png", 24, 24);
        RETURN_ICON = loadIcon("/icons/return.png", 16, 16);
        EXCHANGE_ICON = loadIcon("/icons/exchange.png", 28, 28);

        controller = new ConversorController();
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { }
        initializeUI();
    }

    // Si lo usas sin usuario, por defecto muestra "Usuario"
    public JApplication() {
        this("Usuario");
    }

    private Icon loadIcon(String path, int width, int height) {
        try {
            java.net.URL imgURL = getClass().getResource(path);
            if (imgURL != null) {
                ImageIcon originalIcon = new ImageIcon(imgURL);
                Image img = originalIcon.getImage();
                if (img != null) {
                    Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaledImg);
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar y escalar el icono: " + path);
        }
        return null;
    }

    private void initializeUI() {
        setTitle("Conversor de Unidades 📐");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 680);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(BACKGROUND_PRIMARY);
        setLayout(new BorderLayout());
        createComponents();
        setupLayout();
        setupEvents();
        SwingUtilities.updateComponentTreeUI(this);
    }

    // Estilo y componentes
    private void createComponents() {
        lblTitle = new JLabel("CONVERSOR DE UNIDADES", SwingConstants.CENTER);
        lblTitle.setFont(FONT_TITLE);
        lblTitle.setForeground(TEXT_DARK);

        txtNumber = new JTextField();
        txtNumber.setFont(FONT_INPUT);
        txtNumber.setHorizontalAlignment(JTextField.RIGHT);
        txtNumber.setBackground(BACKGROUND_SECONDARY);
        txtNumber.setForeground(TEXT_DARK);
        txtNumber.setCaretColor(ACCENT_BLUE);
        txtNumber.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS));
        txtNumber.setText(""); // Sin texto por default

        cmbInUnit = createStyledComboBox(units);
        cmbOutUnit = createStyledComboBox(units);

        btnConvert = createStyledButton("CONVERTIR", ACCENT_BLUE, ACCENT_BLUE_DARK, TEXT_LIGHT, FONT_BUTTON);
        btnClear = createStyledButton("LIMPIAR", GRAY_BUTTON, GRAY_BUTTON_DARK, TEXT_DARK, FONT_BUTTON);
        btnSwap = createIconButton(EXCHANGE_ICON, "⇄", ACCENT_BLUE, ACCENT_BLUE_DARK);

        lblResult = new JLabel("0.00", SwingConstants.CENTER);
        lblResult.setFont(FONT_RESULT);
        lblResult.setForeground(TEXT_DARK);

        statusLabel = new JLabel("Listo para convertir", SwingConstants.CENTER);
        statusLabel.setFont(FONT_STATUS);
        statusLabel.setForeground(TEXT_LIGHT_GRAY);

        // Panel compuesto con icono y nombre de usuario
        userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
        userPanel.setOpaque(false);

        JLabel userIconLabel = new JLabel(USER_ICON);
        userIconLabel.setBorder(new EmptyBorder(0, 0, 0, 6));

        JLabel userNameLabel = new JLabel(username);
        userNameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userNameLabel.setForeground(TEXT_MEDIUM);

        userPanel.add(userIconLabel);
        userPanel.add(userNameLabel);
        userPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private JButton createStyledButton(String text, Color baseColor, Color hoverColor, Color foreground, Font font) {
        JButton button = new RoundedButton(text, baseColor, CORNER_RADIUS);
        button.setFont(font);
        button.setForeground(foreground);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(12, 25, 12, 25));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { button.setBackground(hoverColor); }
            public void mouseExited(MouseEvent evt) { button.setBackground(baseColor); }
        });
        return button;
    }

    private JButton createIconButton(Icon icon, String fallbackText, Color baseColor, Color hoverColor) {
        JButton button = new CircularButton(fallbackText, icon, baseColor);
        if (icon == null) { button.setFont(new Font("Segoe UI", Font.BOLD, 20)); }
        button.setForeground(TEXT_LIGHT);
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(48, 48));
        button.setMinimumSize(new Dimension(48, 48));
        button.setMaximumSize(new Dimension(48, 48));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) { button.setBackground(hoverColor); }
            public void mouseExited(MouseEvent evt) { button.setBackground(baseColor); }
        });
        return button;
    }

    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> cmb = new JComboBox<>(items);
        cmb.setFont(FONT_COMBO);
        cmb.setBackground(BACKGROUND_SECONDARY);
        cmb.setForeground(TEXT_DARK);
        cmb.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS));
        cmb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cmb.setOpaque(true);
        cmb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setOpaque(true);
                label.setBorder(new EmptyBorder(8, 10, 8, 10));
                if (isSelected) {
                    label.setBackground(ACCENT_BLUE_DARK);
                    label.setForeground(TEXT_LIGHT);
                } else {
                    label.setBackground(BACKGROUND_SECONDARY);
                    label.setForeground(TEXT_DARK);
                }
                return label;
            }
        });
        return cmb;
    }

    // Menú usuario (cerrar sesión)
    private void showUserMenu(MouseEvent e) {
        JPopupMenu userMenu = new JPopupMenu();
        JMenuItem logoutItem = new JMenuItem("Cerrar Sesión", RETURN_ICON);
        logoutItem.setFont(FONT_USER_MENU);
        logoutItem.addActionListener(evt -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro que desea cerrar la sesión?",
                    "Confirmar Cierre de Sesión",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                java.awt.EventQueue.invokeLater(() ->
                        new ec.edu.carrera.view.JLogin().setVisible(true));
            }
        });
        userMenu.add(logoutItem);
        userMenu.show(userPanel, e.getX(), e.getY());
    }

    private void setupLayout() {
    RoundedPanel mainPanel = new RoundedPanel(CORNER_RADIUS + 4, BACKGROUND_SECONDARY);
    mainPanel.setLayout(new GridBagLayout());
    mainPanel.setBorder(new EmptyBorder(10, 30, 30, 30)); // margen arriba reducido

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.weightx = 1.0;

    // CABECERA VERTICAL (usuario muy arriba)
    JPanel verticalHeader = new JPanel();
    verticalHeader.setOpaque(false);
    verticalHeader.setLayout(new BoxLayout(verticalHeader, BoxLayout.Y_AXIS));

    // Usuario arriba alineado derecha SIN espacio extra
    JPanel userPanelWrapper = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 50));
    userPanelWrapper.setOpaque(false);
    userPanelWrapper.add(userPanel);

    verticalHeader.add(userPanelWrapper); // sin margen
    verticalHeader.add(lblTitle);

    gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 3;
    gbc.insets = new Insets(0, 0, 8, 0); // margen muy chico abajo
    mainPanel.add(verticalHeader, gbc);

    // ... resto igual
    gbc.gridy = 1; gbc.insets = new Insets(0, 0, 13, 0);
    mainPanel.add(txtNumber, gbc);
    gbc.gridy = 2; gbc.insets = new Insets(0, 0, 18, 0);
    mainPanel.add(statusLabel, gbc);

    gbc.gridwidth = 1; gbc.weightx = 0; gbc.insets = new Insets(0, 0, 3, 0);
    JLabel lblFrom = new JLabel("De:", SwingConstants.LEFT);
    lblFrom.setFont(FONT_SUBTITLE); lblFrom.setForeground(TEXT_MEDIUM);
    gbc.gridy = 3; gbc.gridx = 0; mainPanel.add(lblFrom, gbc);

    JLabel lblTo = new JLabel("A:", SwingConstants.LEFT);
    lblTo.setFont(FONT_SUBTITLE); lblTo.setForeground(TEXT_MEDIUM);
    gbc.gridy = 3; gbc.gridx = 2; mainPanel.add(lblTo, gbc);

    gbc.gridy = 4; gbc.insets = new Insets(0, 0, 14, 0);
    gbc.gridx = 0; gbc.weightx = 1.0; mainPanel.add(cmbInUnit, gbc);

    gbc.gridx = 1; gbc.weightx = 0;
    gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
    gbc.insets = new Insets(0, 8, 14, 8); mainPanel.add(btnSwap, gbc);

    gbc.gridx = 2; gbc.weightx = 1.0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new Insets(0, 0, 14, 0); mainPanel.add(cmbOutUnit, gbc);

    JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
    buttonPanel.setOpaque(false);
    buttonPanel.add(btnConvert);
    buttonPanel.add(btnClear);
    gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 3; gbc.weightx = 1.0;
    gbc.insets = new Insets(8, 0, 18, 0); mainPanel.add(buttonPanel, gbc);

    gbc.gridy = 6; gbc.insets = new Insets(0, 0, 0, 0);
    gbc.gridwidth = 3; mainPanel.add(lblResult, gbc);

    add(mainPanel, BorderLayout.CENTER);

    JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    footerPanel.setBackground(BACKGROUND_PRIMARY);
    JLabel footerLabel = new JLabel("© 2025 Conversor de Unidades | GR03", SwingConstants.CENTER);
    footerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    footerLabel.setForeground(TEXT_LIGHT_GRAY);
    footerPanel.add(footerLabel);
    footerPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
    add(footerPanel, BorderLayout.SOUTH);
}

    private void setupEvents() {
        btnConvert.addActionListener(e -> performConversion());
        btnClear.addActionListener(e -> clearAll());
        btnSwap.addActionListener(e -> swapUnits());

        userPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { showUserMenu(e); }
        });

        txtNumber.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!((c >= '0' && c <= '9') || c == '.' || c == ',' ||
                        c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                }
            }
        });
    }

    private void performConversion() {
            try {
                String text = txtNumber.getText().trim().replace(',', '.');
                double number = Double.parseDouble(text);
                if (number < 0) {
                    throw new NumberFormatException("Número negativo no permitido");
                }
                int intNumber = (int) number;

                String inUnit = (String) cmbInUnit.getSelectedItem();
                String outUnit = (String) cmbOutUnit.getSelectedItem();

                statusLabel.setText("Convirtiendo via SOAP...");
                statusLabel.setForeground(ACCENT_BLUE);
                btnConvert.setEnabled(false);
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

                SwingWorker<Double, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Double doInBackground() throws Exception {
                        Thread.sleep(800);
                        return controller.performConversion(intNumber, inUnit, outUnit);
                    }

                    @Override
                    protected void done() {
                        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                        btnConvert.setEnabled(true);
                        try {
                            double result = get();
                            lblResult.setText(String.format("%,.4f %s", result, Objects.requireNonNull(outUnit).substring(0, 1).toLowerCase()));
                            statusLabel.setText("Conversión completada con éxito");
                            statusLabel.setForeground(SUCCESS_GREEN);
                            lblResult.setForeground(SUCCESS_GREEN);

                            Timer timer = new Timer(1000, e -> {
                                lblResult.setForeground(TEXT_DARK);
                                statusLabel.setText("Listo para convertir");
                                statusLabel.setForeground(TEXT_LIGHT_GRAY);
                                ((Timer) e.getSource()).stop();
                            });
                            timer.setRepeats(false);
                            timer.start();

                        } catch (Exception ex) {
                            statusLabel.setText("Error: Fallo en el servicio o conversión");
                            statusLabel.setForeground(ERROR_RED);
                            lblResult.setText("ERROR");
                            lblResult.setForeground(ERROR_RED);

                            Timer timer = new Timer(2000, e -> {
                                lblResult.setForeground(TEXT_DARK);
                                lblResult.setText("0.00");
                                statusLabel.setForeground(TEXT_LIGHT_GRAY);
                                statusLabel.setText("Listo para convertir");
                                ((Timer) e.getSource()).stop();
                            });
                            timer.setRepeats(false);
                            timer.start();
                        }
                    }
                };
                worker.execute();
            } catch (NumberFormatException nfe) {
                statusLabel.setText("⚠️ Ingrese un número positivo válido.");
                statusLabel.setForeground(ERROR_RED);
                txtNumber.setBorder(new RoundBorder(ERROR_RED, 2, CORNER_RADIUS));
                new Timer(2000, e -> {
                    txtNumber.setBorder(new RoundBorder(BORDER_GRAY, 1, CORNER_RADIUS));
                    statusLabel.setText("Listo para convertir");
                    statusLabel.setForeground(TEXT_LIGHT_GRAY);
                    ((Timer) e.getSource()).stop();
                }).start();
            }
        }

    private void clearAll() {
        txtNumber.setText("");
        cmbInUnit.setSelectedIndex(0);
        cmbOutUnit.setSelectedIndex(1);
        lblResult.setText("0.00");
        statusLabel.setText("Campos reiniciados");
        statusLabel.setForeground(TEXT_LIGHT_GRAY);
    }

    private void swapUnits() {
        int fromIndex = cmbInUnit.getSelectedIndex();
        int toIndex = cmbOutUnit.getSelectedIndex();
        cmbInUnit.setSelectedIndex(toIndex);
        cmbOutUnit.setSelectedIndex(fromIndex);
        statusLabel.setText("Unidades intercambiadas");
        statusLabel.setForeground(TEXT_LIGHT_GRAY);
    }

    // Clases internas de estilo
    static class RoundBorder extends AbstractBorder {
        private final Color color; private final int thickness; private final int radius;
        public RoundBorder(Color color, int thickness, int radius) {
            this.color = color; this.thickness = thickness; this.radius = radius;
        }
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(thickness));
            g2.setColor(color);
            g2.drawRoundRect(x + thickness / 2, y + thickness / 2, w - thickness, h - thickness, radius, radius);
            g2.dispose();
        }
        public Insets getBorderInsets(Component c) { return new Insets(15, 15, 15, 15); }
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.top = insets.right = insets.bottom = 15; return insets;
        }
    }

    static class RoundedPanel extends JPanel {
        private final int radius; private final Color bgColor;
        public RoundedPanel(int radius, Color bgColor) { this.radius = radius; this.bgColor = bgColor; setOpaque(false); }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
            g2.dispose();
        }
    }

    private static class RoundedButton extends JButton {
        private final int radius; private Color baseColor;
        public RoundedButton(String text, Color baseColor, int radius) {
            super(text); this.baseColor = baseColor; this.radius = radius;
            setContentAreaFilled(false); setOpaque(false); super.setBackground(baseColor);
        }
        public void setBackground(Color bg) {
            if (bg != null) { this.baseColor = bg; }
            super.setBackground(bg);
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(this.baseColor);
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static class CircularButton extends JButton {
        private Color baseColor;
        public CircularButton(String text, Icon icon, Color baseColor) {
            super(text, icon); this.baseColor = baseColor;
            setContentAreaFilled(false); setOpaque(false);
            if (icon != null) { super.setText(null); }
            super.setBackground(baseColor);
        }
        public void setBackground(Color bg) {
            if (bg != null) { this.baseColor = bg; }
            super.setBackground(bg);
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(this.baseColor);
            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }
        public Insets getInsets() { int p = 4; return new Insets(p, p, p, p); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JApplication("MONSTER").setVisible(true));
    }
}
