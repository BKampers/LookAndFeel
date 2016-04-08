/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import java.awt.*;


/**
 * Holds the x-value, y-value and computed pixel of a point in a chart.
 * @param <S> Area (Shape) this date ocupies on the chart canvas
 */
public class PixelAreaGeometry<S extends Shape> extends PointAreaGeometry<S> {

    
    PixelAreaGeometry(Number x, Number y, S area, Point pixel) {
        super(x, y, area);
        this.pixel = pixel;
    }
    
    
    PixelAreaGeometry(Number x, Number y, Point pixel) {
        this(x, y, (S) defaultArea(pixel), pixel);
    }


    public java.awt.Point getPixel() {
        return pixel;
    }


    private static Rectangle defaultArea(Point pixel) {
        return new Rectangle(pixel.x - 5, pixel.y - 5, 11, 11);
    }


    private final Point pixel;
    
}
