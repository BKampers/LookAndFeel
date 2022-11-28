/*
** © Bart Kampers
*/

package bka.swing.clock;

import java.awt.*;


public abstract class IntervalRing extends Ring {

    protected IntervalRing(Point center, int radius, Scale scale, double interval, boolean itemsRotated) {
        super(center, radius, scale);
        this.interval = interval;
        this.itemsRotated = itemsRotated;
    }

    public void setItemsRotated(boolean rotated) {
        itemsRotated = rotated;
    }

    protected boolean itemsAreRotated() {
        return itemsRotated;
    }

    @Override
    public void paint(Graphics2D g2d) {
        Scale scale = getScale();
        Point center = getCenter();
        double minValue = Math.min(scale.getMinValue(), scale.getMaxValue());
        double maxValue = Math.max(scale.getMinValue(), scale.getMaxValue());
        if (interval > 0.0) {
            for (double value = minValue; value <= maxValue; value += interval) {
                if (itemsRotated) {
                    double angle = scale.angle(value);
                    g2d.rotate(angle, center.x, center.y);
                    g2d.translate(center.x, center.y - getRadius());
                    paintItem(g2d, value);
                    g2d.translate(-center.x, -(center.y - getRadius()));
                    g2d.rotate(-angle, center.x, center.y);
                }
                else {
                    Point translation = itemPoint(value);
                    g2d.translate(translation.x, translation.y);
                    paintItem(g2d, value);
                    g2d.translate(-translation.x, -translation.y);
                }
            }
        }
    }

    protected abstract void paintItem(Graphics2D g2d, double value);

    private Point itemPoint(double value) {
        Point point = new Point();
        double angle = getScale().angle(value);
        point.x = getCenter().x + Math.round((float) (Math.sin(angle) * getRadius()));
        point.y = getCenter().y - Math.round((float) (Math.sin(Math.PI / 2 - angle) * getRadius()));
        return point;
    }

    protected double getInterval() {
        return interval;
    }

    public void setInterval(double interval) {
        this.interval = interval;
    }

    private double interval;
    private boolean itemsRotated;

}
