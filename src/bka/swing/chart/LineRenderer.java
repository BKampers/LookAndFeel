/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;
import java.util.*;

public abstract class LineRenderer extends AbstractDataAreaRenderer<PixelAreaGeometry> {


    LineRenderer(LineLooks lineLooks, int markerWidth, int markerHeight) {
        super(lineLooks.getAreaLooks());
        this.lineLooks = lineLooks;
        this.markerWidth = markerWidth;
        this.markerHeight = markerHeight;
    }


    protected abstract void draw(Graphics2D g2d, PixelAreaGeometry geometry1, PixelAreaGeometry geometry2);
    
    
    @Override
    public void draw(Graphics2D g2d, TreeSet<PixelAreaGeometry> graphGeometry) {
        previous = null;
        super.draw(g2d, graphGeometry);
    }


    @Override
    TreeSet<PixelAreaGeometry> createDataGeomerty(Map<Number, Number> graph) {
        TreeSet<PixelAreaGeometry> dataGeometry = new TreeSet<>();
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            Number x = entry.getKey();
            Number y = entry.getValue();
            int xPixel = chartGeometry.xPixel(x);
            int yPixel = chartGeometry.yPixel(y);
            Rectangle area = createArea(xPixel, yPixel);
            dataGeometry.add(new PixelAreaGeometry(x, y, area, new Point(xPixel, yPixel)));
        }
        return dataGeometry;
    }


    protected Rectangle createArea(int x, int y) {
        return new Rectangle(x - markerWidth / 2, y - markerHeight / 2, markerWidth, markerHeight);
    }


    protected final LineLooks lineLooks;
    protected final int markerWidth;
    protected final int markerHeight;

    protected PixelAreaGeometry previous;
    
}
