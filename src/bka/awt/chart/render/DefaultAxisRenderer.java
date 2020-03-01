/*
** Copyright © Bart Kampers
*/

package bka.awt.chart.render;


import bka.chart.grid.*;
import java.awt.*;
import java.util.*;


public class DefaultAxisRenderer extends AxisRenderer {

    
    public DefaultAxisRenderer() {
        this(Color.BLACK);
    }
    
    
    public DefaultAxisRenderer(Color color) {
        this(color, color, color, color, color);
    }


    public DefaultAxisRenderer(Color axisColor, Color markerColor, Color labelColor, Color titleColor, Color unitColor) {
        this.axisColor = axisColor;
        this.markerColor = markerColor;
        this.labelColor = labelColor;
        this.titleColor = titleColor;
        this.unitColor = unitColor;
    }


    @Override
    public void drawXAxis(Graphics2D g2d, Locale locale) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int xMin = xMin();
        int xMax = xMax();
        int y = yAxisPixelPosition();
        g2d.setStroke(new BasicStroke());
        if (axisColor != null) {
            g2d.setColor(axisColor);
            g2d.drawLine(xMin, y, xMax, y);
        }
        int labelLine = 1;
        boolean drawMarker = true;
        for (Grid.MarkerList markerSet : xMarkerLists()) {
            java.util.List<Number> values = markerSet.getValues();
            int count = values.size();
            for (int i = 0; i < count; ++i) {
                Number value = values.get(i);
                int x = xPixel(value);
                if (xMin <= x && x <= xMax) {
                    if (drawMarker && markerColor != null) {
                        g2d.setColor(markerColor);
                        g2d.drawLine(x, y - 2, x, y + 2);
                    }
                    if (labelColor != null) {
                        g2d.setColor(labelColor);
                        int yLabel = y + fontMetrics.getHeight() * labelLine;
                        String label = markerSet.getLabel(locale, value);
                        if (label.endsWith(">")) {
                            // draw label between two markers
                            if (i < count - 1) {
                                int xNext = xPixel(values.get(i + 1));
                                if (xNext <= xMax) {
                                    label = label.substring(0, label.length() - 1);
                                    int width = fontMetrics.stringWidth(label);
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
            }
            drawMarker = false;
            labelLine++;
        }
        if (getTitle() != null && titleColor != null) {
            int width = fontMetrics.stringWidth(getTitle());
            int xPos = xMin + (xMax() - xMin) / 2 + width / 2;
            int yPos = yMin() + fontMetrics.getHeight() * 3;
            g2d.setColor(titleColor);
            g2d.drawString(getTitle(), xPos, yPos);
            drawArrow(g2d, xPos + width, yPos);
        }
        if (getUnit() != null && unitColor != null) {
            g2d.setColor(unitColor);
            g2d.drawString(getUnitText(getUnit()), xMax() + 3, yMin() + fontMetrics.getHeight() / 4);
        }
    }

    
    @Override
    public void drawYAxis(Graphics2D g2d, Locale locale) {
        FontMetrics fontMetrics = g2d.getFontMetrics();
        int x = xAxisPixelPosition();
        int yMin = yMin();
        int yMax = yMax();
        int xMin = xMin();
        if (axisColor != null) {
            g2d.setColor(axisColor);
            g2d.drawLine(x, yMin, x, yMax);
        }
        int titlePosition = xMin;
        int labelOffset = 4;
        boolean drawMarker = true;
        for (Grid.MarkerList markerSet : yMarkerLists()) {
            int columnWidth = 0;
            java.util.List<Number> values = markerSet.getValues();
            for (Number value : values) {
                int y = yPixel(value);
                if (yMax <= y && y <= yMin) {
                    if (drawMarker && markerColor != null) {
                        g2d.setColor(markerColor);
                        g2d.drawLine(x - 2, y, x + 2, y);
                    }
                    if (labelColor != null) {
                        g2d.setColor(labelColor);
                        String label = markerSet.getLabel(locale, value);
                        int width = fontMetrics.stringWidth(label);
                        columnWidth = Math.max(width, columnWidth);
                        int labelPosition = x - width - labelOffset;
                        g2d.drawString(label, labelPosition, y + fontMetrics.getDescent());
                        titlePosition = Math.min(titlePosition, labelPosition);
                    }
                }
            }
            labelOffset = columnWidth;
            drawMarker = false;
        }
        if (getTitle() != null && titleColor != null) {
            int width = fontMetrics.stringWidth(getTitle());
            int xPos = titlePosition - fontMetrics.getHeight();
            int yPos = yMax + (yMin - yMax) / 2 + width / 2;
            g2d.rotate(-0.5 * Math.PI, xPos, yPos);
            g2d.setColor(titleColor);
            g2d.drawString(getTitleText(getTitle()), xPos, yPos);
            drawArrow(g2d, xPos + width, yPos);
            g2d.rotate(+0.5 * Math.PI, xPos, yPos);
        }
        if (getUnit() != null && unitColor != null) {
            String string = getUnitText(getUnit());
            int width = fontMetrics.stringWidth(string);
            g2d.setColor(unitColor);
            g2d.drawString(string, xMin - width / 2, yMax - fontMetrics.getHeight());
        }
    }


    public void setTitleFormat(String titleFormat) {
        this.titleFormat = titleFormat;
    }


    public void setUnitFormat(String unitFormat) {
        this.unitFormat = unitFormat;
    }


    protected String getUnitFormat() {
        return Objects.toString(unitFormat, "%s");
    }


    protected String getTitleFormat() {
        return Objects.toString(titleFormat, "%s");
    }


    private String getTitleText(String title) {
        return String.format(getTitleFormat(), title);
    }


    private String getUnitText(String unit) {
        return String.format(getUnitFormat(), unit);
    }


    private int xAxisPixelPosition() {
        switch (getYAxisPosition()) {
            case MINIMUM:
                return xMin();
            case MAXIMUM:
                return xMax();
            default:
                return x0();
        }
    }


    private int yAxisPixelPosition() {
        switch (getXAxisPosition()) {
            case MINIMUM:
                return yMin();
            case MAXIMUM:
                return yMax();
            default:
                return y0();
        }
    }

    
    private void drawArrow(Graphics2D g2d, int x, int y) {
        Font font = g2d.getFont();
        g2d.setFont(new Font("Courier", font.getStyle(), font.getSize()));
        g2d.drawString(" \u2192", x, y);
        g2d.setFont(font);
    }
    
    
    private final Color axisColor;
    private final Color markerColor;
    private final Color labelColor;
    private final Color titleColor;
    private final Color unitColor;
    
    private String titleFormat;
    private String unitFormat;

}
