/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public abstract class PointRenderer<S extends Shape> extends CoordinateAreaRenderer<S> {


    public PointRenderer(int width, int height, AreaLooks looks) {
        super(looks);
        this.width = width;
        this.height = height;
    }


    @Override
    protected abstract S createArea(int x, int y);


   @Override
    protected void drawSymbol(Graphics2D g2d, int x, int y) {
        S area = createArea(x, y);
        draw(g2d, area);
    }


    protected int width;
    protected int height;

}
