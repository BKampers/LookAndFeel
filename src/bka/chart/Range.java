/*
** © Bart Kampers
*/

package bka.chart;


public class Range {


    public Range(Range range) {
        this(range.min, range.max);
    }


    public Range(Number min, Number max) {
        this.min = min;
        this.max = max;
    }


    public void set(Number min, Number max) {
        this.min = min;
        this.max = max;
    }


    public Number getMin() {
        return min;
    }


    public void setMin(Number min) {
        this.min = min;
    }


    public Number getMax() {
        return max;
    }


    public void setMax(Number max) {
        this.max = max;
    }


    private Number min;
    private Number max;

}
