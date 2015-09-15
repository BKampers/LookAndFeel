/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;


import java.awt.*;
import java.util.*;



public abstract class PieSectorRenderer extends AbstractDataPointRenderer {
        
    
    public Palette getPalette() {
        return palette;
    }
    
    
    public void setPalette(Palette palette) {
        this.palette = palette;
    }
    
    
    @Override
    public void reset(ChartPanel chartPanel, TreeSet<DataPointInterface> graph) {
        super.reset(chartPanel, graph);
        previous = 0.0;
        total = 0.0;
        for (DataPointInterface dataPoint : graph) {
            total += dataPoint.getY().doubleValue();
        }
        if (palette == null) {
            palette = new Palette("chart.piePalette");
        }
        palette.reset();
    }
    

    abstract boolean pointNearDataPoint(Point mousePoint, DataPointInterface dataPoint);
    abstract java.awt.geom.Arc2D getArc(SectorDataPoint dataPoint);

    protected Palette palette;
  
    protected double previous;
    protected double total;
    
}
