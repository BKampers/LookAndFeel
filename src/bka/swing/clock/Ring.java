package bka.swing.clock;

import java.awt.*;

public abstract class Ring {
    
    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    
    public void setInterval(double interval) {
        this.interval = interval;
    }
    
    
    public void setScale(Scale scale) {
        this.scale = scale;
    }
    
    
    public void setCenter(Point point) {
        center = point;
    }
    
    
    public void setRotatedItems(boolean b) {
        rotatedItems = b;
    }
    
    
    public void paint(Graphics2D g2d) {
        double minValue = Math.min(scale.getMinValue(), scale.getMaxValue());
        double maxValue = Math.max(scale.getMinValue(), scale.getMaxValue());
        if (interval > 0.0) {
            for (double value = minValue; value <= maxValue; value += interval) {
                if (rotatedItems) {
                    double angle = scale.angle(value);
                    g2d.rotate(angle, center.x, center.y);
                    g2d.translate(center.x, center.y-radius);
                    paintItem(g2d, value);
                    g2d.translate(-center.x, -(center.y-radius));
                    g2d.rotate(-angle, center.x, center.y);
                }
                else {
                    Point translation = itemPoint(value);
                    g2d.translate(translation.x, translation.y);
                    paintItem(g2d, value);
                    g2d.translate(-translation.x, -translation.y);
                }
            }
        }
    }

    
    public abstract void paintItem(Graphics2D g2d, double value);
    
    
    private Point itemPoint(double value) {
        Point point = new Point();
        double angle = scale.angle(value);
        point.x = center.x + Math.round((float) (Math.sin(angle) * radius));
        point.y = center.y - Math.round((float) (Math.sin(Math.PI / 2 - angle) * radius));
        return point;
    }
    
    
    protected boolean rotatedItems = true;
    
    private int radius = 0;
    private double interval = 1.0;
    private Scale scale = new Scale();
    private Point center = new Point(0, 0);
    
}
