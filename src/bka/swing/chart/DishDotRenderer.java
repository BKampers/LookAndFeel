/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


public class DishDotRenderer extends OvalDotRenderer {
    
    
    public DishDotRenderer(int size, int offset) {
        super(size, size);
        this.offset = offset;
    }

    
    public void drawSymbol(java.awt.Graphics2D g2d, int x, int y) {
        super.drawSymbol(g2d, x, y);
        int xRadius = width / 2 + offset / 2;
        int yRadius = height / 2 + offset / 2;
        g2d.drawOval(x - xRadius, y - yRadius, width + offset, height + offset);
    }
    
    
    private int offset;

}
