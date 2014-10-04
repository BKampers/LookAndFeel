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
    

    public void drawSymbol(java.awt.Graphics2D g2d, int x, int y) {
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.fillRect(x - width / 2, y - height / 2, width, height);
    }
    

    protected int width;
    protected int height;

}
