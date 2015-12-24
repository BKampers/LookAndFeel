/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.Color;
import java.util.*;


public abstract class PointRenderer extends AbstractDataPointRenderer {
    
    
    @Override
    public void draw(java.awt.Graphics2D g2d, DataPoint dataPoint) {
        PixelDataPoint pixelDataPoint = (PixelDataPoint) dataPoint;
        drawSymbol(g2d, pixelDataPoint.getPixel().x, pixelDataPoint.getPixel().y);
    }
    
    
    @Override
    public void draw(java.awt.Graphics2D g2d, DataPoint dataPoint, java.awt.Point location) {
        draw(g2d, dataPoint);
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
            return number.toString();
        }
    }
    
    
    protected Color color = Color.BLACK;
    
    protected String xFormat;
    protected String yFormat;
    
}
