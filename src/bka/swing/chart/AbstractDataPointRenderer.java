/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.util.*;



public abstract class AbstractDataPointRenderer {

    
    public abstract void draw(Graphics2D g2d, DataPoint dataPoint);
    public abstract void drawSymbol(java.awt.Graphics2D g2d, int x, int y);

    
    public DataPoint createDataPoint(Number x, Number y) {
        return new PixelDataPoint(x, y, new Point(dataSet.xPixel(x), dataSet.yPixel(y)));
    }


    void reset(ChartPanel chartPanel, TreeSet<DataPoint> graph) {
        this.chartPanel = chartPanel;
        this.graph = graph;
    }


    void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }


    protected ChartPanel chartPanel;
    protected TreeSet<DataPoint> graph;
    private DataSet dataSet;
    
}
