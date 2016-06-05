/*
** Copyright © Bart Kampers
*/

package bka.awt;

import java.awt.Color;


public class Palette {

        
    public static Color[] generateColors(int count) {
        Color[] colors = new Color[count];
        float range = count;
        for (int i = 0; i < count; ++i) {
            colors[i] = new Color(Color.HSBtoRGB(i / range, 1.0f, 1.0f));
        }
        return colors;
    }
    

}
