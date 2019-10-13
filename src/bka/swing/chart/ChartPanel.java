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
import javax.swing.*;


public class ChartPanel extends javax.swing.JPanel implements java.awt.print.Printable {
    
    public enum AxisPosition { ORIGIN, MINIMUM, MAXIMUM }
    public enum GridMode { NONE, X, Y }
    public enum DragZoomMode { NONE, X, Y, XY }
    public enum ClickZoomMode { NONE, DOUBLE_CLICK_GRID_AREA }

    
    public ChartPanel() {
        this(100, 100, 50, 50);
    }

    
    public ChartPanel(int leftMargin, int rightMargin, int topMargin, int bottomMargin) {
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
        selectionRectangleColor = UIManager.getColor("Chart.selectionRectangleColor");
        if (selectionRectangleColor == null) {
            selectionRectangleColor = Color.GRAY;
        }
        addListeners();
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
        LOGGER.log(Level.FINE, "setGraphs {0}", charts);
        setData(charts);
    }
    
    
    public void clearGraphs() {
        LOGGER.log(Level.FINE, "clearGraphs");
        if (! geometry.isEmpty()) {
            synchronized (geometry) {
                geometry.clear();
            }
            repaint();
        }
    }

    
    public void setTitle(String title) {
        this.title = title;
    }
    
    
    public void setShowLegend(boolean showLegend) {
        this.showLegend = showLegend;
    }
    
    
    public void setXWindowMinimum(Number min) {
        if (! Objects.equals(xWindowMin, min)) {
            synchronized (geometry) {
                xWindowMin = min;
                initializeGeometry();
            }
            repaint();
            notifyZoomListeners(min, null, null, null);
        }
    }
    
    
    public void setXWindowMaximum(Number max) {
        if (! Objects.equals(xWindowMax, max)) {
            synchronized (geometry) {
                xWindowMax = max;
                initializeGeometry();
            }
            repaint();
            notifyZoomListeners(null, max, null, null);
        }
    }
    
    
    public void setXWindow(Number min, Number max) {
        if (! Objects.equals(xWindowMin, min) && ! Objects.equals(xWindowMax, max)) {
            synchronized (geometry) {
                xWindowMin = min;
                xWindowMax = max;
                initializeGeometry();
            }
            repaint();
            notifyZoomListeners(min, max, null, null);
        }
    }
    
    
    public void setYWindowMinimum(Number min) {
        if (! Objects.equals(yWindowMin, min)) {
            synchronized (geometry) {
                yWindowMin = min;
                initializeGeometry();
            }
            repaint();
            notifyZoomListeners(null, null, min, null);
        }
    }
    
    
    public void setYWindowMaximum(Number max) {
        if (! Objects.equals(yWindowMax, max)) {
            synchronized (geometry) {
                yWindowMax = max;
                initializeGeometry();
            }
            repaint();
            notifyZoomListeners(null, null, null, max);
        }
    }
    
    
    public void setYWindowBase(Number base) {
        Number min = null;
        Number max = null;
        synchronized (geometry) {
            Number oldMin = geometry.getYMin();
            Number oldMax = geometry.getYMax();
            yWindowBase = base;
            initializeGeometry();
            Number newMin = geometry.getYMin();
            Number newMax = geometry.getYMax();
            if (oldMin != null && ! oldMin.equals(newMin)) {
                min = newMin;
            }
            if (oldMax != null && ! oldMax.equals(newMax)) { max = newMax; }
        }
        if (min != null || max != null) {
            repaint();
            notifyZoomListeners(null, null, min, max);        
        }
    }
    
    
    public void setYWindow(Number min, Number max) {
        if (! Objects.equals(yWindowMin, min) && ! Objects.equals(yWindowMax, max)) {
            synchronized (geometry) {
                yWindowMin = min;
                yWindowMax = max;
                initializeGeometry();
            }
            repaint();
            notifyZoomListeners(null, null, min, max);
        }
    }
    
    
    public void setWindow(Number xMin, Number xMax, Number yMin, Number yMax) {
        if (! Objects.equals(xWindowMin, xMin) || ! Objects.equals(xWindowMax, xMax) || ! Objects.equals(yWindowMin, yMin) || ! Objects.equals(yWindowMax, yMax)) {
            synchronized (geometry) {
                xWindowMin = xMin;
                xWindowMax = xMax;
                yWindowMin = yMin;
                yWindowMax = yMax;
                initializeGeometry();
            }
            repaint();
            notifyZoomListeners(xMin, xMax, yMin, yMax);
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
            renderer.setChartPanel(this);
            renderers.put(key, renderer);
        }
        else {
            renderers.remove(key);
        }
        geometry.setRenderers(renderers);
        initializeGeometry();
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
    
    
   public void setAxisRenderer(AxisRenderer axisRenderer) {
       this.axisRenderer = axisRenderer;
       if (axisRenderer != null) {
           axisRenderer.setPanel(this);
       }
   }
   
   
   public void setAxisPositions(AxisPosition xAxisPosition, AxisPosition yAxisPosition) {
       this.xAxisPosition = xAxisPosition;
       this.yAxisPosition = yAxisPosition;
   }
   
   
   public void setGridRenderer(GridRenderer renderer, GridMode mode) {
       this.gridRenderer = renderer;
       this.gridMode = mode;
       if (renderer != null) {
           renderer.setPanel(this);
       }
   }
   
   
   public void setGridMode(GridMode mode) {
       if (gridRenderer == null && mode != GridMode.NONE) {
           setGridRenderer(new DefaultGridRenderer(GridStyle.create(Color.WHITE)), mode);
       }
       else {
           this.gridMode = mode;
       }
   }
   
   
   public void setDragZoomMode(DragZoomMode dragZoomMode) {
       this.dragZoomMode = dragZoomMode;
   }
   
   
   public void setClickZoomMode(ClickZoomMode clickZoomMode) {
       this.clickZoomMode = clickZoomMode;
   }
   
   
    public void addZoomListener(ZoomListener listener) {
        synchronized (zoomListeners) {
            zoomListeners.add(listener);
        }
    }
    
    
    boolean removeZoomListener(ZoomListener listener) {
        synchronized (zoomListeners) {
            return zoomListeners.remove(listener);
        }
    }
    
    
    @Override
    public void invalidate() {
        initializeGeometry();
        super.invalidate();
    }
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        synchronized (geometry) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            draw(g2d);
            drawSelectionRectangle(g2d);
            if (highlightRenderer != null && nearestToMouse != null) {
                highlightRenderer.draw(g2d, nearestToMouse, mousePoint);
            }
        }
    }

    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        synchronized (geometry) {
            Graphics2D g2d = (Graphics2D) graphics;
            page = new Rectangle(
                (int) pageFormat.getImageableX(),
                (int) pageFormat.getImageableY(),
                (int) pageFormat.getImageableWidth(),
                (int) pageFormat.getImageableHeight());
            initializeGeometry();
            if (gridRenderer != null) {
                gridRenderer.draw(g2d);
            }
            draw(g2d);
            page = null;
            initializeGeometry();
        }
        return java.awt.print.Printable.PAGE_EXISTS;
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
    }


    private void drawData(Graphics2D g2d) {
        for (Map.Entry<Object, GraphGeometry<AreaGeometry>> entry : geometry.getGraphs().entrySet()) {
            AbstractDataAreaRenderer renderer = getRenderer(entry.getKey());
            renderer.draw(g2d, entry.getValue());
        }
    }


    private void drawAxises(Graphics2D g2d) {
        if (axisRenderer != null) {
            axisRenderer.drawXAxis(g2d);
            axisRenderer.drawYAxis(g2d);
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


    private void drawSelectionRectangle(Graphics2D g2d) {
        if (dragStartPoint != null && dragEndPoint != null) {
           g2d.setColor(selectionRectangleColor);
           int x = Math.min(dragStartPoint.x, dragEndPoint.x);
           int width = Math.max(dragStartPoint.x, dragEndPoint.x) - x;
           int y = Math.min(dragStartPoint.y, dragEndPoint.y);
           int height = Math.max(dragStartPoint.y, dragEndPoint.y) - y;
           g2d.drawRect(x, y, width, height);
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

    
    private synchronized void setData(Map<Object, ChartData<Number, Number>> data) {
        synchronized (geometry) {
            geometry.setData(data, renderers);
            initializeGeometry();
        }
        repaint();
    }
    
    
    private void initializeGeometry() {
        geometry.initialize(chartArea(), xWindowMin, xWindowMax, yWindowMin, yWindowMax, yWindowBase, getLocale());
    }

    
    private Rectangle chartArea() {
        if (page == null) {
            Rectangle bounds = getBounds();
            return new Rectangle(
                bounds.x + leftMargin, 
                bounds.y + topMargin, 
                bounds.width - leftMargin - rightMargin, 
                bounds.height - topMargin - bottomMargin);
        }
        else {
            // Prining
            return new Rectangle(
                page.x + LEFT_MARGIN_PRINT,
                page.y + TOP_MARGIN_PRINT, 
                page.width - LEFT_MARGIN_PRINT - RIGHT_MARGIN_PRINT,
                page.height - TOP_MARGIN_PRINT - BOTTOM_MARGIN_PRINT);
        }
    }
    
    
    private void notifyZoomListeners(Number xMin, Number xMax, Number yMin, Number yMax) {
        synchronized (zoomListeners) {
            for (ZoomListener listener : zoomListeners) {
                listener.zoomed(xMin, xMax, yMin, yMax);
            }
        }
    }
    
    
    private void addListeners() {
        addComponentListener(new ComponentAdapter());
        MouseAdapter mouseAdapter = new MouseAdapter();
        addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);
    }
    

    private boolean zoomEnabled() {
        return dragZoomMode != DragZoomMode.NONE || clickZoomMode != ClickZoomMode.NONE;
    }


    private class ComponentAdapter extends java.awt.event.ComponentAdapter {
        
        @Override
        public void componentResized(java.awt.event.ComponentEvent evt) {
            synchronized (geometry) {
                initializeGeometry();
            }
        }
        
    }


    private class MouseAdapter extends java.awt.event.MouseAdapter {

        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            mousePoint = evt.getPoint();
            if (chartArea().contains(mousePoint)) {
                if (defaultCursor == null && zoomEnabled()) {
                    defaultCursor = getCursor();
                    setCursor(new Cursor(ZOOM_CURSOR));
                }
                highlight();
            }
            else if (defaultCursor != null) {
                setCursor(defaultCursor);
                defaultCursor = null;
            }
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent evt) {
            if (dragZoomMode != DragZoomMode.NONE && evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                dragStartPoint = new Point(evt.getX(), evt.getY());
                switch (dragZoomMode) {
                    case X: 
                        dragStartPoint.x = Math.max(areaLeft(), dragStartPoint.x);
                        dragStartPoint.x = Math.min(areaRight(), dragStartPoint.x);
                        dragStartPoint.y = areaTop();
                        break;
                    case Y:
                        dragStartPoint.x = areaLeft();
                        dragStartPoint.y = Math.max(areaTop(), dragStartPoint.y);
                        dragStartPoint.y = Math.min(areaBottom(), dragStartPoint.y);
                        break;
                    case XY:
                        dragStartPoint.x = Math.max(areaLeft(), dragStartPoint.x);
                        dragStartPoint.x = Math.min(areaRight(), dragStartPoint.x);
                        dragStartPoint.y = Math.max(areaTop(), dragStartPoint.y);
                        dragStartPoint.y = Math.min(areaBottom(), dragStartPoint.y);
                        break;
                }
                repaint();
            }
        }
        
        @Override
        public void mouseDragged(java.awt.event.MouseEvent evt) {
            if (dragZoomMode != DragZoomMode.NONE && dragStartPoint != null) {
                dragEndPoint = new Point(evt.getX(), evt.getY());
                switch (dragZoomMode) {
                    case X: 
                        dragEndPoint.x = Math.max(areaLeft(), dragEndPoint.x);
                        dragEndPoint.x = Math.min(areaRight(), dragEndPoint.x);
                        dragEndPoint.y = areaBottom();
                        break;
                    case Y:
                        dragEndPoint.x = areaRight();
                        dragEndPoint.y = Math.max(areaTop(), dragEndPoint.y);
                        dragEndPoint.y = Math.min(areaBottom(), dragEndPoint.y);
                        break;
                    case XY:
                        dragEndPoint.x = Math.max(areaLeft(), dragEndPoint.x);
                        dragEndPoint.x = Math.min(areaRight(), dragEndPoint.x);
                        dragEndPoint.y = Math.max(areaTop(), dragEndPoint.y);
                        dragEndPoint.y = Math.min(areaBottom(), dragEndPoint.y);
                        break;
                }
                repaint();
            }
        }
        
        @Override
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            if (dragStartPoint != null && dragEndPoint != null) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                    switch (dragZoomMode) {
                        case X:
                            setXWindow(xMin(), xMax());
                            break;
                        case Y:
                            setYWindow(yMin(), yMax());
                            break;
                        case XY:
                            setWindow(xMin(), xMax(), yMin(), yMax());
                            break;
                    }
                }
                dragStartPoint = null;
                dragEndPoint = null;
            }
        }

        private Number xMin() {
            return geometry.xValue(Math.min(dragStartPoint.x, dragEndPoint.x));
        }

        private Number xMax() {
            return geometry.xValue(Math.max(dragStartPoint.x, dragEndPoint.x));
        }

        private Number yMin(){
            return geometry.yValue(Math.max(dragStartPoint.y, dragEndPoint.y));
        }

        private Number yMax() {
            return geometry.yValue(Math.min(dragStartPoint.y, dragEndPoint.y));
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            Point point = evt.getPoint();
            if (clickZoomMode == ClickZoomMode.DOUBLE_CLICK_GRID_AREA && chartArea().contains(point)) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
                    if (gridMode == GridMode.X || gridMode == GridMode.Y) {
                        zoom(point);
                    }
                }
            }
        }
        
        private void highlight() {
            AreaGeometry nearest = findContainingArea();
            if (nearestToMouse != nearest) {
                nearestToMouse = nearest;
                repaint();
            }
        }

        private AreaGeometry findContainingArea() {
            synchronized (geometry) {
                Map<Object, GraphGeometry<AreaGeometry>> graphGeometries = geometry.getGraphs();
                for (Map.Entry<Object, GraphGeometry<AreaGeometry>> entry : graphGeometries.entrySet()) {
                    for (AreaGeometry dataAreaGeometry : entry.getValue().getDataPoints()) {
                        if (dataAreaGeometry.getArea() != null && dataAreaGeometry.getArea().contains(mousePoint)) {
                            highlightRenderer = pointHighlightRenderers.get(entry.getKey());
                            return dataAreaGeometry;
                        }
                    }
                }
            }
            highlightRenderer = null;
            return null;
        }

        private void zoom(Point point) {
            boolean xMode = gridMode == GridMode.X;
            java.util.List<Number> values = (xMode) ? geometry.getXGrid().getValues() : geometry.getYGrid().getValues();
            if (values.size() >= 2) {
                Number min = values.get(0);
                boolean zoomed = false;
                int i = 1;
                while (! zoomed && i < values.size()) {
                    Number max = values.get(i);
                    if (xMode && geometry.xPixel(min) <= point.x && point.x <= geometry.xPixel(max)) {
                        setXWindow(min, max);
                        zoomed = true;
                    }
                    else if (! xMode && geometry.yPixel(min) <= point.y && point.y <= geometry.yPixel(max)) {
                        setYWindow(min, max);
                        zoomed = true;
                    }
                    else {
                        min = max;
                        i++;
                    }
                }
            }
        }
               
        private Cursor defaultCursor;
        
    };

    
    private final ChartGeometry geometry = new ChartGeometry();


    private Color selectionRectangleColor;
    
    private String title;
    
    private AxisPosition xAxisPosition = AxisPosition.ORIGIN;
    private AxisPosition yAxisPosition = AxisPosition.ORIGIN;

    private GridMode gridMode = GridMode.NONE;
    
    private boolean showLegend;
        
    private DragZoomMode dragZoomMode = DragZoomMode.NONE;
    private ClickZoomMode clickZoomMode = ClickZoomMode.NONE;
    
    private Number xWindowMin;
    private Number xWindowMax;
    private Number yWindowMin;
    private Number yWindowMax;
    private Number yWindowBase;

    
    private AxisRenderer axisRenderer;
    private GridRenderer gridRenderer;
    
    private final Map<Object, AbstractDataAreaRenderer> renderers = new HashMap<>();
    private final Map<Object, DefaultPointHighlightRenderer> pointHighlightRenderers = new HashMap<>();
    
    private Point dragStartPoint;
    private Point dragEndPoint;

    private DefaultPointHighlightRenderer highlightRenderer;
    private AreaGeometry nearestToMouse;
    private Point mousePoint;
    
    private Rectangle page;

    // Margins in pixels
    private final int leftMargin;
    private final int rightMargin;
    private final int topMargin;
    private final int bottomMargin;
    

    private final ArrayList<ZoomListener> zoomListeners = new ArrayList<>();

    private static final int ZOOM_CURSOR = Cursor.HAND_CURSOR;
    
    private static final int LEFT_MARGIN_PRINT = 60;
    private static final int RIGHT_MARGIN_PRINT = 5;
    private static final int TOP_MARGIN_PRINT = 40;
    private static final int BOTTOM_MARGIN_PRINT = 20;

    
    private static final Logger LOGGER = Logger.getLogger(ChartPanel.class.getName());

}
