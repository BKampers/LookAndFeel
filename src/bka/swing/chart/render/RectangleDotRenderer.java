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

    
    public RectangleDotRenderer(int size, Color color) {
        super(size, size, DefaultDrawStyle.createSolid(color));
    }
    

    @Override
    protected Rectangle createArea(int x, int y) {
        return new Rectangle(x - width / 2, y - height / 2, width, height);
    }
    
}
