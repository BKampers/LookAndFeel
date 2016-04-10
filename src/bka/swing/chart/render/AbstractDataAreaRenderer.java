/*
** Copyright © Bart Kampers
*/


package bka.swing.chart.render;


import bka.swing.chart.ChartPanel;
import bka.swing.chart.geometry.LegendGeometry;
import bka.swing.chart.custom.AreaLooks;
import bka.swing.chart.geometry.*;
import java.awt.*;
import java.util.*;


public abstract class AbstractDataAreaRenderer<G extends AreaGeometry> {


    AbstractDataAreaRenderer(AreaLooks looks) {
        this.looks = looks;
    }

    
    public abstract TreeSet<G> createGraphGeomerty(Map<Number, Number> graph);
    protected abstract G createSymbolGeometry(int x, int y, G geometry);


    public void draw(Graphics2D g2d, TreeSet<G> graphGeometry) {
        for (G dataAreaGeometry : graphGeometry) {
            draw(g2d, dataAreaGeometry);
        }
    }


    protected void draw(Graphics2D g2d, G geometry) {
        Shape area = geometry.getArea();
        Paint paint = looks.getPaint(geometry);
        if (paint != null) {
            g2d.setPaint(paint);
            g2d.fill(area);
        }
        Paint borderPaint = looks.getBorderPaint(geometry);
        Stroke borderStroke = looks.getBorderStroke(geometry);
        if (borderPaint != null && borderStroke != null) {
            g2d.setPaint(borderPaint);
            g2d.setStroke(borderStroke);
            g2d.draw(area);
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


    protected void drawSymbol(Graphics2D g2d, int x, int y) {
        draw(g2d, createSymbolGeometry(x, y, null));
    }


    public void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }


    public void setChartGeometry(ChartGeometry chartGeometry) {
        this.chartGeometry = chartGeometry;
    }


    protected ChartPanel chartPanel;
    ChartGeometry chartGeometry;

    protected final AreaLooks<G> looks;
    
}
