/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;


public class BarRenderer extends CoordinateAreaRenderer<Rectangle> {


    public BarRenderer(int width, Color color) {
        this.width = width;
        this.color1 = color;
    }


    public BarRenderer(Color color) {
        this(7, color);
    }


    public BarRenderer(int width) {
        this(width, null);
    }


    public BarRenderer() {
        this(null);
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
        Rectangle area = geometry.getArea();
        Paint paint = new GradientPaint(area.x, 0, color2, area.x, chartPanel.areaBottom(), color1);
        g2d.setPaint(paint);
        g2d.fill(area);
        if (borderColor != null) {
            g2d.setPaint(borderColor);
            g2d.draw(area);
        }
    }

    
    @Override
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        int fontHeight = g2d.getFontMetrics().getHeight();
        int left = x - width / 2;
        int top = y - fontHeight / 2;
        Paint paint = new GradientPaint(left, top, color2, left, top + fontHeight, color1);
        g2d.setPaint(paint);
        g2d.fillRect(left, top, width, fontHeight);
    }


    @Override
    protected Rectangle createArea(int x, int y) {
        int left = Math.round(x - width / 2.0f + shift);
        int barHeight = chartPanel.areaBottom() - y;
        return new Rectangle(left, y, width, barHeight);

    }
    

    
    public void setColors(Color color1, Color color2, Color borderColor) {
        this.color1 = color1;
        this.color2 = color2;
        this.borderColor = borderColor;
    }


    private Color color1, color2, borderColor;
    private int width, shift;
    
}
