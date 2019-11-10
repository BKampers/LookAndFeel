/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;

import bka.swing.chart.*;
import bka.swing.chart.custom.*;
import bka.swing.chart.geometry.*;
import java.awt.*;
import java.util.*;


public class ScatterRenderer<S extends Shape> extends AbstractDataAreaRenderer<ScatterGeometry<S>> {


    public ScatterRenderer(AreaDrawStyle drawStyle) {
        super(drawStyle);
    }


    @Override
    public GraphGeometry<ScatterGeometry<S>> createGraphGeomerty(ChartData<Number, Number> chart) {
        Map<ChartDataElement<Number, Number>, ScatterGeometry<S>> map = new HashMap<>();
        for (ChartDataElement<Number, Number> element : chart) {
            Number x = element.getKey();
            Number y = element.getValue();
            ScatterGeometry<S> scaterGeometry = map.get(element);
            int count = (scaterGeometry == null) ? 1 : scaterGeometry.getCount() + 1;
            Shape area = createShape(getWindow().xPixel(x), getWindow().yPixel(y), count * 3.0f);
            scaterGeometry = new ScatterGeometry(x, y, area, count);
            map.put(element, scaterGeometry);
        }
        return new GraphGeometry<>(map.values());
    }


    @Override
    protected ScatterGeometry<S> createSymbolGeometry(int x, int y, ScatterGeometry<S> geometry) {
        Shape shape = createShape(x, y, 3.0f);
        return new ScatterGeometry(x, y, shape);
    }


    private Shape createShape(float x, float y, float diameter) {
        float radius = diameter / 2.0f;
        return new java.awt.geom.Ellipse2D.Float(x - radius, y - radius, diameter, diameter);
    }


}
