/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.grid;

import bka.numeric.*;
import java.util.*;


public class IntegerGrid extends Grid {


    @Override
    protected void compute(Number min, Number max) {
        List<Number> values = new ArrayList<>();
        double low = min.doubleValue();
        double high = max.doubleValue();
        if (low == high) {
            values.add(min.longValue());
        }
        else {
            addValues(low, high, values);
        }
        markerLists.add(new MarkerList(values, "%d"));

    }

    
    private void addValues(double low, double high, List<Number> values) {
        long step = computeStep(high, low);
        long start = (long) (low / step) * step;
        long markerValue = start;
        values.add(markerValue);
        boolean ready = false;
        while (! ready) {
            markerValue += step;
            values.add(markerValue);
            ready = markerValue > high;
        }
    }


    private long computeStep(double high, double low) {
        Scientific range = new Scientific(high - low);
        return (long) (computeNormalizedStep(range) * range.factor() / 10);
    }


    private long computeNormalizedStep(Scientific range) {
        double coefficient = range.getCoefficient() * 10;
        if (coefficient < 15) {
            return 1;
        }
        if (coefficient < 20) {
            return 2;
        }
        if (coefficient < 50) {
            return 5;
        }
        return 10;
    }


}
