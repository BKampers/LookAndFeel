/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;


import java.awt.*;
import java.awt.geom.*;
import java.util.*;



public abstract class PieSectorRenderer extends AbstractDataPointRenderer {
        
    
    @Override
    public TreeSet<DataPoint> createDataPoints(Map<Number, Number> graph) {
        TreeSet<DataPoint> dataPoints = new TreeSet<>();
        diameter = Math.min(chartPanel.areaWidth(), chartPanel.areaHeight()) - DIAMETER_MARGIN;
        float pieLeft = chartPanel.areaLeft() + (chartPanel.areaWidth() - diameter) / 2.0f;
        float pieTop = chartPanel.areaTop() + (chartPanel.areaHeight() - diameter) / 2.0f;
        float radius = diameter / 2.0f;
        center = new Point(Math.round(pieLeft + radius), Math.round(pieTop + radius));
        double previous = 0.0;
        double total = total(graph.values());
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            double value = entry.getValue().doubleValue();
            double startAngle = previous / total * 360;
            double angularExtent = value / total * 360;
            Arc2D arc = new Arc2D.Float(pieLeft, pieTop, diameter, diameter, (float) startAngle, (float) angularExtent, Arc2D.PIE);
            ArcDataPoint dataPoint = new ArcDataPoint(entry.getKey(), entry.getValue(), arc);
            dataPoints.add(dataPoint);
            previous += value;
        }
        return dataPoints;
    }


    public Palette getPalette() {
        return palette;
    }
    
    
    public void setPalette(Palette palette) {
        this.palette = palette;
    }


    @Override
    void setGraph(TreeSet<DataPoint> graph) {
        super.setGraph(graph);
        if (palette == null) {
            palette = new Palette("chart.piePalette");
        }
        palette.reset();
    }


    protected Point getCenter() {
        return new Point(center);
    }


    protected int getDiameter() {
        return diameter;
    }


    private double total(Collection<Number> numbers) {
        double total = 0.0;
        for (Number value : numbers) {
            total += value.doubleValue();
        }
        return total;
    }


    protected Palette palette;

    private Point center;
    private int diameter;

    
    private static final int DIAMETER_MARGIN = 50; // pixels

}
