/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.*;
import bka.swing.chart.geometry.*;
import bka.swing.chart.grid.*;


public abstract class AxisRenderer {
    
    
    public abstract void drawXAxis(java.awt.Graphics2D g2d);
    public abstract void drawYAxis(java.awt.Graphics2D g2d);


    public void setPanel(ChartPanel panel) {
        this.panel = panel;
        chartGeometry = panel.getChartGeometry();
    }


    public void setXTitle(String xTitle) {
        this.xTitle = xTitle;
    }


    public void setYTitle(String yTitle) {
        this.yTitle = yTitle;
    }


    public void setXUnit(String xUnit) {
        this.xUnit = xUnit;
    }


    public void setYUnit(String yUnit) {
        this.yUnit = yUnit;
    }


    protected ChartPanel.AxisPosition getXAxisPosition() {
        return panel.getXAxisPosition();
    }


    protected ChartPanel.AxisPosition getYAxisPosition() {
        return panel.getYAxisPosition();
    }


    protected String xGridLabel(Number value) {
        return chartGeometry.getXGrid().label(value);
    }


    protected java.util.List<Number> xGridValues() {
        java.util.List<Number> values = new java.util.ArrayList<>();
        Grid xGrid = chartGeometry.getXGrid();
        if (xGrid != null) {
            values.addAll(xGrid.getValues());
        }
        return values;
    }


    protected String yGridLabel(Number value) {
        return chartGeometry.getYGrid().label(value);
    }


    protected java.util.List<Number> yGridValues() {
        java.util.List<Number> values = new java.util.ArrayList<>();
        Grid yGrid = chartGeometry.getYGrid();
        if (yGrid != null) {
            values.addAll(yGrid.getValues());
        }
        return values;
    }


    protected int xPixel(Number value) {
        return panel.getChartGeometry().xPixel(value);
    }
    
    
    protected int xMin() {
        return panel.areaLeft();
    }

    
    protected int xMax() {
        return panel.areaRight();
    }

    
    protected int x0() {
        int x = xPixel(0);
        if (panel.areaLeft() <= x && x <= panel.areaRight()) {
            return x;
        }
        return panel.areaLeft();
    }

    
    protected int yPixel(Number value) {
        return panel.getChartGeometry().yPixel(value);
    }


    protected int yMin() {
        return panel.areaBottom();
    }

    
    protected int yMax() {
        return panel.areaTop();
    }

    
    protected int y0() {
        int y = yPixel(0);
        if (panel.areaTop() <= y && y <= panel.areaBottom()) {
            return y;
        }
        return panel.areaBottom();
    }
    
    
    protected String xTitle = null;
    protected String yTitle = null;
    
    protected String xUnit = null;
    protected String yUnit = null;

    
    // Private attributes must be initialized by a Panel.
    private ChartPanel panel; 
    private ChartGeometry chartGeometry;
    
}
