/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public class DefaultLineRenderer extends LineRenderer {
    
    
    @Override
    public void draw(Graphics2D g2d, PixelAreaGeometry geometry1, PixelAreaGeometry geometry2) {
        java.awt.Point pixel1 = geometry1.getPixel();
        java.awt.Point pixel2 = geometry2.getPixel();
        draw(g2d, pixel1.x, pixel1.y, pixel2.x, pixel2.y);
    }

    
    @Override
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        draw(g2d, x - 3, y, x + 3, y);
    }

    
    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }
    
    
    @Override
    protected void draw(Graphics2D g2d, PixelAreaGeometry geometry) {
        PixelAreaGeometry current = (PixelAreaGeometry) geometry;
        if (previous != null) {
            java.awt.Point pixel1 = previous.getPixel();
            java.awt.Point pixel2 = current.getPixel();
            draw(g2d, pixel1.x, pixel1.y, pixel2.x, pixel2.y);
        }
        previous = current;
    }


    private void draw(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        g2d.setColor(color);
        Stroke defaultStroke = g2d.getStroke();
        if (stroke != null)  {
            g2d.setStroke(stroke);
        }
        g2d.drawLine(x1, y1, x2, y2);
        g2d.setStroke(defaultStroke);        
    }
    
    
    private Stroke stroke = null;

}
