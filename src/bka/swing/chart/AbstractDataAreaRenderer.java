/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.util.*;



public abstract class AbstractDataAreaRenderer<G extends PointAreaGeometry> {

    
    protected abstract void draw(Graphics2D g2d, G geometry);
    public abstract void draw(Graphics2D g2d, G geometry, Point location);
    public abstract void drawSymbol(Graphics2D g2d, int x, int y);


    public void draw(Graphics2D g2d, TreeSet<G> graphGeometry) {
        for (G dataAreaGeometry : graphGeometry) {
            draw(g2d, dataAreaGeometry);
        }
    }

    
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


    void setGeometry(ChartGeometry chartGeometry) {
        this.chartGeometry = chartGeometry;
    }


    protected ChartPanel chartPanel;
    ChartGeometry chartGeometry;
    
}
