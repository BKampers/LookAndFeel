/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.custom.*;
import java.awt.*;


public class PolygonDotRenderer extends CoordinateAreaRenderer<Polygon> {
    
    
    public PolygonDotRenderer(Polygon polygon, AreaDrawStyle drawStyle) {
        super(drawStyle);
        this.polygon = polygon;
    }
    

    @Override
    protected Polygon createArea(int x, int y) {
        Polygon area = new Polygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
        area.translate(x, y);
        return area;
    }


    private final Polygon polygon;
    
}
