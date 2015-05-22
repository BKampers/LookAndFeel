/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.Color;


public class Palette {

    
    Palette(String uiManagerKey) {
        try {
            colors = (Color[]) javax.swing.UIManager.get(uiManagerKey);
        }
        catch (Exception ex) {
            colors = null;
        }
        if (colors == null) {
            colors = new Color[10];
            for (int i = 0; i < 10; ++i) {
                colors[i] = new Color((i + 1) * 10, (i + 1) * 10, (i + 1) * 25);
            }
        }
    }

    
    Color next() {
        Color color = colors[index];
        index = (index + 1) % colors.length;
        return color;
    }
    
    
    void reset() {
        index = 0;
    }

    
    private Color[] colors;
    private int index = 0;

}
