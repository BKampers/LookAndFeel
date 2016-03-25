/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;
import java.awt.geom.*;


public class OvalDotRenderer extends PointRenderer {
    
    
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
    protected Shape createArea(int x, int y) {
        return new Ellipse2D.Double(x - width / 2, y - height / 2, width, height);
    }
    

    protected int width;
    protected int height;

}
