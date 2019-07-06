/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.geometry;


import bka.swing.chart.*;
import bka.swing.chart.grid.*;
import bka.swing.chart.render.*;
import java.awt.*;
import java.util.*;
import java.util.logging.*;


public final class ChartGeometry {
    
    
    public void setData(Map<Object, ChartData<Number, Number>> dataMap, Map<Object, AbstractDataAreaRenderer> renderers) {
        this.dataMap = dataMap;
        this.renderers = renderers;
    }


    public void setRenderers(Map<Object, AbstractDataAreaRenderer> renderers) {
        this.renderers = renderers;
        for (AbstractDataAreaRenderer renderer : renderers.values()) {
            renderer.setChartGeometry(this);
        }
    }
    

    /**
     * Calculates pixels for all points in the data map. This method must be called whenever
     * the data map changes or the area to draw on is resized.
     * 
     * @param area: area to draw on
     * @param xWindowMin: start of the x-axis, null means minimum x-value of the data set
     * @param xWindowMax: end of the x-axis, null means maximum x-value of the data set
     * @param yWindowMin: start of the y-axis, null means minimum y-value of the data set
     * @param yWindowMax: end of the y-axis, null means maximum y-value of the data set
     * @param yWindowBase: x location where the y-axis is drawn, null means origin
     * @param locale: to convert numbers and dates to strings
     */
    public void initialize(Rectangle area, Number xWindowMin, Number xWindowMax, Number yWindowMin, Number yWindowMax, Number yWindowBase, Locale locale) {
        LOGGER.log(Level.FINE, "Initialize area {0}", area);
        this.area = area;
        graphs.clear();
        xMin = xWindowMin;
        xMax = xWindowMax;
        yMin = yWindowMin;
        yMax = yWindowMax;
        if (dataMap != null) {
            Window window = computeWindow(xWindowMin, xWindowMax, yWindowMin, yWindowMax);
            computeRanges(window, yWindowBase);
            computeDataPoints(window);
        }
        initializeGrids(locale);
    }


    private void computeRanges(Window window, Number yWindowBase) {
        if (xMin == null) {
            xMin = window.xMin;
        }
        if (xMax == null) {
            xMax = window.xMax;
        }
        if (yMin == null) {
            yMin = window.yMin;
        }
        if (yMax == null) {
            yMax = window.yMax;
        }
        if (yWindowBase != null) {
            if (yMin != null && value(yMin) > value(yWindowBase)) {
                yMin = yWindowBase;
            }
            if (yMax != null && value(yMax) < value(yWindowBase)) {
                yMax = yWindowBase;
            }
        }
    }

    
    public Map<Object, java.util.List<AreaGeometry>> getGraphs() {
        return graphs;
    }

    
    public Number getXMin() {
        return xMin;
    }

    
    public Number getXMax() {
        return xMax;
    }

    
    public Number getYMin() {
        return yMin;
    }

    
    public Number getYMax() {
        return yMax;
    }

    
    public void clear() {
        graphs.clear();
        dataMap = null;
    }
    
    
    public double xValue(int pixelX) {
        double ratio = xRange() / area.width;
        return (pixelX - area.x) * ratio + value(xMin);
    }

    
    public double yValue(int pixelY) {
        double ratio = yRange() / area.height;
        return (area.height - pixelY + area.y) * ratio + value(yMin);
    }
    
    
    public int xPixel(Number x) {
        return area.x + pixel(x, xMin, xRange(), area.width);
    }

    
    public int yPixel(Number y) {
        return area.height + area.y - pixel(y, yMin, yRange(), area.height);
    }


    public Grid getXGrid() {
        return xGrid;
    }


    public void setXGrid(Grid grid) {
        this.xGrid = grid;
    }


    public Grid getYGrid() {
        return yGrid;
    }


    public void setYGrid(Grid grid) {
        this.yGrid = grid;
    }


