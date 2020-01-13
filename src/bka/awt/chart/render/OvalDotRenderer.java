/*
** Copyright © Bart Kampers
*/

package bka.awt.chart.render;


import bka.awt.chart.custom.*;
import java.awt.geom.*;


public class OvalDotRenderer extends PointRenderer<Ellipse2D.Float> {
    
    
    public OvalDotRenderer(int width, int height, AreaDrawStyle drawStyle) {
        super(width, height, drawStyle);
    }
    
    
    public OvalDotRenderer(int size, AreaDrawStyle drawStyle) {
        super(size, size, drawStyle);
    }
    
    
    public OvalDotRenderer(AreaDrawStyle drawStyle) {
        super(DEFAULT_SIZE, DEFAULT_SIZE, drawStyle);
    }
    
    
    @Override
    protected Ellipse2D.Float createArea(int x, int y) {
        return new Ellipse2D.Float(x - width / 2.0f, y - height / 2.0f, width, height);
    }
    
    
    private static final int DEFAULT_SIZE = 7;

}
