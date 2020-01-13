/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import bka.swing.chart.geometry.*;
import bka.swing.chart.render.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public final class ChartPanel extends javax.swing.JPanel {


    public enum DragZoomMode { NONE, X, Y, XY }
    public enum ClickZoomMode { NONE, DOUBLE_CLICK_GRID_AREA }

   
    public ChartPanel(ChartRenderer renderer) {
        this.renderer = Objects.requireNonNull(renderer);
        selectionRectangleColor = UIManager.getColor("Chart.selectionRectangleColor");
        if (selectionRectangleColor == null) {
            selectionRectangleColor = Color.GRAY;
        }
        addListeners();
    }


    public void setDragZoomMode(DragZoomMode dragZoomMode) {
        this.dragZoomMode = Objects.requireNonNull(dragZoomMode);
    }
   
   
   public void setClickZoomMode(ClickZoomMode clickZoomMode) {
       this.clickZoomMode = Objects.requireNonNull(clickZoomMode);
   }
    
    
    @Override
    public void invalidate() {
        renderer.invalidate();
        super.invalidate();
    }
    
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.setLocale(getLocale());
        renderer.paint((Graphics2D) g, getBounds());
        drawSelectionRectangle((Graphics2D) g);
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
    
    
    private void addListeners() {
        MouseAdapter mouseAdapter = new MouseAdapter();
        addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);
    }
    

    private boolean zoomEnabled() {
        return dragZoomMode != DragZoomMode.NONE || clickZoomMode != ClickZoomMode.NONE;
    }


    private class MouseAdapter extends java.awt.event.MouseAdapter {

        @Override
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            Point mousePoint = evt.getPoint();
            if (renderer.chartArea().contains(mousePoint)) {
                if (defaultCursor == null && zoomEnabled()) {
                    defaultCursor = getCursor();
                    setCursor(new Cursor(ZOOM_CURSOR));
                }
                renderer.highlight(mousePoint);
                repaint();
            }
            else if (defaultCursor != null) {
                setCursor(defaultCursor);
                defaultCursor = null;
            }
        }

        @Override
        public void mousePressed(java.awt.event.MouseEvent evt) {
            if (dragZoomMode != DragZoomMode.NONE && evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                dragStartPoint = getZoomPoint(evt);
                repaint();
            }
        }

        @Override
        public void mouseDragged(java.awt.event.MouseEvent evt) {
           if (dragZoomMode != DragZoomMode.NONE && dragStartPoint != null) {
                dragEndPoint = getZoomPoint(evt);
                repaint();
            }
        }
        
        private Point getZoomPoint(MouseEvent evt) {
            switch (dragZoomMode) {
                case X:
                    return new Point(getZoomX(evt), renderer.areaTop());
                case Y:
                    return new Point(renderer.areaLeft(), getZoomY(evt));
                case XY:
                    return new Point(getZoomX(evt), getZoomY(evt));
                default:
                    throw new IllegalStateException(dragZoomMode.name());
            }
        }

        private int getZoomX(MouseEvent evt) {
            return Math.min(Math.max(renderer.areaLeft(), evt.getX()), renderer.areaRight());
        }

        private int getZoomY(MouseEvent evt) {
            return Math.min(Math.max(renderer.areaTop(), evt.getY()), renderer.areaBottom());
        }

        @Override
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            if (dragStartPoint != null && dragEndPoint != null) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                    adjustWindow();
                }
                dragStartPoint = null;
                dragEndPoint = null;
                repaint();
            }
        }

        private void adjustWindow() {
            switch (dragZoomMode) {
                case X:
                    renderer.setXWindow(xMin(), xMax());
                    break;
                case Y:
                    setYWindows();
                    break;
                case XY:
                    setYWindows();
                    renderer.setXWindow(xMin(), xMax());
                    break;
            }
        }

        private void setYWindows() {
            ChartGeometry geometry = renderer.getChartGeometry();
            int minPixel = geometry.yPixel(yMin());
            int maxPixel = geometry.yPixel(yMax());
            RangeMap rangeMap = renderer.getChartGeometry().getYDataRanges();
            for (Map.Entry<Object, Range> entry : rangeMap.getRanges().entrySet()) {
                Range range = entry.getValue();
                double min = geometry.yValueByRange(range, minPixel);
                double max = geometry.yValueByRange(range, maxPixel);
                renderer.setYWindow(entry.getKey(), min, max);
            }
        }

        private Number xMin() {
            return renderer.xValue(Math.min(dragStartPoint.x, dragEndPoint.x));
        }

        private Number xMax() {
            return renderer.xValue(Math.max(dragStartPoint.x, dragEndPoint.x));
        }

        private Number yMin(){
            return renderer.yValue(Math.max(dragStartPoint.y, dragEndPoint.y));
        }

        private Number yMax() {
            return renderer.yValue(Math.min(dragStartPoint.y, dragEndPoint.y));
        }

        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            Point point = evt.getPoint();
            if (clickZoomMode == ClickZoomMode.DOUBLE_CLICK_GRID_AREA && renderer.chartArea().contains(point)) {
                if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount() == 1) {
                    ChartRenderer.GridMode gridMode = renderer.getGridMode();
                    if (gridMode == ChartRenderer.GridMode.X || gridMode == ChartRenderer.GridMode.Y) {
                        zoom(point);
                        repaint();
                    }
                }
            }
        }

        private void zoom(Point point) {
            boolean xMode = renderer.getGridMode() == ChartRenderer.GridMode.X;
            java.util.List<Number> values = (xMode) ? renderer.xGridValues() : renderer.yGridValues();
            if (values.size() >= 2) {
                Number min = values.get(0);
                boolean zoomed = false;
                int i = 1;
                while (! zoomed && i < values.size()) {
                    Number max = values.get(i);
                    if (xMode && renderer.xPixel(min) <= point.x && point.x <= renderer.xPixel(max)) {
                        renderer.setXWindow(min, max);
                        zoomed = true;
                    }
                    else if (! xMode && renderer.yPixel(min) <= point.y && point.y <= renderer.yPixel(max)) {
                        renderer.setYWindow(min, max);
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
        
    }


    private final ChartRenderer renderer;

    private DragZoomMode dragZoomMode = DragZoomMode.NONE;
    private ClickZoomMode clickZoomMode = ClickZoomMode.NONE;
    
    private Point dragStartPoint;
    private Point dragEndPoint;

    private Color selectionRectangleColor;

    private static final int ZOOM_CURSOR = Cursor.HAND_CURSOR;

}
