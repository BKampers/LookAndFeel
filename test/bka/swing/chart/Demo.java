/*
** Copyright © Bart Kampers
*/
package bka.swing.chart;

import bka.awt.*;
import bka.swing.chart.custom.*;
import bka.swing.chart.grid.*;
import bka.swing.chart.render.*;
import java.awt.*;
import java.util.*;
import java.util.logging.*;


public class Demo extends javax.swing.JFrame {


    public Demo() {
        populateDataSets();
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
            java.util.logging.Logger.getLogger(Demo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        Demo demo = new Demo();
        java.awt.EventQueue.invokeLater(() -> {
            demo.setVisible(true);
        });
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Chart Demo");
        setPreferredSize(new java.awt.Dimension(800, 600));

        displayPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        displayPanel.setMaximumSize(new java.awt.Dimension(500, 500));
        displayPanel.setPreferredSize(new java.awt.Dimension(500, 500));
        displayPanel.setSize(new java.awt.Dimension(500, 500));
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

        javax.swing.GroupLayout controlPanelLayout = new javax.swing.GroupLayout(controlPanel);
        controlPanel.setLayout(controlPanelLayout);
        controlPanelLayout.setHorizontalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 410, Short.MAX_VALUE)
            .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, controlPanelLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(styleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        controlPanelLayout.setVerticalGroup(
            controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(controlPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(controlPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(styleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 18, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        getContentPane().add(controlPanel, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void populateComboBox() {
        styleComboBox.addItem(GraphStyle.PIE);
        styleComboBox.addItem(GraphStyle.LINE);
        styleComboBox.addItem(GraphStyle.DOT);
        styleComboBox.addItem(GraphStyle.POLYGON);
        styleComboBox.addItem(GraphStyle.BAR);
        styleComboBox.addItem(GraphStyle.SCATTER);
    }


    private void initChartPanel() {
        chartPanel.setHighlightFormat("G1", "x = %d", "y = %.2f");
        chartPanel.setHighlightFormat("G2", "x = %d", "y = %d");
        chartPanel.setHighlightFormat("G3", "x = %d", "y = %d");
        chartPanel.setHighlightFormat("G4", "x = %d", "y = %f");
        chartPanel.setHighlightFormat("G5", "yyyy-MM-dd", "y = %d");
        chartPanel.setShowLegend(true);
    }


    private void styleComboBox_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_styleComboBox_actionPerformed
        Object selectedItem = styleComboBox.getSelectedItem();
        if (GraphStyle.PIE.equals(selectedItem)) {
            Map graphs = new HashMap<>();
            graphs.put("G4", g4);
            configurePieChart(graphs);
        }
        else {
            chartPanel.setAxisRenderer(new DefaultAxisRenderer());
            chartPanel.setClickZoomMode(ChartPanel.ClickZoomMode.DOUBLE_CLICK_GRID_AREA);
            chartPanel.setDragZoomMode(ChartPanel.DragZoomMode.XY);
            if (GraphStyle.BAR.equals(selectedItem)) {
                Map<Object, Map<Number, Number>> graphs = new HashMap<>();
                graphs.put("G1", g1);
                graphs.put("G2", g2);
                configureBarChart(graphs);
            }
            else if (GraphStyle.DOT.equals(selectedItem)) {
                Map graphs = new HashMap<>();
                graphs.put("G5", g5);
                configureDotChart(graphs);
            }
            else if (GraphStyle.SCATTER.equals(selectedItem)) {
                configureScatterPlotChart();
            }
            else if (GraphStyle.LINE.equals(selectedItem)) {
                Map graphs = new HashMap<>();
                graphs.put("G1", g1);
                graphs.put("G2", g2);
                configureLineChart(graphs);
            }
            else {
                Map graphs = new HashMap<>();
                graphs.put("G1", g1);
                configureDefaultChart(graphs);
            }
        }
        chartPanel.revalidate();
    }//GEN-LAST:event_styleComboBox_actionPerformed


    private void configureDefaultChart(Map graphs) {
        chartPanel.setGraphs(graphs);
        chartPanel.setRenderer("G1", createPolygonDotRenderer());
        chartPanel.setWindow(null, null, null, null);
        chartPanel.setAxisPositions(ChartPanel.AxisPosition.MINIMUM, ChartPanel.AxisPosition.MINIMUM);
        chartPanel.setXGrid(new Grid());
        chartPanel.setGridRenderer(new DefaultGridRenderer(DEFAULT_GRID_STYLE), ChartPanel.GridMode.X);
    }


    private void configureLineChart(Map graphs) {
        chartPanel.setGraphs(graphs);
        chartPanel.setRenderer("G1", new DefaultLineRenderer(createLineDrawStyle(Color.GREEN), LINE_MARKER_SIZE));
        chartPanel.setRenderer("G2", new DefaultLineRenderer(createLineDrawStyle(Color.RED), LINE_MARKER_SIZE));
        chartPanel.setWindow(null, null, null, null);
        chartPanel.setAxisPositions(ChartPanel.AxisPosition.MINIMUM, ChartPanel.AxisPosition.MINIMUM);
        chartPanel.setXGrid(new Grid());
        chartPanel.setGridRenderer(new DefaultGridRenderer(WHITE_GRADIENT_GRID_STYLE), ChartPanel.GridMode.X);
    }


    private void configureScatterPlotChart() {
        Number xWindowMinimum = null;
        Number xWindowMaximum = null;
        Number yWindowMaximum = null;
        for (ChartDataElement<Number, Number> data : s1) {
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
        chartPanel.setChart("S1", s1);
        chartPanel.setRenderer("S1", new ScatterRenderer<>(createPointDrawStyle()));
        chartPanel.setWindow(xWindowMinimum, xWindowMaximum, 0, yWindowMaximum);
        chartPanel.setAxisPositions(ChartPanel.AxisPosition.ORIGIN, ChartPanel.AxisPosition.ORIGIN);
        chartPanel.setXGrid(new Grid());
        chartPanel.setYGrid(new Grid());
        chartPanel.setGridRenderer(new DefaultGridRenderer(GRAY_GRID_STYLE), ChartPanel.GridMode.X);
    }


    private void configureDotChart(Map graphs) {
        chartPanel.setGraphs(graphs);
        chartPanel.setRenderer("G5", createRectangleDotRenderer());
        chartPanel.setWindow(null, null, null, null);
        chartPanel.setAxisPositions(ChartPanel.AxisPosition.MINIMUM, ChartPanel.AxisPosition.MINIMUM);
        chartPanel.setXGrid(new TimestampGrid());
        chartPanel.setYGrid(new IntegerGrid());
        chartPanel.setGridRenderer(new DefaultGridRenderer(ZEBRA_GRID_STYLE), ChartPanel.GridMode.X);
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
        chartPanel.setGraphs(graphs);
        chartPanel.setRenderer("G1", b1);
        chartPanel.setRenderer("G2", b2);
        chartPanel.setWindow(0, xMax, 0, null);
        chartPanel.setXGrid(new IntegerGrid());
        chartPanel.setGridRenderer(new DefaultGridRenderer(WHITE_GRADIENT_GRID_STYLE), ChartPanel.GridMode.Y);
        chartPanel.setAxisPositions(ChartPanel.AxisPosition.ORIGIN, ChartPanel.AxisPosition.ORIGIN);
    }


    private void configurePieChart(Map graphs) {
        chartPanel.setXWindowMinimum(null);
        chartPanel.setXWindowMaximum(null);
        chartPanel.setYWindowMinimum(null);
        chartPanel.setGraphs(graphs);
        chartPanel.setAxisRenderer(null);
        chartPanel.setAxisPositions(null, null);
        chartPanel.setGridRenderer(null, ChartPanel.GridMode.NONE);
        chartPanel.setClickZoomMode(ChartPanel.ClickZoomMode.NONE);
        chartPanel.setDragZoomMode(ChartPanel.DragZoomMode.NONE);
        chartPanel.setRenderer("G4", new DefaultPieSectorRenderer(createPieDrawStyle()));
    }

    
    private void populateDataSets() {
        for (int x = 1; x <= 20; ++x) {
            g1.put(x, 1.0 / x * 25.0);
            g2.put(x, x);
        }
        for (int x = 1; x <= 1000000; ++x) {
            g3.put(x, x);
        }
        for (int x = 1950; x <= 2010; x += 10) {
            Random random = new Random();
            g4.put(x, random.nextFloat());
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.JANUARY, 1);
        for (int x = 0; x < 24; ++x) {
            g5.put(calendar.getTimeInMillis(), calendar.get(Calendar.MONTH));
            calendar.add(Calendar.MONTH, 1);
        }
        s1.add(1.5, 1.75);
        s1.add(1.5, 1.75);
        s1.add(2, 1);
        s1.add(2, 1);
        s1.add(2, 1);
        s1.add(2, 0.75);
        s1.add(2, 0.75);
        s1.add(2, 0.75);
        s1.add(-1.5, 1.5);
        s1.add(-1.5, 1.5);
        s1.add(-1.5, 1.5);
        s1.add(-1.5, 1.5);
        s1.add(-1.5, 1.5);
        s1.add(-1.5, 1.5);
        s1.add(4.3, 1.5);
        s1.add(4.3, 1.5);
        s1.add(4.3, 1.5);
        s1.add(4.3, 1.5);
        s1.add(1, 2.5);
        s1.add(1, 2.5);
        s1.add(1, 2.5);
        s1.add(1, 2.5);
        s1.add(1, 2.5);
        s1.add(1, 2.5);
        s1.add(1, 2.5);
        s1.add(1, 2.5);
    }


    private final ChartPanel chartPanel = new ChartPanel();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JComboBox styleComboBox;
    // End of variables declaration//GEN-END:variables


    private static PieDrawStyle createPieDrawStyle() {
        return PieDrawStyle.create(Palette.generateColors(PIE_COLOR_COUNT));
    }


    private static LineDrawStyle createLineDrawStyle(Color color) {
        LineDrawStyle lineDrawStyle = LineDrawStyle.create(Color.MAGENTA, PointDrawStyle.createRadial(new Color[] { color, color.darker() }));
        Color areaColor = new Color(0x80FFFF00, true);
        lineDrawStyle.setTopAreaPaint(areaColor);
        lineDrawStyle.setBottomAreaPaint(areaColor.darker());
        return lineDrawStyle;
    }


    private static RectangleDotRenderer createRectangleDotRenderer() {
        return new RectangleDotRenderer(RECTANGE_DOT_SIZE, DefaultDrawStyle.createSolid(Color.BLACK));
    }


    private static PolygonDotRenderer createPolygonDotRenderer() {
        return new PolygonDotRenderer(PolygonFactory.createStar(4, 5, 15), createPointDrawStyle());
    }


    private static PointDrawStyle createPointDrawStyle() {
        PointDrawStyle pointDrawStyle = PointDrawStyle.createLinear(new Color[] { Color.RED, Color.WHITE, Color.BLUE });
        pointDrawStyle.setBorder(Color.GREEN.darker());
        return pointDrawStyle;
    }


    private final Map<Number, Number> g1 = new HashMap<>();
    private final Map<Number, Number> g2 = new HashMap<>();
    private final Map<Number, Number> g3 = new HashMap<>();
    private final Map<Number, Number> g4 = new TreeMap<>();
    private final Map<Number, Number> g5 = new TreeMap<>();
    private final ChartData<Number, Number> s1 = new ChartData<>();


    private enum GraphStyle { PIE, LINE, DOT, POLYGON, BAR, SCATTER };


    private static final GridStyle DEFAULT_GRID_STYLE = GridStyle.createGradient(
        new BasicStroke(0.5f),
        Color.GRAY,
        new Color[] { new Color(0x00e68a), new Color(0x009973) },
        new Color[] { new Color(0xccffcc), new Color(0xccffcc) });
    
    private static final GridStyle GRAY_GRID_STYLE = GridStyle.create(
        new BasicStroke(2.0f), 
        Color.LIGHT_GRAY, 
        new Color[] { Color.WHITE });

    private static final GridStyle ZEBRA_GRID_STYLE = GridStyle.create(
        new Color[] { Color.WHITE, Color.LIGHT_GRAY });
    
    private static final GridStyle WHITE_GRADIENT_GRID_STYLE = GridStyle.createGradient(
        new Color[] { Color.WHITE });

    private static final int PIE_COLOR_COUNT = 7;
    private static final int LINE_MARKER_SIZE = 9;
    private static final int RECTANGE_DOT_SIZE = 2;

}
