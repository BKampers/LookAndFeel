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
        for (Map.Entry<K, V> entry : source.entrySet()) {
            add(entry.getKey(), entry.getValue(), false);
        }
    }


    public final void add(K key, V value) {
        add(key, value, false);
    }


    public final void add(K key, V value, boolean outsideWindow) {
        entries.add(new ChartDataElement<>(key, value, outsideWindow));
    }


    @Override
    public final Iterator<ChartDataElement<K, V>> iterator() {
        return entries.iterator();
    }


    public final boolean isEmpty() {
        return entries.isEmpty();
    }


    public final int size() {
        return entries.size();
    }


    public ChartDataElement<K, V> get(int index) {
        return entries.get(index);
    }


    private final List<ChartDataElement<K, V>> entries;

}
