package bka.swing.clock;

import java.awt.*;


public class SimpleValueRing extends ValueRing {
    
    public SimpleValueRing() {
        super();
    }

    
    public SimpleValueRing(Point center, int radius) {
        super();
        setCenter(center);
        setRadius(radius);
    }
    
    
    public SimpleValueRing(Font font, Color color, Point center, int radius) {
        super();
        this.font = font;
        this.color = color;
        setCenter(center);
        setRadius(radius);
    }
    
    
    public void setFont(Font font) {
        this.font = font;
    }
    
    
    public void setColor(Color color) {
        this.color = color;
    }

    
    public void paintValue(Graphics2D g2d, String value){
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(font);
        g2d.setColor(color);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int width = fontMetrics.stringWidth(value);
        int height = fontMetrics.getHeight();
        int descent = fontMetrics.getDescent();
        g2d.drawString(value, width / -2, height / 2 - descent);
    }    

    
    private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 50);
    private Color color = Color.BLACK;
    
}
