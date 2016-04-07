/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;


public class BarRenderer extends CoordinateAreaRenderer<Rectangle> {


    public BarRenderer(int width, AreaLooks paintFactory) {
        this.width = width;
        this.looks = paintFactory;
    }


    public BarRenderer(AreaLooks paintFactory) {
        this(7, paintFactory);
    }


    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * Set horizontal shift of the bar w.r.t. the x value.
     * Useful when plotting multiple bar graphs into one chart.
     * @param shift
     */
    public void setShift(int shift) {
        this.shift = shift;
    }


    @Override
    protected void draw(Graphics2D g2d, PointAreaGeometry<Rectangle> geometry) {
        draw(g2d, geometry.getArea());
    }

    
    @Override
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        int fontHeight = g2d.getFontMetrics().getHeight();
        int left = x - width / 2;
        int top = y - fontHeight / 2;
        draw(g2d, new Rectangle(left, top, width, fontHeight));
    }


    @Override
    protected Rectangle createArea(int x, int y) {
        int left = x - width / 2 + shift;
        int barHeight = chartPanel.areaBottom() - y;
        return new Rectangle(left, y, width, barHeight);

    }


    private void draw(Graphics2D g2d, Rectangle area) {
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

   
    private int width;
    private int shift;
    private AreaLooks looks;

}
