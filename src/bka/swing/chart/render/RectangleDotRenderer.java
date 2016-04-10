/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.custom.AreaLooks;
import java.awt.Rectangle;


public class RectangleDotRenderer extends PointRenderer<Rectangle> {
    
    
    public RectangleDotRenderer(int width, int height, AreaLooks paintFactory) {
        super(width, height, paintFactory);
    }

    
    public RectangleDotRenderer(int size, AreaLooks paintFactory) {
        super(size, size, paintFactory);
    }

    
    public RectangleDotRenderer(AreaLooks paintFactory) {
        super(7, 7, paintFactory);
    }
    

    @Override
    protected Rectangle createArea(int x, int y) {
        return new Rectangle(x - width / 2, y - height / 2, width, height);
    }
    
}
