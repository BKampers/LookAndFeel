/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.grid;


public class IntegerDemarcations extends Demarcations {


    @Override
    protected void compute(Number min, Number max) {
        double low = min.doubleValue();
        double high = max.doubleValue();
        bka.numeric.Scientific range = new bka.numeric.Scientific(high - low);
        long step = 10;
        double coefficient = range.getCoefficient() * 10;
        if (coefficient < 15) {
            step = 1;
        }
        else if (coefficient < 20) {
            step = 2;
        }
        else if (coefficient < 50) {
            step = 5;
        }
        step = (long) (step * range.factor() / 10);
        long start = (long) (low / step) * step;
        long markerValue = start;
        values.add(markerValue);
        boolean ready = false;
        while (! ready) {
            markerValue += step;
            values.add(markerValue);
            ready = markerValue > high;
        }
        format = "%d";
    }


}
