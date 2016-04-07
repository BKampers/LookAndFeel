/*
** Copyright © Bart Kampers
*/


package bka.swing.chart;

import java.awt.*;


public class BarRenderer extends CoordinateAreaRenderer<Rectangle> {


    public BarRenderer(int width, AreaLooks paintFactory) {
        this.width = width;
        this.paintFactory = paintFactory;
    }


    public BarRenderer(AreaLooks paintFactory) {
        this(7, paintFactory);
    }


    public void setWidth(int width) {
        this.width = width;
    }
    
    /**
     * Set horizontal shift of the bar w.r.t. the x value.
     * Useful when plotting multiple bar graphs into one chart.
     * @param shift
     */
    public void setShift(int shift) {
        this.shift = shift;
    }
    
    
    @Override
    protected void draw(Graphics2D g2d, PointAreaGeometry<Rectangle> geometry) {
        Rectangle area = geometry.getArea();
        g2d.setPaint(paintFactory.getPaint(geometry.getArea()));
        g2d.fill(area);
        if (borderColor != null) {
            g2d.setPaint(borderColor);
            g2d.draw(area);
        }
    }

    
    @Override
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        int fontHeight = g2d.getFontMetrics().getHeight();
        int left = x - width / 2;
        int top = y - fontHeight / 2;
        Rectangle area = new Rectangle(left, top, width, fontHeight);
        g2d.setPaint(paintFactory.getPaint(area));
        g2d.fillRect(left, top, width, fontHeight);
        if (borderColor != null) {
            g2d.setPaint(borderColor);
            g2d.draw(area);
        }
    }


    @Override
    protected Rectangle createArea(int x, int y) {
        int left = x - width / 2 + shift;
        int barHeight = chartPanel.areaBottom() - y;
        return new Rectangle(left, y, width, barHeight);

    }

    
    private int width;
    private int shift;
    private AreaLooks paintFactory;
    private Color borderColor;

}
