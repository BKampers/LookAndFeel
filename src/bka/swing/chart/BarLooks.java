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
            new Color[] {edgeColor, centerColor, edgeColor});
    }


    @Override
    public Paint getPaint(Rectangle area) {
        return new LinearGradientPaint(area.x, area.y, area.x + area.width, area.y, pattern, colors);
    }


    private final float[] pattern;
    private final Color[] colors;

}
