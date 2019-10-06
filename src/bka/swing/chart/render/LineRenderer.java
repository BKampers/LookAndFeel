/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.*;
import bka.swing.chart.custom.*;
import bka.swing.chart.geometry.*;
import java.awt.*;
import java.awt.geom.*;


public abstract class LineRenderer extends AbstractDataAreaRenderer<PixelAreaGeometry> {


    LineRenderer(LineDrawStyle drawStyle, int markerWidth, int markerHeight) {
        super(drawStyle.getAreaDrawStyle());
        this.lineDrawStyle = drawStyle;
        this.markerWidth = markerWidth;
        this.markerHeight = markerHeight;
    }

    
    @Override
    public GraphGeometry createGraphGeomerty(ChartData<Number, Number> chart) {
        GraphGeometry<AreaGeometry> dataGeometry = new GraphGeometry<>();
        for (int i = 1; i < chart.size(); ++i) {
            ChartDataElement<Number, Number> p0 = chart.get(i - 1);
            ChartDataElement<Number, Number> p1 = chart.get(i);
            add(p0, dataGeometry);
            add(p1, dataGeometry);
        }
        return dataGeometry;
    }


    private void add(ChartDataElement<Number, Number> element, GraphGeometry<AreaGeometry> dataGeometry) {
        add(element.getKey(), element.getValue(), dataGeometry, ! element.isOutsideWindow());
    }


    private void add(Number x, Number y, GraphGeometry<AreaGeometry> dataGeometry) {
        add(x, y, dataGeometry, false);
    }


    private void add(Number x, Number y, GraphGeometry<AreaGeometry> dataGeometry, boolean createArea) {
        int xPixel = chartGeometry.xPixel(x);
        int yPixel = chartGeometry.yPixel(y);
        RectangularShape area = (createArea) ? createSymbolArea(xPixel, yPixel) : null;
        dataGeometry.add(new PixelAreaGeometry(x, y, area, new Point(xPixel, yPixel)));
    }

    
    protected RectangularShape createSymbolArea(int x, int y) {
        return new Rectangle(x - markerWidth / 2, y - markerHeight / 2, markerWidth, markerHeight);
    }


    protected final LineDrawStyle lineDrawStyle;
    protected final int markerWidth;
    protected final int markerHeight;
    
}
