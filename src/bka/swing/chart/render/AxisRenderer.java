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


    public void setChartRenderer(ChartRenderer renderer) {
        chartRenderer = renderer;
        chartGeometry = chartRenderer.getChartGeometry();
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


    protected ChartRenderer.AxisPosition getXAxisPosition() {
        return chartRenderer.getXAxisPosition();
    }


    protected ChartRenderer.AxisPosition getYAxisPosition() {
        return chartRenderer.getYAxisPosition();
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
        return chartRenderer.xPixel(value);
    }
    
    
    protected int xMin() {
        return chartRenderer.areaLeft();
    }

    
    protected int xMax() {
        return chartRenderer.areaRight();
    }

    
    protected int x0() {
        int x = xPixel(0);
        if (chartRenderer.areaLeft() <= x && x <= chartRenderer.areaRight()) {
            return x;
        }
        return chartRenderer.areaLeft();
    }

    
    protected int yPixel(Number value) {
        return chartRenderer.yPixel(value);
    }


    protected int yMin() {
        return chartRenderer.areaBottom();
    }

    
    protected int yMax() {
        return chartRenderer.areaTop();
    }

    
    protected int y0() {
        int y = yPixel(0);
        if (chartRenderer.areaTop() <= y && y <= chartRenderer.areaBottom()) {
            return y;
        }
        return chartRenderer.areaBottom();
    }
    
    
    protected String xTitle;
    protected String yTitle;
    
    protected String xUnit;
    protected String yUnit;

    // Private attributes must be initialized by a ChartPanel.
    private ChartRenderer chartRenderer;
    private ChartGeometry chartGeometry;
    
}
