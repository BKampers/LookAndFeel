/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.*;
import bka.swing.chart.custom.*;
import bka.swing.chart.geometry.*;
import java.awt.*;


public abstract class CoordinateAreaRenderer<S extends Shape> extends AbstractDataAreaRenderer<AreaGeometry<S>> {


    CoordinateAreaRenderer(AreaDrawStyle drawStyle) {
        super(drawStyle);
    }


    protected abstract S createArea(int x, int y);


    @Override
    public GraphGeometry<AreaGeometry<S>> createGraphGeomerty(ChartData<Number, Number> chart) {
        GraphGeometry<AreaGeometry<S>> graphGeometry = new GraphGeometry<>();
        for (ChartDataElement<Number, Number> element : chart) {
            if (! element.isOutOfRange()) {
                Number x = element.getKey();
                Number y = element.getValue();
                graphGeometry.add(new AreaGeometry<>(x, y, createArea(x, y)));
            }
        }
        return graphGeometry;
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
