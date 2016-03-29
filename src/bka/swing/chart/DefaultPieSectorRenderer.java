/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.awt.geom.Arc2D;


public class DefaultPieSectorRenderer extends PieSectorRenderer {


    @Override
    public void draw(Graphics2D g2d, ArcAreaGeometry geometry, Point location) {
        draw(g2d, geometry);
    }

    
    @Override
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    @Override
    protected void draw(Graphics2D g2d, ArcAreaGeometry geometry) {
        drawArc(g2d, geometry);
        drawLabel(g2d, geometry);
    }


    protected RadialGradientPaint getGradientPaint(Color color1, Color color2) {
        final float[] fractions = new float[] { 0.0f, 1.0f };
        return new RadialGradientPaint(getCenter(), getDiameter(), fractions, new Color[] { color1, color2 });
    }


    private void drawArc(Graphics2D g2d, DataAreaGeometry geometry) {
        Color color = palette.next();
        g2d.setPaint(getGradientPaint(color, Color.BLACK));
        g2d.fill(geometry.getArea());
        g2d.setColor(color.darker());
        g2d.draw(geometry.getArea());
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


    private static final double LEFT_SIDE_MIN = 0.5 * Math.PI; // radians
    private static final double LEFT_SIDE_MAX = 1.5 * Math.PI; // radians

    private static final int TEXT_RADIUS_EXTENT = 15; // pixels


}
