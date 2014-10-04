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
    
    
    public Number getX() {
        return x;
    }
    
    
    public Number getY() {
        return y;
    }
    
    
    public java.awt.Point getPixel() {
        return pixel;
    }
    
    
    public boolean equals(Object other) {
        return other instanceof DataPoint && pixel.equals(((DataPoint) other).pixel);
    }

    
    public int compareTo(DataPoint other) {
        return
            (pixel.x < other.pixel.x) ? -1 :
            (pixel.x > other.pixel.x) ?  1 :
            (pixel.y < other.pixel.y) ? -1 : 
            (pixel.y > other.pixel.y) ?  1 :     
                                         0;
    }

    
    private Number x;
    private Number y;
    private java.awt.Point pixel;
    
}
