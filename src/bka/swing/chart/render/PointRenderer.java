/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.custom.AreaLooks;
import java.awt.*;


public abstract class PointRenderer<S extends Shape> extends CoordinateAreaRenderer<S> {


    public PointRenderer(int width, int height, AreaLooks looks) {
        super(looks);
        this.width = width;
        this.height = height;
    }


    @Override
    protected abstract S createArea(int x, int y);


    protected int width;
    protected int height;

}
