package bka.swing.chart;


import java.awt.*;
import java.awt.geom.*;

/*
** Copyright © Bart Kampers
*/


public class PieLooks implements AreaLooks<ArcAreaGeometry> {


    private PieLooks(Color[] colors) {
        this.colors = colors;
    }


    public static PieLooks create(Color[] colors) {
        return new PieLooks(colors);
    }


    @Override
    public Paint getPaint(ArcAreaGeometry geometry) {
        Arc2D.Float area = geometry.getArea();
        Point2D.Float center = new Point2D.Float((float) area.getCenterX(), (float) area.getCenterY());
        float diameter = (float) Math.max(area.getWidth(), area.getHeight());
        Color color = colors[geometry.getIndex()];
        return new RadialGradientPaint(center, diameter / 2.0f, fractions, new Color[] { color, color.darker() });
    }


    @Override
    public Paint getBorderPaint(ArcAreaGeometry geometry) {
        return colors[geometry.getIndex()].brighter();
    }


    @Override
    public Stroke getBorderStroke(ArcAreaGeometry geometry) {
        return new BasicStroke(1.0f);
    }


    private float[] fractions = new float[] { 0.0f, 1.0f };
    private Color[] colors;

}
