/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;
import java.awt.geom.Arc2D;


class ArcDataPoint implements DataPoint {


    ArcDataPoint(Number x, Number y, Arc2D area) {
        this.x = x;
        this.y = y;
        this.area = area;
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
        return x.hashCode() + 3 * y.hashCode();
    }


    @Override
    public boolean equals(Object other) {
        return
            other == this ||
            other instanceof ArcDataPoint &&
            area.equals(((ArcDataPoint) other).area);
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
    private final Arc2D area;
    
}
