/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.util.*;


public class ChartDataElement<K, V> {


    ChartDataElement(K key, V value) {
        this.key = key;
        this.value = value;
    }

    
    public K getKey() {
        return key;
    }


    public V getValue() {
        return value;
    }


    @Override
    public boolean equals(Object other) {
        return
            this == other ||
            (other instanceof ChartDataElement) &&
            key.equals(((ChartDataElement) other).key) &&
            value.equals(((ChartDataElement) other).value);
    }


    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(key);
        hash = 97 * hash + Objects.hashCode(value);
        return hash;
    }


    private final K key;
    private final V value;

}
