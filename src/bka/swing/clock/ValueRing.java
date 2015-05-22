package bka.swing.clock;

import java.awt.*;
import java.text.DecimalFormat;


public abstract class ValueRing extends Ring {
    
    public ValueRing() {
        rotatedItems = false;
        formatter.setGroupingUsed(false);
    }
    
    
    public void setDecimalFormat(DecimalFormat format) {
        formatter = format;
    }

    public void paintItem(Graphics2D g2d, double value) {
        String string = formatter.format(value);
        paintValue(g2d, string);
    }
    
    
    public abstract void paintValue(Graphics2D g2d, String value);    

    private DecimalFormat formatter = new DecimalFormat();
    
}
