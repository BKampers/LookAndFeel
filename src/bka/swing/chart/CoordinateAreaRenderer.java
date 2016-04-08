/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import java.awt.*;
import java.util.*;


public abstract class CoordinateAreaRenderer<S extends Shape> extends AbstractDataAreaRenderer<AreaGeometry<S>, S> {


    CoordinateAreaRenderer(AreaLooks looks) {
        super(looks);
    }


    protected abstract S createArea(int x, int y);


    @Override
    protected S createSymbolArea(int x, int y) {
        return createArea(x, y);
    }


    @Override
    protected AreaGeometry<S> createSymbolGeometry(S area) {
        return new AreaGeometry<>(null, null, area);
    }


    S createArea(Number x, Number y) {
        int pixelX = chartGeometry.xPixel(x);
        int pixelY = chartGeometry.yPixel(y);
        return createArea(pixelX, pixelY);
    }


    @Override
    TreeSet<AreaGeometry<S>> createGraphGeomerty(Map<Number, Number> graph) {
        TreeSet<AreaGeometry<S>> geometry = new TreeSet<>();
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            Number x = entry.getKey();
            Number y = entry.getValue();
            geometry.add(new AreaGeometry<>(x, y, createArea(x,y)));
        }
        return geometry;
    }

}
