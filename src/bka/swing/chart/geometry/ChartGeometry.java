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
        this.xWindowMin = xDataMin = xWindowMin;
        this.xWindowMax = xDataMax = xWindowMax;
        this.yWindowMin = yDataMin = yWindowMin;
        this.yWindowMax = yDataMax = yWindowMax;
        if (dataMap != null) {
            Window window = computeWindow();
            computeRanges(window, yWindowBase);
            computeDataPoints(window);
        }
        initializeGrids(locale);
    }


    private void computeRanges(Window window, Number yWindowBase) {
        if (yWindowBase != null) {
            if (yDataMin != null && yDataMin.doubleValue() > yWindowBase.doubleValue()) {
                yDataMin = yWindowBase;
            }
            if (yDataMax != null && yDataMax.doubleValue() < yWindowBase.doubleValue()) {
                yDataMax = yWindowBase;
            }
        }
    }

    
    public Map<Object, GraphGeometry<AreaGeometry>> getGraphs() {
        return graphs;
    }

    
    public Number getXMin() {
        return xDataMin;
    }

    
    public Number getXMax() {
        return xDataMax;
    }

    
    public Number getYMin() {
        return yDataMin;
    }

    
    public Number getYMax() {
        return yDataMax;
    }

    
    public void clear() {
        graphs.clear();
        dataMap = null;
    }


    public boolean isEmpty() {
        return dataMap == null && graphs.isEmpty();
    }
    
    
    public double xValue(int pixelX) {
        double ratio = xRange() / area.width;
        return (pixelX - area.x) * ratio + xDataMin.doubleValue();
    }

    
    public double yValue(int pixelY) {
        double ratio = yRange() / area.height;
        return (area.height - pixelY + area.y) * ratio + yDataMin.doubleValue();
    }
    
    
    public int xPixel(Number x) {
        return area.x + pixel(x, xDataMin, xRange(), area.width);
    }

    
    public int yPixel(Number y) {
        return area.height + area.y - pixel(y, yDataMin, yRange(), area.height);
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


    public Number getXWindowMin() {
        return xWindowMin;
    }


    public Number getXWindowMax() {
        return xWindowMax;
    }


    public Number getYWindowMin() {
        return yWindowMin;
    }


    public Number getYWindowMax() {
        return yWindowMax;
    }


    public Rectangle getArea() {
        return area;
    }


    private static int pixel(Number number, Number min, double range, int size) {
        Objects.requireNonNull(number, "Cannot compute null pixel");
        double ratio = size / range;
        long pixel = Math.round((number.doubleValue() - min.doubleValue()) * ratio);
        if (pixel < Integer.MIN_VALUE || Integer.MAX_VALUE < pixel) {
            LOGGER.log(Level.WARNING, "Pixel {0} out of range [{1}, {2}]", new Object[] { pixel, Integer.MIN_VALUE, Integer.MAX_VALUE });
        }
        return (int) pixel;
    }


    /**
     * Collect all points in x,y range and determine their min and max values.
     * return everything in a Dataset.Window object 
     */
    private Window computeWindow() {
        Window window = new Window();
        for (Map.Entry<Object, ChartData<Number, Number>> dataGraph : dataMap.entrySet()) {
            AbstractDataAreaRenderer renderer = renderers.get(dataGraph.getKey());
            if (renderer != null) {
                renderer.addPointsInWindow(dataGraph.getKey(), dataGraph.getValue(), window);
            }
        }
        return window;
    }
    
    
    /**
     * Compute render data for points in window
     */
    private void computeDataPoints(Window window) {
        for (Map.Entry<Object, ChartData<Number, Number>> map : window.entrySet()) {
            AbstractDataAreaRenderer renderer = renderers.get(map.getKey());
            if (renderer != null) {
                graphs.put(map.getKey(), renderer.createGraphGeomerty(map.getValue()));
            }
        }
    }


    private void initializeGrids(Locale locale) {
        if (xGrid == null) {
            xGrid = new Grid();
        }
        xGrid.setLocale(locale);
        xGrid.initialize(xDataMin, xDataMax);
        if (yGrid == null) {
            yGrid = new Grid();
        }
        yGrid.setLocale(locale);
        yGrid.initialize(yDataMin, yDataMax);
    }

    
    private double xRange() {
        return xDataMax.doubleValue() - xDataMin.doubleValue();
    }

    
    private double yRange() {
        return yDataMax.doubleValue() - yDataMin.doubleValue();
    }

    
    public class Window {

        private Window() {
        }

        public void putPoints(Object key, ChartData<Number, Number> graphPointsInWindow) {
            points.put(key, graphPointsInWindow);
        }


        public void setBounds(Number x, Number y) {
            if (xDataMin == null || x.doubleValue() < xDataMin.doubleValue()) {
                xDataMin = x;
            }
            if (xDataMax == null || xDataMax.doubleValue() < x.doubleValue()) {
                xDataMax = x;
            }
            if (yDataMin == null || y.doubleValue() < yDataMin.doubleValue()) {
                yDataMin = y;
            }
            if (yDataMax == null || yDataMax.doubleValue() < y.doubleValue()) {
                yDataMax = y;
            }
        }

        public boolean inXRange(Number x) {
            return inRange(x, xWindowMin, xWindowMax);
        }

        public boolean inYRange(Number y) {
            return inRange(y, yWindowMin, yWindowMax);
        }

        private boolean inRange(Number n, Number min, Number max) {
            return (min == null || min.doubleValue() <= n.doubleValue()) && (max == null || n.doubleValue() <= max.doubleValue());
        }

        private Set<Map.Entry<Object, ChartData<Number, Number>>> entrySet() {
            return points.entrySet();
        }

        private final Map<Object, ChartData<Number, Number>> points = new LinkedHashMap<>();
    }

    
    private Grid xGrid = null;
    private Grid yGrid = null;

    
    private Rectangle area = null;
    
    private Map<Object, ChartData<Number, Number>> dataMap;
    private Map<Object, AbstractDataAreaRenderer> renderers;

    private final Map<Object, GraphGeometry<AreaGeometry>> graphs = new LinkedHashMap<>();

    private Number xDataMin;
    private Number xDataMax;
    private Number yDataMin;
    private Number yDataMax;

    private Number xWindowMin;
    private Number xWindowMax;
    private Number yWindowMin;
    private Number yWindowMax;

    private static final Logger LOGGER = Logger.getLogger(ChartGeometry.class.getName());
    
}
