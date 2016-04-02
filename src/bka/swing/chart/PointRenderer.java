/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;
import java.util.*;


public abstract class PointRenderer<S extends Shape> extends AbstractDataAreaRenderer<PointAreaGeometry<S>> {


    protected abstract S createArea(int x, int y);


    @Override
    public void draw(Graphics2D g2d, PointAreaGeometry<S> geometry, java.awt.Point location) {
        draw(g2d, geometry);
    }


    @Override
    protected void draw(Graphics2D g2d, PointAreaGeometry<S> geometry) {
        g2d.setColor(color);
        g2d.fill(geometry.getArea());
    }
    
    
   @Override
    protected void drawSymbol(Graphics2D g2d, int x, int y) {
        g2d.setColor(color);
        g2d.fill(createArea(x, y));
    }


    public void setColor(Color color) {
        this.color = color;
    }
    
    
    public Color getColor() {
        return color;
    }


    @Override
    TreeSet<PointAreaGeometry<S>> createDataGeomerty(Map<Number, Number> graph) {
        TreeSet<PointAreaGeometry<S>> geometry = new TreeSet<>();
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            Number x = entry.getKey();
            Number y = entry.getValue();
            int pixelX = chartGeometry.xPixel(x);
            int pixelY = chartGeometry.yPixel(y);
            S area = createArea(pixelX, pixelY);
            geometry.add(new PointAreaGeometry(x, y, area));
        }
        return geometry;
    }

    
    protected Color color = Color.BLACK;
    
}
