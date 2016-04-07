/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public class BarLooks implements AreaLooks<Rectangle> {


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
    public Paint getPaint(Rectangle area) {
        return new LinearGradientPaint(area.x, area.y, area.x + area.width, area.y, pattern, colors);
    }


    @Override
    public Paint getBorderPaint(Rectangle area) {
        return borderPaint;
    }


    @Override
    public Stroke getBorderStroke(Rectangle area) {
        return borderStroke;
    }


    private final float[] pattern;
    private final Color[] colors;

    private Paint borderPaint;
    private Stroke borderStroke;

}
