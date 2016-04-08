/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public class DefaultLooks implements AreaLooks<AreaGeometry> {


    private DefaultLooks(Color color, Color borderColor, Stroke borderStroke) {
        this.color = color;
        this.borderColor = borderColor;
        this.borderStroke = borderStroke;
    }


    public static DefaultLooks create(Color color, Color borderColor, Stroke borderStroke) {
        return new DefaultLooks(color, borderColor, borderStroke);
    }


    public static DefaultLooks create(Color color, Color borderColor) {
        return new DefaultLooks(color, borderColor, new BasicStroke());
    }


    public static DefaultLooks createSolid(Color color) {
        return new DefaultLooks(color, null, null);
    }


    public static DefaultLooks createBorder(Color borderColor, Stroke borderStroke) {
        return new DefaultLooks(null, borderColor, borderStroke);
    }


    public static DefaultLooks createBorder(Color borderColor) {
        return new DefaultLooks(null, borderColor, new BasicStroke());
    }


    @Override
    public Paint getPaint(AreaGeometry geometry) {
        return color;
    }


    @Override
    public Paint getBorderPaint(AreaGeometry geometry) {
        return borderColor;
    }


    @Override
    public Stroke getBorderStroke(AreaGeometry geometry) {
        return borderStroke;
    }


    private final Color color;
    private final Color borderColor;
    private final Stroke borderStroke;

}
