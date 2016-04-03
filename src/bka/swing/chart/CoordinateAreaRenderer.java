/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import java.awt.*;
import java.util.*;

public abstract class CoordinateAreaRenderer<S extends Shape> extends AbstractDataAreaRenderer<PointAreaGeometry<S>> {

    @Override
    protected abstract void draw(Graphics2D g2d, PointAreaGeometry<S> geometry);

    protected abstract S createArea(int x, int y);

    
    @Override
    TreeSet<PointAreaGeometry<S>> createDataGeomerty(Map<Number, Number> graph) {
        TreeSet<PointAreaGeometry<S>> geometry = new TreeSet<>();
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            Number x = entry.getKey();
            Number y = entry.getValue();
            int pixelX = chartGeometry.xPixel(x);
            int pixelY = chartGeometry.yPixel(y);
            S area = createArea(pixelX, pixelY);
            geometry.add(new PointAreaGeometry(x, y, area));
        }
        return geometry;
    }
}
