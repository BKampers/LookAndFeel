/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.geometry;


/**
 * Information needed for rendering and highlighting data in a graph.
 * @param <S> Area (Shape) this date ocupies on the chart canvas
 */
public class AreaGeometry<S extends java.awt.Shape> {

    public AreaGeometry(Number x, Number y, S area) {
        this.x = x;
        this.y = y;
        this.area = area;
    }


    public Number getX() {
        return x;
    }


    public Number getY() {
        return y;
    }


    public S getArea() {
        return area;
    }


    protected final Number x;
    protected final Number y;
    protected final S area;
    
}
