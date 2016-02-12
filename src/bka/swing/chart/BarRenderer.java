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


    public BarRenderer(java.awt.Color color) {
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
    public DataPoint createDataPoint(Number x, Number y) {
        Point pixel = new Point(dataSet.xPixel(x), dataSet.yPixel(y));
        double left = pixel.x - width / 2.0 + shift;
        double height = chartPanel.areaBottom() - pixel.y;
        java.awt.geom.Rectangle2D rectangle = new java.awt.geom.Rectangle2D.Double(left, pixel.y, width, height);
        return new PixelDataPoint(rectangle, x, y, new Point(dataSet.xPixel(x), dataSet.yPixel(y)));
    }


    @Override
    public void draw(java.awt.Graphics2D g2d, DataPoint dataPoint) {
        PixelDataPoint pixelDataPoint = (PixelDataPoint) dataPoint;
        java.awt.Point pixel = pixelDataPoint.getPixel();
        double left = pixel.x - width / 2.0 + shift;
        double height = chartPanel.areaBottom() - pixel.y;
        g2d.setPaint(getGradientPaint());
        g2d.fill(new java.awt.geom.Rectangle2D.Double(left, pixel.y, width, height));
    }

    
    @Override
    public void drawSymbol(java.awt.Graphics2D g2d, int x, int y) {
        int height = g2d.getFontMetrics().getHeight();
        g2d.setColor(color);
        g2d.fillRect(x - width / 2, y - height / 2, width, height);
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
