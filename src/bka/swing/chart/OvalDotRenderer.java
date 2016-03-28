/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.geom.*;


public class OvalDotRenderer extends PointRenderer<Ellipse2D.Float> {
    
    
    public OvalDotRenderer(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    
    public OvalDotRenderer(int size) {
        this(size, size);
    }
    
    
    public OvalDotRenderer() {
        this(7, 7);
    }
    
    
    @Override
    protected Ellipse2D.Float createArea(int x, int y) {
        return new Ellipse2D.Float(x - width / 2.0f, y - height / 2.0f, width, height);
    }
    

    protected int width;
    protected int height;

}
