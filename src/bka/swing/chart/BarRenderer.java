/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;


public class BarRenderer extends PointRenderer {
    
    
    public BarRenderer(int width, java.awt.Color color) {
        this.width = width;
        this.color = color;
    }

    
    public BarRenderer(java.awt.Color color) {
        this(7, color);
    }
    
    public BarRenderer(int width) {
        this(width, javax.swing.UIManager.getColor("chart.dataPointColor"));
    }
    
    
    public BarRenderer() {
        this(7);
    }
    
    
    public void setWidth(int width) {
        this.width = width;
    }
    
    
    public void setShift(int shift) {
        this.shift = shift;
    }
    
    
    public void draw(java.awt.Graphics2D g2d, DataPoint dataPoint) {
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        java.awt.Point pixel = dataPoint.getPixel();
        g2d.setColor(color);
        g2d.fillRect(pixel.x - width / 2 + shift, pixel.y, width, chartPanel.areaBottom() - pixel.y);
    }

    
    public void drawSymbol(java.awt.Graphics2D g2d, int x, int y) {
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        int height = g2d.getFontMetrics().getHeight();
        g2d.setColor(color);
        g2d.fillRect(x - width / 2, y - height / 2, width, height);
    }

    
    private int width;
    
    private int shift = 0;
    
}
