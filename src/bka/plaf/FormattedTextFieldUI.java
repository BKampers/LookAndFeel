/*
** Copyright © Bart Kampers
*/

package bka.plaf;

import java.awt.*;
import javax.swing.UIManager;


public class FormattedTextFieldUI extends javax.swing.plaf.basic.BasicFormattedTextFieldUI {
    
    
    public static javax.swing.plaf.ComponentUI createUI(javax.swing.JComponent c) {
        return new FormattedTextFieldUI();
    }
    
    
    protected void paintBackground(Graphics g) {
        super.paintBackground(g);
        javax.swing.text.JTextComponent component = getComponent();
        Object label = component.getClientProperty("label");
        if (label != null) {
            java.awt.Rectangle bounds = component.getBounds();
            g.setColor(UIManager.getColor("FormattedTextField.label"));
            String string = label.toString();
            FontMetrics metrics = g.getFontMetrics();
            int width = metrics.stringWidth(string);
            g.drawString(string, bounds.width - width - 3, metrics.getHeight() - 1);
        }
    }
    
    
}
