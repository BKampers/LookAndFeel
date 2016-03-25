/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


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

    
    public void setXFormat(String xFormat) {
        this.xFormat = xFormat;
    }

    
    public void setYFormat(String yFormat) {
        this.yFormat = yFormat;
    }
    
    
    public String xLabel(DataPoint dataPoint) {
        if (xFormat != null) {
            return label(xFormat, dataPoint.getX());
        }
        else {
            return dataPoint.getX().toString();
        }
    }
    
    
    public String yLabel(DataPoint dataPoint) {
        if (yFormat != null) {
            return label(yFormat, dataPoint.getY());
        }
        else {
            return dataPoint.getY().toString();
        }
    }


    protected abstract Shape createArea(int x, int y);
    
    
    @Override
    void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }
    
    
    private String label(String format, Number number) {
        try {
            if (format.indexOf('%') >= 0) {
                Formatter formatter = new Formatter(Locale.getDefault());
                formatter.format(format, number);
                return formatter.toString();
            }
            else {
                java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, Locale.getDefault());
                return formatter.format(new Date(number.longValue()));
            }
        }
        catch (Exception ex) {
            Logger.getLogger(PointRenderer.class.getName()).log(Level.FINEST, format, ex);
            return number.toString();
        }
    }
    
    
    protected Color color = Color.BLACK;
    
    protected String xFormat;
    protected String yFormat;
    
}
