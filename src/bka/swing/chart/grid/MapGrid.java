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
    public String label(Number value) {
        return map.get(value);
    }
    
    
    @Override
    protected void compute(Number min, Number max) {
        double minValue = min.doubleValue();
        double maxValue = max.doubleValue();
        for (Number number : map.keySet()) {
            double value = number.doubleValue();
            if (minValue <= value && value <= maxValue) {
                values.add(number);
            }
        }
    }
    
    
    private Map<Number, String> map;
    
}
