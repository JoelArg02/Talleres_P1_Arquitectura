/**
 *
 * @author nahir
 */
package ec.edu.carrera.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.AbstractBorder;

// Esta clase la tomamos del ejemplo JLogin.java
public class RoundBorder extends AbstractBorder {
    private final Color color;
    private final int thickness;
    private final int cornerRadius;

    public RoundBorder(Color color, int thickness, int cornerRadius) {
        this.color = color;
        this.thickness = thickness;
        this.cornerRadius = cornerRadius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(color);
        g2.setStroke(new java.awt.BasicStroke(thickness));
        g2.draw(new RoundRectangle2D.Double(x + thickness / 2.0, y + thickness / 2.0, 
                                           width - thickness, height - thickness, 
                                           cornerRadius, cornerRadius));
        g2.dispose();
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(thickness, thickness, thickness, thickness);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.left = insets.top = insets.right = insets.bottom = thickness;
        return insets;
    }
}
