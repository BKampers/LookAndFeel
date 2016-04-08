/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.geom.Arc2D;

public class ArcAreaGeometry extends AreaGeometry<Arc2D.Float> {

    ArcAreaGeometry(Number x, Number y, Arc2D.Float area, int index) {
        super(x, y, area);
        this.index = index;
    }


    public int getIndex() {
        return index;
    }


    private final int index;
    
}
