/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.grid;


import java.math.*;
import java.util.*;


public class NumberGrid extends Grid {

    
    public NumberGrid(Map<BigDecimal, BigDecimal> map) {
        stepSizeMap.putAll(map);
    }


    public NumberGrid() {
        this(createDefualtMap());
    }


   private static Map<BigDecimal, BigDecimal> createDefualtMap() {
        Map<BigDecimal, BigDecimal> map =  new TreeMap<>();
        map.put(BigDecimal.valueOf(15, 1), BigDecimal.valueOf(1, 0));
        map.put(BigDecimal.valueOf(2, 0), BigDecimal.valueOf(2, 0));
        map.put(BigDecimal.valueOf(3, 0), BigDecimal.valueOf(25, 1));
        map.put(BigDecimal.valueOf(5, 0), BigDecimal.valueOf(5, 0));
        map.put(BigDecimal.valueOf(10, 0), BigDecimal.valueOf(10, 0));
        return map;
    }


    @Override
    protected String label(String format, Locale locale, Number value) {
        Formatter formatter = new Formatter(locale);
        formatter.format(format, value);
        return formatter.toString();
    }


    @Override
    protected void compute(Number min, Number max) {
        double low = min.doubleValue();
        double high = max.doubleValue();
        BigDecimal range = new BigDecimal(high - low);
        int rangeExponent = range.precision() - range.scale() - 1;
        int stepExponent = rangeExponent - 1;
        BigDecimal stepSize = determineStepSize(range.movePointLeft(rangeExponent));
        double step = stepSize.movePointRight(stepExponent).doubleValue();
        List<Number> markerValues = determineMarkerValues(low, high, step);
        int digits = Math.max(0, stepSize.scale() - stepExponent);
        addMarkerList(new MarkerList(markerValues, String.format(FORMAT, digits)));
    }


    protected BigDecimal determineStepSize(BigDecimal significand) {
        BigDecimal lastKey = stepSizeMap.lastKey();
        List<BigDecimal> keys = new ArrayList<>(stepSizeMap.keySet());
        keys.remove(lastKey);
        for (BigDecimal key : keys) {
            if (significand.compareTo(key) < 0) {
                return stepSizeMap.get(key);
            }
        }
        return stepSizeMap.get(lastKey);
    }


    private List<Number> determineMarkerValues(double low, double high, double step) {
        List<Number> markerValues = new ArrayList<>();
        double markerValue = Math.floor(low / step) * step;
        while (markerValue <= high) {
            markerValues.add(markerValue);
            markerValue += step;
        }
        markerValues.add(markerValue);
        return markerValues;
    }


    private final SortedMap<BigDecimal, BigDecimal> stepSizeMap = new TreeMap<>();

    private static final String FORMAT = "%%.%df";

}
