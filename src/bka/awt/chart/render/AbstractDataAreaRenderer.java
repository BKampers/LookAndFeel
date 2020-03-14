/*
** Copyright © Bart Kampers
*/


package bka.awt.chart.render;


import bka.awt.chart.*;
import bka.awt.chart.custom.*;
import bka.awt.chart.geometry.*;
import java.awt.*;
import java.util.*;


public abstract class AbstractDataAreaRenderer<G extends AreaGeometry> {

    
    public enum Layer { BACKGROUND, FOREGROUND }


    AbstractDataAreaRenderer(AreaDrawStyle drawStyle) {
        this.areaDrawStyle = drawStyle;
    }


    public void setStackBase(AbstractDataAreaRenderer stackBase) {
        this.stackBase = stackBase;
    }


    public abstract GraphGeometry<G> createGraphGeomerty(ChartData<Number, Number> chart);
    public abstract void drawLegend(Graphics2D g2d, Object key, LegendGeometry geometry);


    public void setChartRenderer(ChartRenderer renderer) {
        chartRenderer = renderer;
    }


    /**
     * Set the ChartGeometry.Window object that will hold the computed coordinates needed to
     * draw graphs.
     * @param window
     */
    public void setWindow(ChartGeometry.Window window) {
        this.window = window;
    }


    /**
     * Collect all points that are contained in this renderer's window and adjust
     * data bounds of the window.
     * 
     * @param key
     * @param chartData
     */
    public void addPointsInWindow(Object key, ChartData<Number, Number> chartData) {
        ChartData<Number, Number> graphPointsInWindow = new ChartData<>();
        for (ChartDataElement<Number, Number> element : chartData) {
            Number x = element.getKey();
            Number y = getY(element);
            if (window.inXWindowRange(x) && window.inYWindowRange(y)) {
                graphPointsInWindow.add(x, y);
                window.adjustBounds(x, y);
            }
        }
        window.putPoints(key, graphPointsInWindow);
    }


    protected Number getY(ChartDataElement<Number, Number> element) {
        return getY(element.getKey());
    }


    protected Number getY(Number x) {
        ChartData<Number, Number> chartData = chartRenderer.getChartGeometry().getChartData(this);
        double y = getY(chartData, x).doubleValue();
        if (stackBase != null) {
            y += stackBase.getY(x).doubleValue();
        }
        return y;
    }


    private Number getY(ChartData<Number, Number> chartData, Number x) {
        Iterator<ChartDataElement<Number, Number>> it = chartData.iterator();
        while (it.hasNext()) {
            ChartDataElement<Number, Number> next = it.next();
            if (x.equals(next.getKey())) {
                return next.getValue();
            }
        }
        throw new NoSuchElementException(String.format("No y value for %s", x.toString()));
    }


    public void draw(Graphics2D g2d, Layer layer, GraphGeometry<G> graphGeometry) {
        if (layer == Layer.FOREGROUND) {
            for (G dataAreaGeometry : graphGeometry.getDataPoints()) {
                draw(g2d, dataAreaGeometry);
            }
        }
    }


    protected void draw(Graphics2D g2d, G geometry) {
        if (areaDrawStyle != null) {
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
    }


    protected AbstractDataAreaRenderer getStackBase() {
        return stackBase;
    }


    protected AreaDrawStyle<G> getAreaDrawStyle() {
        return areaDrawStyle;
    }


    protected ChartRenderer getChartRenderer() {
        return chartRenderer;
    }


    protected ChartGeometry.Window getWindow() {
        return window;
    }


    private ChartRenderer chartRenderer;
    private ChartGeometry.Window window;

    private final AreaDrawStyle<G> areaDrawStyle;
    private AbstractDataAreaRenderer stackBase;

}
