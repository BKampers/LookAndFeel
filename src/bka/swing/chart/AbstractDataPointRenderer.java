/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.util.*;



public abstract class AbstractDataPointRenderer {

    
    public abstract void draw(Graphics2D g2d, DataPoint dataPoint);
    public abstract void draw(Graphics2D g2d, DataPoint dataPoint, Point location);
    public abstract void drawSymbol(Graphics2D g2d, int x, int y);


    TreeSet<DataPoint> createDataPoints(Map<Number, Number> graph) {
        TreeSet<DataPoint> dataPoints = new TreeSet<>();
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            Number x = entry.getKey();
            Number y = entry.getValue();
            dataPoints.add(new PixelDataPoint(x, y, new Point(dataSet.xPixel(x), dataSet.yPixel(y))));
        }
        return dataPoints;
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
    private TreeSet<DataPoint> graph;
    DataSet dataSet;
    
}
