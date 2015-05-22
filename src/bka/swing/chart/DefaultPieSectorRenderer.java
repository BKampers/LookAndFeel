/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;


public class DefaultPieSectorRenderer extends PieSectorRenderer {

    
    @Override
    public void draw(Graphics2D g2d, DataPoint dataPoint) {
        double diameter = Math.min(chartPanel.areaWidth(), chartPanel.areaHeight()) - 50;
        double x = chartPanel.areaLeft() + (chartPanel.areaWidth() - diameter) / 2;
        double y = chartPanel.areaTop() + (chartPanel.areaHeight() - diameter) / 2;
        double value = dataPoint.getY().doubleValue();
        double start = previous / total * 360;
        double extent = value / total * 360;
        java.awt.geom.Arc2D arc = new java.awt.geom.Arc2D.Double(x, y, diameter, diameter, start, extent, java.awt.geom.Arc2D.PIE);
        g2d.setColor(palette.next());
        g2d.fill(arc);
        g2d.setColor(Color.BLACK);
        g2d.draw(arc);
        
        Point point = new Point();
        double degrees = start + 0.5 * extent;
        double angle = (previous + value / 2.0) / total * -2 * Math.PI + 0.5 * Math.PI;
        double radius = diameter / 2.0 + 15;
        Point center = new Point((int) (x + diameter / 2.0), (int) (y + diameter / 2.0));
        point.x = center.x + Math.round((float) (Math.sin(angle) * radius));
        point.y = center.y - Math.round((float) (Math.sin(Math.PI / 2 - angle) * radius));

        String text = dataPoint.getX().toString();
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(text);

        if (90 < degrees && degrees < 270) {
            g2d.drawString(text, point.x - stringWidth, point.y + fontMetrics.getHeight() / 2);
        }
        else {
            g2d.drawString(text, point.x, point.y + fontMetrics.getHeight() / 2);
        }
        
        previous += value;
    }

    
    @Override
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
