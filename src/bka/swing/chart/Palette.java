/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.Color;


class Palette {

    
    Palette(String key) {
        try {
            colors = (Color[]) javax.swing.UIManager.get(key);
        }
        catch (Exception ex) {
            colors = null;
        }
        if (colors == null) {
            colors = new Color[] { Color.BLUE, Color.RED, Color.MAGENTA, Color.ORANGE, Color.GREEN };
        }
    }

    
    Color next() {
        Color color = colors[index];
        index = (index + 1) % colors.length;
        return color;
    }

    
    private Color[] colors;
    private int index = 0;

}
