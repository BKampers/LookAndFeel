package bka.swing.chart.custom;


import bka.swing.chart.geometry.*;
import java.awt.*;
import java.awt.geom.*;



public class PieDrawStyle implements AreaDrawStyle<ArcAreaGeometry> {


    private PieDrawStyle(Color[] colors) {
        this.colors = colors;
    }


    public static PieDrawStyle create(Color[] colors) {
        return new PieDrawStyle(colors);
    }


    public void setRotatedLabels(boolean rotatedLabels) {
        this.rotatedLabels = rotatedLabels;
    }


    public boolean getRotatedLabels() {
        return rotatedLabels;
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


     @Override
    public Paint getLabelPaint(ArcAreaGeometry geometry) {
        return Color.BLACK;
    }


    @Override
    public Font getLabelFont(ArcAreaGeometry geometry) {
        return null;
    }


    private final float[] fractions = new float[] { 0.0f, 1.0f };
    private final Color[] colors;
    private boolean rotatedLabels;

}