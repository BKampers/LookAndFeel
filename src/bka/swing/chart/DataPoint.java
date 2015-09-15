/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


/**
 * Holds the x-value, y-value and computed pixel of a point in a chart.
 */
public class DataPoint implements DataPointInterface {

    
    DataPoint(Number x, Number y, java.awt.Point pixel) {
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
    public java.awt.Point getPixel() {
        return pixel;
    }


    @Override
    public java.awt.Point  getHighlightPosition() {
        return pixel;
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
        return other instanceof DataPoint && pixel.equals(((DataPoint) other).pixel);
    }

    
    @Override
    public int compareTo(DataPointInterface other) {
        return
            (pixel.x < other.getPixel().x) ? -1 :
            (pixel.x > other.getPixel().x) ? 1 :
            (pixel.y < other.getPixel().y) ? -1 :
            (pixel.y > other.getPixel().y) ? 1 :
            0;
    }


    private final Number x;
    private final Number y;
    private final java.awt.Point pixel;
    
}
