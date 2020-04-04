/*
** Copyright © Bart Kampers
*/

package bka.awt.chart.render;


import bka.awt.chart.*;
import bka.awt.chart.custom.*;
import bka.awt.chart.geometry.*;
import bka.chart.*;
import bka.chart.grid.*;
import java.awt.*;
import java.awt.print.*;
import java.util.*;
import java.util.logging.*;

/**
 * Renders charts of Number Maps on a Graphics2D.
 */
public final class ChartRenderer implements java.awt.print.Printable {


    public enum AxisPosition { ORIGIN, MINIMUM, MAXIMUM }
    public enum GridMode { NONE, X, Y }


    public void setChartDrawStyle(ChartDrawStyle chartDrawStyle) {
        this.chartDrawStyle = chartDrawStyle;
    }


    /**
     * Set margins around the graph area.
     * @param leftMargin
     * @param rightMargin
     * @param topMargin
     * @param bottomMargin
     */
    public void setMargins(int leftMargin, int rightMargin, int topMargin, int bottomMargin) {
        if (this.leftMargin != leftMargin || this.rightMargin != rightMargin || this.topMargin != topMargin || this.bottomMargin != bottomMargin) {
            this.leftMargin = leftMargin;
            this.rightMargin = rightMargin;
            this.topMargin = topMargin;
            this.bottomMargin = bottomMargin;
            chartArea = null;
        }
    }


    /**
     * Set the minimum distances for data points from edges of the graph area
     * @param leftOffset
     * @param rightOffset
     */
    public void setOffsets(int leftOffset, int rightOffset) {
        this.leftOffset = leftOffset;
        this.rightOffset = rightOffset;
    }


    public void setExpandToGrid(boolean xExpandToGrid, boolean yExpandToGrid) {
        this.xExpandToGrid = xExpandToGrid;
        this.yExpandToGrid = yExpandToGrid;
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
        if (! Objects.equals(xRange.getMin(), min)) {
            xRange.setMin(min);
            invalidate();
        }
    }
    
    
    public void setXWindowMaximum(Number max) {
        if (! Objects.equals(xRange.getMax(), max)) {
            xRange.setMax(max);
            invalidate();
        }
    }
    
    
    public void setXWindow(Number min, Number max) {
        if (! xRange.equals(min, max)) {
            xRange.set(min, max);
            invalidate();
        }
    }
    
    
    public void setYWindowMinimum(Number min) {
        if (! Objects.equals(yRanges.getDefault().getMin(), min)) {
            yRanges.getDefault().setMin(min);
            invalidate();
        }
    }


