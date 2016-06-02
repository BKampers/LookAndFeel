/*
** Copyright © Bart Kampers
*/


package bka.swing.chart.render;


import bka.swing.chart.geometry.LegendGeometry;
import bka.swing.chart.custom.PieLooks;
import bka.swing.chart.geometry.*;

import java.awt.*;
import java.awt.geom.Arc2D;


public class DefaultPieSectorRenderer extends PieSectorRenderer {


    public DefaultPieSectorRenderer(PieLooks looks) {
        super(looks);
    }


    @Override
    public void drawLegend(Graphics2D g2d, Object key, LegendGeometry geometry) {
        int x = geometry.getX();
        int y = geometry.getY();
        g2d.setFont(geometry.getFont());
        FontMetrics fontMetrics = g2d.getFontMetrics();
        for (AreaGeometry areaGeometry : graphGeometry) {
            drawSymbol(g2d, x, y, (ArcAreaGeometry) areaGeometry);
            g2d.setColor(geometry.getColor());
            g2d.drawString(areaGeometry.getX().toString(), x + geometry.getSpace(), y + fontMetrics.getDescent());
            y += geometry.getFeed() + fontMetrics.getHeight();
        }
        geometry.setY(y);
    }


    @Override
    protected void draw(Graphics2D g2d, ArcAreaGeometry geometry) {
        super.draw(g2d, geometry);
        drawLabel(g2d, geometry);
    }


    @Override
    protected ArcAreaGeometry createSymbolGeometry(int x, int y, ArcAreaGeometry geometry) {
        Arc2D.Float area = createSymbolArea(x, y);
        return new ArcAreaGeometry(null, null, area, geometry.getIndex());
    }


    private void drawSymbol(Graphics2D g2d, int x, int y, ArcAreaGeometry geometry) {
        super.draw(g2d, createSymbolGeometry(x, y, geometry));
    }


    private void drawLabel(Graphics2D g2d, ArcAreaGeometry geometry) {
        String label = geometry.getX().toString();
        Arc2D.Float arc = geometry.getArea();
        double angle = Math.toRadians(arc.start + arc.extent / 2.0);
        double labelRadius = getDiameter() / 2.0 + TEXT_RADIUS_EXTENT;
        Point center = getCenter();
        double labelX = center.x + Math.cos(angle) * labelRadius;
        double labelY = center.y - Math.sin(angle) * labelRadius;
        g2d.setColor(Color.BLACK);
        if (((PieLooks) looks).getRotatedLabels()) {
            drawRotatedLabel(g2d, angle, labelX, labelY, label);
        }
        else {
            drawHorizontalLabel(g2d, angle, labelX, labelY, label);
        }
    }
    
    
    private void drawHorizontalLabel(Graphics2D g2d, double angle, double labelX, double labelY, String label) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        if (LEFT_SIDE_MIN < angle && angle < LEFT_SIDE_MAX) {
            labelX -= fontMetrics.stringWidth(label);
        }
        labelY += fontMetrics.getHeight() / 2.0;
        drawString(g2d, label, labelX, labelY);
    }

    
    private void drawRotatedLabel(Graphics2D g2d, double angle, double labelX, double labelY, String label) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        double x = labelX;
        if (LEFT_SIDE_MIN < angle && angle < LEFT_SIDE_MAX) {
            angle -= Math.PI;
            x -= fontMetrics.stringWidth(label);
        }
        g2d.rotate(-angle, labelX, labelY);
        drawString(g2d, label, x, labelY);
        g2d.rotate(angle, labelX, labelY);
    }


    private void drawString(Graphics2D g2d, String label, double labelX, double labelY) {
        g2d.drawString(label, Math.round((float) labelX), Math.round((float) labelY));
    }


    private Arc2D.Float createSymbolArea(int x, int y) {
        return new Arc2D.Float(x - 10, y - 10, 20, 20, -40, 80, Arc2D.PIE);
    }


    private static final double LEFT_SIDE_MIN = 0.5 * Math.PI; // radians
    private static final double LEFT_SIDE_MAX = 1.5 * Math.PI; // radians

    private static final int TEXT_RADIUS_EXTENT = 15; // pixels

}
