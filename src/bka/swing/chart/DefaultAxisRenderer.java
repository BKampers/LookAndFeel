/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import java.awt.*;


public class DefaultAxisRenderer extends AxisRenderer {
    
    
    public DefaultAxisRenderer() {
        this(javax.swing.UIManager.getColor("Chart.xAxisColor"), javax.swing.UIManager.getColor("Chart.yAxisColor"));
    }
    
    
    public DefaultAxisRenderer(Color axisColor) {
        this(axisColor, axisColor);
    }
    
    
    public DefaultAxisRenderer(Color xAxisColor, Color yAxisColor) {
        this.xAxisColor = (xAxisColor != null) ? xAxisColor : Color.BLACK;
        this.yAxisColor = (yAxisColor != null) ? yAxisColor : Color.BLACK;
    }

    
    @Override
    public void drawXAxis(Graphics2D g2d) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int xMin = xMin();
        int xMax = xMax();
        int y;
        switch (getXAxisPosition()) {        
            case MINIMUM : y = yMin(); break;
            case MAXIMUM : y = yMax(); break;
            default      : y = y0();
        }
        int yLabel = y + fontMetrics.getHeight();
        g2d.setColor(xAxisColor);
        g2d.setStroke(new BasicStroke());
        g2d.drawLine(xMin, y, xMax, y);
        java.util.List<Number> values = xDemarcationValues();
        int count = values.size();
        for (int i = 0; i < count; i++) {
            Number value = values.get(i);
            int x = xPixel(value);
            if (xMin <= x && x <= xMax) {
                g2d.drawLine(x, y - 2, x, y + 2);
                String label = xDemarcationLabel(value);
                if (label.endsWith(">")) {
                    // draw label between two markers
                    if (i < count - 1) { 
                        label = label.substring(0, label.length() - 1);
                        int width = fontMetrics.stringWidth(label);
                        int xNext = xPixel(values.get(i + 1));
                        if (xNext <= xMax) {
                            g2d.drawString(label, x + (xNext - x) / 2 - width / 2, yLabel);
                        }
                    }
                }
                else {
                    // draw label at the marker
                    int width = fontMetrics.stringWidth(label);
                    g2d.drawString(label, x - width / 2, yLabel);
                }
            }
        }
        if (xTitle != null) {
            int width = fontMetrics.stringWidth(xTitle);
            int xPos = xMin() + (xMax() - xMin()) / 2 + width / 2;
            int yPos = yMin() + fontMetrics.getHeight() * 3;
            g2d.drawString(xTitle,  xPos, yPos);
            drawArrow(g2d, xPos + width, yPos);
        }
        if (xUnit != null) {
            String string = '[' + xUnit + ']';
            g2d.drawString(string, xMax() + 3, yMin() + fontMetrics.getHeight() / 4);
        }
    }
    
    
    public void drawYAxis(Graphics2D g2d) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int x;
        switch (getYAxisPosition()) {        
            case MINIMUM : x = xMin(); break;
            case MAXIMUM : x = xMax(); break;
            default      : x = x0();
        }
        g2d.setColor(yAxisColor);
        g2d.drawLine(x, yMin(), x, yMax());
        int titlePosition = xMin();
        for (Number value : yDemarcationValues()) {
            int y = yPixel(value);
            if (yMax() <= y && y <= yMin()) {
                g2d.drawLine(x - 2, y, x + 2, y);
                String label = yDemarcationLabel(value);
                int width = fontMetrics.stringWidth(label);
                int labelPosition = x - width - 4;
                g2d.drawString(label, labelPosition, y + fontMetrics.getDescent());
                titlePosition = Math.min(titlePosition, labelPosition);
            }
        }
        if (yTitle != null) {
            int width = fontMetrics.stringWidth(yTitle);
            int xPos = titlePosition - fontMetrics.getHeight();
            int yPos = yMax() + (yMin() - yMax()) / 2 + width / 2;
            g2d.rotate(-0.5 * Math.PI, xPos, yPos);
            g2d.drawString(yTitle,  xPos, yPos);
            drawArrow(g2d, xPos + width, yPos);
            g2d.rotate(+0.5 * Math.PI, xPos, yPos);
        }
        if (yUnit != null) {
            String string = '[' + yUnit + ']';
            int width = fontMetrics.stringWidth(string);
            g2d.drawString(string, xMin() - width / 2, yMax() - fontMetrics.getHeight());
        }
    }
    
    
    private void drawArrow(Graphics2D g2d, int x, int y) {
        Font defaultFont = g2d.getFont();
        Font arial = new Font("Courier", defaultFont.getStyle(), defaultFont.getSize());
        g2d.setFont(arial);
        g2d.drawString(" →",  x, y);
        g2d.setFont(defaultFont);
    }
    
    
    private Color xAxisColor;
    private Color yAxisColor;

}
