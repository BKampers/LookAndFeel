/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.util.*;


public class MapDemarcations extends Demarcations{

    
    public MapDemarcations(Map<Number, String> map) {
        this.map = map;
    }
    
    
    public MapDemarcations(long minimum, long maximum, long step) {
        map = new TreeMap<Number, String>();
        for (long value = minimum; value <= maximum; value += step) {
            map.put(value, Long.toString(value));
        }
    }
    
    
    public MapDemarcations(long minimum, long maximum) {
        this(minimum, maximum, 1);
    }
    
    
    String label(Number value) {
        return map.get(value);
    }
    
    
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
