/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Palette {

    
    public Palette(String uiManagerKey) {
        try {
            colors = (Color[]) javax.swing.UIManager.get(uiManagerKey);
        }
        catch (Exception ex) {
            Logger.getLogger(Palette.class.getName()).log(Level.WARNING, uiManagerKey, ex);
            colors = new Color[] { Color.WHITE };
        }
    }
    
    
    public Palette(int count) {
        if (count >= 2) {
            generateColors(count);
        }
        else {
            colors = new Color[] { Color.WHITE };
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
    
    
    private void generateColors(int count) {
        colors = new Color[count];
        float range = count;
        for (int i = 0; i < count; ++i) {
            colors[i] = new Color(Color.HSBtoRGB(i / range, 1.0f, 1.0f));
        }
    }
    
    
    private Color[] colors;
    private int index;

}
