/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.custom.*;
import java.awt.*;


public class RectangleDotRenderer extends PointRenderer<Rectangle> {
    
    
    public RectangleDotRenderer(int width, int height, AreaDrawStyle areaDrawStyle) {
        super(width, height, areaDrawStyle);
    }

    
    public RectangleDotRenderer(int size, AreaDrawStyle areaDrawStyle) {
        super(size, size, areaDrawStyle);
    }

    
    public RectangleDotRenderer(AreaDrawStyle areaDrawStyle) {
        super(DEFAULT_SIZE, DEFAULT_SIZE, areaDrawStyle);
    }
    

    @Override
    protected Rectangle createArea(int x, int y) {
        return new Rectangle(x - width / 2, y - height / 2, width, height);
    }
    
    
    private static final int DEFAULT_SIZE = 7;
    
}
