/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.grid;


import java.util.*;


public class MapGrid extends Grid{

    
    public MapGrid(Map<Number, String> map) {
        this.map = map;
    }
    
    
    public MapGrid(long minimum, long maximum, long step) {
        map = new TreeMap<>();
        for (long value = minimum; value <= maximum; value += step) {
            map.put(value, Long.toString(value));
        }
    }
    
    
    public MapGrid(long minimum, long maximum) {
        this(minimum, maximum, 1);
    }
    
    
    @Override
    protected void compute(Number min, Number max) {
        double minValue = min.doubleValue();
        double maxValue = max.doubleValue();
        List<Number> values = new ArrayList<>();
        for (Number number : map.keySet()) {
            double value = number.doubleValue();
            if (minValue <= value && value <= maxValue) {
                values.add(number);
            }
        }
        markerLists.add(new MarkerList(values, null));
    }
    
    
    private final Map<Number, String> map;
    
}
