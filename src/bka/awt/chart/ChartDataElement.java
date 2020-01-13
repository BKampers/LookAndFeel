/*
** Copyright © Bart Kampers
*/

package bka.awt.chart;

import java.util.*;


public class ChartDataElement<K, V> {


    public ChartDataElement(K key, V value, boolean outsideWindow) {
        this.key = key;
        this.value = value;
        this.outsideWindow = outsideWindow;
    }

    
    public K getKey() {
        return key;
    }


    public V getValue() {
        return value;
    }


    public boolean isOutsideWindow() {
        return outsideWindow;
    }


    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (! (other instanceof ChartDataElement)) {
            return false;
        }
        ChartDataElement element = (ChartDataElement) other;
        return Objects.equals(key, element.key) && Objects.equals(value, element.value);
    }


    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }


    private final K key;
    private final V value;
    private final boolean outsideWindow;

}
