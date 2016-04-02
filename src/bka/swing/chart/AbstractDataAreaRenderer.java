/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.util.*;



public abstract class AbstractDataAreaRenderer<G extends PointAreaGeometry> {


    public abstract void draw(Graphics2D g2d, G geometry, Point location);
    protected abstract void draw(Graphics2D g2d, G geometry);
    protected abstract void drawSymbol(Graphics2D g2d, int x, int y);


    public void draw(Graphics2D g2d, TreeSet<G> graphGeometry) {
        for (G dataAreaGeometry : graphGeometry) {
            draw(g2d, dataAreaGeometry);
        }
    }


    public void drawLegend(Graphics2D g2d, Object key, LegendGeometry geometry) {
        drawSymbol(g2d, geometry.getX(), geometry.getY());
        g2d.setColor(geometry.getColor());
        g2d.setFont(geometry.getFont());
        FontMetrics fontMetrics = g2d.getFontMetrics();
        g2d.drawString(key.toString(), geometry.getX() + geometry.getSpace(), geometry.getY() + fontMetrics.getDescent());
        geometry.setY(geometry.getY() + geometry.getFeed() + fontMetrics.getHeight());
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