    private static int pixel(Number number, Number min, double range, int size) {
        if (number == null) {
            LOGGER.log(Level.SEVERE, "Cannot compute null pixel");
            throw new java.lang.IllegalArgumentException();
        }
        double ratio = size / range;
        long pixel = Math.round((value(number) - value(min)) * ratio);
        if (pixel < Integer.MIN_VALUE || Integer.MAX_VALUE < pixel) {
            LOGGER.log(Level.WARNING, "Pixel {0} out of range [{1}, {2}]", new Object[] { pixel, Integer.MIN_VALUE, Integer.MAX_VALUE });
        }
        return (int) pixel;
    }


    /**
     * Collect all points in x,y range and determine their min and max values for x and y.
     * return everything in a Dataset.Window object 
     */
    private Window computeWindow(Number xWindowMin, Number xWindowMax, Number yWindowMin, Number yWindowMax) {
        Window window = new Window();
        for (Map.Entry<Object, ChartData<Number, Number>> dataGraph : dataMap.entrySet()) {
            ChartData<Number, Number> graphPointsInWindow = new ChartData<>();
            window.points.put(dataGraph.getKey(), graphPointsInWindow);
            for (ChartDataElement<Number, Number> element : dataGraph.getValue()) {
                Number x = element.getKey();
                Number y = element.getValue();
                double valueX = value(x);
                double valueY = value(y);
                if (inRange(xWindowMin, xWindowMax, valueX) && inRange(yWindowMin, yWindowMax, valueY)) {
                    graphPointsInWindow.add(x, y);
                    if (window.xMin == null || valueX < value(window.xMin)) { 
                        window.xMin = x;
                    }
                    if (window.xMax == null || value(window.xMax) < valueX) {
                        window.xMax = x;
                    }
                    if (window.yMin == null || valueY < value(window.yMin)) {
                        window.yMin = y;
                    }
                    if (window.yMax == null || value(window.yMax) < valueY) {
                        window.yMax = y;
                    }
                }
            }
        }
        return window;
    }
    
    
    private static boolean inRange(Number min, Number max, double value) {
        return (min == null || value(min) <= value) && (max == null || value <= value(max));
    }

    
    /**
     * Compute render data for points in window
     */
    private void computeDataPoints(Window window) {
        for (Map.Entry<Object, ChartData<Number, Number>> map : window.points.entrySet()) {
            AbstractDataAreaRenderer renderer = renderers.get(map.getKey());
            if (renderer != null) {
                java.util.List<AreaGeometry> points = renderer.createGraphGeomerty(map.getValue());
                graphs.put(map.getKey(), points);
            }
        }
    }


    private void initializeGrids(Locale locale) {
        if (xGrid == null) {
            xGrid = new Grid();
        }
        xGrid.setLocale(locale);
        xGrid.initialize(xMin, xMax);
        if (yGrid == null) {
            yGrid = new Grid();
        }
        yGrid.setLocale(locale);
        yGrid.initialize(yMin, yMax);
    }

    
    private double xRange() {
        return value(xMax) - value(xMin);
    }

    
    private double yRange() {
        return value(yMax) - value(yMin);
    }

    
    private static double value(Number number) {
        return number.doubleValue();
    }
    
    
    private class Window {
        Map<Object, ChartData<Number, Number>> points = new LinkedHashMap<>();
        Number xMin, xMax, yMin, yMax;
    }

    
    private Grid xGrid = null;
    private Grid yGrid = null;

    
    private Rectangle area = null;
    
    private Map<Object, ChartData<Number, Number>> dataMap;
    private Map<Object, AbstractDataAreaRenderer> renderers;

    private final Map<Object, java.util.List<AreaGeometry>> graphs = new LinkedHashMap<>();

    private Number xMin = null;
    private Number xMax = null;
    private Number yMin = null;
    private Number yMax = null;

    private static final Logger LOGGER = Logger.getLogger(ChartGeometry.class.getName());
    
}
