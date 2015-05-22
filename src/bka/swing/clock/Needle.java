package bka.swing.clock;

import java.awt.*;


public abstract class Needle {

    public void setValue(double value) {
        this.value = value;
    } 
    
    
    public void setScale(Scale scale) {
        this.scale = scale;
    }
    
    
    public Scale getScale() {
        return scale;
    }
    
    
    public void setTurningPoint(Point point) {
        turningPoint = point;
    }

    
    public Point getTurningPoint() {
        return turningPoint;
    }

    
    public void paint(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        double angle = scale.angle(value);
        g2d.rotate(angle, turningPoint.x, turningPoint.y);
        paintNeedle(g2d);
        g2d.rotate(-angle, turningPoint.x, turningPoint.y);
    }
    

    public abstract void paintNeedle(Graphics2D g2d);    

    
    protected Point turningPoint = new Point(0, 0);

    private Scale scale = new Scale();
    private double value = 0.0;

}
