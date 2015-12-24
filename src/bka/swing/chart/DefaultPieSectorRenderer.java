/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.*;


public class DefaultPieSectorRenderer extends PieSectorRenderer {


    @Override
    void reset(ChartPanel chartPanel, TreeSet<DataPoint> graph) {
        super.reset(chartPanel, graph);
        diameter = Math.min(chartPanel.areaWidth(), chartPanel.areaHeight()) - 50;
        sectors.clear();
        textRadius = diameter / 2.0 + TEXT_RADIUS_EXTENT;
        double pieLeft = chartPanel.areaLeft() + (chartPanel.areaWidth() - diameter) / 2;
        double pieTop = chartPanel.areaTop() + (chartPanel.areaHeight() - diameter) / 2;
        center = new Point((int) (pieLeft + diameter / 2.0), (int) (pieTop + diameter / 2.0));
        for (DataPoint dataPoint : graph) {
            Sector sector = new Sector();
            double value = dataPoint.getY().doubleValue();
            double start = previous / total * 360;
            double extent = value / total * 360;
            sector.arc = new Arc2D.Double(pieLeft, pieTop, diameter, diameter, start, extent, Arc2D.PIE);

            sector.degrees = (int) Math.round(start + 0.5 * extent);
            double angle = (previous + value / 2.0) / total * -2 * Math.PI + 0.5 * Math.PI;
            sector.textPoint.x = center.x + Math.round((float) (Math.sin(angle) * textRadius));
            sector.textPoint.y = center.y - Math.round((float) (Math.sin(Math.PI / 2 - angle) * textRadius));

            sectors.put(dataPoint, sector);

            previous += value;
        }
    }


    @Override
    public void draw(Graphics2D g2d, DataPoint dataPoint) {
        Sector sector = sectors.get((ArcDataPoint) dataPoint);
        drawArc(g2d, sector);
        drawText(g2d, sector, dataPoint);
    }


    @Override
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    protected RadialGradientPaint getGradientPaint(Color color1, Color color2) {
        final float[] fractions = new float[] { 0.0f, 1.0f };
        return new RadialGradientPaint(center, (float) diameter, fractions, new Color[] { color1, color2 });
    }



    @Override
    boolean pointNearDataPoint(Point mousePoint, DataPoint dataPoint) {
        Arc2D arc = sectors.get(dataPoint).arc;
        return arc.contains(mousePoint);
    }


    @Override
    Arc2D getArc(ArcDataPoint dataPoint) {
        return sectors.get(dataPoint).arc;
    }


    private void drawArc(Graphics2D g2d, Sector sector) {
        g2d.setPaint(getGradientPaint(palette.next(), Color.BLACK));
        g2d.fill(sector.arc);
    }


    private void drawText(Graphics2D g2d, Sector sector, DataPoint dataPoint) {
        String text = dataPoint.getX().toString();
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(text);
        int yText = sector.textPoint.y + fontMetrics.getHeight() / 2;
        if (LEFT_SIDE_MIN < sector.degrees && sector.degrees < LEFT_SIDE_MAX) {
            g2d.drawString(text, sector.textPoint.x - stringWidth, yText);
        }
        else {
            g2d.drawString(text, sector.textPoint.x, yText);
        }
    }


    private class Sector {
        int degrees;
        Arc2D arc;
        Point textPoint = new Point();
    }

    private Point center;
    private double diameter;
    private double textRadius;

    private final Map<DataPoint, Sector> sectors = new HashMap<>();

    private static final int LEFT_SIDE_MIN = 90; // degrees
    private static final int LEFT_SIDE_MAX = 270; // degrees
    private static final int TEXT_RADIUS_EXTENT = 15; // pixels


}
