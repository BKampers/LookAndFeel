/*
** Copyright © Bart Kampers
*/

package bka.plaf;


import bka.swing.RoundedBorder;
import java.awt.*;
import javax.swing.*;


public class ButtonUI extends javax.swing.plaf.metal.MetalButtonUI {


    public ButtonUI() {
    }


    public static javax.swing.plaf.ComponentUI createUI(javax.swing.JComponent c) {
        return buttonUI;
    }


    public void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        Class buttonClass = b.getClass();
        if (buttonClass == JButton.class) {
//            Dimension size = b.getSize();
//            if (size.width == 0) {
//                size.width = 40;
//            }
//            if (size.height == 0) {
//                size.height = 25;
//            }
//            b.setSize(size);
            RoundedBorder border = new RoundedBorder(1, 2);
            border.setNormalColor(UIManager.getColor("Button.normalBorderColor"));
            border.setHoverColor(UIManager.getColor("Button.hoverBorderColor"));
            b.setBorder(border);
            b.setRolloverEnabled(true);
        }
    }


    public void update(Graphics g, JComponent c) {
	if (c.isOpaque()) {
            paintGradient(g, c, "Button.normalGradient");
	}
	paint(g, c);
    }


    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        if (b.isContentAreaFilled()) {
            paintGradient(g, b, "Button.pressedGradient");
        }
    }


    private void paintGradient(Graphics g, JComponent c, String key) {
        Color background1 = UIManager.getColor("Button.background");
        Color background2 = UIManager.getColor(key);
        Dimension size = c.getSize();
        float halfWidth = size.width * 0.5f;
        ((Graphics2D) g).setPaint(new GradientPaint(halfWidth, 0, background1, halfWidth, size.height, background2));
        g.fillRect(0, 0, size.width, size.height);
    }


    private static ButtonUI buttonUI = new ButtonUI();

}
