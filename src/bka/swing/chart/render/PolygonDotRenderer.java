/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.custom.AreaLooks;
import java.awt.*;
import java.awt.geom.*;


public class PolygonDotRenderer extends PointRenderer<Polygon> {
    
    
    public PolygonDotRenderer(int width, int height, AreaLooks looks) {
        super(width, height, looks);
    }
    
    
    public PolygonDotRenderer(int size, AreaLooks looks) {
        super(size, size, looks);
    }
    
    
    public PolygonDotRenderer(AreaLooks paintFactory) {
        super(7, 7, paintFactory);
    }
    
    
    @Override
    protected Polygon createArea(int x, int y) {
        Polygon polygon = new Polygon();
        Point point = new Point(x, y - height/2);
        int i = 0;
        while (i < 5) {
            point = rotate(point.x, point.y, new Point(x,y), ((2 * Math.PI) / 5));
            polygon.addPoint(point.x, point.y);
            i++;
        }
        return polygon;
    }


    private static Point rotate(int x, int y, Point center, double radians) {
        double[] src = {x, y};
        double[] dst = new double[2];
        AffineTransform.getRotateInstance(radians, center.x, center.y).transform(src, 0, dst, 0, 1); // specifying to use this double[] to hold coords
        double newX = dst[0];
        double newY = dst[1];
        return new Point((int) Math.round(newX), (int) Math.round(newY));
    }
    
}
