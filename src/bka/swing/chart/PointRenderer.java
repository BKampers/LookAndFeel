/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;
import java.util.*;


public abstract class PointRenderer extends AbstractDataPointRenderer {


    protected abstract Shape createArea(int x, int y);


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


    @Override
    TreeSet<DataPoint> createDataPoints(Map<Number, Number> graph) {
        TreeSet<DataPoint> dataPoints = new TreeSet<>();
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            Number x = entry.getKey();
            Number y = entry.getValue();
            int pixelX = dataSet.xPixel(x);
            int pixelY = dataSet.yPixel(y);
            Shape area = createArea(pixelX, pixelY);
            Point pixel = new Point(pixelX, pixelY);
            dataPoints.add(new PixelDataPoint(x, y, area, pixel));
        }
        return dataPoints;
    }

    
    protected Color color = Color.BLACK;
    
}
