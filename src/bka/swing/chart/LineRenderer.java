/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public abstract class LineRenderer extends AbstractDataPointRenderer {
    
    
    public abstract void draw(java.awt.Graphics2D g2d, DataPoint dataPoint1, DataPoint dataPoint2);
    
    
    @Override
    public void reset(ChartPanel chartPanel, java.util.TreeSet<DataPoint> graph) {
        super.reset(chartPanel, graph);
        previous = null;
    }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    
    public Color getColor() {
        return color;
    }
    
    
    protected Color color;
    protected DataPoint previous;
    
}
