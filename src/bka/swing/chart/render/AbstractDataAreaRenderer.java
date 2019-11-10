/*
** Copyright © Bart Kampers
*/


package bka.swing.chart.render;


import bka.swing.chart.*;
import bka.swing.chart.custom.*;
import bka.swing.chart.geometry.*;
import java.awt.*;


public abstract class AbstractDataAreaRenderer<G extends AreaGeometry> {


    AbstractDataAreaRenderer(AreaDrawStyle drawStyle) {
        this.areaDrawStyle = drawStyle;
    }

    
    public abstract GraphGeometry<G> createGraphGeomerty(ChartData<Number, Number> chart);
    protected abstract G createSymbolGeometry(int x, int y, G geometry);


    public void setChartPanel(ChartPanel chartPanel) {
        this.chartPanel = chartPanel;
    }


    public void setWindow(ChartGeometry.Window window) {
        this.window = window;
    }


    public void addPointsInWindow(Object key, ChartData<Number, Number> chartData, ChartGeometry.Window window) {
        ChartData<Number, Number> graphPointsInWindow = new ChartData<>();
        for (ChartDataElement<Number, Number> element : chartData) {
            Number x = element.getKey();
            Number y = element.getValue();
            if (window.inXRange(x) && window.inYRange(y)) {
                graphPointsInWindow.add(x, y);
                window.setBounds(x, y);
            }
        }
        window.putPoints(key, graphPointsInWindow);
    }


    public void draw(Graphics2D g2d, GraphGeometry<G> graphGeometry) {
        for (G dataAreaGeometry : graphGeometry.getDataPoints()) {
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


    protected void drawSymbol(Graphics2D g2d, int x, int y) {
        draw(g2d, createSymbolGeometry(x, y, null));
    }


    protected void draw(Graphics2D g2d, G geometry) {
        Shape area = geometry.getArea();
        Paint paint = areaDrawStyle.getPaint(geometry);
        if (paint != null) {
            g2d.setPaint(paint);
            g2d.fill(area);
        }
        Paint borderPaint = areaDrawStyle.getBorderPaint(geometry);
        Stroke borderStroke = areaDrawStyle.getBorderStroke(geometry);
        if (borderPaint != null && borderStroke != null) {
            g2d.setPaint(borderPaint);
            g2d.setStroke(borderStroke);
            g2d.draw(area);
        }
    }


    protected ChartGeometry.Window getWindow() {
        return window;
    }


    protected AreaDrawStyle<G> getAreaDrawStyle() {
        return areaDrawStyle;
    }


    protected ChartPanel getChartPanel() {
        return chartPanel;
    }


    private ChartPanel chartPanel;
    private ChartGeometry.Window window;

    private final AreaDrawStyle<G> areaDrawStyle;

}
