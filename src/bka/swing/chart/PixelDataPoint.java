/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


/**
 * Holds the x-value, y-value and computed pixel of a point in a chart.
 */
public class PixelDataPoint implements DataPoint {

    
    PixelDataPoint(Number x, Number y, java.awt.Point pixel) {
        this.x = x;
        this.y = y;
        this.pixel = pixel;
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
    public boolean contains(java.awt.Point point) {
        java.awt.Rectangle rectangle = new java.awt.Rectangle(pixel.x-10, pixel.y-10, 20, 20);
        return rectangle.contains(point);
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
    private final java.awt.Point pixel;
    
}
