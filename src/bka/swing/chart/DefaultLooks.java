/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public class DefaultLooks implements AreaLooks<Shape> {

    private DefaultLooks(Color color) {
        this.color = color;
    }


    public static DefaultLooks create(Color color) {
        return new DefaultLooks(color);
    }


    @Override
    public Paint getPaint(Shape area) {
        return Color.BLACK;
    }


    private final Color color;

}