    public void setYWindowMaximum(Number max) {
        if (! Objects.equals(yRanges.getDefault().getMax(), max)) {
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
        if (! yRanges.getDefault().equals(min, max)) {
            yRanges.getDefault().set(min, max);
            invalidate();
        }
    }


    public void setYWindow(Object key, Number min, Number max) {
        yRanges.put(key, min, max);
        invalidate();
    }

    
    public void setWindow(Number xMin, Number xMax, Number yMin, Number yMax) {
        if (! xRange.equals(xMin, xMax) || ! yRanges.getDefault().equals(yMin, yMax)) {
            xRange.set(xMin, xMax);
            yRanges.getDefault().set(yMin, yMax);
            invalidate();
        }
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
            if (pointHighlightRenderer.getBackground() == null) {
                pointHighlightRenderer.setBackground(getChartDrawStyle().getHighlightBackground());
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
       this.xAxisRenderer = axisRenderer;
       this.yAxisRenderer = axisRenderer;
       if (axisRenderer != null) {
           axisRenderer.setChartRenderer(this);
       }
   }


   public void setAxisRenderers(AxisRenderer xAxisRenderer, AxisRenderer yAxisRenderer) {
       this.xAxisRenderer = xAxisRenderer;
       if (xAxisRenderer != null) {
           xAxisRenderer.setChartRenderer(this);
       }
       this.yAxisRenderer = yAxisRenderer;
       if (yAxisRenderer != null) {
           yAxisRenderer.setChartRenderer(this);
       }
   }


   public void setAxisPositions(AxisPosition xAxisPosition, AxisPosition yAxisPosition) {
       this.xAxisPosition = Objects.requireNonNull(xAxisPosition);
       this.yAxisPosition = Objects.requireNonNull(yAxisPosition);
   }
   
   
   public void setGridRenderer(GridRenderer gridRenderer, GridMode gridMode) {
       this.gridRenderer = gridRenderer;
       this.gridMode = gridMode;
       if (gridRenderer != null) {
           gridRenderer.setChartRenderer(this);
       }
   }
   
   
   public void setGridMode(GridMode gridMode) {
       this.gridMode = Objects.requireNonNull(gridMode);
       if (gridRenderer == null && gridMode != GridMode.NONE) {
           setGridRenderer(new DefaultGridRenderer(GridStyle.createSolid(getChartDrawStyle().getGridBackground())), gridMode);
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
        geometry.invalidate();
    }
    

    public void paint(Graphics2D g2d, Rectangle bounds) throws ChartDataException {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        draw(g2d, bounds);
    }


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        try {
            draw((Graphics2D) graphics, rectangle(pageFormat));
        }
        catch (ChartDataException ex) {
            Logger.getLogger(ChartRenderer.class.getName()).log(Level.SEVERE, "Unable to paint chart", ex);
        }
        return java.awt.print.Printable.PAGE_EXISTS;
    }


    private static Rectangle rectangle(PageFormat pageFormat) {
        return new Rectangle(
            (int) pageFormat.getImageableX(),
            (int) pageFormat.getImageableY(),
            (int) pageFormat.getImageableWidth(),
            (int) pageFormat.getImageableHeight());
    }
    

    private void draw(Graphics2D g2d, Rectangle bounds) throws ChartDataException {
        setBounds(bounds);
        geometry.initialize(new ChartGeometry.Layout(chartArea(),leftOffset, rightOffset, xRange, yRanges, yWindowBase));
        expandToGrid();
        draw(g2d);
    }


    private void expandToGrid() throws ChartDataException {
        Range expandedXRange = expandedXRange();
        RangeMap expandedYRanges = expandedYRanges();
        if (! xRange.equals(expandedXRange) || ! yRanges.getDefault().equals(expandedYRanges.getDefault())) {
            geometry.reinitialize(expandedXRange, expandedYRanges.getDefault());
        }
    }


    private Range expandedXRange() {
        if (! xExpandToGrid || geometry.getXGrid().getMarkerLists().isEmpty()) {
            return xRange;
        }
        java.util.List<Number> markerValues = geometry.getXGrid().getMarkerLists().get(0).getValues();
        if (markerValues.size() < 2) {
            return xRange;
        }
        Range expandedXRange = new Range(xRange);
        if (xRange.getMin() == null) {
            expandedXRange.setMin(markerValues.get(0));
        }
        if (xRange.getMax() == null) {
            expandedXRange.setMax(markerValues.get(markerValues.size() - 1));
        }
        return expandedXRange;
    }


    private RangeMap expandedYRanges() {
        if (! yExpandToGrid || geometry.getYGrid().getMarkerLists().isEmpty()) {
            return yRanges;
        }
        java.util.List<Number> markerValues = geometry.getYGrid().getMarkerLists().get(0).getValues();
        if (markerValues.size() < 2) {
            return yRanges;
        }
        RangeMap expandedYRanges = new RangeMap(yRanges);
        if (yRanges.getDefault().getMin() == null) {
            expandedYRanges.getDefault().setMin(markerValues.get(0));
        }
        if (yRanges.getDefault().getMax() == null) {
            expandedYRanges.getDefault().setMax(markerValues.get(markerValues.size() - 1));
        }
        return expandedYRanges;
    }


    public Rectangle chartArea() {
        if (chartArea == null) {
            chartArea = new Rectangle(
                bounds.x + leftMargin,
                bounds.y + topMargin,
                bounds.width - leftMargin - rightMargin,
                bounds.height - topMargin - bottomMargin);
        }
        return chartArea;
    }


   private void setBounds(Rectangle bounds) {
        if (! bounds.equals(this.bounds)) {
            this.bounds = new Rectangle(bounds);
            chartArea = null;
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
            legendRenderer.drawLegend(g2d, geometry, renderers, legendOrder());
        }
        drawTitle(g2d);
        if (highlight != null) {
            highlight.renderer.draw(g2d, highlight.geometry);
        }
    }


    private java.util.List<Object> legendOrder() {
        java.util.List<Object> keyOrder = new ArrayList<>();
        java.util.List<AbstractDataAreaRenderer> rendererOrder = new ArrayList<>();
        Set<Object> keySet = new LinkedHashSet<>(geometry.getGraphs().keySet());
        while (! keySet.isEmpty()) {
            Iterator<Object> it = keySet.iterator();
            while (it.hasNext()) {
                Object key = it.next();
                AbstractDataAreaRenderer renderer = renderers.get(key);
                int index = (renderer.getStackBase() == null) ? rendererOrder.size() : rendererOrder.indexOf(renderer.getStackBase());
                if (index >= 0) {
                    rendererOrder.add(index, renderer);
                    keyOrder.add(index, key);
                    it.remove();
                }
            }
        }
        return keyOrder;
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
        if (xAxisRenderer != null) {
            xAxisRenderer.drawXAxis(g2d, locale);
        }
        if (yAxisRenderer != null) {
            yAxisRenderer.drawYAxis(g2d, locale);
        }
    }

    
    private void drawTitle(Graphics2D g2d) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        if (title != null) {
            g2d.setColor(getChartDrawStyle().getTitleColor());
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


    private ChartDrawStyle getChartDrawStyle() {
        if (chartDrawStyle == null) {
            chartDrawStyle = new ChartDrawStyle();
        }
        return chartDrawStyle;
    }

    
    private void setData(Map<Object, ChartData<Number, Number>> data) {
        geometry.setData(data, renderers);
        invalidate();
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

    private ChartDrawStyle chartDrawStyle;
    private Locale locale = Locale.getDefault();

    private String title;
    
    private AxisPosition xAxisPosition = AxisPosition.ORIGIN;
    private AxisPosition yAxisPosition = AxisPosition.ORIGIN;

    private GridMode gridMode = GridMode.NONE;
    
    private boolean showLegend;
    
    private final Range xRange = new Range(null, null);
    private final RangeMap yRanges = new RangeMap(null, null);
    private Number yWindowBase;
    
    private AxisRenderer xAxisRenderer;
    private AxisRenderer yAxisRenderer;
    private GridRenderer gridRenderer;
    
    private final Map<Object, AbstractDataAreaRenderer> renderers = new HashMap<>();
    private final Map<Object, DefaultPointHighlightRenderer> pointHighlightRenderers = new HashMap<>();
    
    private Highlight highlight;
    
    private Rectangle bounds;
    private Rectangle chartArea;

    // Margins in pixels
    private int leftMargin;
    private int rightMargin;
    private int topMargin;
    private int bottomMargin;

    private int leftOffset;
    private int rightOffset;

    private boolean xExpandToGrid;
    private boolean yExpandToGrid;
    
}
