/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


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


    private final K key;
    private final V value;

}
