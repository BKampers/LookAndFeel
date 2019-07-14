/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.custom;


import java.awt.*;
import java.awt.geom.*;
import java.util.*;


public abstract class GridStyle {


    private GridStyle(Stroke stroke, Color gridColor) {
        this.stroke = stroke;
        this.gridColor = gridColor;
    }
    
    
    public static GridStyle create(Stroke stroke, Color gridColor, Color[] colors) {
        return new DefaultGridStyle(stroke, gridColor, colors);
    }


    public static GridStyle createGradient(Stroke stroke, Color gridColor, Color[] colors1, Color[] colors2) {
        return new GradientGridStyle(stroke, gridColor, colors1, colors2);
    }


    public static GridStyle createGradient(Color[] colors) {
        return new GradientGridStyle(null, null, colors, darker(colors));
    }


    public static GridStyle create(Color[] colors) {
        return new DefaultGridStyle(null, null, colors);
    }


    public static GridStyle create(Color color) {
        return create(new Color[] { color });
    }


    public Stroke getStroke() {
        return stroke;
    }


    public Paint getGridPaint() {
        return gridColor;
    }
    
    
    private static Color[] darker(Color[] colors) {
        Color[] darker = new Color[colors.length];
        for (int i = 0; i < colors.length; ++i) {
            darker[i] = colors[i].darker();
        }
        return darker;
    }


    abstract public Paint getHorizontalPaint(RectangularShape area, int index);
    abstract public Paint getVerticalPaint(RectangularShape area, int index);

    
    private static class DefaultGridStyle extends GridStyle {
    
        private DefaultGridStyle(Stroke stroke, Color gridColor, Color[] colors) {
            super(stroke, gridColor);
            this.colors = Objects.requireNonNull(colors);
        }
        
        @Override
        public Paint getHorizontalPaint(RectangularShape area, int index) {
            return colors[index % colors.length];
        }

        @Override
        public Paint getVerticalPaint(RectangularShape area, int index) {
            return colors[index % colors.length];
        }

        private final Color[] colors;

    }
    
    
    private static class GradientGridStyle extends GridStyle {
        
        private GradientGridStyle(Stroke stroke, Color gridColor, Color[] colors1, Color[] colors2) {
            super(stroke, gridColor);
            this.colors1 = Objects.requireNonNull(colors1);
            this.colors2 = Objects.requireNonNull(colors2);
        }
        
        @Override
        public Paint getHorizontalPaint(RectangularShape area, int index) {
            Color color1 = colors1[index % colors1.length];
            Color color2 = colors2[index % colors2.length];
            return new GradientPaint((float) area.getCenterX(), (float) area.getMinY(), color1, (float) area.getCenterX(), (float) area.getMaxY(), color2);
        }

        @Override
        public Paint getVerticalPaint(RectangularShape area, int index) {
            Color color1 = colors1[index % colors1.length];
            Color color2 = colors2[index % colors2.length];
            return new GradientPaint((float) area.getMaxX(), (float) area.getCenterY(), color1, (float) area.getMinX(), (float) area.getCenterY(), color2);
        }

        private final Color[] colors1;
        private final Color[] colors2;
        
    }


    private final Stroke stroke;
    private final Color gridColor;

}
