package bka.plaf;

/*
** Copyright © Bart Kampers
*/

public class SplitPaneDeviderUI extends javax.swing.plaf.basic.BasicSplitPaneDivider {

    public SplitPaneDeviderUI(javax.swing.plaf.basic.BasicSplitPaneUI ui) {
        super(ui);
    }

    public void paint(java.awt.Graphics g) {
        int width = getSize().width - 3;
        int height = getSize().height - 3;
//        g.setColor(java.awt.Color.CYAN);
//        g.fillRoundRect(0, 0, width, height, 5, 5);
        g.setColor(java.awt.Color.LIGHT_GRAY);
        g.drawRoundRect(1, 1, width, height, 4, 4);
    }

}
