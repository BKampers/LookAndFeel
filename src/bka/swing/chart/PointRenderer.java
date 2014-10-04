/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.Color;
import java.util.*;


public abstract class PointRenderer {
    
    
    public abstract void drawSymbol(java.awt.Graphics2D g2d, int x, int y);

    
    public void draw(java.awt.Graphics2D g2d, DataPoint dataPoint) {
        drawSymbol(g2d, dataPoint.getPixel().x, dataPoint.getPixel().y);
        
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
                Formatter formatter = new Formatter(chartPanel.getLocale());
                formatter.format(format, number);
                return formatter.toString();
            }
            else {
                java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, chartPanel.getLocale());
                return formatter.format(new Date(number.longValue()));
            }
        }
        catch (Exception ex) {
            return number.toString();
        }
    }
    
    
    protected Color color; // Must be initialized by Panel
    
    protected String xFormat = null;
    protected String yFormat = null;
    
    protected ChartPanel chartPanel;
    
}
