/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.grid;


import java.util.*;


public class Demarcations {

    
    public void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    
    public void initialize(Number min, Number max) {
        values.clear();
        format = null;
        if (min != null && max != null) {
            compute(min, max);
        }
    }
    
    
    public String label(Number value) {
        Formatter formatter = new Formatter(locale);
        formatter.format(format, value);
        return formatter.toString();
    }


    public List<Number> getValues() {
        return values;
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
        values.add(markerValue);
        boolean ready = false;
        while (! ready) {
            markerValue += step;
            values.add(markerValue);
            ready = markerValue > high;
        }
        short exponent = new bka.numeric.Scientific(step).getExponent();
        digits += (exponent < 0) ? -exponent : 0;
        format = "%." + digits + "f";
    }

    
    protected final ArrayList<Number> values = new ArrayList<>();

    protected String format;
    protected Locale locale;
    
}
