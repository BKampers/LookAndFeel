/*
** Copyright © Bart Kampers
*/

package bka.awt;

import java.awt.*;
import java.util.*;


public class DrawStyle {


    public DrawStyle() {
    }


    public DrawStyle(DrawStyle drawStyle) {
        this.paints.putAll(drawStyle.paints);
        this.strokes.putAll(drawStyle.strokes);
    }


    public Map<Object, Paint> getPaints() {
        return new HashMap<>(paints);
    }


    public void setPaints(Map<Object, Paint> paints) {
        this.paints.putAll(paints);
    }


    public Map<Object, Stroke> getStrokes() {
        return new HashMap<>(strokes);
    }


    public void setStrokes(Map<Object, Stroke> strokes) {
        this.strokes.putAll(strokes);
    }


    public Paint getPaint(Object key) {
        return paints.get(key);
    }


    public void setPaint(Object key, Paint paint) {
        paints.put(key, paint);
    }


    public Stroke getStroke(Object key) {
        return strokes.get(key);
    }


    public void setStroke(Object key, Stroke stroke) {
        strokes.put(key, stroke);
    }


    private final Map<Object, Paint> paints = new HashMap<>();
    private final Map<Object, Stroke> strokes = new HashMap<>();

}
