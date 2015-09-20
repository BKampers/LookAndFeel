/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


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
    public void drawSymbol(java.awt.Graphics2D g2d, int x, int y) {
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_OFF);
        g2d.setColor(color);
        g2d.fillOval(x - width / 2, y - height / 2, width, height);
    }
    

    protected int width;
    protected int height;

}
