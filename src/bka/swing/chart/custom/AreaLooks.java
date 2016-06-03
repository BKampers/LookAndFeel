/*
 * Copyright © Bart Kampers
 */
package bka.swing.chart.custom;


import bka.swing.chart.geometry.AreaGeometry;
import java.awt.*;


/**
 * @param <G>
 */
public interface AreaLooks<G extends AreaGeometry> {

    public Paint getPaint(G geometry);
    public Paint getBorderPaint(G geometry);
    public Stroke getBorderStroke(G geometry);
    public Paint getLabelPaint(G geometry);
    public Font getLabelFont(G geometry);
    
}
