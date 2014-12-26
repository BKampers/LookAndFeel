/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;

import java.awt.*;


public class DefaultDemarcationRenderer extends DemarcationRenderer {

    
    DefaultDemarcationRenderer() {
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
        DataSet dataSet = chartPanel.getDataSet();
        java.util.List<Number> values = dataSet.xDemarcations.values;
        int count = values.size();
        int colorIndex = 0;
        for (int i = 1; i < count; ++i) {
            int left = Math.max(dataSet.xPixel(values.get(i-1)), areaLeft);
            int right = Math.min(dataSet.xPixel(values.get(i)), areaRight);
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
        DataSet dataSet = chartPanel.getDataSet();
        java.util.List<Number> values = dataSet.yDemarcations.values;
        int count = values.size();
        int colorIndex = 0;
        for (int i = 1; i < count; ++i) {
            int bottom = Math.min(dataSet.yPixel(values.get(i-1)), areaBottom);
            int top = Math.max(dataSet.yPixel(values.get(i)), areaTop);
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
