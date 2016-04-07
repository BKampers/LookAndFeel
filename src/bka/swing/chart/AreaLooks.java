/*
 * Copyright © Bart Kampers
 */
package bka.swing.chart;

import java.awt.*;

/**
 * @param <S>
 */
public interface AreaLooks<S extends Shape> {

    public Paint getPaint(S area);
    public Paint getBorderPaint(S area);
    public Stroke getBorderStroke(S area);
    
}
