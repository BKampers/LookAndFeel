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
    public java.util.List<ScatterGeometry<S>> createGraphGeomerty(ChartData<Number, Number> chart) {
        Map<ChartDataElement<Number, Number>, ScatterGeometry<S>> map = new HashMap<>();
        for (ChartDataElement<Number, Number> element : chart) {
            Number x = element.getKey();
            Number y = element.getValue();
            ScatterGeometry<S> geom = map.get(element);
            int count = (geom == null) ? 1 : geom.getCount() + 1;
            Shape area = createShape(chartGeometry.xPixel(x), chartGeometry.yPixel(y), count * 3.0f);
            geom = new ScatterGeometry(x, y, area, count);
            map.put(element, geom);
        }
        return new ArrayList(map.values());
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
