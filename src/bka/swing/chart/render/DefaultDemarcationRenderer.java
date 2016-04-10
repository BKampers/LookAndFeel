/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;

import bka.swing.chart.geometry.ChartGeometry;
import java.awt.*;


public class DefaultDemarcationRenderer extends DemarcationRenderer {

    
    public DefaultDemarcationRenderer() {
        setDefaultPalette();
    }
    
    
    public final void setPalette(Color[] palette) {
        if (palette != null) {
            this.palette = palette;
        }
        else {
            setDefaultPalette();
        }
    }
    
  
    @Override
    public void draw(Graphics2D g2d) {
        if (palette != null && palette.length > 0) {
            switch (chartPanel.getDemarcationMode()) {
                case X:
                    drawVerticalDemarcations(g2d);
                    break;
                case Y:
                    drawHorizontalDemarcations(g2d);
                    break;
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
        int colorIndex = 0;
        for (int i = 1; i < count; ++i) {
            int left = Math.max(chartGeometry.xPixel(values.get(i-1)), areaLeft);
            int right = Math.min(chartGeometry.xPixel(values.get(i)), areaRight);
            g2d.setColor(palette[colorIndex]);
            g2d.fillRect(left, areaTop, right - left, areaHeight);
            colorIndex = (colorIndex + 1) % palette.length;
        }
    }
    
    
    private void drawHorizontalDemarcations(Graphics2D g2d) {
        int areaTop = chartPanel.areaTop();
        int areaBottom = chartPanel.areaBottom();
        int areaLeft = chartPanel.areaLeft();
        int width = chartPanel.areaWidth();
        ChartGeometry chartGeometry = chartPanel.getChartGeometry();
        java.util.List<Number> values = chartGeometry.getYDemarcations().getValues();
        int count = values.size();
        int colorIndex = 0;
        for (int i = 1; i < count; ++i) {
            int bottom = Math.min(chartGeometry.yPixel(values.get(i-1)), areaBottom);
            int top = Math.max(chartGeometry.yPixel(values.get(i)), areaTop);
            g2d.setColor(palette[colorIndex]);
            g2d.fillRect(areaLeft, top, width, bottom - top);
            colorIndex = (colorIndex + 1) % palette.length;
        }
    }


    private void setDefaultPalette() {
        try {
            palette = (Color[]) javax.swing.UIManager.get("Chart.demarcationPalette");
        }
        catch (Exception ex) {
            palette = null;
        }
        if (palette == null) {
            palette = new Color[] { Color.WHITE, Color.LIGHT_GRAY };
        }
    }


    private Color[] palette;
    
}
