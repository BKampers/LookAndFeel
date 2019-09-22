/*
** © Bart Kampers
*/

package bka.swing.chart.geometry;

import java.util.*;


/**
 * Coordinates needed to paint a graph into a ChartGeometry
 */
public class GraphGeometry<G extends AreaGeometry> {


    public GraphGeometry() {
        this.dataPoints = new ArrayList<>();
    }


    public GraphGeometry(Collection<G> dataPoints) {
        this.dataPoints = new ArrayList(dataPoints);
    }


    public void add(G dataPoint) {
        dataPoints.add(dataPoint);
    }
    
    
    public List<G> getDataPoints() {
        return Collections.unmodifiableList(dataPoints);
    }


    private final List<G> dataPoints;

}
