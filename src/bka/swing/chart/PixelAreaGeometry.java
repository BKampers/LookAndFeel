/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import java.awt.*;


/**
 * Holds the x-value, y-value and computed pixel of a point in a chart.
 * @param <S> Area (Shape) this date ocupies on the chart canvas
 */
public class PixelAreaGeometry<S extends Shape> implements DataAreaGeometry<S> {

    
    PixelAreaGeometry(Number x, Number y, S area, Point pixel) {
        this.x = x;
        this.y = y;
        this.area = area;
        this.pixel = pixel;
    }
    
    
    PixelAreaGeometry(Number x, Number y, Point pixel) {
        this(x, y, (S) defaultArea(pixel), pixel);
    }


    @Override
    public Number getX() {
        return x;
    }
    
    
    @Override
    public Number getY() {
        return y;
    }
    
    
    @Override
    public S getArea() {
        return area;
    }


    @Override
    public int hashCode() {
        return pixel.hashCode();
    }
    
    
    @Override
    public boolean equals(Object other) {
        return other instanceof PixelAreaGeometry && pixel.equals(((PixelAreaGeometry) other).pixel);
    }

    
    @Override
    public int compareTo(DataAreaGeometry other) {
        PixelAreaGeometry otherGeometry = (PixelAreaGeometry) other;
        return
            (pixel.x < otherGeometry.pixel.x) ? -1 :
            (pixel.x > otherGeometry.pixel.x) ? 1 :
            (pixel.y < otherGeometry.pixel.y) ? -1 :
            (pixel.y > otherGeometry.pixel.y) ? 1 :
            0;
    }


    public java.awt.Point getPixel() {
        return pixel;
    }


    private static Rectangle defaultArea(Point pixel) {
        return new Rectangle(pixel.x - 1, pixel.y - 1, 3, 3);
    }

    private final Number x;
    private final Number y;
    private final S area;
    private final Point pixel;
    
}
