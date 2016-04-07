/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public abstract class PointRenderer<S extends Shape> extends CoordinateAreaRenderer<S> {


    public PointRenderer(int width, int height, AreaLooks looks) {
        this.width = width;
        this.height = height;
        this.looks = looks;
    }


    @Override
    protected abstract S createArea(int x, int y);


    @Override
    protected void draw(Graphics2D g2d, PointAreaGeometry<S> geometry) {
        draw(g2d, geometry.getArea());
    }


   @Override
    protected void drawSymbol(Graphics2D g2d, int x, int y) {
        S area = createArea(x, y);
        draw(g2d, area);
    }


    private void draw(Graphics2D g2d, S area) {
        Paint paint = looks.getPaint(area);
        if (paint != null) {
            g2d.setPaint(paint);
            g2d.fill(area);
        }
        Paint borderPaint = looks.getBorderPaint(area);
        Stroke borderStroke = looks.getBorderStroke(area);
        if (borderPaint != null && borderStroke != null) {
            g2d.setPaint(borderPaint);
            g2d.setStroke(borderStroke);
            g2d.draw(area);
        }
    }


    protected int width;
    protected int height;

    private final AreaLooks looks;

}
