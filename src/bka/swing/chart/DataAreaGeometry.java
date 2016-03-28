/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


/**
 * Information needed for rendering and highlighting data in a graph.
 * @param <S> Area (Shape) this date ocupies on the chart canvas
 */
public interface DataAreaGeometry<S extends java.awt.Shape> extends Comparable<DataAreaGeometry> {

    Number getX();
    Number getY();
    S getArea();
    
}
