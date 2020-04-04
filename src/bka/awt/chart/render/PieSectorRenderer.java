/*
** Copyright © Bart Kampers
*/


package bka.awt.chart.render;


import bka.awt.chart.*;
import bka.awt.chart.custom.*;
import bka.awt.chart.geometry.*;
import java.awt.*;
import java.awt.geom.*;


public abstract class PieSectorRenderer extends AbstractDataAreaRenderer<ArcAreaGeometry> {


    PieSectorRenderer(PieDrawStyle drawStyle) {
        super(drawStyle);
    }


    @Override
    public GraphGeometry<ArcAreaGeometry> createGraphGeomerty(ChartData<Number, Number> chart) {
        graphGeometry = new GraphGeometry<>();
        ChartRenderer RendererPanel = getChartRenderer();
        diameter = Math.min(RendererPanel.areaWidth(), RendererPanel.areaHeight()) - DIAMETER_MARGIN;
        float pieLeft = RendererPanel.areaLeft() + (RendererPanel.areaWidth() - diameter) / 2.0f;
        float pieTop = RendererPanel.areaTop() + (RendererPanel.areaHeight() - diameter) / 2.0f;
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


    @Override
    public void addPointsInWindow(Object key, ChartData<Number, Number> chartData) {
        getWindow().putPoints(key, chartData);
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


    @Override
    public boolean supportStack() {
        return false;
    }


    protected GraphGeometry<ArcAreaGeometry> graphGeometry;


    private Point center;
    private int diameter;

    
    private static final int DIAMETER_MARGIN = 50; // pixels

}
