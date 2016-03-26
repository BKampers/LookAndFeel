/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


public class RectangleDotRenderer extends PointRenderer {
    
    
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
    protected java.awt.Shape createArea(int x, int y) {
        return new java.awt.Rectangle(x - width / 2, y - height / 2, width, height);
    }
    

    protected int width;
    protected int height;

}
