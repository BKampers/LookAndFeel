/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public class BarLooks implements AreaLooks<AreaGeometry<Rectangle>> {


    private BarLooks(float[] pattern, Color[] colors) {
        this.pattern = pattern;
        this.colors = colors;
    }


    public static BarLooks create(float[] pattern, Color[] colors) {
        return new BarLooks(pattern, colors);
    }


    public static BarLooks create(Color centerColor, Color edgeColor) {
        return new BarLooks(
            new float[] { 0.0f, 0.5f, 1.0f },
            new Color[] { edgeColor, centerColor, edgeColor });
    }


    public void setBorder(Paint borderPaint, Stroke borderStroke) {
        this.borderPaint = borderPaint;
        this.borderStroke = borderStroke;
    }


    public void setBorder(Paint borderPaint) {
        setBorder(borderPaint, new BasicStroke());
    }


    @Override
    public Paint getPaint(AreaGeometry<Rectangle> geometry) {
        Rectangle area = geometry.getArea();
        return new LinearGradientPaint(area.x, area.y, area.x + area.width, area.y, pattern, colors);
    }


    @Override
    public Paint getBorderPaint(AreaGeometry<Rectangle> geometry) {
        return borderPaint;
    }


    @Override
    public Stroke getBorderStroke(AreaGeometry<Rectangle> geometry) {
        return borderStroke;
    }


    private final float[] pattern;
    private final Color[] colors;

    private Paint borderPaint;
    private Stroke borderStroke;

}
