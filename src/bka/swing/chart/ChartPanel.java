/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.*;
import java.util.logging.*;
import javax.swing.UIManager;


public class ChartPanel extends javax.swing.JPanel implements java.awt.print.Printable {
    
    @Deprecated public enum Style { POINT, LINE, POINT_AND_LINE, PIE }
    public enum AxisPosition { ORIGIN, MINIMUM, MAXIMUM }
    public enum DemarcationMode { NONE, X, Y }
    public enum DragZoomMode { NONE, X, Y, XY }
    public enum ClickZoomMode { NONE, DOUBLE_CLICK_DEMARCATION }

    
    public ChartPanel() {
        selectionRectangleColor = UIManager.getColor("Chart.selectionRectangleColor");
        if (selectionRectangleColor == null) {
            selectionRectangleColor = Color.GRAY;
        }
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                synchronized (dataSet) {
                    initializeDataSet();
                }
            }
        });
        addMouseMotionListener(MOUSE_ADAPTER);
        addMouseListener(MOUSE_ADAPTER);
    }
    
    
    public ChartPanel(int leftMargin, int rightMargin, int topMargin, int bottomMargin) {
        this();
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;
        this.topMargin = topMargin;
        this.bottomMargin = bottomMargin;
    }
    

    public void setGraph(Object key, Map graph) {
        Map<Object, Map> graphs = new HashMap<>();
        graphs.put(key, graph);
        setGraphs(graphs);
    }
    
    
    public void setGraphs(Map<Object, Map> graphs) {
        Map<Object, Map<Number, Number>> data = new LinkedHashMap<>();
        for (Map.Entry<Object, Map> graph : graphs.entrySet()) {
            Map<Number, Number> numbers = new HashMap<>();
            for (Object point : graph.getValue().entrySet()) {
                Object x = ((Map.Entry) point).getKey();
                Object y = ((Map.Entry) point).getValue();
                if (x instanceof Number && y instanceof Number) {
                    numbers.put((Number) x, (Number) y);
                }
                else {
                    throw new ClassCastException("ChartPanel needs Map containing Numbers only");
                }
            }
            data.put(graph.getKey(), numbers);
        }
        setData(data);
    }
    
    
    public void clearGraphs() {
        synchronized (dataSet) {
            dataSet.clear();
        }
        repaint();
    }

    
    public void setTitle(String title) {
        this.title = title;
    }
    
    
    public void setXTitle(String title) {
        getAxisRenderer().setXTitle(title);
    }
    
    
    public void setYTitle(String title) {
        getAxisRenderer().setYTitle(title);
    }
    
    
    public void setXUnit(String unit) {
        getAxisRenderer().setXUnit(unit);
    }
    
    
    public void setYUnit(String unit) {
        getAxisRenderer().setYUnit(unit);
    }
    
    
    public void setShowLegend(boolean showLegend) {
        this.showLegend = showLegend;
    }
    
    
    public void setXWindowMinimum(Number min) {
        synchronized (dataSet) {
            xWindowMin = min;
            initializeDataSet();
        }
        repaint();
        notifyZoomListeners(min, null, null, null);
    }
    
    
    public void setXWindowMaximum(Number max) {
        synchronized (dataSet) {
            xWindowMax = max;
            initializeDataSet();
        }
        repaint();
        notifyZoomListeners(null, max, null, null);
    }
    
    
    public void setXWindow(Number min, Number max) {
        synchronized (dataSet) {
            xWindowMin = min;
            xWindowMax = max;
            initializeDataSet();
        }
        repaint();
        notifyZoomListeners(min, max, null, null);
    }
    
    
    public void setYWindowMinimum(Number min) {
        synchronized (dataSet) {
            yWindowMin = min;
            initializeDataSet();
        }
        repaint();
        notifyZoomListeners(null, null, min, null);
    }
    
    
    public void setYWindowMaximum(Number max) {
        synchronized (dataSet) {
            yWindowMax = max;
            initializeDataSet();
        }
        repaint();
        notifyZoomListeners(null, null, null, max);
    }
    
    
    public void setYWindowBase(Number base) {
        Number min = null;
        Number max = null;
        synchronized (dataSet) {
            Number oldMin = dataSet.getYMin();
            Number oldMax = dataSet.getYMax();
            yWindowBase = base;
            initializeDataSet();
            Number newMin = dataSet.getYMin();
            Number newMax = dataSet.getYMax();
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
        synchronized (dataSet) {
            yWindowMin = min;
            yWindowMax = max;
            initializeDataSet();
        }
        repaint();
        notifyZoomListeners(null, null, min, max);
    }
    
    
    public void setWindow(Number xMin, Number xMax, Number yMin, Number yMax) {
        synchronized (dataSet) {
            xWindowMin = xMin;
            xWindowMax = xMax;
            yWindowMin = yMin;
            yWindowMax = yMax;
            initializeDataSet();
        }        
        repaint();
        notifyZoomListeners(xMin, xMax, yMin, yMax);
    }
    
    
    public void setXDemarcations(Demarcations xDemarcations) {
        dataSet.xDemarcations = xDemarcations;
    }
    
    
    public void setYDemarcations(Demarcations yDemarcations) {
        dataSet.yDemarcations = yDemarcations;
    }
    
    
    @Deprecated
   public void setStyle(Object key, Style style) {
       styles.put(key, style);
   }
   
   
   public void setRenderer(Object key, AbstractDataPointRenderer renderer) {
       renderers.put(key, renderer);
   }
   
   
   @Deprecated
    public void setPointRenderer(Object key, PointRenderer pointRenderer) {
        if (pointRenderer.getColor() == null) {
            pointRenderer.setColor(getPointColor(key));
        }
        pointRenderer.setChartPanel(this);
        pointRenderers.put(key, pointRenderer);
    }
    
    
    @Deprecated
    public void setPointColor(Object key, Color color) {
        pointColors.put(key, color);
    }
    
    
    @Deprecated
    public void setLineRenderer(Object key, LineRenderer lineRenderer) {
        if (lineRenderer.getColor() == null) {
            lineRenderer.setColor(getLineColor(key));
        }
        lineRenderers.put(key, lineRenderer);
    }
    
    
    @Deprecated
    public void setPieSectorRenderer(Object key, PieSectorRenderer pieSectorRenderer) {
        if (pieSectorRenderer.getPalette() == null) {
            pieSectorRenderer.setPalette(getPiePalette(key));
        }
        pieSectorRenderers.put(key, pieSectorRenderer);
    } 
    
    
    public void setPointHighlightRenderer(Object key, PointRenderer pointHighlightRenderer) {
        if (pointHighlightRenderer != null) {
            if (pointHighlightRenderer.getColor() == null) {
                pointHighlightRenderer.setColor(getHighlightColor());
            }
            pointHighlightRenderer.setChartPanel(this);
        }
        pointHighlightRenderers.put(key, pointHighlightRenderer);
    }
    
    
    public void setHighlightFormat(Object key, String xFormat, String yFormat) {
        PointRenderer highlightRenderer = pointHighlightRenderers.get(key);
        if (highlightRenderer == null) {
            highlightRenderer = new DefaultPointHighlightRenderer();
            setPointHighlightRenderer(key, highlightRenderer);
        }
        highlightRenderer.setXFormat(xFormat);
        highlightRenderer.setYFormat(yFormat);
    }
    
    
    public void setLineColor(Object key, Color color) {
        lineColors.put(key, color);
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
   
   
   public void setDemarcations(DemarcationRenderer demarcationRenderer, DemarcationMode demarcationMode) {
       this.demarcationRenderer = demarcationRenderer;
       this.demarcationMode = demarcationMode;
       if (demarcationRenderer != null) {
           demarcationRenderer.setPanel(this);
       }
   }
   
   
   public void setDemarcationMode(DemarcationMode demarcationMode) {
       if (demarcationRenderer == null && demarcationMode != DemarcationMode.NONE) {
           setDemarcations(new DefaultDemarcationRenderer(), demarcationMode);
       }
       else {
           this.demarcationMode = demarcationMode;
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
        initializeDataSet();
        super.invalidate();
    }
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        synchronized (dataSet) {
            Graphics2D g2d = (Graphics2D) g;
            DataPointInterface highlightPoint = null;
            PointRenderer highlightRenderer = null;
            if (demarcationRenderer != null) {
                demarcationRenderer.draw(g2d);
            }
            boolean showAxes = false;
            for (Map.Entry<Object, TreeSet<DataPointInterface>> entry : dataSet.getGraphs().entrySet()) {
                Object key = entry.getKey();
                TreeSet<DataPointInterface> graph = entry.getValue();
                AbstractDataPointRenderer renderer = getRenderer(key);
                renderer.reset(this, graph);
                for (DataPointInterface dataPoint : graph) {
                    renderer.draw(g2d, dataPoint);
                }
                Style style = getStyle(key);
                if (style != Style.PIE) {
                    showAxes = true;
                }
                for (DataPointInterface dataPoint : graph) {
                    PointRenderer pointHighlightRenderer = pointHighlightRenderers.get(key);
                    if (pointHighlightRenderer != null && dataPoint.equals(nearestToMouse)) {
                        highlightPoint = dataPoint;
                        highlightRenderer = pointHighlightRenderer;
                    }
                }
            }
            if (showAxes) {
                getAxisRenderer().drawXAxis(g2d);
                getAxisRenderer().drawYAxis(g2d);
            }
            if (showLegend) {
                drawLegend(g2d);
            }
            drawSelectionRectangle(g2d);
            if (highlightRenderer != null && highlightPoint != null) {
                highlightRenderer.draw(g2d, highlightPoint);
            }
            drawTitle(g2d);
        }
    }
    
    
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        synchronized (dataSet) {
            Graphics2D g2d = (Graphics2D) graphics;
            page = new Rectangle(
                (int) pageFormat.getImageableX(),
                (int) pageFormat.getImageableY(),
                (int) pageFormat.getImageableWidth(),
                (int) pageFormat.getImageableHeight());
            initializeDataSet();
            if (demarcationRenderer != null) {
                demarcationRenderer.draw(g2d);
            }
            getAxisRenderer().drawXAxis(g2d);
            getAxisRenderer().drawYAxis(g2d);
            for (Map.Entry<Object, TreeSet<DataPointInterface>> entry : dataSet.getGraphs().entrySet()) {
                Object key = entry.getKey();
                Style style = getStyle(key);
                TreeSet<DataPointInterface> graph = entry.getValue();
                DataPointInterface previous = null;
                for (DataPointInterface dataPoint : graph) {
                    if (style == Style.POINT || style == Style.POINT_AND_LINE) {
                        PointRenderer pointRenderer = getPointRenderer(key);
                        pointRenderer.draw(g2d, dataPoint);
                    }
                    if (previous != null && (style == Style.LINE || style == Style.POINT_AND_LINE)) {
                        LineRenderer lineRenderer = getLineRenderer(key);
                        lineRenderer.draw(g2d, previous, dataPoint);
                    }
                    previous = dataPoint;
                }
            }
            drawTitle(g2d);
            page = null;
            initializeDataSet();
        }
        return java.awt.print.Printable.PAGE_EXISTS;
    }
    

    int areaLeft() {
        return chartArea().x;
    }
    
    
    int areaRight() {
        return chartArea().x + chartArea().width;
    }
    
    
    int areaWidth() {
        return chartArea().width;
    }
    
    
    int areaTop() {
        return chartArea().y;
    }
    
    
    int areaBottom() {
        return chartArea().height + chartArea().y;
    }
    
    
    int areaHeight() {
        return chartArea().height;
    }
    
    
    int xRangePixel(Number x) {
        if (x == null || xRangeMin == null || xRangeMax == null) { 
            return -1;
        }
        double ratio = chartArea().width / (xRangeMax.doubleValue() - xRangeMin.doubleValue());
        long pixel = Math.round((x.doubleValue() - xRangeMin.doubleValue()) * ratio);
        if (pixel < Integer.MIN_VALUE || pixel > Integer.MAX_VALUE) {
            logger.log(Level.WARNING, "x pixel {0} out of range [{1}, {2}]", new Object[] { pixel, Integer.MIN_VALUE, Integer.MAX_VALUE });
        }
        return areaLeft() + (int) pixel;
    }

    
    int yRangePixel(Number y) {
        if (y == null || yRangeMin == null || yRangeMax == null) { 
            return -1;
        }
        double ratio = chartArea().height / (yRangeMax.doubleValue() - yRangeMin.doubleValue());
        long pixel = Math.round((y.doubleValue() - yRangeMin.doubleValue()) * ratio);
        if (pixel < Integer.MIN_VALUE || pixel > Integer.MAX_VALUE) {
            logger.log(Level.WARNING, "y pixel {0} out of range [{1}, {2}]", new Object[] { pixel, Integer.MIN_VALUE, Integer.MAX_VALUE });
        }
        return areaBottom() - (int) pixel;
    }

    
    double xRangeValue(int pixelX) {
        if (xRangeMin != null && xRangeMax != null) {
            Rectangle area = chartArea();
            double ratio = (xRangeMax.doubleValue() - xRangeMin.doubleValue()) / area.width;
            return (pixelX - area.x) * ratio + xRangeMin.doubleValue();
        }
        else {
            return 0.0;
        }
    }

    
    double yRangeValue(int pixelY) {
        if (yRangeMin != null && yRangeMax != null) {
            Rectangle area = chartArea();
            double ratio = (yRangeMax.doubleValue() - yRangeMin.doubleValue()) / area.height;
            return (area.height + area.y - pixelY) * ratio + yRangeMin.doubleValue();
        }
        else {
            return 0.0;
        }
    }
    

    void resetXWindowMin(int xPixel) {
        xWindowMin = xRangeValue(xPixel);
        initializeDataSet();
        repaint();
    }

    
    void resetXWindowMax(int xPixel) {
        xWindowMax = xRangeValue(xPixel);
        initializeDataSet();
        repaint();
    }

    
    void resetYWindowMin(int yPixel) {
        yWindowMin = yRangeValue(yPixel);
        initializeDataSet();
        repaint();
    }

    
    void resetYWindowMax(int yPixel) {
        yWindowMax = yRangeValue(yPixel);
        initializeDataSet();
        repaint();
    }
    
    
    DataSet getDataSet() {
        return dataSet;
    }
    
    
    AxisPosition getXAxisPosition() {
        return xAxisPosition;
    }
    
    
    AxisPosition getYAxisPosition() {
        return yAxisPosition;
    }
    
    
    DemarcationMode getDemarcationMode() {
        return demarcationMode;
    }


    int xPixel(Number x) {
        return dataSet.xPixel(x);
    }


    int yPixel(Number y) {
        return dataSet.yPixel(y);
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
    
    
    private void drawLegend(Graphics2D g2d) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int x = areaRight() + 15;
        int y = topMargin;
        ArrayList<Object> keys = new ArrayList<>(dataSet.getGraphs().keySet());
        for (int i = keys.size() - 1; i >= 0; i--) {
            Object key = keys.get(i);
            LineRenderer lineRenderer = lineRenderers.get(key);
            if (lineRenderer != null) {
                lineRenderer.drawSymbol(g2d, x, y);
            }
            PointRenderer pointRenderer = pointRenderers.get(key);
            if (pointRenderer != null) {
                pointRenderer.drawSymbol(g2d, x, y);
            }
            g2d.setColor(Color.BLACK);
            g2d.drawString(key.toString(), x + 15, y + fontMetrics.getDescent());
            y += fontMetrics.getHeight() + 5;
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
    
    
    private AbstractDataPointRenderer getRenderer(Object key) {
        AbstractDataPointRenderer renderer = renderers.get(key);
        if (renderer == null) {
            renderer = new OvalDotRenderer();
            renderers.put(key, renderer);
        }
        return renderer;
    }

    
    private AxisRenderer getAxisRenderer() {
        if (axisRenderer == null) {
            setAxisRenderer(new DefaultAxisRenderer());
        }
        return axisRenderer;
    }
    
    
    private Style getStyle(Object key) {
        Style style = styles.get(key);
        return (style != null) ? style : Style.POINT;
    }
    
    
    private PointRenderer getPointRenderer(Object key) {
        PointRenderer pointRenderer = pointRenderers.get(key);
        if (pointRenderer == null) {
            pointRenderer = new OvalDotRenderer();
            setPointRenderer(key, pointRenderer);
        }
        return pointRenderer;
    }
    
    
    private LineRenderer getLineRenderer(Object key) {
        LineRenderer lineRenderer = lineRenderers.get(key);
        if (lineRenderer == null) {
            lineRenderer = new DefaultLineRenderer();
            setLineRenderer(key, lineRenderer);
        }
        return lineRenderer;
    }
    
    
    private Color getPointColor(Object key) {
        Color color = pointColors.get(key);
        if (color == null) {
            color = lineColors.get(key);
            if (color == null) {
                if (pointPalette == null) {
                    pointPalette = new Palette("Chart.pointPalette");
                }
                color = pointPalette.next();
            }
            pointColors.put(key, color);
        }
        return color;
    }
    
    
    private Color getLineColor(Object key) {
        Color color = lineColors.get(key);
        if (color == null) {
            color = pointColors.get(key);
            if (color == null) {
                if (linePalette == null) {
                    linePalette = new Palette("Chart.pointPalette");
                }
                color = linePalette.next();
            }
            lineColors.put(key, color);
        }
        return color;
    }
    
    
    private Palette getPiePalette(Object key) {
        Palette piePalette = piePalettes.get(key);
        if (piePalette == null) {
            piePalette = new Palette("Chart.piePalette");
            piePalettes.put(key, piePalette);
        }
        return piePalette;
    }
    
    
    private Color getHighlightColor() {
        Color color = javax.swing.UIManager.getColor("Chart.highlightColor");
        return (color != null) ? color : Color.YELLOW;
    }

    
    private synchronized void setData(Map<Object, Map<Number, Number>> data) {
        synchronized (dataSet) {
            dataSet.setData(data, renderers);
            initializeDataSet();
            xRangeMin = dataSet.getXMin();
            xRangeMax = dataSet.getXMax();
            yRangeMin = dataSet.getYMin();
            yRangeMax = dataSet.getYMax();
        }
        repaint();
    }
    
    
    private void initializeDataSet() {
        dataSet.initialize(chartArea(), xWindowMin, xWindowMax, yWindowMin, yWindowMax, yWindowBase, getLocale());
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
    
    
    private final java.awt.event.MouseAdapter MOUSE_ADAPTER = new java.awt.event.MouseAdapter() {

        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            Point mousePoint = evt.getPoint();
            if (chartArea().contains(mousePoint)) {
                if ((dragZoomMode != DragZoomMode.NONE || clickZoomMode != ClickZoomMode.NONE) && defaultCursor == null) {
                    defaultCursor = getCursor();
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                DataPointInterface nearest = null;
                synchronized (dataSet) {
                    for (Map.Entry entry : dataSet.getGraphs().entrySet()) {
                        Object key = entry.getKey();
                        AbstractDataPointRenderer renderer = renderers.get(key);
                        TreeSet<DataPoint> graph = (TreeSet<DataPoint>) entry.getValue();
                        for (DataPointInterface dataPoint : graph) {
                            if (renderer instanceof PieSectorRenderer) {
                                if (((PieSectorRenderer) renderer).pointNearDataPoint(mousePoint, dataPoint)) {
                                    nearest = dataPoint;
                                }
                            }
                            else {
                                if (dataPoint.contains(mousePoint)) {
                                    nearest = dataPoint;
                                }
                            }
                        }
                    }
                }
                if (nearestToMouse != nearest) {
                    nearestToMouse = nearest;
                    repaint();
                }
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
            if (dragStartPoint != null && dragEndPoint != null && evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                Number xMin = null;
                Number xMax = null;
                Number yMin = null;
                Number yMax = null;
                if (dataSet.getXMin() != null && dataSet.getXMax() != null) {
                    xMin = dataSet.xValue(Math.min(dragStartPoint.x, dragEndPoint.x));
                    xMax = dataSet.xValue(Math.max(dragStartPoint.x, dragEndPoint.x));
                }
                if (dataSet.getYMin() != null && dataSet.getYMax() != null) {
                    yMin = dataSet.yValue(Math.max(dragStartPoint.y, dragEndPoint.y));
                    yMax = dataSet.yValue(Math.min(dragStartPoint.y, dragEndPoint.y));
                }
                dragStartPoint = null;
                dragEndPoint = null;
                switch (dragZoomMode) {
                    case X  : setXWindow(xMin, xMax);            break;
                    case Y  : setYWindow(yMin, yMax);            break;
                    case XY : setWindow(xMin, xMax, yMin, yMax); break;
                }
            }
            else {
                dragStartPoint = null;
                dragEndPoint = null;
                repaint();
            }
        }
        
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            Point point = evt.getPoint();
            if (clickZoomMode == ClickZoomMode.DOUBLE_CLICK_DEMARCATION && chartArea().contains(point)) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
                    if (demarcationMode == DemarcationMode.X || demarcationMode == DemarcationMode.Y) {
                        zoom(point);
                    }
                }
            }
        }
        
        private void zoom(Point point) {
            boolean xMode = demarcationMode == DemarcationMode.X;
            java.util.List<Number> values = (xMode) ? dataSet.xDemarcations.values : dataSet.yDemarcations.values;
            if (values.size() >= 2) {
                Number min = values.get(0);
                boolean zoomed = false;
                int i = 1;
                while (! zoomed && i < values.size()) {
                    Number max = values.get(i);
                    if (xMode && dataSet.xPixel(min) <= point.x && point.x <= dataSet.xPixel(max)) {
                        setXWindow(min, max);
                        zoomed = true;
                    }
                    else if (! xMode && dataSet.yPixel(min) <= point.y && point.y <= dataSet.yPixel(max)) {
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
        
        private long squareDistance(Point p, Point q) {
            long Δx = p.x - q.x;
            long Δy = p.y - q.y;
            return Δx * Δx + Δy * Δy;
        }
        
        private Cursor defaultCursor = null;
        
    };

    
    private Color selectionRectangleColor;

    
    private final DataSet dataSet = new DataSet();
    
    
    private String title = null;
    
    private AxisPosition xAxisPosition = AxisPosition.ORIGIN;
    private AxisPosition yAxisPosition = AxisPosition.ORIGIN;

    private DemarcationMode demarcationMode = DemarcationMode.NONE;
    
    private boolean showLegend = false;
        
    private DragZoomMode dragZoomMode = DragZoomMode.NONE;
    private ClickZoomMode clickZoomMode = ClickZoomMode.NONE;
    
    
    private Number xRangeMin = null;
    private Number xRangeMax = null;
    private Number yRangeMin = null;
    private Number yRangeMax = null;
    
    private Number xWindowMin = null;
    private Number xWindowMax = null;
    private Number yWindowMin = null;
    private Number yWindowMax = null;
    private Number yWindowBase = null;

    
    private AxisRenderer axisRenderer = null;
    private DemarcationRenderer demarcationRenderer = null;
    
    private final Map<Object, Style> styles = new HashMap<>();
    private final Map<Object, AbstractDataPointRenderer> renderers = new HashMap<>();
    private final Map<Object, PointRenderer> pointRenderers = new HashMap<>();
    private final Map<Object, LineRenderer> lineRenderers = new HashMap<>();
    private final Map<Object, PieSectorRenderer> pieSectorRenderers = new HashMap<>();
    private final Map<Object, PointRenderer> pointHighlightRenderers = new HashMap<>();
    
    private final Map<Object, Color> pointColors = new HashMap<>();
    private final Map<Object, Color> lineColors = new HashMap<>();
    private final Map<Object, Palette> piePalettes = new HashMap<>();
    
    private Palette pointPalette = null;
    private Palette linePalette = null;
    
    
    private Point dragStartPoint = null;
    private Point dragEndPoint = null;

    private DataPointInterface nearestToMouse = null;
    
    
    private Rectangle page = null;
    
    private int leftMargin   = 100; // pixels
    private int rightMargin  = 100; // pixels
    private int topMargin    =  50; // pixels
    private int bottomMargin =  50; // pixels
    

    private final ArrayList<ZoomListener> zoomListeners = new ArrayList<>();

    private static final Logger logger = Logger.getLogger(ChartPanel.class.getName());
    
    private static final int LEFT_MARGIN_PRINT   = 60;
    private static final int RIGHT_MARGIN_PRINT  =  5;
    private static final int TOP_MARGIN_PRINT    = 40;
    private static final int BOTTOM_MARGIN_PRINT = 20;
    
}
