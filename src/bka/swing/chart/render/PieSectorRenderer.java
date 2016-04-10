/*
** Copyright © Bart Kampers
*/


package bka.swing.chart.render;


import bka.swing.chart.custom.PieLooks;
import bka.swing.chart.geometry.ArcAreaGeometry;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;


public abstract class PieSectorRenderer extends AbstractDataAreaRenderer<ArcAreaGeometry> {


    PieSectorRenderer(PieLooks looks) {
        super(looks);
    }


    @Override
    public TreeSet<ArcAreaGeometry> createGraphGeomerty(Map<Number, Number> graph) {
        graphGeometry = new TreeSet<>();
        diameter = Math.min(chartPanel.areaWidth(), chartPanel.areaHeight()) - DIAMETER_MARGIN;
        float pieLeft = chartPanel.areaLeft() + (chartPanel.areaWidth() - diameter) / 2.0f;
        float pieTop = chartPanel.areaTop() + (chartPanel.areaHeight() - diameter) / 2.0f;
        float radius = diameter / 2.0f;
        center = new Point(Math.round(pieLeft + radius), Math.round(pieTop + radius));
        double previous = 0.0;
        double total = total(graph.values());
        int index = 0;
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            double value = entry.getValue().doubleValue();
            double startAngle = previous / total * 360;
            double angularExtent = value / total * 360;
            Arc2D.Float arc = new Arc2D.Float(pieLeft, pieTop, diameter, diameter, (float) startAngle, (float) angularExtent, Arc2D.PIE);
            ArcAreaGeometry arcGeometry = new ArcAreaGeometry(entry.getKey(), entry.getValue(), arc, index);
            graphGeometry.add(arcGeometry);
            previous += value;
            index++;
        }
        return graphGeometry;
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


    protected TreeSet<ArcAreaGeometry> graphGeometry;


    private Point center;
    private int diameter;

    
    private static final int DIAMETER_MARGIN = 50; // pixels

}
