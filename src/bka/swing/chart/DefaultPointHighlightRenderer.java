/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public class DefaultPointHighlightRenderer extends PointRenderer {


    DefaultPointHighlightRenderer() {
        color = Color.YELLOW;
    }


    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }


    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    
    @Override
    public void draw(Graphics2D g2d, DataPoint dataPoint, Point location) {
        Point point = location; //dataPoint.getHighlightPosition();
        String xLabel = xLabel(dataPoint);
        String yLabel = yLabel(dataPoint);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int xWidth = fontMetrics.stringWidth(xLabel);
        int yWidth = fontMetrics.stringWidth(yLabel);
        int labelWidth = Math.max(xWidth, yWidth);
        int labelBase = point.y - fontMetrics.getHeight();
        Composite originalComposite = g2d.getComposite();
        g2d.setPaint(color);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.fillRoundRect(point.x - labelWidth / 2, labelBase - fontMetrics.getAscent(), labelWidth, fontMetrics.getHeight() * 2, 5, 5);
        g2d.setComposite(originalComposite);
        g2d.setColor(borderColor);
        g2d.drawRoundRect(point.x - labelWidth / 2, labelBase - fontMetrics.getAscent(), labelWidth, fontMetrics.getHeight() * 2, 5, 5);
        g2d.setColor(textColor);
        g2d.drawString(xLabel, point.x - xWidth / 2, labelBase);
        g2d.drawString(yLabel, point.x - yWidth / 2, labelBase + fontMetrics.getHeight());
    }
    
    
    @Override
    public void drawSymbol(Graphics2D g2d, int x, int y) {
        throw new java.lang.NoSuchMethodError();
    }
    
    
    private Color textColor = Color.BLACK;
    private Color borderColor = new Color(137, 51, 0);

}
