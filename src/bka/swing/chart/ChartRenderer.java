/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import bka.swing.chart.custom.*;
import bka.swing.chart.geometry.*;
import bka.swing.chart.grid.*;
import bka.swing.chart.render.*;
import java.awt.*;
import java.awt.print.*;
import java.util.*;
import java.util.logging.*;


public final class ChartRenderer implements java.awt.print.Printable {


    public enum AxisPosition { ORIGIN, MINIMUM, MAXIMUM }
    public enum GridMode { NONE, X, Y }


    public ChartRenderer(int leftMargin, int rightMargin, int topMargin, int bottomMargin) {
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
    }


    public void setLocale(Locale locale) {
        this.locale = Objects.requireNonNull(locale);
    }


    public void setGraph(Object key, Map<Number, Number> graph) {
        setChart(key, new ChartData(graph));
    }


    public void setGraphs(Map<Object, Map<Number, Number>> graphs) {
        Map<Object, ChartData<Number, Number>> charts = new LinkedHashMap<>();
        for (Map.Entry<Object, Map<Number, Number>> graph : graphs.entrySet()) {
            charts.put(graph.getKey(), new ChartData<>(graph.getValue()));
        }
        setCharts(charts);
    }

    
    public void setChart(Object key, ChartData<Number, Number> chart) {
        Map<Object, ChartData<Number, Number>> charts = new HashMap<>();
        charts.put(key, chart);
        setCharts(charts);
    }


    public void setCharts(Map<Object, ChartData<Number, Number>> charts) {
        getLogger().log(Level.FINE, "setGraphs {0}", charts);
        setData(charts);
    }

    
    public void setTitle(String title) {
        this.title = title;
    }
    
    
    public void setShowLegend(boolean showLegend) {
        this.showLegend = showLegend;
    }
    
    
    public void setXWindowMinimum(Number min) {
        if (! minEquals(xRange, min)) {
            xRange.setMin(min);
            invalidate();
        }
    }
    
    
    public void setXWindowMaximum(Number max) {
        if (! maxEquals(xRange, max)) {
            xRange.setMax(max);
            invalidate();
        }
    }
    
    
    public void setXWindow(Number min, Number max) {
        if (! rangeEquals(xRange, min, max)) {
            xRange.set(min, max);
            invalidate();
        }
    }
    
    
    public void setYWindowMinimum(Number min) {
        if (! minEquals(yRanges.getDefault(), min)) {
            yRanges.getDefault().setMin(min);
            invalidate();
        }
    }


    public void setYWindowMaximum(Number max) {
        if (! maxEquals(yRanges.getDefault(), max)) {
            yRanges.getDefault().setMax(max);
            invalidate();
        }
    }
    
    
    public void setYWindowBase(Number base) {
        if (! Objects.equals(yWindowBase, base)) {
            yWindowBase = base;
            invalidate();
        }
    }
    
    
    public void setYWindow(Number min, Number max) {
        if (! rangeEquals(yRanges.getDefault(), min, max)) {
            yRanges.getDefault().set(min, max);
            invalidate();
        }
    }


    public void setYWindow(Object key, Number min, Number max) {
        yRanges.put(key, min, max);
        invalidate();
    }

    
    public void setWindow(Number xMin, Number xMax, Number yMin, Number yMax) {
        if (! rangeEquals(xRange, xMin, xMax) || ! rangeEquals(yRanges.getDefault(), yMin, yMax)) {
            xRange.set(xMin, xMax);
            yRanges.getDefault().set(yMin, yMax);
            invalidate();
        }
    }


    private static boolean rangeEquals(Range range, Number min, Number max) {
        return minEquals(range, min) && maxEquals(range, max);
    }


    private static boolean minEquals(Range range, Number number) {
        return Objects.equals(range.getMin(), number);
    }


    private static boolean maxEquals(Range range, Number number) {
        return Objects.equals(range.getMax(), number);
    }


    public void setXGrid(Grid grid) {
        geometry.setXGrid(grid);
    }
    
    
    public void setYGrid(Grid grid) {
        geometry.setYGrid(grid);
    }


    public void setRenderer(Object key, AbstractDataAreaRenderer renderer) {
        if (renderer != null) {
            renderer.setChartRenderer(this);
            renderers.put(key, renderer);
        }
        else {
            renderers.remove(key);
        }
        invalidate();
    }
    

