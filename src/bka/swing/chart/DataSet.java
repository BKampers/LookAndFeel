/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import java.awt.*;
import java.util.*;
import java.util.logging.*;


class DataSet {
    
    
    void setData(Map<Object, Map<Number, Number>> dataMap) {
        this.dataMap = dataMap;
    }
    

    /**
     * Calculates pixels for all points in the data map. This method must be called whenever
     * the data map changes or the area to draw on is resized.
     * 
     * @param area: area to draw on
     * @param xWindowMin: minimum of the x-axis, null means minimum x-value of the data set
     * @param xWindowMax: maximum of the x-axis, null means maximum x-value of the data set
     * @param yWindowMin: minimum of the y-axis, null means minimum y-value of the data set
     * @param yWindowMax: maximum of the y-axis, null means maximum y-value of the data set
     * @param locale: to convert numbers and dates to strings
     */
    void initialize(Rectangle area, Number xWindowMin, Number xWindowMax, Number yWindowMin, Number yWindowMax, Number yWindowBase, Locale locale) {
        logger.log(Level.FINE, "Initialize area {0}", area);
        this.area = area;
        graphs.clear();
        xMin = xWindowMin;
        xMax = xWindowMax;
        yMin = yWindowMin;
        yMax = yWindowMax;
        if (dataMap != null) {
            Window window = computeWindow(xWindowMin, xWindowMax, yWindowMin, yWindowMax);
            if (xMin == null) { xMin = window.xMin; }
            if (xMax == null) { xMax = window.xMax; }
            if (yMin == null) { yMin = window.yMin; }
            if (yMax == null) { yMax = window.yMax; }
            if (yWindowBase != null) {
                if (yMin != null && yMin.doubleValue() > yWindowBase.doubleValue()) { yMin = yWindowBase; }
                if (yMax != null && yMax.doubleValue() < yWindowBase.doubleValue()) { yMax = yWindowBase; }
            }
            computeDataPoints(window);
        }
        initializeDemarcations(locale);
    }

    
    Map<Object, TreeSet<DataPoint>> getGraphs() {
        return graphs;
    }

    
    Number getXMin() {
        return xMin;
    }

    
    Number getXMax() {
        return xMax;
    }

    
    Number getYMin() {
        return yMin;
    }

    
    Number getYMax() {
        return yMax;
    }

    
    void clear() {
        graphs.clear();
        dataMap = null;
    }
    
    
    double xValue(int pixelX) {
        double ratio = xRange() / area.width;
        return (pixelX - area.x) * ratio + value(xMin);
    }

    
    double yValue(int pixelY) {
        double ratio = yRange() / area.height;
        return (area.height - pixelY + area.y) * ratio + value(yMin);
    }
    
    
    int xPixel(Number x) /*throws Exception */{
        if (x == null) { 
            return -1;
        }
        double ratio = area.width / xRange();
        long pixel = Math.round((value(x) - value(xMin)) * ratio);
        if (pixel < Integer.MIN_VALUE || pixel > Integer.MAX_VALUE) {
            logger.log(Level.WARNING, "x pixel {0} out of range [{1}, {2}]\n", new Object[] { pixel, Integer.MIN_VALUE, Integer.MAX_VALUE });
        }
        return area.x + (int) pixel;
    }

    
    int yPixel(Number y) /*throws Exception */{
        int height = area.height;
        double ratio = height / yRange();
        long pixel = Math.round((value(y) - value(yMin)) * ratio);
        if (pixel < Integer.MIN_VALUE || pixel > Integer.MAX_VALUE) {
            logger.log(Level.WARNING, "y pixel {0} out of range [{1}, {2}]\n", new Object[] { pixel, Integer.MIN_VALUE, Integer.MAX_VALUE });
        }
        return area.height + area.y - (int) pixel;
    }

    
    /**
     * Select all points in window and determine ranges for x and y
     */
    private Window computeWindow(Number xWindowMin, Number xWindowMax, Number yWindowMin, Number yWindowMax) {
        Window window = new Window();
        for (Map.Entry<Object, Map<Number, Number>> dataGraph : dataMap.entrySet()) {
            Map<Number, Number> graphPointsInWindow = new HashMap<>();
            window.points.put(dataGraph.getKey(), graphPointsInWindow);
            for (Map.Entry<Number, Number> entry : dataGraph.getValue().entrySet()) {
                Number x = entry.getKey();
                Number y = entry.getValue();
                if (
                        (xWindowMin == null || value(xWindowMin) <= value(x)) &&
                        (xWindowMax == null || value(x) <= value(xWindowMax)) &&
                        (yWindowMin == null || value(yWindowMin) <= value(y)) &&
                        (yWindowMax == null || value(y) <= value(yWindowMax)))
                {
                    /** Point (x,y) is in window **/
                    graphPointsInWindow.put(x, y);
                    if (window.xMin == null || value(window.xMin) > value(x)) { window.xMin = x; }
                    if (window.xMax == null || value(window.xMax) < value(x)) { window.xMax = x; }
                    if (window.yMin == null || value(window.yMin) > value(y)) { window.yMin = y; }
                    if (window.yMax == null || value(window.yMax) < value(y)) { window.yMax = y; }
                }
            }
        }
        return window;
    }

    
    /**
     * Compute pixels for points in window
     */
    private void computeDataPoints(Window window) {
        for (Map.Entry<Object, Map<Number, Number>> map : window.points.entrySet()) {
            TreeSet<DataPoint> points = new TreeSet<>();
            graphs.put(map.getKey(), points);
            for (Map.Entry<Number, Number> entry : map.getValue().entrySet()) {
                Number x = entry.getKey();
                Number y = entry.getValue();
                points.add(new DataPoint(x, y, new Point(xPixel(x), yPixel(y))));
            }
        }
    }

    
    private void initializeDemarcations(Locale locale) {
        if (xDemarcations == null) {
            xDemarcations = new Demarcations();
        }
        xDemarcations.setLocale(locale);
        xDemarcations.initialize(xMin, xMax);
        if (yDemarcations == null) {
            yDemarcations = new Demarcations();
        }
        yDemarcations.setLocale(locale);
        yDemarcations.initialize(yMin, yMax);
    }

    
    private double xRange() {
        return value(xMax) - value(xMin);
    }

    
    private double yRange() {
        return value(yMax) - value(yMin);
    }

    
    private double value(Number number) {
        return number.doubleValue();
    }
    
    
    private class Window {
        Map<Object, Map<Number, Number>> points = new LinkedHashMap<>();
        Number xMin, xMax, yMin, yMax;
    }

    
    Demarcations xDemarcations = null;
    Demarcations yDemarcations = null;

    
    private Rectangle area = null;
    
    private Map<Object, Map<Number, Number>> dataMap;    
    private final Map<Object, TreeSet<DataPoint>> graphs = new LinkedHashMap<>();

    private Number xMin = null;
    private Number xMax = null;
    private Number yMin = null;
    private Number yMax = null;

    private static final Logger logger = Logger.getLogger(DataSet.class.getName());
    
}
