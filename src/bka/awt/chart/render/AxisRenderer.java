/*
** Copyright © Bart Kampers
*/

package bka.awt.chart.render;


import bka.awt.chart.geometry.*;
import bka.chart.grid.*;
import java.util.*;


public abstract class AxisRenderer {
    
    
    public abstract void drawXAxis(java.awt.Graphics2D g2d, Locale locale);
    public abstract void drawYAxis(java.awt.Graphics2D g2d, Locale locale);


    public final void setChartRenderer(ChartRenderer renderer) {
        chartRenderer = renderer;
        chartGeometry = chartRenderer.getChartGeometry();
    }


    public final void setDrawXValues(boolean drawXValues) {
        this.drawXValues = drawXValues;
    }


    public final void setDrawYValues(boolean drawYValues) {
        this.drawYValues = drawYValues;
    }


    public final void setXTitle(String xTitle) {
        this.xTitle = xTitle;
    }


    public final void setYTitle(String yTitle) {
        this.yTitle = yTitle;
    }


    public final void setXUnit(String xUnit) {
        this.xUnit = xUnit;
    }


    public final void setYUnit(String yUnit) {
        this.yUnit = yUnit;
    }


    protected final boolean getDrawXValues() {
        return drawXValues;
    }


    protected final boolean getDrawYValues() {
        return drawYValues;
    }


    protected final String getXTitle() {
        return xTitle;
    }


    protected final String getYTitle() {
        return yTitle;
    }


    protected final String getXUnit() {
        return xUnit;
    }


    protected final String getYUnit() {
        return yUnit;
    }


    protected final ChartRenderer.AxisPosition getXAxisPosition() {
        return chartRenderer.getXAxisPosition();
    }


    protected final ChartRenderer.AxisPosition getYAxisPosition() {
        return chartRenderer.getYAxisPosition();
    }


    protected final java.util.List<Grid.MarkerList> xMarkerLists() {
        return getMarketLists(chartGeometry.getXGrid());
    }


    protected final java.util.List<Grid.MarkerList> yMarkerLists() {
        return getMarketLists(chartGeometry.getYGrid());
    }


    private List<Grid.MarkerList> getMarketLists(Grid grid) {
        if (grid != null) {
            return grid.getMarkerLists();
        }
        return Collections.emptyList();
    }


    protected final int xPixel(Number value) {
        return chartGeometry.xPixel(value);
    }
    
    
    protected final int xMin() {
        return chartRenderer.areaLeft();
    }

    
    protected final int xMax() {
        return chartRenderer.areaRight();
    }

    
    protected final int x0() {
        int x = xPixel(0);
        if (chartRenderer.areaLeft() <= x && x <= chartRenderer.areaRight()) {
            return x;
        }
        return chartRenderer.areaLeft();
    }

    
    protected final int yPixel(Number value) {
        return chartGeometry.yPixel(value);
    }


    protected final int yMin() {
        return chartRenderer.areaBottom();
    }

    
    protected final int yMax() {
        return chartRenderer.areaTop();
    }

    
    protected final int y0() {
        int y = yPixel(0);
        if (chartRenderer.areaTop() <= y && y <= chartRenderer.areaBottom()) {
            return y;
        }
        return chartRenderer.areaBottom();
    }


    private boolean drawXValues = true;
    private boolean drawYValues = true;
    
    private String xTitle;
    private String yTitle;
    
    private String xUnit;
    private String yUnit;

    private ChartRenderer chartRenderer;
    private ChartGeometry chartGeometry;
    
}
