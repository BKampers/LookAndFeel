/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.custom.AreaLooks;
import java.awt.geom.*;


public class OvalDotRenderer extends PointRenderer<Ellipse2D.Float> {
    
    
    public OvalDotRenderer(int width, int height, AreaLooks looks) {
        super(width, height, looks);
    }
    
    
    public OvalDotRenderer(int size, AreaLooks looks) {
        super(size, size, looks);
    }
    
    
    public OvalDotRenderer(AreaLooks paintFactory) {
        super(7, 7, paintFactory);
    }
    
    
    @Override
    protected Ellipse2D.Float createArea(int x, int y) {
        return new Ellipse2D.Float(x - width / 2.0f, y - height / 2.0f, width, height);
    }
    
}
