/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;
import java.util.*;

public abstract class LineRenderer extends AbstractDataAreaRenderer<PixelAreaGeometry> {

    LineRenderer(LineLooks lineLooks) {
        super(lineLooks.getAreaLooks());
        this.lineLooks = lineLooks;
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
            dataGeometry.add(new PixelAreaGeometry(x, y, new Point(chartGeometry.xPixel(x), chartGeometry.yPixel(y))));
        }
        return dataGeometry;
    }


    protected final LineLooks lineLooks;

    protected PixelAreaGeometry previous;
    
}
