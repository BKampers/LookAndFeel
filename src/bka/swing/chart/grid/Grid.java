/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.grid;


import java.math.*;
import java.util.*;


public class Grid {

    
    public class MarkerList {

        protected MarkerList(List<Number> values, String format) {
            this.values = values;
            this.format = format;
        }

        public List<Number> getValues() {
            return values;
        }

        public String getLabel(Locale locale, Number value) {
            return label(format, locale, value);
        }

        private final List<Number> values;
        private final String format;
    }


    public Grid(SortedMap<BigDecimal, BigDecimal> map) {
        stepSizeMap.putAll(map);
    }


     public Grid() {
        this(createDefualtMap());
    }


   private static SortedMap<BigDecimal, BigDecimal> createDefualtMap() {
        SortedMap<BigDecimal, BigDecimal> map =  new TreeMap<>();
        map.put(BigDecimal.valueOf(15, 1), BigDecimal.valueOf(1, 0));
        map.put(BigDecimal.valueOf(2, 0), BigDecimal.valueOf(2, 0));
        map.put(BigDecimal.valueOf(3, 0), BigDecimal.valueOf(25, 1));
        map.put(BigDecimal.valueOf(5, 0), BigDecimal.valueOf(5, 0));
        map.put(BigDecimal.valueOf(10, 0), BigDecimal.valueOf(10, 0));
        return map;
    }


    public void initialize(Number min, Number max) {
        markerLists.clear();
        if (min != null && max != null) {
            compute(min, max);
        }
    }


    protected String label(String format, Locale locale, Number value) {
        Formatter formatter = new Formatter(locale);
        formatter.format(format, value);
        return formatter.toString();
    }


    public List<MarkerList> getMarkerLists() {
        return markerLists;
    }


    public List<Number> getValues() {
        if (markerLists.isEmpty()) {
            return Collections.emptyList();
        }
        return markerLists.get(0).getValues();
    }

    
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
        markerLists.add(new MarkerList(markerValues, String.format(FORMAT, digits)));
    }


    protected BigDecimal determineStepSize(BigDecimal significand) {
        BigDecimal last = null;
        for (Map.Entry<BigDecimal, BigDecimal> entry : stepSizeMap.entrySet()) {
            if (significand.compareTo(entry.getKey()) < 0) {
                return entry.getValue();
            }
            last = entry.getValue();
        }
        return last;
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


    protected void addMarkerList(MarkerList markerList) {
        markerLists.add(markerList);
    }


    private final SortedMap<BigDecimal, BigDecimal> stepSizeMap = new TreeMap<>();

    private final List<MarkerList> markerLists = new ArrayList<>();

    private static final String FORMAT = "%%.%df";

}
