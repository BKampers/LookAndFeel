package bka.swing;


import java.awt.*;
import javax.swing.*;


public class RoundedBorder extends javax.swing.border.AbstractBorder {

    public RoundedBorder(int thickness, int arcWidth, int arcHeight) {
       this.thickness = thickness;
       this.arcWidth = arcWidth;
       this.arcHeight = arcHeight;
    }


    public RoundedBorder(int thickness, int arcDiameter) {
        this(thickness, arcDiameter, arcDiameter);
    }


    public void setNormalColor(Color color) {
        normalColor = color;
    }


    public void setHoverColor(Color color) {
        hoverColor = color;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        Color color = normalColor;
        if (c instanceof AbstractButton) {
            if (! c.isEnabled()) {
                color = null;
            }
            else if (((AbstractButton) c).isRolloverEnabled() && ((AbstractButton) c).getModel().isRollover()) {
                color = hoverColor;
            }
        }
        if (color != null) {
            int offset = thickness / 2;
            Stroke stroke = new BasicStroke(thickness);
            graphics2D.setStroke(stroke);
            graphics2D.setColor(color);
            graphics2D.drawRoundRect(x + offset, y + offset, width - thickness, height - thickness, arcWidth, arcHeight);
        }
    }


    public Insets getBorderInsets(Component c) {
        return INSETS;
    }


    private int thickness;
    private int arcWidth;
    private int arcHeight;

    private Color normalColor = null;
    private Color hoverColor = null;


    private static final Insets INSETS = new Insets(3, 3, 3, 3);

}
