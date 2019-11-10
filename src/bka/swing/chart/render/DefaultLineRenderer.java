/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.custom.*;
import bka.swing.chart.geometry.*;
import java.awt.*;
import java.awt.geom.*;


public class DefaultLineRenderer extends LineRenderer {


    public DefaultLineRenderer(LineDrawStyle lineStyle, int markerWidth, int markerHeight) {
        super(lineStyle, markerWidth, markerHeight);
    }


    public DefaultLineRenderer(LineDrawStyle lineDrawStyle, int markerSize) {
        this(lineDrawStyle, markerSize, markerSize);
    }


    public DefaultLineRenderer(LineDrawStyle lineDrawStyle) {
        this(lineDrawStyle, DEFAULT_SIZE, DEFAULT_SIZE);
    }


    @Override
    public void draw(Graphics2D g2d, GraphGeometry<PixelAreaGeometry> graphGeometry) {
        Polygon polyline = createPolyline(graphGeometry);
        if (polyline.npoints > 0) {
            Shape restoreShape = g2d.getClip();
            g2d.clip(getWindow().getChartArea());
            if (lineDrawStyle.getBottomAreaPaint() != null) {
                fillBottomArea(g2d, polyline);
            }
            if (lineDrawStyle.getTopAreaPaint() != null) {
                fillTopArea(g2d, polyline);
            }
            if (lineDrawStyle.getLinePaint() != null && lineDrawStyle.getLineStroke() != null) {
                g2d.setPaint(lineDrawStyle.getLinePaint());
                g2d.setStroke(lineDrawStyle.getLineStroke());
                g2d.drawPolyline(polyline.xpoints, polyline.ypoints, polyline.npoints);
            }
            g2d.setClip(restoreShape);
            if (lineDrawStyle.getAreaDrawStyle() != null) {
                super.draw(g2d, graphGeometry);
            }
        }
    }


    @Override
    protected PixelAreaGeometry createSymbolGeometry(int x, int y, PixelAreaGeometry geometry) {
        RectangularShape area = createSymbolArea(x, y);
        Point pixel = new Point((int) area.getCenterX(), (int) area.getCenterY());
        return new PixelAreaGeometry<>(null, null, area, pixel);
    }


    @Override
    protected void drawSymbol(Graphics2D g2d, int x, int y) {
        RectangularShape area = createSymbolArea(x, y);
        super.draw(g2d, new PixelAreaGeometry<>(null, null, area, new Point(x, y)));
        drawLine(g2d, x - SYMBOL_WIDTH / 2, y, x + SYMBOL_WIDTH / 2, y);
    }


    private Polygon createPolyline(GraphGeometry<PixelAreaGeometry> graphGeometry) {
        Polygon polyline = new Polygon();
        for (PixelAreaGeometry<RectangularShape> dataAreaGeometry : graphGeometry.getDataPoints()) {
            Point pixel = dataAreaGeometry.getPixel();
            polyline.addPoint(pixel.x, pixel.y);
        }
        return polyline;
    }


    private void fillBottomArea(Graphics2D g2d, Polygon polyline) {
        fillArea(g2d, lineDrawStyle.getBottomAreaPaint(), polyline, getWindow().getYPixelBottom());
    }


    private void fillTopArea(Graphics2D g2d, Polygon polyline) {
        fillArea(g2d, lineDrawStyle.getTopAreaPaint(), polyline, getWindow().getYPixelTop());
    }


    private void fillArea(Graphics2D g2d, Paint paint, Polygon polyline, int base) {
        Polygon area = new Polygon(polyline.xpoints, polyline.ypoints, polyline.npoints);
        area.addPoint(polyline.xpoints[polyline.npoints - 1], base);
        area.addPoint(polyline.xpoints[0], base);
        g2d.setPaint(paint);
        g2d.fillPolygon(area);
    }


    private void drawLine(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        Paint paint = lineDrawStyle.getLinePaint();
        Stroke stroke = lineDrawStyle.getLineStroke();
        if (paint != null && stroke != null) {
            g2d.setPaint(paint);
            g2d.setStroke(stroke);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

    
    private static final int DEFAULT_SIZE = 3;
    private static final int SYMBOL_WIDTH = 15;

}
