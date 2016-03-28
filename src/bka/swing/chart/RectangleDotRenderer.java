/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import java.awt.Rectangle;


public class RectangleDotRenderer extends PointRenderer<Rectangle> {
    
    
    public RectangleDotRenderer(int width, int height) {
        this.width = width;
        this.height = height;
    }

    
    public RectangleDotRenderer(int size) {
        this(size, size);
    }

    
    public RectangleDotRenderer() {
        this(7, 7);
    }
    

    @Override
    protected Rectangle createArea(int x, int y) {
        return new Rectangle(x - width / 2, y - height / 2, width, height);
    }
    

    protected int width;
    protected int height;

}
