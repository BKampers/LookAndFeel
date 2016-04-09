/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;
import java.awt.geom.*;


public class DefaultLineRenderer extends LineRenderer {


    public DefaultLineRenderer(LineLooks lineLooks, int markerWidth, int markerHeight) {
        super(lineLooks, markerWidth, markerHeight);
    }


    public DefaultLineRenderer(LineLooks lineLooks, int markerSize) {
        this(lineLooks, markerSize, markerSize);
    }


    public DefaultLineRenderer(LineLooks lineLooks) {
        this(lineLooks, 3, 3);
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
            super.draw(g2d, geometry);
        }
        if (previous != null) {
            java.awt.Point pixel1 = previous.getPixel();
            java.awt.Point pixel2 = geometry.getPixel();
            draw(g2d, pixel1.x, pixel1.y, pixel2.x, pixel2.y);
        }
        previous = geometry;
    }


    @Override
    protected PixelAreaGeometry<RectangularShape> createSymbolGeometry(int x, int y, PixelAreaGeometry<RectangularShape> geometry) {
        RectangularShape area = createSymbolArea(x, y);
        Point pixel = new Point((int) area.getCenterX(), (int) area.getCenterY());
        return new PixelAreaGeometry<>(null, null, area, pixel);
    }


    @Override
    protected void drawSymbol(Graphics2D g2d, int x, int y) {
        RectangularShape area = createSymbolArea(x, y);
        super.draw(g2d, new PixelAreaGeometry<>(null, null, area, new Point(x, y)));
        draw(g2d, x - 7, y, x + 7, y);
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
