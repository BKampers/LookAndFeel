/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.custom;


import java.awt.*;
import java.awt.geom.*;


public class GridStyle {


    private GridStyle(Stroke stroke, Color gridColor, Color[] colors, boolean gradient) {
        this.stroke = stroke;
        this.gridColor = gridColor;
        this.colors = colors;
        this.gradient = gradient;
    }


    public static GridStyle create(Stroke stroke, Color gridColor, Color[] colors) {
        return new GridStyle(stroke, gridColor, colors, false);
    }


    public static GridStyle create(Stroke stroke, Color gridColor, Color[] colors, boolean gradient) {
        return new GridStyle(stroke, gridColor, colors, gradient);
    }


    public static GridStyle create(Color[] colors, boolean gradient) {
        return create(null, null, colors, gradient);
    }


    public static GridStyle create(Color[] colors) {
        return create(null, null, colors);
    }


    public static GridStyle create(Color color) {
        return create(new Color[] { color });
    }


    public Stroke getStroke() {
        return stroke;
    }


    public Paint getGridPaint() {
        return gridColor;
    }


    public Paint getHorizontalPaint(RectangularShape area, int index) {
        Color color = colors[index % colors.length];
        if (! gradient) {
            return color;
        }
        return new GradientPaint((float) area.getCenterX(), (float) area.getMinY(), color, (float) area.getCenterX(), (float) area.getMaxY(), color.darker());
    }


    public Paint getVerticalPaint(RectangularShape area, int index) {
        Color color = colors[index % colors.length];
        if (! gradient) {
            return color;
        }
        return new GradientPaint((float) area.getMaxX(), (float) area.getCenterY(), color, (float) area.getMinX(), (float) area.getCenterY(), color.darker());
    }


    private final Stroke stroke;
    private final Color gridColor;
    private final Color[] colors;
    private final boolean gradient;

}
