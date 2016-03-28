/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


/**
 * Information needed for rendering and highlighting data in a graph.
 */
public interface DataPoint extends Comparable<DataPoint> {

    Number getX();
    Number getY();
    Shape getArea();
    
}
