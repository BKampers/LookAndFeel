/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public class DefaultLineRenderer extends LineRenderer {


    public DefaultLineRenderer(LineLooks lineLooks) {
        super(lineLooks);
    }
    
    
    @Override
    public void draw(Graphics2D g2d, PixelAreaGeometry geometry1, PixelAreaGeometry geometry2) {
        java.awt.Point pixel1 = geometry1.getPixel();
        java.awt.Point pixel2 = geometry2.getPixel();
        draw(g2d, pixel1.x, pixel1.y, pixel2.x, pixel2.y);
    }
    
    
    @Override
    protected void draw(Graphics2D g2d, PixelAreaGeometry geometry) {
        if (lineLooks.getAreaLooks() != null) {
            draw(g2d, geometry.getArea());
        }
        if (previous != null) {
            java.awt.Point pixel1 = previous.getPixel();
            java.awt.Point pixel2 = geometry.getPixel();
            draw(g2d, pixel1.x, pixel1.y, pixel2.x, pixel2.y);
        }
        previous = geometry;
    }


    @Override
    protected void drawSymbol(Graphics2D g2d, int x, int y) {
        if (lineLooks.getAreaLooks() != null) {
            draw(g2d, new Rectangle(x - 3, y - 3, 7, 7));
        }
        draw(g2d, x - 5, y, x + 5, y);
    }


    private void draw(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        Paint paint = lineLooks.getLinePaint();
        Stroke stroke = lineLooks.getLineStroke();
        if (paint != null && stroke != null) {
            g2d.setPaint(paint);
            g2d.setStroke(stroke);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }


}
