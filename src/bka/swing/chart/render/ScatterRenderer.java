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


    public ScatterRenderer(AreaLooks looks) {
        super(looks);
    }


    @Override
    public java.util.List<ScatterGeometry<S>> createGraphGeomerty(ChartData<Number, Number> chart) {
        ArrayList<ScatterGeometry<S>> graphGeometry = new ArrayList<>();
        for (ChartDataElement<Number, Number> element : chart) {
            Number x = element.getKey();
            Number y = element.getValue();
            ScatterGeometry<S> scatterGeometry = null;
            for (ScatterGeometry<S> g : graphGeometry) {
                if (x.equals(g.getX()) && y.equals(g.getY())) {
                    scatterGeometry = g;
                }
            }
            if (scatterGeometry == null) {
                Shape area = createShape(chartGeometry.xPixel(x), chartGeometry.yPixel(y), 7.0f);
                scatterGeometry = new ScatterGeometry(x, y, area);
            }
            else {
                graphGeometry.remove(scatterGeometry);
                int count = scatterGeometry.getCount() + 1;
                Shape area = createShape(chartGeometry.xPixel(x), chartGeometry.yPixel(y), 1.0f + count * 2.0f);
                scatterGeometry = new ScatterGeometry(x, y, area, count);
            }
            graphGeometry.add(scatterGeometry);
        }
        return graphGeometry;
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
