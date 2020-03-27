/*
** Copyright © Bart Kampers
*/

package bka.awt.chart.geometry;


import bka.awt.chart.*;
import bka.awt.chart.render.*;
import bka.chart.*;
import bka.chart.grid.*;
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
    }


    /**
     * Mark as invalid so geometry will be recomputed when initializing.
     */
    public void invalidate() {
        graphs.clear();
    }


    /**
     * Calculates pixels for all points in the data map. This method must be called whenever
     * the data map changes or the area to draw on is resized.
     * 
     * @param area: area to draw on
     * @param xRange: range of the x-axis
     * @param yRanges: ranges of the y-axis(es)
     * @param yWindowBase: y location where the y-axis is drawn, null means origin
     */
    public void initialize(Layout layout) throws ChartDataException {
        if (graphs.isEmpty()) {
            this.layout = Objects.requireNonNull(layout);
            xDataRange = new Range(layout.xWindowRange);
            yDataRanges = new RangeMap(layout.yWindowRanges);
            computeWindows();
            computeRanges();
            computeDataPoints();
            initializeGrids();
        }
    }


    private void computeWindows() throws ChartDataException {
        windows.clear();
        if (dataMap != null) {
            for (Map.Entry<Object, ChartData<Number, Number>> dataGraph : dataMap.entrySet()) {
                Object key = dataGraph.getKey();
                AbstractDataAreaRenderer renderer = renderers.get(key);
                if (renderer != null) {
                    renderer.setWindow(getWindow(key));
                    renderer.addPointsInWindow(key, dataGraph.getValue());
                }
            }
        }
    }


    private void computeRanges() {
        if (layout.yWindowBase != null) {
            for (Range range : yDataRanges.values()) {
                range.adjust(layout.yWindowBase);
            }
        }
    }

    
    /**
     * Compute render data for points in window
     */
    private void computeDataPoints() throws ChartDataException {
        if (dataMap != null) {
            for (Object key : dataMap.keySet()) {
                AbstractDataAreaRenderer renderer = renderers.get(key);
                if (renderer != null) {
                    Window window = getWindow(key);
                    graphs.put(key, renderer.createGraphGeomerty(window.getPoints(key)));
                }
            }
        }
    }


    private void initializeGrids() {
        if (xGrid == null) {
            xGrid = new NumberGrid();
        }
        initializeGrid(xGrid, xDataRange);
        if (yGrid == null) {
            yGrid = new NumberGrid();
        }
        initializeGrid(yGrid, yDataRanges.getDefault());
    }


    private static void initializeGrid(Grid grid, Range range) {
        grid.initialize(range.getMin(), range.getMax());
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

    
    public double xValue(int pixelX) {
        double ratio = xRange() / (layout.area.width - offsetSum());
        return (pixelX - layout.area.x - leftOffset()) * ratio + xDataRange.getMin().doubleValue();
    }


    public double yValue(int pixelY) {
        return yValue(null, pixelY);
    }

    
    public double yValue(Object key, int pixelY) {
        return yValueByRange(yDataRanges.get(key), pixelY);
    }


    public double yValueByRange(Range range, int pixelY) {
        double ratio = size(range) / layout.area.height;
        return (layout.area.height - pixelY + layout.area.y) * ratio + range.getMin().doubleValue();
    }

    
    public int xPixel(Number x) {
        double range = xRange();
        if (range == 0.0) {
            return layout.area.x + layout.area.width / 2;
        }
        return layout.area.x + leftOffset() + pixel(x, xDataRange.getMin(), range, layout.area.width - offsetSum());
    }


    public int yPixel(Number y) {
        return yPixel(null, y);
    }

    
    private int yPixel(Object key, Number y) {
        double range = yRange(key);
        if (range == 0.0) {
            return layout.area.y + layout.area.height / 2;
        }
        return layout.area.height + layout.area.y - pixel(y, yDataRanges.get(key).getMin(), range, layout.area.height);
    }


    public Range getXDataRange() {
        return xDataRange;
    }


    public RangeMap getYDataRanges() {
        return new RangeMap(yDataRanges);
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
        return layout.xWindowRange.getMin();
    }


    public Number getXWindowMax() {
        return layout.xWindowRange.getMax();
    }


    public Number getYWindowMin(Object key) {
        return layout.yWindowRanges.get(key).getMin();
    }


    public Number getYWindowMax(Object key) {
        return layout.yWindowRanges.get(key).getMax();
    }


    private static int pixel(Number number, Number min, double range, int size) {
        long pixel = Math.round((number.doubleValue() - min.doubleValue()) * (size / range));
        if (pixel < Integer.MIN_VALUE || Integer.MAX_VALUE < pixel) {
            getLogger().log(Level.WARNING, "Pixel {0} out of range [{1}, {2}]", new Object[] { pixel, Integer.MIN_VALUE, Integer.MAX_VALUE });
        }
        return (int) pixel;
    }


    private Window getWindow(Object key) {
        if (! layout.yWindowRanges.containsKey(key)) {
            return getFromWindows(null);
        }
        return getFromWindows(key);
    }

    
    private Window getFromWindows(Object key) {
        return windows.computeIfAbsent(key, k -> new Window(k));
    }
    
    
    private double xRange() {
        return size(xDataRange);
    }

    
    private double yRange(Object key) {
        return size(yDataRanges.get(key));
    }


    private static double size(Range range) {
        if (! range.isInitialized()) {
            return Double.POSITIVE_INFINITY;
        }
        return range.getMax().doubleValue() - range.getMin().doubleValue();
    }


    public ChartData<Number, Number> getChartData(AbstractDataAreaRenderer renderer) {
        return dataMap.get(getKey(Objects.requireNonNull(renderer)));
    }


    private Object getKey(AbstractDataAreaRenderer renderer) {
        for (Map.Entry<Object, AbstractDataAreaRenderer> entry : renderers.entrySet()) {
            if (renderer.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException();
    }
    
    private static Logger getLogger() {
        return Logger.getLogger(ChartGeometry.class.getName());
    }


    private int offsetSum() {
        return leftOffset() + rightOffset();
    }


    private int leftOffset() {
        return (layout.xWindowRange.getMin() != null) ? 0 : layout.leftOffset;
    }


    private int rightOffset() {
        return (layout.xWindowRange.getMax() != null) ? 0 : layout.rightOffset;
    }


    public class Window {

        private Window(Object key) {
            this.key = key;
        }

        public void putPoints(Object key, ChartData<Number, Number> graphPointsInWindow) {
            points.put(key, graphPointsInWindow);
        }

        public void adjustBounds(Number x, Number y) {
            adjustXBounds(x);
            adjustYBounds(y);
        }

        public void adjustXBounds(Number x) {
            adjustBounds(xDataRange, x);
        }

        public void adjustYBounds(Number y) {
            adjustBounds(yDataRanges.get(key), y);
        }

        private void adjustBounds(Range range, Number value) {
            range.adjust(value);
        }

        public Rectangle getChartArea() {
            return layout.area;
        }

        public int getYPixelBottom() {
            return yPixel(yDataRanges.get(key).getMin());
        }

        public int getYPixelTop() {
            return yPixel(yDataRanges.get(key).getMax());
        }

        public int xPixel(Number x) {
            return ChartGeometry.this.xPixel(x);
        }

        public int yPixel(Number y) {
            return ChartGeometry.this.yPixel(key, y);
        }

        public boolean inXWindowRange(Number x) {
            return layout.xWindowRange.includes(x);
        }

        public boolean inYWindowRange(Number y) {
            return layout.yWindowRanges.get(key).includes(y);
        }
 
        private ChartData<Number, Number> getPoints(Object key) {
            return points.get(key);
        }

        private final Object key;
        private final Map<Object, ChartData<Number, Number>> points = new LinkedHashMap<>();
    }


    public static class Layout {

        public Layout(Rectangle area, int leftOffset, int rightOffset, Range xWindowRange, RangeMap yWindowRanges, Number yWindowBase) {
            this.area = area;
            this.leftOffset = leftOffset;
            this.rightOffset = rightOffset;
            this.xWindowRange = new Range(xWindowRange);
            this.yWindowRanges = new RangeMap(yWindowRanges);
            this.yWindowBase = yWindowBase;
        }

        private final Rectangle area;
        private final int leftOffset;
        private final int rightOffset;
        private final Range xWindowRange;
        private final RangeMap yWindowRanges;
        private final Number yWindowBase;
    }


    private final Map<Object, GraphGeometry<AreaGeometry>> graphs = new LinkedHashMap<>();
    private final Map<Object, Window> windows = new HashMap<>();

    private Layout layout;

    private Map<Object, ChartData<Number, Number>> dataMap;
    private Map<Object, AbstractDataAreaRenderer> renderers;

    private Grid xGrid;
    private Grid yGrid;

    private Range xDataRange;
    private RangeMap yDataRanges;

}
