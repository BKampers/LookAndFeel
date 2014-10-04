/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


public class IntegerDemarcations extends Demarcations {


    protected void compute(Number min, Number max) {
        super.compute(min, max);
        java.util.ArrayList<Number> integerValues = new java.util.ArrayList<Number>();
        for (Number value : values) {
            double doubleValue = value.doubleValue();
            if (Math.round(doubleValue * 10) % 10 == 0) {
                integerValues.add(Math.round(doubleValue));
            }
        }
        values = integerValues;
        format = "%d";
    }


}
