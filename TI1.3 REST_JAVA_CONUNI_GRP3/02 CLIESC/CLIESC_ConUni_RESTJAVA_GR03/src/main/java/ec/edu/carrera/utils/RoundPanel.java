package ec.edu.carrera.utils;

/**
 *
 * @author nahir
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

// Esta clase la tomamos del ejemplo JApplication.java
public class RoundPanel extends JPanel {
    private final int radius;
    private final Color baseColor;

    public RoundPanel(int radius, Color baseColor) {
        super();
        this.radius = radius;
        this.baseColor = baseColor;
        setOpaque(false); // Hacemos el panel transparente por defecto
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(this.baseColor);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
        g2.dispose();
        super.paintComponent(g);
    }
}
