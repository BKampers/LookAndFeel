/*
** Copyright © Bart Kampers
*/

package bka.awt;

import java.awt.*;
import java.awt.geom.*;


public class PolygonFactory {


    public static Polygon createPolygon(int count, int radius) {
        if (count < 3) {
            throw new IllegalArgumentException();
        }
        Polygon polygon = new Polygon();
        Point origin = new Point(0, 0);
        Point point = new Point(0, -radius);
        polygon.addPoint(point.x, point.y);
        int i = 1;
        while (i < count) {
            point = rotate(point.x, point.y, origin, ((2 * Math.PI) / count));
            polygon.addPoint(point.x, point.y);
            i++;
        }
        return polygon;
    }


    public static Polygon createStar(int count, int innerRadius, int outerRadius) {
        if (count < 2) {
            throw new IllegalArgumentException();
        }
        Polygon star = new Polygon();
        Point origin = new Point(0, 0);
        Point outerPoint = new Point(0, -outerRadius);
        star.addPoint(outerPoint.x, outerPoint.y);
        Point innerPoint = new Point(0, -innerRadius);
        innerPoint = rotate(innerPoint.x, innerPoint.y, origin, Math.PI / count);
        star.addPoint(innerPoint.x, innerPoint.y);
        int i = 1;
        while (i < count) {
            outerPoint = rotate(outerPoint.x, outerPoint.y, origin, ((2 * Math.PI) / count));
            star.addPoint(outerPoint.x, outerPoint.y);
            innerPoint = rotate(innerPoint.x, innerPoint.y, origin, ((2 * Math.PI) / count));
            star.addPoint(innerPoint.x, innerPoint.y);
            i++;
        }
        return star;
    }


    private static Point rotate(int x, int y, Point center, double radians) {
        AffineTransform transform = AffineTransform.getRotateInstance(radians, center.x, center.y);
        double[] transformed = new double[2];
        transform.transform(new double[] {x, y}, 0, transformed, 0, 1);
        return new Point(Math.round((float) transformed[0]), Math.round((float) transformed[1]));
    }


}
