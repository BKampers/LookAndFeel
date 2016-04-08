/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import java.awt.*;
import java.util.*;


public abstract class CoordinateAreaRenderer<S extends Shape> extends AbstractDataAreaRenderer<AreaGeometry<S>> {


    CoordinateAreaRenderer(AreaLooks looks) {
        super(looks);
    }


    protected abstract S createArea(int x, int y);


    @Override
    protected void drawSymbol(Graphics2D g2d, int x, int y) {
        draw(g2d, createAreaGeometry(x, y));
    }


    S createArea(Number x, Number y) {
        int pixelX = chartGeometry.xPixel(x);
        int pixelY = chartGeometry.yPixel(y);
        return createArea(pixelX, pixelY);
    }


    AreaGeometry<S> createAreaGeometry(int pixelX, int pixelY) {
        return new AreaGeometry<>(null, null, createArea(pixelX, pixelY));
    }


    @Override
    TreeSet<AreaGeometry<S>> createDataGeomerty(Map<Number, Number> graph) {
        TreeSet<AreaGeometry<S>> geometry = new TreeSet<>();
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            Number x = entry.getKey();
            Number y = entry.getValue();
            geometry.add(new AreaGeometry<>(x, y, createArea(x,y)));
        }
        return geometry;
    }

}
