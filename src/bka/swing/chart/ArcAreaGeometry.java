/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.geom.Arc2D;

public class ArcAreaGeometry implements DataAreaGeometry<Arc2D.Float> {


    ArcAreaGeometry(Number x, Number y, Arc2D.Float area) {
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
    public Arc2D.Float getArea() {
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
            other instanceof ArcAreaGeometry &&
            area.equals(((ArcAreaGeometry) other).area);
    }


    @Override
    public int compareTo(DataAreaGeometry other) {
        ArcAreaGeometry otherGeometry = (ArcAreaGeometry) other;
        return
            (x.doubleValue() < otherGeometry.x.doubleValue()) ? -1 :
            (x.doubleValue() > otherGeometry.x.doubleValue()) ? 1 :
            (y.doubleValue() < otherGeometry.y.doubleValue()) ? -1 :
            (y.doubleValue() > otherGeometry.y.doubleValue()) ? 1 :
            0;
    }


    private final Number x;
    private final Number y;
    private final Arc2D.Float area;
    
}
