/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.Stroke;


public class DefaultLineRenderer extends LineRenderer {
    
    
    @Override
    public void draw(java.awt.Graphics2D g2d, DataPoint dataPoint) {
        PixelDataPoint current = (PixelDataPoint) dataPoint;
        if (previous != null) {
            java.awt.Point pixel1 = previous.getPixel();
            java.awt.Point pixel2 = current.getPixel();
            draw(g2d, pixel1.x, pixel1.y, pixel2.x, pixel2.y);
        }
        previous = current;
    }
    
    
    @Override
    public void draw(java.awt.Graphics2D g2d, DataPoint dataPoint1, DataPoint dataPoint2) {
        java.awt.Point pixel1 = ((PixelDataPoint) dataPoint1).getPixel();
        java.awt.Point pixel2 = ((PixelDataPoint) dataPoint2).getPixel();
        draw(g2d, pixel1.x, pixel1.y, pixel2.x, pixel2.y);
    }

    
    @Override
    public void drawSymbol(java.awt.Graphics2D g2d, int x, int y) {
        draw(g2d, x - 3, y, x + 3, y);
    }

    
    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }
    
    
    private void draw(java.awt.Graphics2D g2d, int x1, int y1, int x2, int y2) {
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
