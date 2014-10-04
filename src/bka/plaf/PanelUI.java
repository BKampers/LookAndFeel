package bka.plaf;

/*
** Copyright © Bart Kampers
*/


import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;


public class PanelUI extends javax.swing.plaf.basic.BasicPanelUI {


    public void paint(Graphics g, JComponent c) {
        if (c.isOpaque()) {
            Rectangle bounds = c.getBounds();
            GradientPaint gradientPaint = new GradientPaint(bounds.width / 2 , 0, backgroundColorTop, bounds.width / 2, bounds.height, backgroundColorBottom);
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setPaint(gradientPaint);
            graphics2D.fillRect(0 , 0 , bounds.width , bounds.height);
        }
    }


    public static ComponentUI createUI(JComponent c) {
        return staticPanelUI;
    }


    private static PanelUI staticPanelUI = new PanelUI();


    private static final Color backgroundColorTop = new Color(0x00EBEBEB);
    private static final Color backgroundColorBottom = new Color(0x00FFFFFF);

}
