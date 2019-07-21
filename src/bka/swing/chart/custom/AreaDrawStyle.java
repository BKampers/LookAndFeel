/*
 * Copyright © Bart Kampers
 */
package bka.swing.chart.custom;


import bka.swing.chart.geometry.*;
import java.awt.*;


/**
 * @param <G> geometry
 */
public interface AreaDrawStyle<G extends AreaGeometry> {

    public Paint getPaint(G geometry);
    public Paint getBorderPaint(G geometry);
    public Stroke getBorderStroke(G geometry);
    public Paint getLabelPaint(G geometry);
    public Font getLabelFont(G geometry);
    
}
