/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.grid;


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


    public void initialize(Number min, Number max) {
        markerLists.clear();
        if (min != null && max != null) {
            compute(min, max);
        }
    }


    public String label(String format, Locale locale, Number value) {
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
        bka.numeric.Scientific range = new bka.numeric.Scientific(high - low);
        double step = 1.0;
        int digits = 0;
        double coefficient = range.getCoefficient();
        if (coefficient < 1.5) {
            step = 0.10;
        }
        else if (coefficient < 2.0) {
            step = 0.20;
        }
        else if (coefficient < 3.0) {
            step = 0.25;
            if (range.getExponent() <= 1) {
                digits++;
            }
        }
        else if (coefficient < 5.0) {
            step = 0.50;
        }
        step *= range.factor();
        double start = Math.floor(low / step) * step;
        double markerValue = start;
        List<Number> markerValues = new ArrayList<>();
        markerValues.add(markerValue);
        boolean ready = false;
        while (! ready) {
            markerValue += step;
            markerValues.add(markerValue);
            ready = markerValue > high;
        }
        short exponent = new bka.numeric.Scientific(step).getExponent();
        if (exponent < 0) {
            digits -= exponent;
        }
        markerLists.add(new MarkerList(markerValues, "%." + digits + "f"));
    }
    

    protected final List<MarkerList> markerLists = new ArrayList<>();
    
}
