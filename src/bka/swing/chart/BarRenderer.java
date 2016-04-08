/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

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
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        int fontHeight = g2d.getFontMetrics().getHeight();
        int left = x - width / 2;
        int top = y - fontHeight / 2;
        draw(g2d, new Rectangle(left, top, width, fontHeight));
    }


    @Override
    protected Rectangle createArea(int x, int y) {
        int left = x - width / 2 + shift;
        int barHeight = chartPanel.areaBottom() - y;
        return new Rectangle(left, y, width, barHeight);

    }


    private int width;
    private int shift;

}
