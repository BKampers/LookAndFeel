/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.util.*;



public abstract class AbstractDataPointRenderer<G extends DataAreaGeometry> {

    
    public abstract void draw(Graphics2D g2d, G geometry);
    public abstract void draw(Graphics2D g2d, G geometry, Point location);
    public abstract void drawSymbol(Graphics2D g2d, int x, int y);

    abstract void reset();

    
    TreeSet<G> createDataGeomerty(Map<Number, Number> graph) {
        TreeSet<PixelAreaGeometry> dataGeometry = new TreeSet<>();
        for (Map.Entry<Number, Number> entry : graph.entrySet()) {
            Number x = entry.getKey();
            Number y = entry.getValue();
            dataGeometry.add(new PixelAreaGeometry(x, y, new Point(chartGeometry.xPixel(x), chartGeometry.yPixel(y))));
        }
        return (TreeSet<G>) dataGeometry;
    }


    void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }


    void setDataSet(ChartGeometry chartGeometry) {
        this.chartGeometry = chartGeometry;
    }


    protected ChartPanel chartPanel;
    ChartGeometry chartGeometry;
    
}
