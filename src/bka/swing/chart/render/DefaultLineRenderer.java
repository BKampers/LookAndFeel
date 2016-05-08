/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.custom.LineLooks;
import bka.swing.chart.geometry.PixelAreaGeometry;
import java.awt.*;
import java.awt.geom.*;


public class DefaultLineRenderer extends LineRenderer {


    public DefaultLineRenderer(LineLooks lineLooks, int markerWidth, int markerHeight) {
        super(lineLooks, markerWidth, markerHeight);
    }


    public DefaultLineRenderer(LineLooks lineLooks, int markerSize) {
        this(lineLooks, markerSize, markerSize);
    }


    public DefaultLineRenderer(LineLooks lineLooks) {
        this(lineLooks, 3, 3);
    }


    @Override
    public void draw(Graphics2D g2d, java.util.List<PixelAreaGeometry> graphGeometry) {
        Polygon polyline = createPolyline(graphGeometry);
        if (lineLooks.getBottomAreaPaint() != null) {
            fillBottomArea(g2d, polyline);
        }
        if (lineLooks.getTopAreaPaint() != null) {
            fillTopArea(g2d, polyline);
        }
        if (lineLooks.getAreaLooks() != null) {
            super.draw(g2d, graphGeometry);
        }
        if (lineLooks.getLinePaint() != null && lineLooks.getLineStroke() != null) {
            g2d.setPaint(lineLooks.getLinePaint());
            g2d.setStroke(lineLooks.getLineStroke());
            g2d.drawPolyline(polyline.xpoints, polyline.ypoints, polyline.npoints);
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
        drawLine(g2d, x - 7, y, x + 7, y);
    }


    private Polygon createPolyline(java.util.List<PixelAreaGeometry> graphGeometry) {
        Polygon polyline = new Polygon();
        for (PixelAreaGeometry<RectangularShape> dataAreaGeometry : graphGeometry) {
            Point pixel = dataAreaGeometry.getPixel();
            polyline.addPoint(pixel.x, pixel.y);
        }
        return polyline;
    }


    private void fillBottomArea(Graphics2D g2d, Polygon polyline) {
        int bottom = chartGeometry.yPixel(chartGeometry.getYMin());
        fillArea(g2d, lineLooks.getBottomAreaPaint(), polyline, bottom);
    }


    private void fillTopArea(Graphics2D g2d, Polygon polyline) {
        int top = chartGeometry.yPixel(chartGeometry.getYMax());
        fillArea(g2d, lineLooks.getTopAreaPaint(), polyline, top);
    }


    private void fillArea(Graphics2D g2d, Paint paint, Polygon polyline, int base) {
        Polygon area = new Polygon(polyline.xpoints, polyline.ypoints, polyline.npoints);
        area.addPoint(polyline.xpoints[polyline.npoints - 1], base);
        area.addPoint(polyline.xpoints[0], base);
        g2d.setPaint(paint);
        g2d.fillPolygon(area);
    }


    private void drawLine(Graphics2D g2d, int x1, int y1, int x2, int y2) {
        Paint paint = lineLooks.getLinePaint();
        Stroke stroke = lineLooks.getLineStroke();
        if (paint != null && stroke != null) {
            g2d.setPaint(paint);
            g2d.setStroke(stroke);
            g2d.drawLine(x1, y1, x2, y2);
        }
    }

}