    public void setPointHighlightRenderer(Object key, DefaultPointHighlightRenderer pointHighlightRenderer) {
        if (pointHighlightRenderer != null) {
            if (pointHighlightRenderer.setBackground() == null) {
                pointHighlightRenderer.getBackground(getHighlightColor());
            }
        }
        pointHighlightRenderers.put(key, pointHighlightRenderer);
    }
    
    
    public void setHighlightFormat(Object key, String xFormat, String yFormat) {
        DefaultPointHighlightRenderer renderer = pointHighlightRenderers.get(key);
        if (renderer == null) {
            renderer = new DefaultPointHighlightRenderer();
            setPointHighlightRenderer(key, renderer);
        }
        renderer.setXFormat(xFormat);
        renderer.setYFormat(yFormat);
    }
    
    
    public void highlight(Point point) {
        highlight = findHighlight(point);
    }


    private Highlight findHighlight(Point point) {
        Map<Object, GraphGeometry<AreaGeometry>> graphGeometries = geometry.getGraphs();
        for (Map.Entry<Object, GraphGeometry<AreaGeometry>> entry : graphGeometries.entrySet()) {
            for (AreaGeometry dataAreaGeometry : entry.getValue().getDataPoints()) {
                if (dataAreaGeometry.getArea() != null && dataAreaGeometry.getArea().contains(point)) {
                    return new Highlight(pointHighlightRenderers.get(entry.getKey()), new HighlightGeometry(dataAreaGeometry, point));
                }
            }
        }
        return null;
    }


   public void setAxisRenderer(AxisRenderer axisRenderer) {
       this.axisRenderer = axisRenderer;
       if (axisRenderer != null) {
           axisRenderer.setChartRenderer(this);
       }
   }
   
   
   public void setAxisPositions(AxisPosition xAxisPosition, AxisPosition yAxisPosition) {
       this.xAxisPosition = xAxisPosition;
       this.yAxisPosition = yAxisPosition;
   }
   
   
   public void setGridRenderer(GridRenderer gridRenderer, GridMode gridMode) {
       this.gridRenderer = gridRenderer;
       this.gridMode = gridMode;
       if (gridRenderer != null) {
           gridRenderer.setChartRenderer(this);
       }
   }
   
   
   public void setGridMode(GridMode gridMode) {
       if (gridRenderer == null && gridMode != GridMode.NONE) {
           setGridRenderer(new DefaultGridRenderer(GridStyle.create(Color.WHITE)), gridMode);
       }
       else {
           this.gridMode = gridMode;
       }
   }
 
    
    public int areaLeft() {
        return chartArea().x;
    }


    public int areaRight() {
        Rectangle area = chartArea();
        return area.x + area.width;
    }


    public int areaWidth() {
        return chartArea().width;
    }


    public int areaTop() {
        return chartArea().y;
    }


    public int areaBottom() {
        Rectangle area = chartArea();
        return area.height + area.y;
    }


    public int areaHeight() {
        return chartArea().height;
    }


    public Number xValue(int xPixel) {
        synchronized (geometry) {
            return geometry.xValue(xPixel);
        }
    }


    public Number yValue(int yPixel) {
        synchronized (geometry) {
            return geometry.yValue(yPixel);
        }
    }


    public int xPixel(Number xValue) {
        synchronized (geometry) {
            return geometry.xPixel(xValue);
        }
    }


    public int yPixel(Number yValue) {
        synchronized (geometry) {
            return geometry.yPixel(yValue);
        }
    }


    public java.util.List<Number> xGridValues() {
        synchronized (geometry) {
            return Collections.unmodifiableList(geometry.getXGrid().getValues());
        }
    }


    public java.util.List<Number> yGridValues() {
        synchronized (geometry) {
            return Collections.unmodifiableList(geometry.getYGrid().getValues());
        }
    }


    public ChartGeometry getChartGeometry() {
        return geometry;
    }


    public AxisPosition getXAxisPosition() {
        return xAxisPosition;
    }


    public AxisPosition getYAxisPosition() {
        return yAxisPosition;
    }


    public GridMode getGridMode() {
        return gridMode;
    }


    public void invalidate() {
        synchronized (geometry) {
            geometry.invalidate();
        }
    }
    

