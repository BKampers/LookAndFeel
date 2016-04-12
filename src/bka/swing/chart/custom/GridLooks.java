/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.custom;


import java.awt.*;
import java.awt.geom.*;


public class GridLooks {


    private GridLooks(Stroke stroke, Color gridColor, Color[] colors) {
        this.stroke = stroke;
        this.gridColor = gridColor;
        this.colors = colors;
    }


    public static GridLooks create(Stroke stroke, Color gridColor, Color[] colors) {
        return new GridLooks(stroke, gridColor, colors);
    }


    public static GridLooks create(Color[] colors) {
        return create(null, null, colors);
    }


    public static GridLooks create(Color color) {
        return create(new Color[] { color });
    }


    public Stroke getStroke() {
        return stroke;
    }


    public Paint getGridPaint() {
        return gridColor;
    }


    public Paint getHorizontalPaint(RectangularShape area, int index) {
        Color color1 = colors[index % colors.length];
        Color color2 = color1.darker();
        return new GradientPaint((float) area.getCenterX(), (float) area.getMinY(), color1, (float) area.getCenterX(), (float) area.getMaxY(), color2);
    }


    public Paint getVerticalPaint(RectangularShape area, int index) {
        Color color1 = colors[index % colors.length];
        Color color2 = color1.darker();
        return new GradientPaint((float) area.getMaxX(), (float) area.getCenterY(), color1, (float) area.getMinX(), (float) area.getCenterY(), color2);
    }


    private final Stroke stroke;
    private final Color gridColor;
    private final Color[] colors;

}
