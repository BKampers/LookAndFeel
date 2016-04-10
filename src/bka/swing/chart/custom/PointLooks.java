/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.custom;


import bka.swing.chart.geometry.AreaGeometry;
import java.awt.*;
import java.awt.geom.*;
import java.util.logging.*;


public class PointLooks implements AreaLooks<AreaGeometry<RectangularShape>> {


    PointLooks(boolean radial, float xShiftFactor, float yShiftFactor, float[] distribution, Color[] colors) {
        this.radial = radial;
        this.xShiftFactor = xShiftFactor;
        this.yShiftFactor = yShiftFactor;
        this.distribution = distribution;
        this.colors = colors;
    }


    public static PointLooks createLinear(float xShiftFactor, float yShiftFactor, float[] distribution, Color[] colors) {
        return new PointLooks(false, xShiftFactor, yShiftFactor, distribution, colors);
    }


    public static PointLooks createLinear(float[] distribution, Color[] colors) {
        return createLinear(0.0f, 0.0f, distribution, colors);
    }


    public static PointLooks createLinear(Color[] colors) {
        if (colors == null || colors.length < 2) {
            throw new IllegalArgumentException();
        }
        return createLinear(uniformDistribution(colors), colors);
    }


    public static PointLooks createRadial(float xShiftFactor, float yShiftFactor, float[] distribution, Color[] colors) {
        return new PointLooks(true, xShiftFactor, yShiftFactor, distribution, colors);
    }


    public static PointLooks createRadial(float[] distribution, Color[] colors) {
        return createRadial(0.0f, 0.0f, distribution, colors);
    }


    public static PointLooks createRadial(Color[] colors) {
        if (colors == null || colors.length < 2) {
            throw new IllegalArgumentException();
        }
        return createRadial(uniformDistribution(colors), colors);
    }


    public void setBorder(Paint borderPaint, Stroke borderStroke) {
        this.borderPaint = borderPaint;
        this.borderStroke = borderStroke;
    }


    public void setBorder(Paint borderPaint) {
        setBorder(borderPaint, new BasicStroke());
    }


    @Override
    public Paint getPaint(AreaGeometry<RectangularShape> geometry) {
        RectangularShape area = geometry.getArea();
        try {
            if (radial) {
                return createRadialGradientPaint(area);
            }
            else {
                return createLinearGradientPaint(area);
            }
        }
        catch (RuntimeException ex) {
            Logger.getLogger(PointLooks.class.getName()).log(Level.SEVERE, "Invalid gradient parameters", ex);
            return null;
        }
    }


    @Override
    public Paint getBorderPaint(AreaGeometry<RectangularShape> area) {
        return borderPaint;
    }


    @Override
    public Stroke getBorderStroke(AreaGeometry<RectangularShape> area) {
        return borderStroke;
    }


    private Paint createRadialGradientPaint(RectangularShape area) {
        float radius = (float) Math.min(area.getWidth(), area.getHeight()) / 2.0f;
        Point2D.Float center = new Point2D.Float(
            (float) area.getX() + xShift(area) + radius,
            (float) area.getY() - yShift(area) + radius);
        return new RadialGradientPaint(center, radius, distribution, colors);
    }


    private Paint createLinearGradientPaint(RectangularShape area) {
        float xShift = xShift(area);
        float yShift = yShift(area);
        float x = (float) area.getX();
        float y = (float) area.getY();
        float xStart = x + xShift;
        float xEnd = x + (float) area.getWidth() - xShift ;
        float yStart = y + yShift;
        float yEnd = y + (float) area.getHeight() - yShift;
        return new LinearGradientPaint(xStart, yStart, xEnd, yEnd, distribution, colors);
    }


    private float xShift(RectangularShape area) {
        return (float) area.getWidth() * xShiftFactor;
    }


    private float yShift(RectangularShape area) {
        return (float) area.getHeight() * yShiftFactor;
    }


    private static float[] uniformDistribution(Color[] colors) {
        float[] distribution = new float[colors.length];
        float interval = 1.0f / (colors.length - 1);
        for (int i = 0; i < distribution.length; ++i) {
            distribution[i] = i * interval;
        }
        return distribution;
    }


    private final boolean radial;
    private final float xShiftFactor, yShiftFactor;
    private final float[] distribution;
    private final Color[] colors;

    private Paint borderPaint;
    private Stroke borderStroke;

 }
