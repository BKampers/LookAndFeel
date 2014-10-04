/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


public abstract class DemarcationRenderer {
    
    
    public abstract void draw(java.awt.Graphics2D g2d);
    
    
    void setPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }
    
    protected ChartPanel chartPanel = null;
    
}
