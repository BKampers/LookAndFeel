/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.geom.*;


public class OvalDotRenderer extends PointRenderer<Ellipse2D.Float> {
    
    
    public OvalDotRenderer(int width, int height, AreaLooks paintFactory) {
        super(width, height, paintFactory);
    }
    
    
    public OvalDotRenderer(int size, AreaLooks paintFactory) {
        super(size, size, paintFactory);
    }
    
    
    public OvalDotRenderer(AreaLooks paintFactory) {
        super(7, 7, paintFactory);
    }
    
    
    @Override
    protected Ellipse2D.Float createArea(int x, int y) {
        return new Ellipse2D.Float(x - width / 2.0f, y - height / 2.0f, width, height);
    }
    
}
