/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;
import java.awt.geom.*;


public class DishDotRenderer extends OvalDotRenderer {
    
    
    public DishDotRenderer(int size, int offset) {
        super(size, size);
        this.offset = offset;
    }


    @Override
    public void draw(Graphics2D g2d, PixelAreaGeometry geometry) {
        draw(g2d, geometry.getArea());
    }


   @Override
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        draw(g2d, createArea(x, y));
    }


    private void draw(Graphics2D g2d, Shape area) {
        g2d.setColor(color);
        Ellipse2D.Float outer = (Ellipse2D.Float) area;
        g2d.draw(outer);
        Ellipse2D inner = new Ellipse2D.Float(
            (float) outer.getX() + offset,
            (float) outer.getY() + offset,
            (float) outer.getWidth() - 2 * offset,
            (float) outer.getHeight() - 2 * offset);
        g2d.fill(inner);
    }
    
    
    private final int offset;

}
