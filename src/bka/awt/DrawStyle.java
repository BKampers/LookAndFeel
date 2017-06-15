/*
** Copyright © Bart Kampers
*/

package bka.awt;

import java.awt.*;
import java.awt.font.*;
import java.util.*;


public class DrawStyle {


    public DrawStyle() {
    }


    public DrawStyle(DrawStyle drawStyle) {
        this.colors.putAll(drawStyle.colors);
        this.strokes.putAll(drawStyle.strokes);
        this.fonts.putAll(drawStyle.fonts);
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


    public Map<Object, Map<TextAttribute, Object>> getFonts() {
        return new HashMap<>(fonts);
    }


    public void setFonts(Map<Object, Map<TextAttribute, Object>> fonts) {
        this.fonts.putAll(fonts);
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


    public Map<TextAttribute, Object> getFont(Object key) {
        return fonts.get(key);
    }


    public void setFont(Object key, Map<TextAttribute, Object> font) {
        fonts.put(key, font);
    }


    private final Map<Object, Color> colors = new HashMap<>();
    private final Map<Object, Stroke> strokes = new HashMap<>();
    private final Map<Object, Map<TextAttribute, Object>> fonts = new HashMap<>();

}
