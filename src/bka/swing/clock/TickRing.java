package bka.swing.clock;

import java.awt.*;

public abstract class TickRing extends Ring {
    
    public void setInterval(double interval) {
        super.setInterval(interval);
        tolerance = interval / 2.0;
    }
    
    
    public void setMajorInterval(double interval) {
        majorInterval = interval;
    }


    public void paintItem(Graphics2D g2d, double value) {
        long majorValue = Math.round(value / majorInterval);
        double distance = value - majorValue * majorInterval;
        if (-tolerance < distance && distance < tolerance) {
            paintMajorTick(g2d);
        }
        else {
            paintMinorTick(g2d);
        }
    }

    
    public abstract void paintMajorTick(Graphics2D g2d);
    public abstract void paintMinorTick(Graphics2D g2d);

    
    private double majorInterval = 1.0;
    private double tolerance = 0.5;

}
