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
        int y0Pixel = getWindow().yPixel(0);
        int yPixel = getWindow().yPixel(y);
        if (y0Pixel > yPixel) {
            int barHeight = (getStackBase() != null) ? getWindow().yPixel(getStackBase().getY(x)) - yPixel : y0Pixel - yPixel;
            return createArea(getWindow().xPixel(x), yPixel, barHeight);
        }
        else {
            int barHeight = (getStackBase() != null) ? getWindow().yPixel(getStackBase().getY(x)) + yPixel : yPixel - y0Pixel;
            return createArea(getWindow().xPixel(x), y0Pixel, barHeight);
        }
    }


    private Rectangle createArea(int x, int y, int barHeight) {
        int left = x - width / 2 + shift;
        return new Rectangle(left, y, width, barHeight);

    }


    @Override
    protected Rectangle createSymbolArea(int x, int y) {
        int symbolWidth = 10;
        int symbolHeight = 16;
        int left = x - symbolWidth / 2;
        int top = y - symbolHeight / 2;
        return new Rectangle(left, top, symbolWidth, symbolHeight);
    }


    private int width;
    private int shift;
    
    private static final int DEFAULT_WIDTH = 7;

}
