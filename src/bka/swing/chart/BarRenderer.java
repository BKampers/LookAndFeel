/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;


public class BarRenderer extends PointRenderer {


    public BarRenderer(int width, Color color) {
        this.width = width;
        this.color = color;
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
    
    
    public void setSecondaryColor(Color color) {
        secondaryColor = color;
    }
    
    
    @Override
    public void draw(Graphics2D g2d, PixelAreaGeometry dataAreaGeometry) {
        g2d.setPaint(getGradientPaint());
        g2d.fill(dataAreaGeometry.getArea());
    }

    
    @Override
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        int height = g2d.getFontMetrics().getHeight();
        g2d.setColor(color);
        g2d.fillRect(x - width / 2, y - height / 2, width, height);
    }


    @Override
    protected Shape createArea(int x, int y) {
        double left = x - width / 2.0 + shift;
        double height = chartPanel.areaBottom() - y;
        return new java.awt.geom.Rectangle2D.Double(left, y, width, height);

    }
    
    
    protected GradientPaint getGradientPaint() {
        if (secondaryColor == null) {
            secondaryColor = color;
        }
        return new GradientPaint(0, 0, secondaryColor, width, chartPanel.areaBottom(), color);
    }

    
    private int width;
    private int shift;
    private Color secondaryColor;
    
}
