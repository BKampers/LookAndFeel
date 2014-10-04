/*
** Copyright © Bart Kampers
*/

package bka.plaf;


import java.awt.*;
import javax.swing.*;


public class ComboBoxUI extends javax.swing.plaf.metal.MetalComboBoxUI {

    public static javax.swing.plaf.ComponentUI createUI(JComponent c) {
        return new ComboBoxUI((JComboBox) c);
    }


    ComboBoxUI(JComboBox comboBox) {
        this.comboBox = comboBox;
    }


    public void setInTable(boolean inTable) {
        this.inTable = inTable;
    }


    public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
        if (! inTable) {
            super.paintCurrentValue(g, bounds, hasFocus);
        }
        else {
            ListCellRenderer renderer = comboBox.getRenderer();
            Component c;
            if (hasFocus && ! isPopupVisible(comboBox)) {
                c = renderer.getListCellRendererComponent(listBox, comboBox.getSelectedItem(), -1, true, false);
            }
            else {
                c = renderer.getListCellRendererComponent(listBox, comboBox.getSelectedItem(), -1, false, false);
            }
            c.setFont(comboBox.getFont());
            c.setForeground(comboBox.getForeground());
            c.setBackground(comboBox.getBackground());
            currentValuePane.paintComponent(g, c, comboBox, bounds.x, bounds.y, bounds.width, bounds.height, false);
        }
    }


    public void setPopupVisible(JComboBox c, boolean v) {
        Object lockProperty = c.getClientProperty("lockPopup");
        boolean lock = (lockProperty instanceof Boolean) ? (Boolean) lockProperty : false;
        super.setPopupVisible(c, v || lock);
    }


    protected javax.swing.JButton createArrowButton() {
        javax.swing.JButton button = super.createArrowButton();
        button.setRolloverEnabled(true);
        bka.swing.RoundedBorder border = new bka.swing.RoundedBorder(1, 2);
        border.setNormalColor(javax.swing.UIManager.getColor("Button.normalBorderColor"));
        border.setHoverColor(javax.swing.UIManager.getColor("Button.hoverBorderColor"));
        button.setBorder(border);
        return button;
    }

    private boolean inTable = false;

}
