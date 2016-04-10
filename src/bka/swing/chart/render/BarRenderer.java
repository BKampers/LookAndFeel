/*
** Copyright © Bart Kampers
*/


package bka.swing.chart.render;

import bka.swing.chart.custom.AreaLooks;
import bka.swing.chart.geometry.*;
import java.awt.*;


public class BarRenderer extends CoordinateAreaRenderer<Rectangle> {


    public BarRenderer(int width, AreaLooks looks) {
        super(looks);
        this.width = width;
    }


    public BarRenderer(AreaLooks paintFactory) {
        this(7, paintFactory);
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
    protected AreaGeometry<Rectangle> createSymbolGeometry(int x, int y, AreaGeometry<Rectangle> geometry) {
        return new AreaGeometry<>(null, null, createSymbolArea(x, y));
    }


    @Override
    protected Rectangle createArea(int x, int y) {
        int left = x - width / 2 + shift;
        int barHeight = chartPanel.areaBottom() - y;
        return new Rectangle(left, y, width, barHeight);

    }


    private Rectangle createSymbolArea(int x, int y) {
        int height = (int) (width * 1.5f);
        int left = x - width / 2;
        int top = y - height / 2;
        return new Rectangle(left, top, width, height);
    }


    private int width;
    private int shift;

}
