/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


/**
 * Information needed for rendering and highlighting data in a graph.
 */
public interface DataPointInterface extends Comparable<DataPointInterface> {

    Number getX();
    Number getY();
    java.awt.Point getPixel();
    java.awt.Point  getHighlightPosition();
    boolean contains(java.awt.Point point);
    
}
