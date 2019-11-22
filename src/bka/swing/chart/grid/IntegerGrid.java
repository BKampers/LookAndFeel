/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.grid;

import bka.numeric.*;
import java.util.*;


public class IntegerGrid extends Grid {


    @Override
    protected void compute(Number min, Number max) {
        double low = min.doubleValue();
        double high = max.doubleValue();
        long step = computeStep(high, low);
        long start = (long) (low / step) * step;
        long markerValue = start;
        List<Number> values = new ArrayList<>();
        values.add(markerValue);
        boolean ready = false;
        while (! ready) {
            markerValue += step;
            values.add(markerValue);
            ready = markerValue > high;
        }
        markerLists.add(new MarkerList(values, "%d"));

    }


    private long computeStep(double high, double low) {
        Scientific range = new Scientific(high - low);
        long step = (long) (computeNormalizedStep(range) * range.factor() / 10);
        if (step == 0) {
            return 1;
        }
        return step;
    }


    private long computeNormalizedStep(Scientific range) {
        double coefficient = range.getCoefficient() * 10;
        if (coefficient < 15) {
            return 1;
        }
        else if (coefficient < 20) {
            return 2;
        }
        else if (coefficient < 50) {
            return 5;
        }
        return 10;
    }


}
