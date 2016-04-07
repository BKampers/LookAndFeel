/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public abstract class PointRenderer<S extends Shape> extends CoordinateAreaRenderer<S> {


    public PointRenderer(int width, int height, AreaLooks paintFactory) {
        this.width = width;
        this.height = height;
        this.paintFactory = paintFactory;
    }


    @Override
    protected abstract S createArea(int x, int y);


    public void setBorder(Color color, Stroke stroke) {
        borderColor = color;
        borderStroke = stroke;
    }


    public void setBorder(Color color) {
        setBorder(color, new BasicStroke(1.0f));
    }


    @Override
    protected void draw(Graphics2D g2d, PointAreaGeometry<S> geometry) {
        S area = geometry.getArea();
        Paint paint = paintFactory.getPaint(area);
        draw(g2d, paint, area);
    }


   @Override
    protected void drawSymbol(Graphics2D g2d, int x, int y) {
        S area = createArea(x, y);
        draw(g2d, paintFactory.getPaint(area), area);
    }


    private void draw(Graphics2D g2d, Paint paint, S area) {
        if (paint != null) {
            g2d.setPaint(paint);
            g2d.fill(area);
        }
        if (borderColor != null) {
            g2d.setPaint(borderColor);
            g2d.draw(area);
        }
    }


    protected int width;
    protected int height;

    private final AreaLooks paintFactory;

    protected Color borderColor;
    protected Stroke borderStroke;

}
