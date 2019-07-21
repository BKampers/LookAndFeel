/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.*;
import bka.swing.chart.custom.*;
import bka.swing.chart.geometry.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;


public abstract class LineRenderer extends AbstractDataAreaRenderer<PixelAreaGeometry> {


    LineRenderer(LineDrawStyle drawStyle, int markerWidth, int markerHeight) {
        super(drawStyle.getAreaDrawStyle());
        this.lineDrawStyle = drawStyle;
        this.markerWidth = markerWidth;
        this.markerHeight = markerHeight;
    }

    
    @Override
    public java.util.List<PixelAreaGeometry> createGraphGeomerty(ChartData<Number, Number> chart) {
        ArrayList<PixelAreaGeometry> dataGeometry = new ArrayList<>();
        for (ChartDataElement<Number, Number> element : chart) {
            Number x = element.getKey();
            Number y = element.getValue();
            int xPixel = chartGeometry.xPixel(x);
            int yPixel = chartGeometry.yPixel(y);
            RectangularShape area = createSymbolArea(xPixel, yPixel);
            dataGeometry.add(new PixelAreaGeometry(x, y, area, new Point(xPixel, yPixel)));
        }
        return dataGeometry;
    }

    
    protected RectangularShape createSymbolArea(int x, int y) {
        return new Rectangle(x - markerWidth / 2, y - markerHeight / 2, markerWidth, markerHeight);
    }


    protected final LineDrawStyle lineDrawStyle;
    protected final int markerWidth;
    protected final int markerHeight;
    
}
