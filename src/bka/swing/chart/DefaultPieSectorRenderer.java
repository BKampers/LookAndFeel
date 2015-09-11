/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.*;


public class DefaultPieSectorRenderer extends PieSectorRenderer {


    @Override
    public void reset(ChartPanel chartPanel, TreeSet<DataPoint> graph) {
        super.reset(chartPanel, graph);
        arcs.clear();
    }

    
    @Override
    public void draw(Graphics2D g2d, DataPoint dataPoint) {
        double diameter = Math.min(chartPanel.areaWidth(), chartPanel.areaHeight()) - 50;
        double x = chartPanel.areaLeft() + (chartPanel.areaWidth() - diameter) / 2;
        double y = chartPanel.areaTop() + (chartPanel.areaHeight() - diameter) / 2;
        double value = dataPoint.getY().doubleValue();
        double start = previous / total * 360;
        double extent = value / total * 360;
        Arc2D arc = arcs.get(dataPoint);
        if (arc == null) {
            arc = new Arc2D.Double(x, y, diameter, diameter, start, extent, Arc2D.PIE);
            arcs.put(dataPoint, arc);
        }
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

    @Override
    boolean pointNearDataPoint(Point mousePoint, DataPoint dataPoint) {
        Arc2D arc = arcs.get(dataPoint);
        return arcs.get(dataPoint).contains(mousePoint);
    }


    private static final Map<DataPoint, Arc2D> arcs = new HashMap<>();
    
    
}
