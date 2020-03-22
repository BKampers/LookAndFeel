/*
** © Bart Kampers
*/

package bka.chart;

import java.util.*;


public class Range {


    public Range(Range range) {
        this(range.min, range.max);
    }


    /**
     * @param min null allowed
     * @param max null allowed
     */
    public Range(Number min, Number max) {
        this.min = min;
        this.max = max;
    }


    /**
     * Set min and max
     * @param min
     * @param max
     * @throws NullPointerException if min or max is null
     */
    public void set(Number min, Number max) {
        setMin(min);
        setMax(max);
    }


    /**
     * @return true if both min and max are not null, false otherwise
     */
    public boolean isInitialized() {
        return min != null && max != null;
    }


    /**
     * @return true is min is not null, false otherwise
     */
    public boolean isMinSet() {
        return min != null;
    }

    /**
     * @return min
     * @throws NullPointerException if min not initialized
     */
    public Number getMin() {
        return Objects.requireNonNull(min);
    }


    /**
     * @param min non null
     * @throws NullPointerException if min is null
     */
    public void setMin(Number min) {
        this.min = Objects.requireNonNull(min);
    }

    /**
     * @return true if max is not null, false otherwise
     */
    public boolean isMaxSet() {
        return max != null;
    }

    /**
     * @return max
     * @throws NullPointerException if max not initialized
     */
    public Number getMax() {
        return Objects.requireNonNull(max);
    }

    /**
     * @param max non null
     * @throws NullPointerException if max is null
     */
    public void setMax(Number max) {
        this.max = Objects.requireNonNull(max);
    }

    /**
     * @param number
     * @return true if given number id between min and max,
     *         where min is handled as negative-infinity if null and max is handles as positive-infinity if null
     */
    public boolean includes(Number number) {
        return (min == null || min.doubleValue() <= number.doubleValue()) && (max == null || number.doubleValue() <= max.doubleValue());
    }


    private Number min;
    private Number max;

}
