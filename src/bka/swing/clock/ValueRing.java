package bka.swing.clock;

import java.awt.*;
import java.text.*;


public abstract class ValueRing extends IntervalRing {

    protected ValueRing(Point center, int radius, Scale scale, double interval, boolean itemsRotated) {
        super(center, radius, scale, interval, itemsRotated);
    }

    public void setNumberFormat(NumberFormat format) {
        this.format = format;
    }

    @Override
    protected void paintItem(Graphics2D g2d, double value) {
        paintValue(g2d, getFormat().format(value));
    }
    
    protected abstract void paintValue(Graphics2D g2d, String value);

    private NumberFormat getFormat() {
        if (format == null) {
            format = new DecimalFormat();
            format.setGroupingUsed(false);
        }
        return format;
    }

    private NumberFormat format;

}
