/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;
import java.util.*;


public abstract class PointRenderer<S extends Shape> extends AbstractDataPointRenderer<PixelAreaGeometry<S>> {


    protected abstract S createArea(int x, int y);


    @Override
    public void draw(Graphics2D g2d, PixelAreaGeometry<S> geometry) {
        g2d.setColor(color);
        g2d.fill(geometry.getArea());
    }
    
    
    @Override
    public void draw(Graphics2D g2d, PixelAreaGeometry<S> geometry, java.awt.Point location) {
        draw(g2d, geometry);
    }

    
   @Override
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        g2d.setColor(color);
        g2d.fill(createArea(x, y));
    }


    @Override
    public void reset() {
        // No operation required
    }


    public void setColor(Color color) {
        this.color = color;
    }
    
    
    public Color getColor() {
        return color;
    }


    @Override
    TreeSet<PixelAreaGeometry<S>> createDataGeomerty(Map<Number, Number> graph) {
        TreeSet<PixelAreaGeometry<S>> geometry = new TreeSet<>();
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            Number x = entry.getKey();
            Number y = entry.getValue();
            int pixelX = chartGeometry.xPixel(x);
            int pixelY = chartGeometry.yPixel(y);
            S area = createArea(pixelX, pixelY);
            Point pixel = new Point(pixelX, pixelY);
            geometry.add(new PixelAreaGeometry(x, y, area, pixel));
        }
        return geometry;
    }

    
    protected Color color = Color.BLACK;
    
}
