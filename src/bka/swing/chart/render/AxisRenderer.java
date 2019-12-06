/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.*;
import bka.swing.chart.geometry.*;
import bka.swing.chart.grid.*;
import java.util.*;


public abstract class AxisRenderer {
    
    
    public abstract void drawXAxis(java.awt.Graphics2D g2d, Locale locale);
    public abstract void drawYAxis(java.awt.Graphics2D g2d, Locale locale);


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


    protected java.util.List<Grid.MarkerList> xMarkerLists() {
        return getMarketLists(chartGeometry.getXGrid());
    }


    protected java.util.List<Grid.MarkerList> yMarkerLists() {
        return getMarketLists(chartGeometry.getYGrid());
    }


    private List<Grid.MarkerList> getMarketLists(Grid grid) {
        if (grid != null) {
            return grid.getMarkerLists();
        }
        return Collections.emptyList();
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
    
    
    protected String xTitle;
    protected String yTitle;
    
    protected String xUnit;
    protected String yUnit;

    // Private attributes must be initialized by a ChartPanel.
    private ChartPanel panel; 
    private ChartGeometry chartGeometry;
    
}
