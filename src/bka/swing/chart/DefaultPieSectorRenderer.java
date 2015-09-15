/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.*;


public class DefaultPieSectorRenderer extends PieSectorRenderer {


    @Override
    public void reset(ChartPanel chartPanel, TreeSet<DataPointInterface> graph) {
        super.reset(chartPanel, graph);
        sectors.clear();
        diameter = Math.min(chartPanel.areaWidth(), chartPanel.areaHeight()) - 50;
        x = chartPanel.areaLeft() + (chartPanel.areaWidth() - diameter) / 2;
        y = chartPanel.areaTop() + (chartPanel.areaHeight() - diameter) / 2;
        for (DataPointInterface dataPoint : graph) {
            Sector sector = new Sector();
            sector.value = dataPoint.getY().doubleValue();
            sector.start = previous / total * 360;
            sector.extent = sector.value / total * 360;
            sectors.put(dataPoint, sector);
            previous += sector.value;
        }
        previous = 0.0;
    }

    
    @Override
    public void draw(Graphics2D g2d, DataPointInterface dataPoint) {
        Sector sector = sectors.get((SectorDataPoint) dataPoint);
        drawArc(g2d, sector);
        drawText(g2d, sector, dataPoint);
    }


    @Override
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    boolean pointNearDataPoint(Point mousePoint, DataPointInterface dataPoint) {
        Arc2D arc = createArc(sectors.get(dataPoint));
        return arc.contains(mousePoint);
    }


    @Override
    Arc2D getArc(SectorDataPoint dataPoint) {
        return createArc(sectors.get(dataPoint));
    }


    private void drawArc(Graphics2D g2d, Sector sector) {
        Arc2D arc = createArc(sector);
        g2d.setColor(palette.next());
        g2d.fill(arc);
        g2d.setColor(Color.BLACK);
        g2d.draw(arc);
    }


    private void drawText(Graphics2D g2d, Sector sector, DataPointInterface dataPoint) {
        int degrees = (int) Math.round(sector.start + 0.5 * sector.extent);
        double angle = (previous + sector.value / 2.0) / total * -2 * Math.PI + 0.5 * Math.PI;
        double radius = diameter / 2.0 + 15;
        Point center = new Point((int) (x + diameter / 2.0), (int) (y + diameter / 2.0));
        Point textPoint = new Point();
        textPoint.x = center.x + Math.round((float) (Math.sin(angle) * radius));
        textPoint.y = center.y - Math.round((float) (Math.sin(Math.PI / 2 - angle) * radius));

        String text = dataPoint.getX().toString();
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(text);

        int yText = textPoint.y + fontMetrics.getHeight() / 2;
        if (LEFT_SIDE_MIN < degrees && degrees < LEFT_SIDE_MAX) {
            g2d.drawString(text, textPoint.x - stringWidth, yText);
        }
        else {
            g2d.drawString(text, textPoint.x, yText);
        }
        previous += sector.value;
    }


    private Arc2D createArc(Sector sector) {
        return new Arc2D.Double(x, y, diameter, diameter, sector.start, sector.extent, Arc2D.PIE);
    }


    private class Sector {
        double value;
        double start; // degrees
        double extent; //degrees
    }

    private double diameter;
    private double x;
    private double y;

    private final Map<DataPointInterface, Sector> sectors = new HashMap<>();

    private static final int LEFT_SIDE_MIN = 90; // degrees
    private static final int LEFT_SIDE_MAX = 270; // degrees

}
