/*
** Copyright © Bart Kampers
*/

package bka.awt.chart.custom;


import bka.awt.chart.geometry.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.logging.*;


public class PointDrawStyle implements AreaDrawStyle<AreaGeometry> {


    PointDrawStyle(boolean radial, float xShiftFactor, float yShiftFactor, float[] distribution, Color[] colors) {
        this.radial = radial;
        this.xShiftFactor = xShiftFactor;
        this.yShiftFactor = yShiftFactor;
        this.distribution = distribution;
        this.colors = colors;
    }


    public static PointDrawStyle createLinear(float xShiftFactor, float yShiftFactor, float[] distribution, Color[] colors) {
        return new PointDrawStyle(false, xShiftFactor, yShiftFactor, distribution, colors);
    }


    public static PointDrawStyle createLinear(float[] distribution, Color[] colors) {
        return createLinear(0.0f, 0.0f, distribution, colors);
    }


    public static PointDrawStyle createLinear(Color[] colors) {
        if (colors == null || colors.length < 2) {
            throw new IllegalArgumentException();
        }
        return createLinear(uniformDistribution(colors), colors);
    }


    public static PointDrawStyle createRadial(float xShiftFactor, float yShiftFactor, float[] distribution, Color[] colors) {
        return new PointDrawStyle(true, xShiftFactor, yShiftFactor, distribution, colors);
    }


    public static PointDrawStyle createRadial(float[] distribution, Color[] colors) {
        return createRadial(0.0f, 0.0f, distribution, colors);
    }


    public static PointDrawStyle createRadial(Color[] colors) {
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
    public Paint getPaint(AreaGeometry geometry) {
        Shape area = geometry.getArea();
        if (area == null) {
            return null;
        }
        try {
            if (radial) {
                return createRadialGradientPaint(area);
            }
            else {
                return createLinearGradientPaint(area);
            }
        }
        catch (RuntimeException ex) {
            Logger.getLogger(PointDrawStyle.class.getName()).log(Level.SEVERE, "Invalid gradient parameters", ex);
            return null;
        }
    }


    @Override
    public Paint getBorderPaint(AreaGeometry area) {
        return borderPaint;
    }


    @Override
    public Stroke getBorderStroke(AreaGeometry area) {
        return borderStroke;
    }


    @Override
    public Paint getLabelPaint(AreaGeometry geometry) {
        return Color.BLACK;
    }


    @Override
    public Font getLabelFont(AreaGeometry geometry) {
        return null;
    }


    private Paint createRadialGradientPaint(Shape area) {
        Rectangle2D bounds = area.getBounds2D();
        float radius = (float) Math.min(bounds.getWidth(), bounds.getHeight()) / 2.0f;
        Point2D.Float center = new Point2D.Float(
            (float) bounds.getX() + xShift(bounds) + radius,
            (float) bounds.getY() - yShift(bounds) + radius);
        return new RadialGradientPaint(center, radius, distribution, colors);
    }


    private Paint createLinearGradientPaint(Shape area) {
        Rectangle2D bounds = area.getBounds2D();
        float xShift = xShift(bounds);
        float yShift = yShift(bounds);
        float x = (float) bounds.getX();
        float y = (float) bounds.getY();
        float xStart = x + xShift;
        float xEnd = x + (float) bounds.getWidth() - xShift ;
        float yStart = y + yShift;
        float yEnd = y + (float) bounds.getHeight() - yShift;
        return new LinearGradientPaint(xStart, yStart, xEnd, yEnd, distribution, colors);
    }


    private float xShift(Rectangle2D bounds) {
        return (float) bounds.getWidth() * xShiftFactor;
    }


    private float yShift(Rectangle2D bounds) {
        return (float) bounds.getHeight() * yShiftFactor;
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
