package bka.swing.clock;

import java.awt.*;

public class SimpleClock extends ClockJPanel {

    
    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }
    
    
    public void paintFace(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.BLACK);
        g2d.fillOval(0, 0, diameter, diameter);
        g2d.setColor(getBackground());
        g2d.fillOval(4, 4, diameter-8, diameter-8);
    }

    
    public Dimension getPreferredSize() {
        return new Dimension(diameter + 1, diameter + 1);
    }

    
    private int diameter;
    
}
