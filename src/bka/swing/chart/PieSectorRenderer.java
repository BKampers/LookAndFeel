/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;


import java.util.*;



public abstract class PieSectorRenderer extends AbstractDataPointRenderer {
        
    
    public Palette getPalette() {
        return palette;
    }
    
    
    public void setPalette(Palette palette) {
        this.palette = palette;
    }


    @Override
    public DataPoint createDataPoint(Number x, Number y) {
        return new ArcDataPoint(x, y, this);
    }
    
    
    @Override
    void setGraph(TreeSet<DataPoint> graph) {
        super.setGraph(graph);
        previous = 0.0;
        total = 0.0;
        for (DataPoint dataPoint : graph) {
            total += dataPoint.getY().doubleValue();
        }
        if (palette == null) {
            palette = new Palette("chart.piePalette");
        }
        palette.reset();
    }
    

    abstract java.awt.geom.Arc2D getArc(ArcDataPoint dataPoint);

    protected Palette palette;
  
    protected double previous;
    protected double total;
    
}
