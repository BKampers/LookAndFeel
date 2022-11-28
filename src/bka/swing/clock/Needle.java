package bka.swing.clock;

import java.awt.*;
import java.util.*;


public abstract class Needle {

    protected Needle(Point turningPoint, Scale scale) {
        setTurningPoint(turningPoint);
        setScale(scale);
    }

    public void setValue(double value) {
        this.value = value;
    } 

    public final void setScale(Scale scale) {
        this.scale = Objects.requireNonNull(scale);
    }
    
    public Scale getScale() {
        return scale;
    }

    public final void setTurningPoint(Point point) {
        turningPoint = Objects.requireNonNull(point);
    }

    public final Point getTurningPoint() {
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

    
    private Point turningPoint;
    private Scale scale;
    private double value;

}
