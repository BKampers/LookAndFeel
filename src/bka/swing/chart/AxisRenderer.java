/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


public abstract class AxisRenderer {
    
    
    public ChartPanel.AxisPosition getXAxisPosition() {
        return panel.getXAxisPosition();
    }

    
    public ChartPanel.AxisPosition getYAxisPosition() {
        return panel.getYAxisPosition();
    }
    
    
    int xPixel(Number value) {
        return panel.getChartGeometry().xPixel(value);
    }
    
    
    public int xMin() {
        return panel.areaLeft();
    }

    
    public int xMax() {
        return panel.areaRight();
    }

    
    public int x0() {
        return xPixel(0);
    }

    
    int yPixel(Number value) {
        return panel.getChartGeometry().yPixel(value);
    }
    
    
    public int yMin() {
        return panel.areaBottom();
    }

    
    public int yMax() {
        return panel.areaTop();
    }

    
    public int y0() {
        return yPixel(0);
    }
    
    
    public String xDemarcationLabel(Number value) {
        return chartGeometry.xDemarcations.label(value);
    }
    
    
    public java.util.List<Number> xDemarcationValues() {
        java.util.List<Number> values = new java.util.ArrayList<Number>();
        if (chartGeometry.xDemarcations != null) {
            values.addAll(chartGeometry.xDemarcations.values);
        }
        return values;
    }

    
    public String yDemarcationLabel(Number value) {
        return chartGeometry.yDemarcations.label(value);
    }
    
    
    public java.util.List<Number> yDemarcationValues() {
        java.util.List<Number> values = new java.util.ArrayList<Number>();
        Demarcations yDemarcations = chartGeometry.yDemarcations;
        if (yDemarcations != null) {
            values.addAll(yDemarcations.values);
        }
        return values;
    }

    
    public abstract void drawXAxis(java.awt.Graphics2D g2d);
    public abstract void drawYAxis(java.awt.Graphics2D g2d);

    
    public void setXTitle(String xTitle) {
        this.xTitle = xTitle;
    }

    
    public void setYTitle(String yTitle) {
        this.yTitle = yTitle;
    }

    
    public void setXUnit(String xUnit) {
        this.xUnit = xUnit;
    }

    
    public void setYUnit(String yUnit) {
        this.yUnit = yUnit;
    }
    
    
    void setPanel(ChartPanel panel) {
        this.panel = panel;
        chartGeometry = panel.getChartGeometry();
    }
    
    
    protected String xTitle = null;
    protected String yTitle = null;
    
    protected String xUnit = null;
    protected String yUnit = null;

    
    // Private attributes must be initialized by a Panel.
    private ChartPanel panel; 
    private ChartGeometry chartGeometry;
    
}
