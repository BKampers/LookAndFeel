/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.util.*;



public abstract class AbstractDataPointRenderer {

    
    public abstract void draw(Graphics2D g2d, DataPoint dataPoint);
    public abstract void draw(Graphics2D g2d, DataPoint dataPoint, Point location);
    public abstract void drawSymbol(java.awt.Graphics2D g2d, int x, int y);

    
    public DataPoint createDataPoint(Number x, Number y) {
        return new PixelDataPoint(x, y, new Point(dataSet.xPixel(x), dataSet.yPixel(y)));
    }


    void setGraph(TreeSet<DataPoint> graph) {
        this.graph = graph;
    }
    
    
    void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }


    void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }


    protected ChartPanel chartPanel;
    protected TreeSet<DataPoint> graph;
    DataSet dataSet;
    
}
