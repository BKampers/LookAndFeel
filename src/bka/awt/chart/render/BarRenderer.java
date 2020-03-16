/*
** Copyright © Bart Kampers
*/


package bka.awt.chart.render;

import bka.awt.chart.*;
import bka.awt.chart.custom.*;
import bka.awt.chart.geometry.*;
import java.awt.*;


public class BarRenderer extends CoordinateAreaRenderer<Rectangle> {


    public BarRenderer(int width, AreaDrawStyle drawStyle) {
        super(drawStyle);
        this.width = width;
    }


    public BarRenderer(AreaDrawStyle drawStyle) {
        this(DEFAULT_WIDTH, drawStyle);
    }


    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * Set horizontal shift of the bar w.r.t. the x value.
     * Useful when plotting multiple bar graphs into one chart.
     * @param shift
     */
    public void setShift(int shift) {
        this.shift = shift;
    }


    @Override
    public void addPointsInWindow(Object key, ChartData<Number, Number> chartData) throws ChartDataException {
        ChartData<Number, Number> graphPointsInWindow = new ChartData<>();
        for (ChartDataElement<Number, Number> element : chartData) {
            Number x = element.getKey();
            if (getWindow().inXWindowRange(x)) {
                Number y = getY(element);
                graphPointsInWindow.add(x, y);
                getWindow().adjustXBounds(x);
                if (getWindow().inYWindowRange(y)) {
                    getWindow().adjustYBounds(y);
                }
            }
        }
        getWindow().putPoints(key, graphPointsInWindow);
    }


    @Override
    protected AreaGeometry<Rectangle> createSymbolGeometry(int x, int y) {
        return new AreaGeometry<>(null, null, createSymbolArea(x, y));
    }


    @Override
    protected Rectangle createArea(Number x, Number y) throws ChartDataException {
        int yPixel = getWindow().yPixel(y);
        int barHeight = (getStackBase() != null) ? getWindow().yPixel(getStackBase().getY(x)) - yPixel : getChartRenderer().areaBottom() - yPixel;
        return createArea(getWindow().xPixel(x), yPixel, barHeight);
    }


    private Rectangle createArea(int x, int y, int barHeight) {
        int left = x - width / 2 + shift;
        return new Rectangle(left, y, width, barHeight);

    }


    @Override
    protected Rectangle createSymbolArea(int x, int y) {
        int height = (int) (width * 1.5f);
        int left = x - width / 2;
        int top = y - height / 2;
        return new Rectangle(left, top, width, height);
    }


    private int width;
    private int shift;
    
    private static final int DEFAULT_WIDTH = 7;

}
