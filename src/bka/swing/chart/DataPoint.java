/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


/**
 * Holds the x-value, y-value and computed pixel of a point in a chart.
 */
public class DataPoint implements Comparable<DataPoint> {

    
    DataPoint(Number x, Number y, java.awt.Point pixel) {
        this.x = x;
        this.y = y;
        this.pixel = pixel;
    }
    
    
    Number getX() {
        return x;
    }
    
    
    Number getY() {
        return y;
    }
    
    
    java.awt.Point getPixel() {
        return pixel;
    }


    boolean contains(java.awt.Point point) {
        java.awt.Rectangle rectangle = new java.awt.Rectangle(pixel.x-10, pixel.y-10, 20, 20);
        return rectangle.contains(point);
    }


    @Override
    public int hashCode() {
        return pixel.hashCode();
    }
    
    
    @Override
    public boolean equals(Object other) {
        return other instanceof DataPoint && pixel.equals(((DataPoint) other).pixel);
    }

    
    @Override
    public int compareTo(DataPoint other) {
        return
            (pixel.x < other.pixel.x) ? -1 :
            (pixel.x > other.pixel.x) ? 1 :
            (pixel.y < other.pixel.y) ? -1 : 
            (pixel.y > other.pixel.y) ? 1 :     
            0;
    }


    private final Number x;
    private final Number y;
    private final java.awt.Point pixel;
    
}
