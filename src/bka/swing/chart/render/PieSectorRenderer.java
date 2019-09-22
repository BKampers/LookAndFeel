/*
** Copyright © Bart Kampers
*/


package bka.swing.chart.render;


import bka.swing.chart.*;
import bka.swing.chart.custom.*;
import bka.swing.chart.geometry.*;
import java.awt.*;
import java.awt.geom.*;


public abstract class PieSectorRenderer extends AbstractDataAreaRenderer<ArcAreaGeometry> {


    PieSectorRenderer(PieDrawStyle drawStyle) {
        super(drawStyle);
    }


    @Override
    public GraphGeometry<ArcAreaGeometry> createGraphGeomerty(ChartData<Number, Number> chart) {
        graphGeometry = new GraphGeometry<>();
        diameter = Math.min(chartPanel.areaWidth(), chartPanel.areaHeight()) - DIAMETER_MARGIN;
        float pieLeft = chartPanel.areaLeft() + (chartPanel.areaWidth() - diameter) / 2.0f;
        float pieTop = chartPanel.areaTop() + (chartPanel.areaHeight() - diameter) / 2.0f;
        float radius = diameter / 2.0f;
        center = new Point(Math.round(pieLeft + radius), Math.round(pieTop + radius));
        double previous = 0.0;
        double total = total(chart);
        int index = 0;
        for (ChartDataElement<Number, Number> element : chart) {
            double value = element.getValue().doubleValue();
            double startAngle = previous / total * 360;
            double angularExtent = value / total * 360;
            Arc2D.Float arc = new Arc2D.Float(pieLeft, pieTop, diameter, diameter, (float) startAngle, (float) angularExtent, Arc2D.PIE);
            ArcAreaGeometry arcGeometry = new ArcAreaGeometry(element.getKey(), element.getValue(), arc, index);
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


    private double total(ChartData<Number, Number> chart) {
        double total = 0.0;
        for (ChartDataElement<Number, Number> element : chart) {
            total += element.getValue().doubleValue();
        }
        return total;
    }


    protected GraphGeometry<ArcAreaGeometry> graphGeometry;


    private Point center;
    private int diameter;

    
    private static final int DIAMETER_MARGIN = 50; // pixels

}
