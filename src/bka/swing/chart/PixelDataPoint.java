/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import java.awt.*;


/**
 * Holds the x-value, y-value and computed pixel of a point in a chart.
 */
public class PixelDataPoint implements DataPoint {

    
    PixelDataPoint(Number x, Number y, Shape area, Point pixel) {
        this.x = x;
        this.y = y;
        this.area = area;
        this.pixel = pixel;
    }
    
    
    PixelDataPoint(Number x, Number y, Point pixel) {
        this(x, y, new Rectangle(pixel.x - 1, pixel.y - 1, 3, 3), pixel);
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
    public Shape getArea() {
        return area;
    }


    @Override
    public int hashCode() {
        return pixel.hashCode();
    }
    
    
    @Override
    public boolean equals(Object other) {
        return other instanceof PixelDataPoint && pixel.equals(((PixelDataPoint) other).pixel);
    }

    
    @Override
    public int compareTo(DataPoint other) {
        PixelDataPoint otherPoint = (PixelDataPoint) other;
        return
            (pixel.x < otherPoint.pixel.x) ? -1 :
            (pixel.x > otherPoint.pixel.x) ? 1 :
            (pixel.y < otherPoint.pixel.y) ? -1 :
            (pixel.y > otherPoint.pixel.y) ? 1 :
            0;
    }


    public java.awt.Point getPixel() {
        return pixel;
    }


    private final Number x;
    private final Number y;
    private final Shape area;
    private final Point pixel;
    
}
