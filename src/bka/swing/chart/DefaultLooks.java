/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public class DefaultLooks implements AreaLooks<Shape> {

    private DefaultLooks(Color color, Color borderColor, Stroke borderStroke) {
        this.color = color;
        this.borderColor = borderColor;
        this.borderStroke = borderStroke;
    }


    public static DefaultLooks create(Color color) {
        return new DefaultLooks(color, null, null);
    }


    public static DefaultLooks create(Color color, Color borderColor) {
        return new DefaultLooks(color, borderColor, null);
    }


    public static DefaultLooks create(Color color, Color borderColor, Stroke borderStroke) {
        return new DefaultLooks(color, borderColor, borderStroke);
    }


    @Override
    public Paint getPaint(Shape area) {
        return Color.BLACK;
    }


    @Override
    public Paint getBorderPaint(Shape area) {
        return borderColor;
    }


    @Override
    public Stroke getBorderStroke(Shape area) {
        return borderStroke;
    }


    private final Color color;
    private final Color borderColor;
    private final Stroke borderStroke;

}
