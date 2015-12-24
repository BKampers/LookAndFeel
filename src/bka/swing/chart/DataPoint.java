/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


/**
 * Information needed for rendering and highlighting data in a graph.
 */
public interface DataPoint extends Comparable<DataPoint> {

    Number getX();
    Number getY();
    boolean contains(java.awt.Point point);
    
}
