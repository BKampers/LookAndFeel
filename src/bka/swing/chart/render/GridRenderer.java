/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.*;


public abstract class GridRenderer {
    
    
    public abstract void draw(java.awt.Graphics2D g2d);
    
    
    public void setPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }
    

    protected ChartPanel chartPanel = null;
    
}
