/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


class ArcDataPoint implements DataPoint {


    ArcDataPoint(Number x, Number y, PieSectorRenderer renderer) {
        this.x = x;
        this.y = y;
        this.renderer = renderer;
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
        return renderer.getArc(this);
    }


    @Override
    public boolean contains(Point point) {
        return getArea().contains(point);
    }

    @Override
    public int hashCode() {
        return x.hashCode() + 3 * y.hashCode();
    }


    @Override
    public boolean equals(Object other) {
        return
            other == this ||
            other instanceof ArcDataPoint &&
            renderer == ((ArcDataPoint) other).renderer &&
            x.equals(((ArcDataPoint) other).x) &&
            y.equals(((ArcDataPoint) other).y);
    }


    @Override
    public int compareTo(DataPoint other) {
        ArcDataPoint otherPoint = (ArcDataPoint) other;
        return
            (x.doubleValue() < otherPoint.x.doubleValue()) ? -1 :
            (x.doubleValue() > otherPoint.x.doubleValue()) ? 1 :
            (y.doubleValue() < otherPoint.y.doubleValue()) ? -1 :
            (y.doubleValue() > otherPoint.y.doubleValue()) ? 1 :
            0;
    }


    private final Number x;
    private final Number y;
    private final PieSectorRenderer renderer;

}
