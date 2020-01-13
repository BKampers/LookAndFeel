/*
** Copyright © Bart Kampers
*/

package bka.awt.chart.render;


import bka.awt.chart.custom.*;
import java.awt.*;


public abstract class PointRenderer<S extends Shape> extends CoordinateAreaRenderer<S> {


    public PointRenderer(int width, int height, AreaDrawStyle drawStyle) {
        super(drawStyle);
        this.width = width;
        this.height = height;
    }


    @Override
    protected abstract S createArea(int x, int y);


    protected int width;
    protected int height;

}
