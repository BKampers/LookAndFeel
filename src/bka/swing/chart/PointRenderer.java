/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.util.logging.*;


public abstract class PointRenderer<S extends Shape> extends AbstractDataAreaRenderer<PointAreaGeometry<S>> {


    protected abstract S createArea(int x, int y);


    public void setRadialGradient(float[] distribution, Color[] colors) {
        setRadialGradient(distribution, colors, 0.0f, 0.0f);
    }


    public void setRadialGradient(float[] distribution, Color[] colors, float xShift, float yShift) {
        setGradient(true, distribution, colors, xShift, yShift);
    }


    public void setLinearGradient(float[] distribution, Color[] colors, float xShift, float yShift) {
        setGradient(false, distribution, colors, xShift, yShift);
    }


    public void setFillColor(Color color) {
        gradientParameters = null;
        fillColor = color;
    }


    public void setBorder(Color color, Stroke stroke) {
        borderColor = color;
        borderStroke = stroke;
    }


    public void setBorder(Color color) {
        setBorder(color, new BasicStroke(1.0f));
    }


    @Override
    public void draw(Graphics2D g2d, PointAreaGeometry<S> geometry, java.awt.Point location) {
        draw(g2d, geometry);
    }


    @Override
    protected void draw(Graphics2D g2d, PointAreaGeometry<S> geometry) {
        S area = geometry.getArea();
        Paint paint = getPaint(geometry);
        draw(g2d, paint, area);
    }


   @Override
    protected void drawSymbol(Graphics2D g2d, int x, int y) {
        draw(g2d, getPaint(x, y), createArea(x, y));
    }


    protected Paint getPaint(PointAreaGeometry<S> geometry) {
        return getPaint(chartGeometry.xPixel(geometry.getX()), chartGeometry.yPixel(geometry.getY()));
    }


    protected Paint getPaint(int x, int y) {
        if (gradientParameters != null) {
            try {
                if (gradientParameters.radial) {
                    return createRadialGradientPaint(x, y);
                }
                else {
                    return createLinearGradientPaint(x, y);
                }
            }
            catch (RuntimeException ex) {
                Logger.getLogger(PointRenderer.class.getName()).log(Level.SEVERE, "Invalid gradient parameters", ex);
                setFillColor(Color.BLACK);
            }
        }
        return fillColor;
    }


    @Override
    TreeSet<PointAreaGeometry<S>> createDataGeomerty(Map<Number, Number> graph) {
        TreeSet<PointAreaGeometry<S>> geometry = new TreeSet<>();
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            Number x = entry.getKey();
            Number y = entry.getValue();
            int pixelX = chartGeometry.xPixel(x);
            int pixelY = chartGeometry.yPixel(y);
            S area = createArea(pixelX, pixelY);
            geometry.add(new PointAreaGeometry(x, y, area));
        }
        return geometry;
    }


    private void draw(Graphics2D g2d, Paint paint, S area) {
        if (paint != null) {
            g2d.setPaint(paint);
            g2d.fill(area);
        }
        if (borderColor != null) {
            g2d.setPaint(borderColor);
            g2d.draw(area);
        }
    }


    private void setGradient(boolean radial, float[] distribution, Color[] colors, float xShift, float yShift) {
        gradientParameters = new GradientParameters();
        gradientParameters.radial = radial;
        gradientParameters.xShift = xShift;
        gradientParameters.yShift = yShift;
        gradientParameters.distribution = distribution;
        gradientParameters.colors = colors;
        fillColor = null;
    }


    private Paint createRadialGradientPaint(int x, int y) {
        float radius = Math.min(width, height) / 2.0f;
        Point2D.Float center = new Point2D.Float(x + width * gradientParameters.xShift, y - height * gradientParameters.yShift);
        return new RadialGradientPaint(center, radius, gradientParameters.distribution, gradientParameters.colors);
    }


    private Paint createLinearGradientPaint(int x, int y) {
        float xShift = width * gradientParameters.xShift;
        float yShift = height * gradientParameters.yShift;
        float xStart = x - xShift;
        float xEnd = x + xShift;
        float yStart = y - yShift;
        float yEnd = y + yShift;
        return new LinearGradientPaint(xStart, yStart, xEnd, yEnd, gradientParameters.distribution, gradientParameters.colors);
    }


    private class GradientParameters {
        boolean radial;
        float[] distribution;
        Color[] colors;
        float xShift, yShift;
    }


    protected int width;
    protected int height;

    protected Color borderColor;
    protected Stroke borderStroke;

    private Color fillColor;
    private GradientParameters gradientParameters;
    
}
