package bka.swing.clock;

import java.awt.*;
import java.text.*;


public abstract class ValueRing extends Ring {
    
    public ValueRing() {
        rotatedItems = false;
    }
    
    
    public void setNumberFormat(NumberFormat format) {
        this.format = format;
    }


   @Override
    public void paintItem(Graphics2D g2d, double value) {
        paintValue(g2d, getFormat().format(value));
    }
    
    
    private NumberFormat getFormat() {
        if (format == null) {
            format = new DecimalFormat();
            format.setGroupingUsed(false);
        }
        return format;
    }
    
    
    public abstract void paintValue(Graphics2D g2d, String value);    

    
    private NumberFormat format;

}
