package bka.swing.clock;

import java.awt.*;


public class SimpleNeedle extends Needle {

    public SimpleNeedle(Point turningPoint, Scale scale) {
        super(turningPoint, scale);
    }
   
    public void setLength(int length) {
        this.length = length;
    }
    
    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    @Override
    public void paintNeedle(Graphics2D g2d) {
        Stroke restoreStroke = null;
        if (stroke != null) {
            restoreStroke = g2d.getStroke();
        }
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (stroke != null) {
            g2d.setStroke(stroke);
        }
        Point turningPoint = getTurningPoint();
        g2d.drawLine(turningPoint.x, turningPoint.y + length / 8, turningPoint.x, turningPoint.y - length);
        if (restoreStroke != null) {
            g2d.setStroke(restoreStroke);
        }
    }

    private int length;
    private Stroke stroke = null;
    
}
