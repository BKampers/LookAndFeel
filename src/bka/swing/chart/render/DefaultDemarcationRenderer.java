/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;

import bka.swing.chart.custom.*;
import bka.swing.chart.geometry.ChartGeometry;
import java.awt.*;


public class DefaultDemarcationRenderer extends DemarcationRenderer {

    
    public DefaultDemarcationRenderer(GridLooks looks) {
        this.looks = looks;
    }
    
    
    @Override
    public void draw(Graphics2D g2d) {
        switch (chartPanel.getDemarcationMode()) {
            case X:
                drawVerticalDemarcations(g2d);
                drawHorizontalLines(g2d);
                drawVerticalLines(g2d);
                break;
            case Y:
                drawHorizontalDemarcations(g2d);
                drawHorizontalLines(g2d);
                drawVerticalLines(g2d);
                break;
        }
    }


    private void drawVerticalLines(Graphics2D g2d) {
        Stroke stroke = looks.getStroke();
        Paint paint = looks.getGridPaint();
        if (stroke != null && paint != null) {
            g2d.setPaint(paint);
            g2d.setStroke(stroke);
            int areaTop = chartPanel.areaTop();
            int areaHeight = chartPanel.areaHeight();
            int areaLeft = chartPanel.areaLeft();
            ChartGeometry chartGeometry = chartPanel.getChartGeometry();
            java.util.List<Number> values = chartGeometry.getXDemarcations().getValues();
            int count = values.size();
            for (int i = 1; i < count; ++i) {
                int x = Math.max(chartGeometry.xPixel(values.get(i-1)), areaLeft);
                g2d.drawLine(x, areaTop, x, areaTop + areaHeight);
            }
        }
    }


    private void drawHorizontalLines(Graphics2D g2d) {
        Stroke stroke = looks.getStroke();
        Paint paint = looks.getGridPaint();
        if (stroke != null && paint != null) {
            g2d.setPaint(paint);
            g2d.setStroke(stroke);
            int areaTop = chartPanel.areaTop();
            int areaLeft = chartPanel.areaLeft();
            int areaRight = chartPanel.areaRight();
            ChartGeometry chartGeometry = chartPanel.getChartGeometry();
            java.util.List<Number> values = chartGeometry.getYDemarcations().getValues();
            int count = values.size();
            for (int i = 1; i < count; ++i) {
                int y = Math.max(chartGeometry.yPixel(values.get(i)), areaTop);
                g2d.drawLine(areaLeft, y, areaRight, y);
            }
        }
    }


    private void drawVerticalDemarcations(Graphics2D g2d) {
        int areaTop = chartPanel.areaTop();
        int areaHeight = chartPanel.areaHeight();
        int areaLeft = chartPanel.areaLeft();
        int areaRight = chartPanel.areaRight();
        ChartGeometry chartGeometry = chartPanel.getChartGeometry();
        java.util.List<Number> values = chartGeometry.getXDemarcations().getValues();
        int count = values.size();
        for (int i = 1; i < count; ++i) {
            int paintLeft = chartGeometry.xPixel(values.get(i-1));
            int paintRight = chartGeometry.xPixel(values.get(i));
            int left = Math.max(paintLeft, areaLeft);
            int right = Math.min(paintRight, areaRight);
            int width = right - left;
            if (width > 0) {
                Rectangle area = new Rectangle(paintLeft, areaTop, paintRight - paintLeft, areaHeight);
                g2d.setPaint(looks.getVerticalPaint(area, i));
                area = new Rectangle(left, areaTop, right - left, areaHeight);
                g2d.fill(area);
            }
        }
    }
    
    
    private void drawHorizontalDemarcations(Graphics2D g2d) {
        int areaTop = chartPanel.areaTop();
        int areaBottom = chartPanel.areaBottom();
        int areaLeft = chartPanel.areaLeft();
        int areaWidth = chartPanel.areaWidth();
        ChartGeometry chartGeometry = chartPanel.getChartGeometry();
        java.util.List<Number> values = chartGeometry.getYDemarcations().getValues();
        int count = values.size();
        for (int i = 1; i < count; ++i) {
            int paintBottom = chartGeometry.yPixel(values.get(i-1));
            int paintTop = chartGeometry.yPixel(values.get(i));
            int bottom = Math.min(paintBottom, areaBottom);
            int top = Math.max(paintTop, areaTop);
            int height = bottom - top;
            if (height > 0) {
                Rectangle area = new Rectangle(areaLeft, paintTop, areaWidth, paintBottom - paintTop);
                g2d.setPaint(looks.getHorizontalPaint(area, i));
                area = new Rectangle(areaLeft, top, areaWidth, height);
                g2d.fill(area);
            }
        }
    }


    private final GridLooks looks;
    
}
