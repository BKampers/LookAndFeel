/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.util.*;


public class ChartData<K, V> implements Iterable<ChartDataElement<K, V>> {


    public ChartData() {
        entries = new ArrayList();
    }


    public ChartData(Map<K, V> source) {
        entries = new ArrayList<>(source.size());
        for (Map.Entry<K,V> entry : source.entrySet()) {
            add(entry.getKey(), entry.getValue());
        }
    }


    public void add(K key, V value) {
        entries.add(new ChartDataElement<>(key, value));
    }


    @Override
    public Iterator<ChartDataElement<K, V>> iterator() {
        return entries.iterator();
    }


    public int size() {
        return entries.size();
    }


    private final List<ChartDataElement<K, V>> entries;

}
