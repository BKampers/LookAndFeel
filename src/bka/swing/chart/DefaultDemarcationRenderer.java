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
    
  
    public void draw(Graphics2D g2d) {
        if (palette != null && palette.length > 0) {
            DataSet dataSet = chartPanel.getDataSet();
            switch (chartPanel.getDemarcationMode()) {
                case X: {
                    int colorIndex = 0;
                    java.util.List<Number> values = dataSet.xDemarcations.values;
                    int count = values.size();
                    for (int i = 1; i < count; i++) {
                        int x0 = Math.max(dataSet.xPixel(values.get(i-1)), chartPanel.areaLeft());
                        int x1 = Math.min(dataSet.xPixel(values.get(i)), chartPanel.areaRight());
                        g2d.setColor(palette[colorIndex]);
                        g2d.fillRect(x0, chartPanel.areaTop(), x1 - x0, chartPanel.areaHeight());
                        colorIndex = (colorIndex + 1) % palette.length;
                    }
                    break;
                }
                case Y: {
                    int colorIndex = 0;
                    int xMinPixel = dataSet.xPixel(dataSet.getXMin());
                    int width = dataSet.xPixel(dataSet.getXMax()) - xMinPixel;
                    java.util.List<Number> values = dataSet.yDemarcations.values;
                    int count = values.size();
                    for (int i = 1; i < count; i++) {
                        int y0 = Math.min(dataSet.yPixel(values.get(i-1)), chartPanel.areaBottom());
                        int y1 = Math.max(dataSet.yPixel(values.get(i)), chartPanel.areaTop());
                        g2d.setColor(palette[colorIndex]);
                        g2d.fillRect(xMinPixel, y0, width, y1 - y0);
                        colorIndex = (colorIndex + 1) % palette.length;
                    }
                    break;
                }
            }
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
