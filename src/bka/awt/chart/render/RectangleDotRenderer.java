/*
** Copyright © Bart Kampers
*/

package bka.awt.chart.render;


import bka.awt.chart.custom.*;
import java.awt.*;


public class RectangleDotRenderer extends PointRenderer<Rectangle> {
    
    
    public RectangleDotRenderer(int width, int height, AreaDrawStyle areaDrawStyle) {
        super(width, height, areaDrawStyle);
    }

    
    public RectangleDotRenderer(int size, AreaDrawStyle areaDrawStyle) {
        super(size, size, areaDrawStyle);
    }

    
    public RectangleDotRenderer(int size, Color color) {
        super(size, size, DefaultDrawStyle.createSolid(color));
    }
    

    @Override
    protected Rectangle createArea(Number x, Number y) {
        return createRectangle(getWindow().xPixel(x), getWindow().yPixel(y));
    }


    @Override
    protected Rectangle createSymbolArea(int x, int y) {
        return createRectangle(x, y);
    }


    private Rectangle createRectangle(int x, int y) {
        return new Rectangle(x - width / 2, y - height / 2, width, height);
    }
    
}
