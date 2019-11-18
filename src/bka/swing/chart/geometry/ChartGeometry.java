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
        updateRenderers();
    }


    public void updateRenderers() {
        if (initialized()) {
            for (Map.Entry<Object, AbstractDataAreaRenderer> entry : renderers.entrySet()) {
                Window window = getWindow(entry.getKey());
                entry.getValue().setWindow(window);
            }
        }
    }


    private boolean initialized() {
        return area != null;
    }
    

    /**
     * Calculates pixels for all points in the data map. This method must be called whenever
     * the data map changes or the area to draw on is resized.
     * 
     * @param area: area to draw on
     * @param xRange: range of the x-axis
     * @param yRanges: ranges of the y-axis(es)
     * @param yWindowBase: x location where the y-axis is drawn, null means origin
     * @param locale: to convert numbers and dates to strings
     */
    public void initialize(Rectangle area, Range xRange ,RangeMap yRanges, Number yWindowBase, Locale locale) {
        if (! area.isEmpty()) {
            this.area = area;
            graphs.clear();
            xWindowRange = new Range(xRange);
            yWindowRanges = new RangeMap(yRanges);
            xDataRange = new Range(xRange);
            yDataRanges = new RangeMap(yRanges);
            if (dataMap != null) {
                computeWindows();
                computeRanges(yWindowBase);
                computeDataPoints();
                initializeGrids(locale);
            }
        }
    }


    private void computeRanges(Number yWindowBase) {
        if (yWindowBase != null) {
            for (Range range : yDataRanges.values()) {
                if (range.getMin() != null && range.getMin().doubleValue() > yWindowBase.doubleValue()) {
                    range.setMin(yWindowBase);
                }
                if (range.getMax() != null && range.getMax().doubleValue() < yWindowBase.doubleValue()) {
                    range.setMax(yWindowBase);
                }
            }
        }
    }

    
    public Map<Object, GraphGeometry<AreaGeometry>> getGraphs() {
        return graphs;
    }

    
    public Number getXMin() {
        return xDataRange.getMin();
    }

    
    public Number getXMax() {
        return xDataRange.getMax();
    }

    
    public Number getYMin() {
        return yDataRanges.getDefault().getMin();
    }

    
    public Number getYMax() {
        return yDataRanges.getDefault().getMax();
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
        return (pixelX - area.x) * ratio + xDataRange.getMin().doubleValue();
    }

    
    public double yValue(Object key, int pixelY) {
        double ratio = yRange(key) / area.height;
        return (area.height - pixelY + area.y) * ratio + yDataRanges.get(key).getMin().doubleValue();
    }
    
    
    public int xPixel(Number x) {
        double range = xRange();
        if (range == 0.0) {
            return area.x + area.width / 2;
        }
        return area.x + pixel(x, xDataRange.getMin(), range, area.width);
    }


    public int yPixel(Number y) {
        return yPixel(null, y);
    }

    
    private int yPixel(Object key, Number y) {
        double range = yRange(key);
        if (range == 0.0) {
            return area.y + area.height / 2;
        }
        return area.height + area.y - pixel(y, yDataRanges.get(key).getMin(), range, area.height);
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
        return xWindowRange.getMin();
    }


    public Number getXWindowMax() {
        return xWindowRange.getMax();
    }


    public Number getYWindowMin(Object key) {
        return yWindowRanges.get(key).getMin();
    }


    public Number getYWindowMax(Object key) {
        return yWindowRanges.get(key).getMax();
    }


    private static int pixel(Number number, Number min, double range, int size) {
        long pixel = Math.round((number.doubleValue() - min.doubleValue()) * (size / range));
        if (pixel < Integer.MIN_VALUE || Integer.MAX_VALUE < pixel) {
            getLogger().log(Level.WARNING, "Pixel {0} out of range [{1}, {2}]", new Object[] { pixel, Integer.MIN_VALUE, Integer.MAX_VALUE });
        }
        return (int) pixel;
    }


    /**
     * Collect all points in x,y range and determine their min and max values.
     * return everything in a Dataset.Window object 
     */
    private void computeWindows() {
        windows.clear();
        for (Map.Entry<Object, ChartData<Number, Number>> dataGraph : dataMap.entrySet()) {
            Object key = dataGraph.getKey();
            AbstractDataAreaRenderer renderer = renderers.get(key);
            if (renderer != null) {
                renderer.addPointsInWindow(key, dataGraph.getValue(), getWindow(key));
            }
        }
    }


    private Window getWindow(Object key) {
        if (! yWindowRanges.containsKey(key)) {
            return getFromWindows(null);
        }
        return getFromWindows(key);
    }

    
    private Window getFromWindows(Object key) {
        Window window = windows.get(key);
        if (window == null) {
            window = new Window(key);
            windows.put(key, window);
        }
        return window;
    }
    
    
    /**
     * Compute render data for points in window
     */
    private void computeDataPoints() {
        for (Object key : dataMap.keySet()) {
            AbstractDataAreaRenderer renderer = renderers.get(key);
            if (renderer != null) {
                Window window = getWindow(key);
                graphs.put(key, renderer.createGraphGeomerty(window.getPoints(key)));
            }
        }
    }


    private void initializeGrids(Locale locale) {
        if (xGrid == null) {
            xGrid = new Grid();
        }
        xGrid.setLocale(locale);
        xGrid.initialize(xDataRange.getMin(), xDataRange.getMax());
        if (yGrid == null) {
            yGrid = new Grid();
        }
        yGrid.setLocale(locale);
        yGrid.initialize(yDataRanges.getDefault().getMin(), yDataRanges.getDefault().getMax());
    }

    
    private double xRange() {
        return xDataRange.getMax().doubleValue() - xDataRange.getMin().doubleValue();
    }

    
    private double yRange(Object key) {
        return yDataRanges.get(key).getMax().doubleValue() - yDataRanges.get(key).getMin().doubleValue();
    }

    
    private static Logger getLogger() {
        return Logger.getLogger(ChartGeometry.class.getName());
    }


    public class Window {

        private Window(Object key) {
            this.key = key;
        }

        public void putPoints(Object key, ChartData<Number, Number> graphPointsInWindow) {
            points.put(key, graphPointsInWindow);
        }

        public void setBounds(Number x, Number y) {
            if (xDataRange.getMin() == null || x.doubleValue() < xDataRange.getMin().doubleValue()) {
                xDataRange.setMin(x);
            }
            if (xDataRange.getMax() == null || xDataRange.getMax().doubleValue() < x.doubleValue()) {
                xDataRange.setMax(x);
            }
            if (yDataRanges.get(key).getMin() == null || y.doubleValue() < yDataRanges.get(key).getMin().doubleValue()) {
                yDataRanges.get(key).setMin(y);
            }
            if (yDataRanges.get(key).getMax() == null || yDataRanges.get(key).getMax().doubleValue() < y.doubleValue()) {
                yDataRanges.get(key).setMax(y);
            }
        }

        public Rectangle getChartArea() {
            return area;
        }

        public int getYPixelBottom() {
            return yPixel(yWindowRanges.get(key).getMin());
        }

        public int getYPixelTop() {
            return yPixel(yWindowRanges.get(key).getMax());
        }

        public int xPixel(Number x) {
            return ChartGeometry.this.xPixel(x);
        }

        public int yPixel(Number y) {
            return ChartGeometry.this.yPixel(key, y);
        }

        public boolean inXRange(Number x) {
            return inRange(x, xWindowRange.getMin(), xWindowRange.getMax());
        }

        public boolean inYRange(Number y) {
            return inRange(y, yWindowRanges.get(key).getMin(), yWindowRanges.get(key).getMax());
        }

        private boolean inRange(Number n, Number min, Number max) {
            return (min == null || min.doubleValue() <= n.doubleValue()) && (max == null || n.doubleValue() <= max.doubleValue());
        }
        
        private ChartData<Number, Number> getPoints(Object key) {
            return points.get(key);
        }

        private final Object key;
        private final Map<Object, ChartData<Number, Number>> points = new LinkedHashMap<>();
    }

    
    private Grid xGrid;
    private Grid yGrid;

    
    private Rectangle area;
    
    private Map<Object, ChartData<Number, Number>> dataMap;
    private Map<Object, AbstractDataAreaRenderer> renderers;

    private final Map<Object, GraphGeometry<AreaGeometry>> graphs = new LinkedHashMap<>();
    private final Map<Object, Window> windows = new HashMap<>();

    private Range xDataRange;
    private RangeMap yDataRanges;

    private Range xWindowRange;
    private RangeMap yWindowRanges;

}
