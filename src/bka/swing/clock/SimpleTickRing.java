package bka.swing.clock;

import java.awt.*;

public class SimpleTickRing extends TickRing {

    public SimpleTickRing(Point center, int radius, Scale scale) {
        this(center, radius, scale, 1.0);
    }

    public SimpleTickRing(Point center, int radius, Scale scale, double interval) {
        this(center, radius, scale, interval, true);
    }

    public SimpleTickRing(Point center, int radius, Scale scale, double interval, boolean itemsRotated) {
        super(center, radius, scale, interval, itemsRotated);
    }

    public void setMajorTickLength(int length) {
        majorTickLength = length;
    }
    
    public void setMinorTickLength(int length) {
        minorTickLength = length;
    }

    @Override
    public void paintMajorTick(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.drawLine(0, 0, 0, majorTickLength);
    }

    @Override
    public void paintMinorTick(Graphics2D g2d) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.YELLOW);
        g2d.drawLine(0, 0, 0, minorTickLength);
    }
    
    
    private int majorTickLength = 4;
    private int minorTickLength = 2;
    
}
