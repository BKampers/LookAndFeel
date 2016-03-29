/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.geom.Arc2D;

public class ArcAreaGeometry extends PointAreaGeometry<Arc2D.Float> {

    ArcAreaGeometry(Number x, Number y, Arc2D.Float area) {
        super(x, y, area);
    }
    
}
