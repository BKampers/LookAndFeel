/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.custom;


import bka.swing.chart.geometry.*;
import java.awt.*;


public class DefaultDrawStyle implements AreaDrawStyle<AreaGeometry> {


    private DefaultDrawStyle(Color color, Color borderColor, Stroke borderStroke) {
        this.color = color;
        this.borderColor = borderColor;
        this.borderStroke = borderStroke;
    }


    public static DefaultDrawStyle create(Color color, Color borderColor, Stroke borderStroke) {
        return new DefaultDrawStyle(color, borderColor, borderStroke);
    }


    public static DefaultDrawStyle create(Color color, Color borderColor) {
        return new DefaultDrawStyle(color, borderColor, new BasicStroke());
    }


    public static DefaultDrawStyle createSolid(Color color) {
        return new DefaultDrawStyle(color, null, null);
    }


    public static DefaultDrawStyle createBorder(Color borderColor, Stroke borderStroke) {
        return new DefaultDrawStyle(null, borderColor, borderStroke);
    }


    public static DefaultDrawStyle createBorder(Color borderColor) {
        return new DefaultDrawStyle(null, borderColor, new BasicStroke());
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


    @Override
    public Paint getLabelPaint(AreaGeometry geometry) {
        return labelColor;
    }


    @Override
    public Font getLabelFont(AreaGeometry geometry) {
        return labelFont;
    }


    private final Color color;
    private final Color borderColor;
    private final Stroke borderStroke;
    private final Color labelColor = Color.BLACK;
    private Font labelFont;

}
