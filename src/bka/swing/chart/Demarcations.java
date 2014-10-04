/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.util.*;


public class Demarcations {

    
    void setLocale(Locale locale) {
        this.locale = locale;
    }
    
    
    void initialize(Number min, Number max) {
        values.clear();
        format = null;
        if (min != null && max != null) {
            compute(min, max);
        }
    }
    
    
    String label(Number value) {
        Formatter formatter = new Formatter(locale);
        formatter.format(format, value);
        return formatter.toString();
    }

    
    protected void compute(Number min, Number max) {
        double low = min.doubleValue();
        double high = max.doubleValue();
        double range = high - low;
        bka.Scientific r = new bka.Scientific(range);
        double step = 1.0;
        int digits = 0;
        double coefficient = r.getCoefficient();
        if      (coefficient < 1.5) { step = 0.10; }
        else if (coefficient < 2.0) { step = 0.20; }
        else if (coefficient < 3.0) { step = 0.25; digits++; }
        else if (coefficient < 5.0) { step = 0.50; }
        step *= r.factor();
        double start = Math.floor(low / step) * step;
        double markerValue = start;
        values.add(markerValue);
        boolean ready = false;
        while (! ready) {
            markerValue += step;
            values.add(markerValue);
            ready = markerValue > high;
        }
        short exponent = new bka.Scientific(step).getExponent();
        digits += (exponent < 0) ? -exponent : 0;
        format = "%." + digits + "f";
    }

    
    protected ArrayList<Number> values = new ArrayList<Number>();
    protected String format;
    
    protected Locale locale;
    
}
