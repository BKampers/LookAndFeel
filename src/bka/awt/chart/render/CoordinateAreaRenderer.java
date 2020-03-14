/*
** Copyright © Bart Kampers
*/

package bka.awt.chart.render;


import bka.awt.chart.*;
import bka.awt.chart.custom.*;
import bka.awt.chart.geometry.*;
import java.awt.*;


public abstract class CoordinateAreaRenderer<S extends Shape> extends AbstractDataAreaRendererBase<AreaGeometry<S>> {


    CoordinateAreaRenderer(AreaDrawStyle drawStyle) {
        super(drawStyle);
    }


    //protected abstract S createArea(int x, int y);
    protected abstract S createArea(Number x, Number y);
    protected abstract S createSymbolArea(int x, int y);


    @Override
    public GraphGeometry<AreaGeometry<S>> createGraphGeomerty(ChartData<Number, Number> chart) {
        GraphGeometry<AreaGeometry<S>> graphGeometry = new GraphGeometry<>();
        for (ChartDataElement<Number, Number> element : chart) {
            if (! element.isOutsideWindow()) {
                Number x = element.getKey();
                Number y = getY(element);
                graphGeometry.add(new AreaGeometry<>(x, y, createArea(x, y)));
            }
        }
        return graphGeometry;
    }


    @Override
    protected AreaGeometry<S> createSymbolGeometry(int x, int y) {
        return new AreaGeometry<>(null, null, createSymbolArea(x, y));
    }


}
