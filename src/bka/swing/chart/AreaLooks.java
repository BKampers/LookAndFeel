/*
 * Copyright © Bart Kampers
 */
package bka.swing.chart;

import java.awt.*;


/**
 * @param <G>
 */
public interface AreaLooks<G extends AreaGeometry> {

    public Paint getPaint(G geometry);
    public Paint getBorderPaint(G geometry);
    public Stroke getBorderStroke(G geometry);
    
}
