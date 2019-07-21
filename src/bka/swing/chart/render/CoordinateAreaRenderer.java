/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.*;
import bka.swing.chart.custom.*;
import bka.swing.chart.geometry.*;
import java.awt.*;
import java.util.*;


public abstract class CoordinateAreaRenderer<S extends Shape> extends AbstractDataAreaRenderer<AreaGeometry<S>> {


    CoordinateAreaRenderer(AreaDrawStyle drawStyle) {
        super(drawStyle);
    }


    protected abstract S createArea(int x, int y);


    @Override
    public java.util.List<AreaGeometry<S>> createGraphGeomerty(ChartData<Number, Number> chart) {
        ArrayList<AreaGeometry<S>> geometry = new ArrayList<>();
        for (ChartDataElement<Number, Number> element : chart) {
            Number x = element.getKey();
            Number y = element.getValue();
            geometry.add(new AreaGeometry<>(x, y, createArea(x, y)));
        }
        return geometry;
    }


    @Override
    protected AreaGeometry<S> createSymbolGeometry(int x, int y, AreaGeometry<S> geometry) {
        return new AreaGeometry<>(null, null, createArea(x, y));
    }


    private S createArea(Number x, Number y) {
        int pixelX = chartGeometry.xPixel(x);
        int pixelY = chartGeometry.yPixel(y);
        return createArea(pixelX, pixelY);
    }


}
