/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.ChartPanel;
import bka.swing.chart.geometry.LegendGeometry;
import bka.swing.chart.geometry.*;
import java.awt.*;
import java.util.*;


public class DefaultLegendRenderer {

    
    public DefaultLegendRenderer(ChartPanel chartPanel, int top) {
        this.chartPanel = chartPanel;
        this.top = top;
    }


    public void drawLegend(Graphics2D g2d, ChartGeometry geometry, Map<Object, AbstractDataAreaRenderer> renderers) {
        LegendGeometry legendGeometry = new LegendGeometry();
        legendGeometry.setX(chartPanel.areaRight() + SPACE);
        legendGeometry.setY(top);
        legendGeometry.setSpace(SPACE);
        legendGeometry.setFeed(FEED);
        legendGeometry.setFont(g2d.getFont());
        legendGeometry.setLabelColor(LABEL_COLOR);
        for (Map.Entry<Object, TreeSet<AreaGeometry>> entry : geometry.getGraphs().entrySet()) {
            Object key = entry.getKey();
            AbstractDataAreaRenderer renderer = renderers.get(key);
            if (renderer != null) {
                renderer.drawLegend(g2d, key, legendGeometry);
            }
        }
    }


    private final ChartPanel chartPanel;
    private final int top;

    private static final int SPACE = 15;
    private static final int FEED = 5;
    private static final Color LABEL_COLOR = Color.BLACK;

}