/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;
import java.util.*;



public abstract class AbstractDataAreaRenderer<G extends AreaGeometry<S>, S extends Shape> {

    AbstractDataAreaRenderer(AreaLooks looks) {
        this.looks = looks;
    }

    
    protected abstract S createSymbolArea(int x, int y);
    protected abstract G createSymbolGeometry(S area);
    abstract TreeSet<G> createGraphGeomerty(Map<Number, Number> graph);


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


    protected void drawSymbol(Graphics2D g2d, int x, int y) {
        draw(g2d, createSymbolGeometry(createSymbolArea(x, y)));
    }


    void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }


    void setChartGeometry(ChartGeometry chartGeometry) {
        this.chartGeometry = chartGeometry;
    }


    protected ChartPanel chartPanel;
    ChartGeometry chartGeometry;

    protected final AreaLooks<G> looks;
    
}