    public void paint(Graphics2D g2d, Rectangle bounds) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        draw(g2d, bounds);
    }


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        draw((Graphics2D) graphics, rectangle(pageFormat));
        return java.awt.print.Printable.PAGE_EXISTS;
    }


    private static Rectangle rectangle(PageFormat pageFormat) {
        return new Rectangle(
            (int) pageFormat.getImageableX(),
            (int) pageFormat.getImageableY(),
            (int) pageFormat.getImageableWidth(),
            (int) pageFormat.getImageableHeight());
    }
    

    private void draw(Graphics2D g2d, Rectangle bounds) {
        synchronized (geometry) {
            setBounds(bounds);
            initializeGeometry();
            draw(g2d);
        }
    }


    private void setBounds(Rectangle bounds) {
        if (! bounds.equals(this.bounds)) {
            this.bounds = new Rectangle(bounds);
            geometry.invalidate();
        }
    }


    private void draw(Graphics2D g2d) {
        if (gridRenderer != null) {
            gridRenderer.draw(g2d);
        }
        drawData(g2d);
        drawAxises(g2d);
        if (showLegend) {
            DefaultLegendRenderer legendRenderer = new DefaultLegendRenderer(this, topMargin);
            legendRenderer.drawLegend(g2d, geometry, renderers);
        }
        drawTitle(g2d);
        if (highlight != null) {
            highlight.renderer.draw(g2d, highlight.geometry);
        }
    }


    private void drawData(Graphics2D g2d) {
        for (AbstractDataAreaRenderer.Layer layer : AbstractDataAreaRenderer.Layer.values()) {
            drawData(g2d, layer);
        }
    }

    
    private void drawData(Graphics2D g2d, AbstractDataAreaRenderer.Layer layer) {
        Map<Object, GraphGeometry<AreaGeometry>> graphs = geometry.getGraphs();
        LinkedList<Object> keyList = new LinkedList<>(graphs.keySet());
        Iterator it = keyList.descendingIterator(); // Descending iterator to draw first graph on top
        while (it.hasNext()) {
            Object key = it.next();
            AbstractDataAreaRenderer renderer = getRenderer(key);
            renderer.draw(g2d, layer, graphs.get(key));
        }
    }


    private void drawAxises(Graphics2D g2d) {
        if (axisRenderer != null) {
            if (geometry.getXMin() != null && geometry.getXMax() != null) {
                axisRenderer.drawXAxis(g2d, locale);
            }
            if (geometry.getYMin() != null && geometry.getYMax() != null) {
                axisRenderer.drawYAxis(g2d, locale);
            }
        }
    }

    
    private void drawTitle(Graphics2D g2d) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        if (title != null) {
            g2d.setColor(Color.BLACK);
            int width = fontMetrics.stringWidth(title);
            int x = areaLeft() + areaWidth() / 2 - width / 2;
            int y = areaTop() - fontMetrics.getAscent();
            g2d.drawString(title, x, y);
        }
    }


    private AbstractDataAreaRenderer getRenderer(Object key) {
        AbstractDataAreaRenderer renderer = renderers.get(key);
        if (renderer == null) {
            renderer = new OvalDotRenderer(DefaultDrawStyle.createSolid(Color.BLACK));
            renderers.put(key, renderer);
        }
        return renderer;
    }

    
    private Color getHighlightColor() {
        Color color = javax.swing.UIManager.getColor("Chart.highlightColor");
        return (color != null) ? color : Color.YELLOW;
    }

    
    private void setData(Map<Object, ChartData<Number, Number>> data) {
        synchronized (geometry) {
            geometry.setData(data, renderers);
            invalidate();
        }
    }
    
    
    private void initializeGeometry() {
        geometry.initialize(chartArea(), xRange, yRanges, yWindowBase);
    }

    
    public Rectangle chartArea() {
        return new Rectangle(
            bounds.x + leftMargin, 
            bounds.y + topMargin, 
            bounds.width - leftMargin - rightMargin, 
            bounds.height - topMargin - bottomMargin);
    }


    private static Logger getLogger() {
        return Logger.getLogger(ChartRenderer.class.getName());
    }


    private class Highlight {
        Highlight(DefaultPointHighlightRenderer renderer, HighlightGeometry geometry) {
            this.renderer = renderer;
            this.geometry = geometry;
        }
        private DefaultPointHighlightRenderer renderer;
        private HighlightGeometry geometry;
    }

    
    private final ChartGeometry geometry = new ChartGeometry();
    private Locale locale = Locale.ENGLISH;

    private String title;
    
    private AxisPosition xAxisPosition = AxisPosition.ORIGIN;
    private AxisPosition yAxisPosition = AxisPosition.ORIGIN;

    private GridMode gridMode = GridMode.NONE;
    
    private boolean showLegend;
    
    private final Range xRange = new Range(null, null);
    private final RangeMap yRanges = new RangeMap(null, null);
    private Number yWindowBase;
    
    private AxisRenderer axisRenderer;
    private GridRenderer gridRenderer;
    
    private final Map<Object, AbstractDataAreaRenderer> renderers = new HashMap<>();
    private final Map<Object, DefaultPointHighlightRenderer> pointHighlightRenderers = new HashMap<>();
    
    private Highlight highlight;
    
    private Rectangle bounds;

    // Margins in pixels
    private final int leftMargin;
    private final int rightMargin;
    private final int topMargin;
    private final int bottomMargin;
    
}
