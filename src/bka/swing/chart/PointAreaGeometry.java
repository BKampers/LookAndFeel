/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


/**
 * Information needed for rendering and highlighting data in a graph.
 * @param <S> Area (Shape) this date ocupies on the chart canvas
 */
public class PointAreaGeometry<S extends java.awt.Shape> implements Comparable<PointAreaGeometry> {

    public PointAreaGeometry(Number x, Number y, S area) {
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


    @Override
    public int compareTo(PointAreaGeometry other) {
        return
            (x.doubleValue() < other.getX().doubleValue()) ? -1 :
            (x.doubleValue() > other.getX().doubleValue()) ? 1 :
            (y.doubleValue() < other.getY().doubleValue()) ? -1 :
            (y.doubleValue() > other.getY().doubleValue()) ? 1 :
            0;
    }


    @Override
    public boolean equals(Object other) {
        return
            other == this ||
            other instanceof PointAreaGeometry &&
            area.equals(((PointAreaGeometry) other).area);
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + java.util.Objects.hashCode(this.area);
        return hash;
    }


    protected final Number x;
    protected final Number y;
    protected final S area;
    
}
