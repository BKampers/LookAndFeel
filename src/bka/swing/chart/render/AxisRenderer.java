/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.ChartPanel;
import bka.swing.chart.grid.Demarcations;
import bka.swing.chart.geometry.ChartGeometry;


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


    protected String xDemarcationLabel(Number value) {
        return chartGeometry.getXDemarcations().label(value);
    }


    protected java.util.List<Number> xDemarcationValues() {
        java.util.List<Number> values = new java.util.ArrayList<>();
        Demarcations xDemarcations = chartGeometry.getXDemarcations();
        if (xDemarcations != null) {
            values.addAll(xDemarcations.getValues());
        }
        return values;
    }


    protected String yDemarcationLabel(Number value) {
        return chartGeometry.getYDemarcations().label(value);
    }


    protected java.util.List<Number> yDemarcationValues() {
        java.util.List<Number> values = new java.util.ArrayList<>();
        Demarcations yDemarcations = chartGeometry.getYDemarcations();
        if (yDemarcations != null) {
            values.addAll(yDemarcations.getValues());
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
        if (chartGeometry.getXMin() != null && chartGeometry.getXMax() != null) {
            return xPixel(0);
        }
        else {
            return panel.areaLeft();
        }
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
        if (chartGeometry.getYMin() != null && chartGeometry.getYMax() != null) {
            return yPixel(0);
        }
        else {
            return panel.areaBottom();
        }
    }
    
    
    protected String xTitle = null;
    protected String yTitle = null;
    
    protected String xUnit = null;
    protected String yUnit = null;

    
    // Private attributes must be initialized by a Panel.
    private ChartPanel panel; 
    private ChartGeometry chartGeometry;
    
}
