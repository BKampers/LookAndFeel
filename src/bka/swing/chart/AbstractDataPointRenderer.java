/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.util.*;



public abstract class AbstractDataPointRenderer {

    
    public abstract void draw(Graphics2D g2d, DataPoint dataPoint);
    public abstract void drawSymbol(java.awt.Graphics2D g2d, int x, int y);

    
    public void reset(ChartPanel chartPanel, TreeSet<DataPoint> graph) {
        this.chartPanel = chartPanel;
        this.graph = graph;
    }
    
        
    protected ChartPanel chartPanel;
    protected TreeSet<DataPoint> graph;
    
}
