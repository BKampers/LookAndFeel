/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.Color;


public abstract class LineRenderer {
    
    
    public abstract void draw(java.awt.Graphics2D g2d, DataPoint dataPoint1, DataPoint dataPoint2);
    public abstract void drawSymbol(java.awt.Graphics2D g2d, int x, int y);
    
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    
    public Color getColor() {
        return color;
    }
    
    
    protected Color color;
    
}
