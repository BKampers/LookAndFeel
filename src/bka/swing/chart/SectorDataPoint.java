/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


class SectorDataPoint implements DataPointInterface {


    SectorDataPoint(Number x, Number y, PieSectorRenderer renderer) {
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
    public Point getPixel() {
        return getHighlightPosition();
    }


    @Override
    public Point getHighlightPosition() {
        java.awt.geom.Arc2D arc = renderer.getArc(this);
        return new Point((int) arc.getX(), (int) arc.getY());
    }


    @Override
    public boolean contains(Point point) {
        return renderer.getArc(this).contains(point);
    }

    @Override
    public int hashCode() {
        return x.hashCode() ^ y.hashCode();
    }


    @Override
    public boolean equals(Object other) {
        return
            other instanceof SectorDataPoint &&
            renderer == ((SectorDataPoint) other).renderer &&
            x.equals(((SectorDataPoint) other).x) &&
            y.equals(((SectorDataPoint) other).y);
    }


    @Override
    public int compareTo(DataPointInterface other) {
        return
            (x.doubleValue() < other.getX().doubleValue()) ? -1 :
            (x.doubleValue() > other.getX().doubleValue()) ? 1 :
            (y.doubleValue() < other.getY().doubleValue()) ? -1 :
            (y.doubleValue() > other.getY().doubleValue()) ? 1 :
            0;
    }


    private final Number x;
    private final Number y;
    private final PieSectorRenderer renderer;

}
