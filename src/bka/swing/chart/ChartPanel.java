/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.util.*;
import javax.swing.UIManager;


public class ChartPanel extends javax.swing.JPanel implements java.awt.print.Printable {

    
    public enum Style { POINT, LINE, POINT_AND_LINE }
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
        Map<Object, Map> graphs = new HashMap<Object, Map>();
        graphs.put(key, graph);
        setGraphs(graphs);
    }
    
    
    public void setGraphs(Map<Object, Map> graphs) {
        Map<Object, Map<Number, Number>> data = new LinkedHashMap<Object, Map<Number, Number>>();
        for (Map.Entry<Object, Map> graph : graphs.entrySet()) {
            Map<Number, Number> numbers = new HashMap<Number, Number>();
            for (Object point : graph.getValue().entrySet()) {
                Object x = ((Map.Entry) point).getKey();
                Object y = ((Map.Entry) point).getValue();
                if (x instanceof Number && y instanceof Number) {
                    numbers.put((Number) x, (Number) y);
                }
                else {
                    throw new ClassCastException();
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
    
    
   public void setStyle(Object key, Style style) {
       styles.put(key, style);
   }
   
   
    public void setPointRenderer(Object key, PointRenderer pointRenderer) {
        if (pointRenderer.getColor() == null) {
            pointRenderer.setColor(getPointColor(key));
        }
        pointRenderer.setChartPanel(this);
        pointRenderers.put(key, pointRenderer);
    }
    
    
    public void setPointColor(Object key, Color color) {
        pointColors.put(key, color);
    }
    
    
    public void setLineRenderer(Object key, LineRenderer lineRenderer) {
        if (lineRenderer.getColor() == null) {
            lineRenderer.setColor(getLineColor(key));
        }
        lineRenderers.put(key, lineRenderer);
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
    
    
    public void invalidate() {
        initializeDataSet();
        super.invalidate();
    }
    
    
    public void paint(Graphics g) {
        super.paint(g);
        synchronized (dataSet) {
            Graphics2D g2d = (Graphics2D) g;
            DataPoint highlightPoint = null;
            PointRenderer highlightRenderer = null;
            if (demarcationRenderer != null) {
                demarcationRenderer.draw(g2d);
            }
            getAxisRenderer().drawXAxis(g2d);
            getAxisRenderer().drawYAxis(g2d);
            for (Map.Entry<Object, TreeSet<DataPoint>> entry : dataSet.getGraphs().entrySet()) {
                Object key = entry.getKey();
                Style style = getStyle(key);
                TreeSet<DataPoint> graph = entry.getValue();
                DataPoint previous = null;
                for (DataPoint dataPoint : graph) {
                    if (style == Style.POINT || style == Style.POINT_AND_LINE) {
                        PointRenderer pointRenderer = getPointRenderer(key);
                        pointRenderer.draw(g2d, dataPoint);
                    }
                    if (previous != null && (style == Style.LINE || style == Style.POINT_AND_LINE)) {
                        LineRenderer lineRenderer = getLineRenderer(key);
                        lineRenderer.draw(g2d, previous, dataPoint);
                    }
                    previous = dataPoint;
                    PointRenderer pointHighlightRenderer = pointHighlightRenderers.get(key);
                    if (pointHighlightRenderer != null && dataPoint.equals(nearestToMouse)) {
                        highlightPoint = dataPoint;
                        highlightRenderer = pointHighlightRenderer;
                    }
                }
            }
            if (showLegend) {
                drawLegend(g2d);
            }
            drawSelectionRectangle(g2d);
            if (highlightPoint != null) {
                highlightRenderer.draw(g2d, highlightPoint);
            }
            drawTitle(g2d);
        }
    }
    
    
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
            for (Map.Entry<Object, TreeSet<DataPoint>> entry : dataSet.getGraphs().entrySet()) {
                Object key = entry.getKey();
                Style style = getStyle(key);
                TreeSet<DataPoint> graph = entry.getValue();
                DataPoint previous = null;
                for (DataPoint dataPoint : graph) {
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
    
    
    Rectangle zoomBarAreaX() {
        Rectangle chartArea = chartArea();
        return new Rectangle(chartArea.x, chartArea.y + chartArea.height + zoomBarOffset, chartArea.width, zoomBarWidth);
    }
    
    
    Rectangle zoomBarAreaY() {
        Rectangle chartArea = chartArea();
        return new Rectangle(chartArea.x + chartArea.width + zoomBarOffset, chartArea.y, zoomBarWidth, chartArea.height);
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
    
    
    int xRangePixel(Number x) /*throws Exception */{
        if (x == null || xRangeMin == null || xRangeMax == null) { 
            return -1;
        }
        double ratio = chartArea().width / (xRangeMax.doubleValue() - xRangeMin.doubleValue());
        long pixel = Math.round((x.doubleValue() - xRangeMin.doubleValue()) * ratio);
        if (pixel < Integer.MIN_VALUE || pixel > Integer.MAX_VALUE) {
            //throw new Exception("Chart data error: " + pixel + " not in [" + Integer.MIN_VALUE + " .. " + Integer.MAX_VALUE + "]");
            System.err.printf("x pixel %d out of range [%d, %d]\n", pixel, Integer.MIN_VALUE, Integer.MAX_VALUE);
        }
        return areaLeft() + (int) pixel;
    }

    
    int yRangePixel(Number y) /*throws Exception */{
        if (y == null || yRangeMin == null || yRangeMax == null) { 
            return -1;
        }
        double ratio = chartArea().height / (yRangeMax.doubleValue() - yRangeMin.doubleValue());
        long pixel = Math.round((y.doubleValue() - yRangeMin.doubleValue()) * ratio);
        if (pixel < Integer.MIN_VALUE || pixel > Integer.MAX_VALUE) {
            //throw new Exception("Chart data error: " + pixel + " not in [" + Integer.MIN_VALUE + " .. " + Integer.MAX_VALUE + "]");
            System.err.printf("y pixel %d out of range [%d, %d]\n", pixel, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
        ArrayList<Object> keys = new ArrayList<Object>(dataSet.getGraphs().keySet());
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
    
    
    private Color getHighlightColor() {
        Color color = javax.swing.UIManager.getColor("Chart.highlightColor");
        return (color != null) ? color : Color.YELLOW;
    }

    
    private synchronized void setData(Map<Object, Map<Number, Number>> data) {
        synchronized (dataSet) {
            dataSet.setData(data);
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
                bounds.height - topMargin - bottomMargin - zoomBarWidth);
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

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            Point mousePoint = evt.getPoint();
            if (chartArea().contains(mousePoint)) {
                if ((dragZoomMode != DragZoomMode.NONE || clickZoomMode != ClickZoomMode.NONE) && defaultCursor == null) {
                    defaultCursor = getCursor();
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                DataPoint nearest = null;
                synchronized (dataSet) {
                    for (TreeSet<DataPoint> graph : dataSet.getGraphs().values()) {
                        for (DataPoint dataPoint : graph) {
                            if ((nearest == null) || (squareDistance(mousePoint, dataPoint.getPixel()) < squareDistance(mousePoint, nearest.getPixel()))) {
                                nearest = dataPoint;
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
        
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            Point point = evt.getPoint();
            if (clickZoomMode == ClickZoomMode.DOUBLE_CLICK_DEMARCATION && chartArea().contains(point)) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
                    switch (demarcationMode) {
                        case X: {
                            java.util.List<Number> values = dataSet.xDemarcations.values;
                            if (values.size() >= 2) {
                                Number min = values.get(0);
                                boolean zoomed = false;
                                int i = 0;
                                while (! zoomed && i < values.size()) {
                                    Number max = values.get(i);
                                    if (dataSet.xPixel(min) <= point.x && point.x <= dataSet.xPixel(max)) {
                                        setXWindow(min, max);
                                        zoomed = true;
                                    }
                                    else {
                                        min = max;
                                        i++;
                                    }
                                }
                            }
                            break;
                        }
                        case Y: {
                            java.util.List<Number> values = dataSet.yDemarcations.values;
                            if (values.size() >= 2) {
                                Number min = values.get(0);
                                boolean zoomed = false;
                                int i = 0;
                                while (! zoomed && i < values.size()) {
                                    Number max = values.get(i);
                                    if (dataSet.yPixel(max) <= point.y && point.y <= dataSet.yPixel(min)) {
                                        setYWindow(min, max);
                                        zoomed = true;
                                    }
                                    else {
                                        min = max;
                                        i++;
                                    }
                                }
                            }
                            break;
                        }
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
    
    private Map<Object, Style> styles = new HashMap<Object, Style>();
    private Map<Object, PointRenderer> pointRenderers = new HashMap<Object, PointRenderer>();
    private Map<Object, LineRenderer> lineRenderers = new HashMap<Object, LineRenderer>();
    private Map<Object, PointRenderer> pointHighlightRenderers = new HashMap<Object, PointRenderer>();
    
    private Map<Object, Color> pointColors = new HashMap<Object, Color>();
    private Map<Object, Color> lineColors = new HashMap<Object, Color>();
    
    private Palette pointPalette = null;
    private Palette linePalette = null;
    
    
    private Point dragStartPoint = null;
    private Point dragEndPoint = null;

    private DataPoint nearestToMouse = null;
    
    
    private Rectangle page = null;
    
    private int leftMargin   = 100; // pixels
    private int rightMargin  = 100; // pixels
    private int topMargin    =  50; // pixels
    private int bottomMargin =  50; // pixels
    
    private int zoomBarOffset = 35; // pixels
    private int zoomBarWidth  = 15; // pixels
    
    
    private final ArrayList<ZoomListener> zoomListeners = new ArrayList<ZoomListener>();
    
    
    private static final int LEFT_MARGIN_PRINT   = 60;
    private static final int RIGHT_MARGIN_PRINT  =  5;
    private static final int TOP_MARGIN_PRINT    = 40;
    private static final int BOTTOM_MARGIN_PRINT = 20;
    
}
