/*
** Copyright © Bart Kampers
*/
package bka.swing.chart;

import java.awt.*;
import java.util.*;
import java.util.logging.*;


public class Demo extends javax.swing.JFrame {


    public Demo() {
        initComponents();
        displayPanel.add(chartPanel);
        styleComboBox.addItem(new DefaultPieSectorRenderer());
        styleComboBox.addItem(new DefaultLineRenderer());
        styleComboBox.addItem(new RectangleDotRenderer());
        styleComboBox.addItem(new DishDotRenderer(30, 10));
        styleComboBox.addItem(new BarRenderer());
        styleComboBox.setSelectedIndex(4);
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
        java.awt.EventQueue.invokeLater(() -> {
            new Demo().setVisible(true);
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


    private void styleComboBox_actionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_styleComboBox_actionPerformed
        Map<Number, Number> g1 = new HashMap<>();
        Map<Number, Number> g2 = new HashMap<>();
        Map<Number, Number> g3 = new HashMap<>();
        for (int x = 1; x <= 20; ++x) {
            g1.put(x, 1.0 / x * 25.0);
            g2.put(x, x);
        }
        for (int x = 1; x <= 1000000; ++x) {
            g3.put(x, x);
        }
        AbstractDataAreaRenderer pointRenderer = (AbstractDataAreaRenderer) styleComboBox.getSelectedItem();
        if (pointRenderer instanceof PieSectorRenderer) {
            ((PieSectorRenderer) pointRenderer).setPalette(new Palette(g1.size()));
            Map graphs = new HashMap<>();
            graphs.put("G1", g1);
            chartPanel.setGraphs(graphs);
            chartPanel.setAxisRenderer(null);
            chartPanel.setAxisPositions(null, null);
            chartPanel.setDemarcations(null, ChartPanel.DemarcationMode.NONE);
            chartPanel.setClickZoomMode(ChartPanel.ClickZoomMode.NONE);
            chartPanel.setDragZoomMode(ChartPanel.DragZoomMode.NONE);
            chartPanel.setRenderer("G1", pointRenderer);
        }
        else {
            chartPanel.setAxisRenderer(new DefaultAxisRenderer());
            chartPanel.setAxisPositions(ChartPanel.AxisPosition.MINIMUM, ChartPanel.AxisPosition.MINIMUM);
            chartPanel.setDemarcations(new DefaultDemarcationRenderer(), ChartPanel.DemarcationMode.X);
            chartPanel.setClickZoomMode(ChartPanel.ClickZoomMode.DOUBLE_CLICK_DEMARCATION);
            chartPanel.setDragZoomMode(ChartPanel.DragZoomMode.XY);
            BarRenderer b1 = new BarRenderer(Color.BLUE);
            b1.setShift(-5);
            BarRenderer b2 = new BarRenderer(Color.RED);
            b2.setShift(5);
            if (pointRenderer instanceof BarRenderer) {
                Map graphs = new HashMap<>();
                graphs.put("G1", g1);
                graphs.put("G2", g2);
                chartPanel.setGraphs(graphs);
                chartPanel.setRenderer("G1", b1);
                chartPanel.setRenderer("G2", b2);
                chartPanel.setXWindowMinimum(0);
                chartPanel.setXWindowMaximum(21);
                chartPanel.setYWindowMinimum(0);
                chartPanel.setXDemarcations(new IntegerDemarcations());
//                chartPanel.setAxisPositions(ChartPanel.AxisPosition.ORIGIN, ChartPanel.AxisPosition.ORIGIN);
            }
            else if (pointRenderer instanceof RectangleDotRenderer) {
                Map graphs = new HashMap<>();
                graphs.put("G3", g3);
                chartPanel.setGraphs(graphs);
                chartPanel.setRenderer("G3", pointRenderer);
                chartPanel.setXWindowMinimum(null);
                chartPanel.setXWindowMaximum(null);
                chartPanel.setYWindowMinimum(null);
            }
            else {
                Map graphs = new HashMap<>();
                graphs.put("G1", g1);
                chartPanel.setGraphs(graphs);
                chartPanel.setRenderer("G1", pointRenderer);
            }
        }
        chartPanel.setHighlightFormat("G1", "x = %d", "y = %.2f");
        chartPanel.setHighlightFormat("G2", "x = %d", "y = %d");
        chartPanel.setHighlightFormat("G3", "x = %d", "y = %f");
        chartPanel.revalidate();
    }//GEN-LAST:event_styleComboBox_actionPerformed


    private final ChartPanel chartPanel = new ChartPanel();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel controlPanel;
    private javax.swing.JPanel displayPanel;
    private javax.swing.JComboBox styleComboBox;
    // End of variables declaration//GEN-END:variables

}
