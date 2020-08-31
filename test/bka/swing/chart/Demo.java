/*
** Copyright © Bart Kampers
*/
package bka.swing.chart;

import bka.awt.*;
import bka.awt.chart.*;
import bka.awt.chart.custom.*;
import bka.awt.chart.grid.*;
import bka.awt.chart.render.*;
import java.awt.*;
import java.awt.print.*;
import java.util.*;
import java.util.logging.*;


public class Demo extends javax.swing.JFrame {


    public Demo() {
        populateDataSets();
        chartRenderer = new ChartRenderer();
        chartRenderer.setMargins(100, 125, 50, 50);
        chartPanel = new ChartPanel(chartRenderer);
        initComponents();
        initChartPanel();
        displayPanel.add(chartPanel);
        populateComboBox();
        styleComboBox.setSelectedIndex(3);
    }


    public static void main(String args[]) {
        for (Handler handler : Logger.getLogger("").getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setLevel(Level.ALL);
            }
        }
        Logger.getLogger("bka.swing.chart").setLevel(Level.INFO);

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            getLogger().log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        Demo demo = new Demo();
        java.awt.EventQueue.invokeLater(() -> {
            demo.setVisible(true);
            demo.startTimer();
        });
    }


    private void startTimer() {
        timer = new Timer("Graph data miner");
        timer.schedule(new DataMiner(), 1000, 1000);
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        displayPanel = new javax.swing.JPanel();
        controlPanel = new javax.swing.JPanel();
        styleComboBox = new javax.swing.JComboBox();
        printButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chart Demo");
        setPreferredSize(new java.awt.Dimension(1000, 600));

        displayPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        displayPanel.setMaximumSize(new java.awt.Dimension(1500, 500));
        displayPanel.setPreferredSize(new java.awt.Dimension(1500, 500));
        displayPanel.setSize(new java.awt.Dimension(1500, 500));
        displayPanel.setLayout(new javax.swing.BoxLayout(displayPanel, javax.swing.BoxLayout.LINE_AXIS));
        getContentPane().add(displayPanel, java.awt.BorderLayout.CENTER);

        controlPanel.setPreferredSize(new java.awt.Dimension(400, 30));

        styleComboBox.setMinimumSize(new java.awt.Dimension(50, 30));
        styleComboBox.setPreferredSize(new java.awt.Dimension(50, 30));
        styleComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                styleComboBox_actionPerformed(evt);
            }
        });

        printButton.setText("Print");
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButton_actionPerformed(evt);
            }
        });

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                .addContainerGap(589, Short.MAX_VALUE)
                .addComponent(printButton)
                .addContainerGap())
            .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                    .addContainerGap(136, Short.MAX_VALUE)
                    .addComponent(styleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(136, Short.MAX_VALUE)))
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlPanelLayout.createSequentialGroup()
                .addComponent(printButton)
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(controlPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(styleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 23, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        getContentPane().add(controlPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void populateComboBox() {
        for (GraphStyle style : GraphStyle.values()) {
            styleComboBox.addItem(style);
        }
    }


    private void initChartPanel() {
        chartRenderer.setHighlightFormat(SINE, "x = %.3f", "y = %.3f");
        chartRenderer.setHighlightFormat(CURVE, "x = %d", "y = %.2f");
        chartRenderer.setHighlightFormat(LINE, "x = %d", "y = %d");
        chartRenderer.setHighlightFormat(YEARS, "year = %d", "value = %f");
        chartRenderer.setHighlightFormat(MONTHS, "yyyy-MM-dd", "y = %d");
        chartRenderer.setLegendRenderer(new DefaultLegendRenderer(900, 80));
    }


    private void styleComboBox_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_styleComboBox_actionPerformed
        Object selectedItem = styleComboBox.getSelectedItem();
        if (GraphStyle.PIE.equals(selectedItem)) {
            Map<Object, Map<Number, Number>> graphs = new HashMap<>();
            graphs.put(YEARS, years);
            configurePieChart(graphs);
        }
        else {
            chartPanel.setClickZoomMode(ChartPanel.ClickZoomMode.DOUBLE_CLICK_GRID_AREA);
            chartPanel.setDragZoomMode(ChartPanel.DragZoomMode.XY);
            if (GraphStyle.BAR.equals(selectedItem)) {
                Map<Object, Map<Number, Number>> graphs = new LinkedHashMap<>();
                graphs.put(CURVE, curve);
                graphs.put(LINE, line);
                configureBarChart(graphs);
            }
            else if (GraphStyle.DOT.equals(selectedItem)) {
                Map<Object, Map<Number, Number>> graphs = new LinkedHashMap<>();
                graphs.put(MONTHS, months);
                configureDotChart(graphs);
            }
            else if (GraphStyle.SCATTER.equals(selectedItem)) {
                configureScatterPlotChart();
            }
            else if (GraphStyle.LINE.equals(selectedItem)) {
                Map<Object, Map<Number, Number>> graphs = new LinkedHashMap<>();
                graphs.put(CURVE, curve);
                graphs.put(LINE, line);
                graphs.put(SINE, sine);
                configureLineChart(graphs);
            }
            else {
                Map<Object, Map<Number, Number>> graphs = new LinkedHashMap<>();
                graphs.put(CURVE, curve);
                configureDefaultChart(graphs);
            }
        }
        chartPanel.repaint();
    }//GEN-LAST:event_styleComboBox_actionPerformed


    private void printButton_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButton_actionPerformed
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(chartRenderer);
        if (job.printDialog()) {
            try {
                job.print();
            }
            catch (PrinterException ex) {
                getLogger().log(Level.SEVERE, "printButton", ex);
            }
            finally {
                chartPanel.invalidate();
            }
        }
    }//GEN-LAST:event_printButton_actionPerformed


    private void configureDefaultChart(Map<Object, Map<Number, Number>> graphs) {
        chartRenderer.setGraphs(graphs);
        chartRenderer.setRenderer(CURVE, createPolygonDotRenderer());
        chartRenderer.setWindow(null, null, null, null);
        setAxisRenderer(ChartRenderer.AxisPosition.MINIMUM);
        chartRenderer.setXGrid(new NumberGrid());
        chartRenderer.setXGrid(new NumberGrid());
        chartRenderer.setYGrid(new NumberGrid());
        chartRenderer.setGridRenderer(new DefaultGridRenderer(DEFAULT_GRID_STYLE), ChartRenderer.GridMode.X);
    }


    private void configureLineChart(Map<Object, Map<Number, Number>> graphs) {
        final Color areaColor = new Color(0x80FFFF00, true);
        chartRenderer.setGraphs(graphs);
        PolygonDotRenderer markerRenderer = new PolygonDotRenderer(PolygonFactory.createStar(5, 3, 9), DefaultDrawStyle.createSolid(Color.BLACK));
        chartRenderer.setRenderer(CURVE, new DefaultLineRenderer(createLineDrawStyle(Color.BLUE, areaColor), markerRenderer));
        chartRenderer.setRenderer(LINE, new DefaultLineRenderer(createLineDrawStyle(Color.RED, areaColor), new OvalDotRenderer(9, null)));
        chartRenderer.setRenderer(SINE, new DefaultLineRenderer(createLineDrawStyle(null, areaColor)));
        chartRenderer.setWindow(null, null, 0, 25);
        chartRenderer.setYWindow(LINE, null, null);
        setAxisRenderer(ChartRenderer.AxisPosition.MINIMUM);
        chartRenderer.setXGrid(new NumberGrid());
        chartRenderer.setXGrid(new NumberGrid());
        chartRenderer.setYGrid(new NumberGrid());
        chartRenderer.setGridRenderer(new DefaultGridRenderer(GRAY_GRID_STYLE), ChartRenderer.GridMode.X);
    }


    private void configureScatterPlotChart() {
        Number xWindowMinimum = null;
        Number xWindowMaximum = null;
        Number yWindowMaximum = null;
        for (ChartDataElement<Number, Number> data : scatter) {
            if (xWindowMinimum == null || data.getKey().doubleValue() < xWindowMinimum.doubleValue()) {
                xWindowMinimum = data.getKey();
            }
            if (xWindowMaximum == null || data.getKey().doubleValue() > xWindowMaximum.doubleValue()) {
                xWindowMaximum = data.getKey();
            }
            if (yWindowMaximum == null || data.getValue().doubleValue() > yWindowMaximum.doubleValue()) {
                yWindowMaximum = data.getValue();
            }
        }
        if (xWindowMinimum != null) {
            xWindowMinimum = Math.floor(xWindowMinimum.doubleValue() - 0.001);
        }
        if (xWindowMaximum != null) {
            xWindowMaximum = Math.ceil(xWindowMaximum.doubleValue() + 0.001);
        }
        if (yWindowMaximum != null) {
            yWindowMaximum = Math.ceil(yWindowMaximum.doubleValue() + 0.001);
        }
        chartRenderer.setChart(SCATTER, scatter);
        chartRenderer.setRenderer(SCATTER, new ScatterRenderer<>(createPointDrawStyle(), 3.0f));
        chartRenderer.setWindow(xWindowMinimum, xWindowMaximum, 0, yWindowMaximum);
        setAxisRenderer(ChartRenderer.AxisPosition.ORIGIN);
        chartRenderer.setXGrid(new NumberGrid());
        chartRenderer.setYGrid(new NumberGrid());
        chartRenderer.setGridRenderer(new DefaultGridRenderer(GRAY_GRID_STYLE), ChartRenderer.GridMode.X);
    }


    private void configureDotChart(Map<Object, Map<Number, Number>> graphs) {
        chartRenderer.setGraphs(graphs);
        chartRenderer.setRenderer(MONTHS, createDotRenderer());
        chartRenderer.setWindow(null, null, null, null);
        setAxisRenderer(ChartRenderer.AxisPosition.MINIMUM);
        chartRenderer.setXGrid(new TimestampGrid());
        chartRenderer.setYGrid(new IntegerGrid());
        chartRenderer.setGridRenderer(new DefaultGridRenderer(ZEBRA_GRID_STYLE), ChartRenderer.GridMode.X);
    }


    private void configureBarChart(Map<Object, Map<Number, Number>> graphs) {
        BarDrawStyle blueBarStyle = BarDrawStyle.create(Color.BLUE, Color.CYAN);
        blueBarStyle.setBorder(Color.BLUE.darker());
        AreaDrawStyle redArea2Style = DefaultDrawStyle.createBorder(Color.RED, new BasicStroke(2.0f));
        BarRenderer b1 = new BarRenderer(10, blueBarStyle);
        b1.setShift(-5);
        BarRenderer b2 = new BarRenderer(10, redArea2Style);
        b2.setShift(5);
        Number xMax = null;
        for (Map<Number, Number> dataSet : graphs.values()) {
            for (Number x : dataSet.keySet()) {
                if (xMax == null || x.doubleValue() > xMax.doubleValue()) {
                    xMax = x.doubleValue();
                }
            }
        }
        if (xMax != null) {
            xMax = xMax.intValue() + 1;
        }
        chartRenderer.setGraphs(graphs);
        chartRenderer.setRenderer(CURVE, b1);
        chartRenderer.setRenderer(LINE, b2);
        chartRenderer.setWindow(0, xMax, 0, null);
        chartRenderer.setXGrid(new IntegerGrid());
        chartRenderer.setGridRenderer(new DefaultGridRenderer(WHITE_GRADIENT_GRID_STYLE), ChartRenderer.GridMode.Y);
        setAxisRenderer(ChartRenderer.AxisPosition.ORIGIN);
    }


    private void configurePieChart(Map<Object, Map<Number, Number>> graphs) {
        chartRenderer.setXWindowMinimum(null);
        chartRenderer.setXWindowMaximum(null);
        chartRenderer.setGraphs(graphs);
        chartRenderer.setXAxisRenderer();
        chartRenderer.setYAxisRenderer();
        chartRenderer.setGridRenderer(null, ChartRenderer.GridMode.NONE);
        chartPanel.setClickZoomMode(ChartPanel.ClickZoomMode.NONE);
        chartPanel.setDragZoomMode(ChartPanel.DragZoomMode.NONE);
        chartRenderer.setRenderer(YEARS, new DefaultPieSectorRenderer(createPieDrawStyle()));
    }


    private void setAxisRenderer(ChartRenderer.AxisPosition position) {
        chartRenderer.setXAxisRenderer(new DefaultAxisRenderer(position, Color.GRAY));
        chartRenderer.setYAxisRenderer(new DefaultAxisRenderer(position, Color.GRAY));
    }

    
    private void populateDataSets() {
        for (double x = 0.0; x <= 20.0; x += Math.PI / 32.0) {
            sine.put(x, 12.5 + Math.sin(x) * 10.0);
        }
        for (int x = 1; x <= 20; ++x) {
            curve.put(x, 25.0 / x);
            line.put(x, x);
        }
        for (int x = 1950; x <= 2010; x += 10) {
            Random random = new Random();
            years.put(x, random.nextFloat());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.JANUARY, 1);
        for (int x = 0; x < 24; ++x) {
            months.put(calendar.getTimeInMillis(), calendar.get(Calendar.MONTH));
            calendar.add(Calendar.MONTH, 1);
        }
        scatter.add(1.5, 1.75);
        scatter.add(1.5, 1.75);
        scatter.add(2, 1);
        scatter.add(2, 1);
        scatter.add(2, 1);
        scatter.add(2, 0.75);
        scatter.add(2, 0.75);
        scatter.add(2, 0.75);
        scatter.add(-1.5, 1.5);
        scatter.add(-1.5, 1.5);
        scatter.add(-1.5, 1.5);
        scatter.add(-1.5, 1.5);
        scatter.add(-1.5, 1.5);
        scatter.add(-1.5, 1.5);
        scatter.add(4.3, 1.5);
        scatter.add(4.3, 1.5);
        scatter.add(4.3, 1.5);
        scatter.add(4.3, 1.5);
        scatter.add(1, 2.5);
        scatter.add(1, 2.5);
        scatter.add(1, 2.5);
        scatter.add(1, 2.5);
        scatter.add(1, 2.5);
        scatter.add(1, 2.5);
        scatter.add(1, 2.5);
        scatter.add(1, 2.5);
    }


    private class DataMiner extends TimerTask {

        @Override
        public void run() {
            if (styleComboBox.getSelectedItem() == GraphStyle.DOT) {
                Object last = new LinkedList(months.keySet()).getLast();
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis((Long) last);
                calendar.add(Calendar.MONTH, 1);
                months.put(calendar.getTimeInMillis(), calendar.get(Calendar.MONTH));
                Map<Object, Map<Number, Number>> graphs = new LinkedHashMap<>();
                graphs.put(MONTHS, months);
                chartRenderer.setGraphs(graphs);
                chartPanel.repaint();
            }
        }

    }


    private final ChartRenderer chartRenderer;
    private final ChartPanel chartPanel;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JButton printButton;
    private javax.swing.JComboBox styleComboBox;
    // End of variables declaration//GEN-END:variables


    private static PieDrawStyle createPieDrawStyle() {
        return PieDrawStyle.create(Palette.generateColors(PIE_COLOR_COUNT));
    }


    private static LineDrawStyle createLineDrawStyle(Color color, Color areaColor) {
        PointDrawStyle pointDrawStyle = (color == null) ? null : PointDrawStyle.createRadial(new Color[] { Color.WHITE, color.darker() });
        LineDrawStyle lineDrawStyle = LineDrawStyle.create(Color.MAGENTA, pointDrawStyle);
        if (areaColor != null) {
            lineDrawStyle.setTopAreaPaint(areaColor);
            lineDrawStyle.setBottomAreaPaint(areaColor.darker());
        }
        return lineDrawStyle;
    }


    private static PointRenderer createDotRenderer() {
        return new OvalDotRenderer(DOT_SIZE, DefaultDrawStyle.createSolid(Color.BLACK));
    }


    private static LineDrawStyle createMarkerDrawStyle(PointDrawStyle pointDrawStyle, Color areaColor) {
        AreaDrawStyle markerDrawStyle = pointDrawStyle;
        LineDrawStyle lineDrawStyle = LineDrawStyle.create(null, markerDrawStyle);
        if (areaColor != null) {
            lineDrawStyle.setTopAreaPaint(areaColor);
            lineDrawStyle.setBottomAreaPaint(areaColor.darker());
        }
        return lineDrawStyle;
    }


    private static PolygonDotRenderer createPolygonDotRenderer() {
        return new PolygonDotRenderer(PolygonFactory.createStar(4, 5, 15), createPointDrawStyle());
    }


    private static PointDrawStyle createPointDrawStyle() {
        PointDrawStyle pointDrawStyle = PointDrawStyle.createLinear(new Color[] { Color.RED, Color.WHITE, Color.BLUE });
        pointDrawStyle.setBorder(Color.GREEN.darker());
        return pointDrawStyle;
    }


    private static Logger getLogger() {
        return Logger.getLogger(Demo.class.getName());
    }


    private final Map<Number, Number> sine = new TreeMap<>();
    private final Map<Number, Number> curve = new TreeMap<>();
    private final Map<Number, Number> line = new TreeMap<>();
    private final Map<Number, Number> years = new TreeMap<>();
    private final Map<Number, Number> months = new TreeMap<>();
    private final ChartData<Number, Number> scatter = new ChartData<>();

    private Timer timer;


    private enum GraphStyle { PIE, LINE, DOT, POLYGON, BAR, SCATTER };


    private static final GridStyle DEFAULT_GRID_STYLE = GridStyle.create(
        new BasicStroke(0.5f),
        Color.GRAY,
        GridStyle.createGradientPaintBox(
            new Color[] { new Color(0x00e68a), new Color(0x009973) },
            new Color[] { new Color(0xccffcc), new Color(0xccffcc) }
        ));

    
    private static final GridStyle GRAY_GRID_STYLE = GridStyle.create(
        new BasicStroke(2.0f), 
        Color.LIGHT_GRAY, 
        GridStyle.createSolidPaintBox(new Color[] { Color.WHITE }));

    private static final GridStyle ZEBRA_GRID_STYLE = GridStyle.createSolid(
        new Color[] { Color.WHITE, Color.LIGHT_GRAY });
    
    private static final GridStyle WHITE_GRADIENT_GRID_STYLE = GridStyle.createGradient(
        new Color[] { Color.WHITE });


    private static final String LINE = "y = x";
    private static final String CURVE = "y = 25 \u00f7 x";
    private static final String SINE = "y = sin(x)";
    private static final String SCATTER = "S1";
    private static final String YEARS = "Random per year";
    private static final String MONTHS = "Per month";

    private static final int PIE_COLOR_COUNT = 7;
    private static final int DOT_SIZE = 4;

}
