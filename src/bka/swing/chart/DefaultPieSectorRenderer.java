/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.awt.geom.Arc2D;


public class DefaultPieSectorRenderer extends PieSectorRenderer {


    DefaultPieSectorRenderer(PieLooks looks) {
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
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(label);
        Arc2D.Float arc = geometry.getArea();
        double angle = Math.toRadians(arc.start + arc.extent / 2.0);
        double labelRadius = getDiameter() / 2.0 + TEXT_RADIUS_EXTENT;
        Point center = getCenter();
        int labelX = (int) Math.round(center.x + Math.cos(angle) * labelRadius);
        int labelY = (int) Math.round(center.y - Math.sin(angle) * labelRadius + fontMetrics.getHeight() / 2.0);
        g2d.setColor(Color.BLACK);
        if (LEFT_SIDE_MIN < angle && angle < LEFT_SIDE_MAX) {
            g2d.drawString(label, labelX - stringWidth, labelY);
        }
        else {
            g2d.drawString(label, labelX, labelY);
        }
    }


    private Arc2D.Float createSymbolArea(int x, int y) {
        return new Arc2D.Float(x - 10, y - 10, 20, 20, -40, 80, Arc2D.PIE);
    }


    private static final double LEFT_SIDE_MIN = 0.5 * Math.PI; // radians
    private static final double LEFT_SIDE_MAX = 1.5 * Math.PI; // radians

    private static final int TEXT_RADIUS_EXTENT = 15; // pixels

}
