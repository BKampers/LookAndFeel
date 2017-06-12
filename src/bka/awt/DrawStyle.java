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
        this.colors.putAll(drawStyle.colors);
        this.strokes.putAll(drawStyle.strokes);
    }


    public Map<Object, Color> getColors() {
        return new HashMap<>(colors);
    }


    public void setColors(Map<Object, Color> colors) {
        this.colors.putAll(colors);
    }


    public Map<Object, Stroke> getStrokes() {
        return new HashMap<>(strokes);
    }


    public void setStrokes(Map<Object, Stroke> strokes) {
        this.strokes.putAll(strokes);
    }


    public Color getColor(Object key) {
        return colors.get(key);
    }


    public void setColor(Object key, Color color) {
        colors.put(key, color);
    }


    public Stroke getStroke(Object key) {
        return strokes.get(key);
    }


    public void setStroke(Object key, Stroke stroke) {
        strokes.put(key, stroke);
    }


    private final Map<Object, Color> colors = new HashMap<>();
    private final Map<Object, Stroke> strokes = new HashMap<>();

}
