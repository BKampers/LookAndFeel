/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public abstract class LineRenderer extends AbstractDataPointRenderer<PixelAreaGeometry> {
    
    
    public abstract void draw(Graphics2D g2d, PixelAreaGeometry geometry1, PixelAreaGeometry geometry2);
    
    
    @Override
    public void draw(Graphics2D g2d, PixelAreaGeometry geometry, Point location) {
        draw(g2d, geometry);
    }
    
    
    @Override
    public void reset() {
        previous = null;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    
    public Color getColor() {
        return color;
    }
    
    
    protected Color color = Color.BLACK;
    protected PixelAreaGeometry previous;
    
}
