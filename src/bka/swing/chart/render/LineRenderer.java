/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.custom.LineLooks;
import bka.swing.chart.geometry.PixelAreaGeometry;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;


public abstract class LineRenderer extends AbstractDataAreaRenderer<PixelAreaGeometry<RectangularShape>> {


    LineRenderer(LineLooks lineLooks, int markerWidth, int markerHeight) {
        super(lineLooks.getAreaLooks());
        this.lineLooks = lineLooks;
        this.markerWidth = markerWidth;
        this.markerHeight = markerHeight;
    }

    
    @Override
    public TreeSet<PixelAreaGeometry<RectangularShape>> createGraphGeomerty(Map<Number, Number> graph) {
        TreeSet<PixelAreaGeometry<RectangularShape>> dataGeometry = new TreeSet<>();
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            Number x = entry.getKey();
            Number y = entry.getValue();
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


    protected final LineLooks lineLooks;
    protected final int markerWidth;
    protected final int markerHeight;
    
}
