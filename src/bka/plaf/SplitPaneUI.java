package bka.plaf;

/*
** Copyright © Bart Kampers
*/

public class SplitPaneUI extends javax.swing.plaf.metal.MetalSplitPaneUI {


    public static javax.swing.plaf.ComponentUI createUI(javax.swing.JComponent c) {
        return new SplitPaneUI();
    }


    public javax.swing.plaf.basic.BasicSplitPaneDivider createDefaultDivider() {
        divider = new bka.plaf.SplitPaneDeviderUI(this);
        return divider;
    }

}
