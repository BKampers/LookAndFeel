/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DefaultPointHighlightRenderer {


    public Color setBackground() {
        return background;
    }


    public void getBackground(Color color) {
        this.background = color;
    }


    public void setTextColor(Color textColor) {
        this.textColor = textColor;
    }


    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    
    public void draw(Graphics2D g2d, DataPoint dataPoint, Point labelLocation) {
        String xLabel = xLabel(dataPoint);
        String yLabel = yLabel(dataPoint);
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int xWidth = fontMetrics.stringWidth(xLabel);
        int yWidth = fontMetrics.stringWidth(yLabel);
        int labelWidth = Math.max(xWidth, yWidth);
        int labelBase = labelLocation.y - fontMetrics.getHeight();
        Composite originalComposite = g2d.getComposite();
        g2d.setPaint(background);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.fillRoundRect(labelLocation.x - labelWidth / 2, labelBase - fontMetrics.getAscent(), labelWidth, fontMetrics.getHeight() * 2, 5, 5);
        g2d.setComposite(originalComposite);
        g2d.setColor(borderColor);
        g2d.drawRoundRect(labelLocation.x - labelWidth / 2, labelBase - fontMetrics.getAscent(), labelWidth, fontMetrics.getHeight() * 2, 5, 5);
        g2d.setColor(textColor);
        g2d.drawString(xLabel, labelLocation.x - xWidth / 2, labelBase);
        g2d.drawString(yLabel, labelLocation.x - yWidth / 2, labelBase + fontMetrics.getHeight());
    }


    public void setXFormat(String xFormat) {
        this.xFormat = xFormat;
    }


    public void setYFormat(String yFormat) {
        this.yFormat = yFormat;
    }


    public String xLabel(DataPoint dataPoint) {
        if (xFormat != null) {
            return label(xFormat, dataPoint.getX());
        }
        else {
            return dataPoint.getX().toString();
        }
    }


    public String yLabel(DataPoint dataPoint) {
        if (yFormat != null) {
            return label(yFormat, dataPoint.getY());
        }
        else {
            return dataPoint.getY().toString();
        }
    }


    private String label(String format, Number number) {
        try {
            if (format.indexOf('%') >= 0) {
                Formatter formatter = new Formatter(Locale.getDefault());
                formatter.format(format, number);
                return formatter.toString();
            }
            else {
                java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(format, Locale.getDefault());
                return formatter.format(new Date(number.longValue()));
            }
        }
        catch (Exception ex) {
            Logger.getLogger(DefaultPointHighlightRenderer.class.getName()).log(Level.FINEST, format, ex);
            return number.toString();
        }
    }

    
    private Color background = Color.YELLOW;

    private Color textColor = Color.BLACK;
    private Color borderColor = new Color(137, 51, 0);

    private String xFormat;
    private String yFormat;

}
