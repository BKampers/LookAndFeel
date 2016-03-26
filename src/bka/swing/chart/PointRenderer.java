/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public abstract class PointRenderer extends AbstractDataPointRenderer {


    @Override
     public DataPoint createDataPoint(Number x, Number y) {
        Point point = new Point(dataSet.xPixel(x), dataSet.yPixel(y));
        return new PixelDataPoint(createArea(point.x, point.y), x, y, point);
    }


    @Override
    public void draw(java.awt.Graphics2D g2d, DataPoint dataPoint) {
        g2d.setColor(color);
        g2d.fill(dataPoint.getArea());
    }
    
    
    @Override
    public void draw(java.awt.Graphics2D g2d, DataPoint dataPoint, java.awt.Point location) {
        draw(g2d, dataPoint);
    }

    
   @Override
    public void drawSymbol(java.awt.Graphics2D g2d, int x, int y) {
        g2d.setColor(color);
        g2d.fill(createArea(x, y));
    }


    public void setColor(Color color) {
        this.color = color;
    }
    
    
    public Color getColor() {
        return color;
    }

    
    protected abstract Shape createArea(int x, int y);
    
    
    @Override
    void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }
    
    
    protected Color color = Color.BLACK;
    
    protected String xFormat;
    protected String yFormat;
    
}
