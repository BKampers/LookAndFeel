/*
** Copyright © Bart Kampers
*/


package bka.plaf;


import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;


public class ScrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
    
    
    public static ComponentUI createUI(JComponent c) {
        return new ScrollBarUI();
    }
    
    
    protected JButton createDecreaseButton(int orientation) {
        return new ArrowButton(orientation);
    }
    
    
    protected JButton createIncreaseButton(int orientation) {
        return new ArrowButton(orientation);
    }
    
    
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        g.setColor(trackColor);
        g.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);	
    }

	
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
	if (! thumbBounds.isEmpty() && scrollbar.isEnabled()) {
            int width = thumbBounds.width;
            int height = thumbBounds.height;		

            g.translate(thumbBounds.x, thumbBounds.y);

            g.setColor(thumbDarkShadowColor);
            g.drawRect(0, 0, width - 1, height - 1);    
            g.drawRect(1, 1, width - 3, height - 3);    
            g.setColor(thumbColor);
            g.fillRect(2, 2, width - 4, height - 4);

            g.translate(-thumbBounds.x, -thumbBounds.y);
        }
    }
    
    
    private class ArrowButton extends javax.swing.plaf.basic.BasicArrowButton {

        ArrowButton(int direction) {
            super(direction);
            switch (direction) {
                case SwingConstants.WEST  : image = (Image) UIManager.get("ScrollBar.leftImage");  break;
                case SwingConstants.EAST  : image = (Image) UIManager.get("ScrollBar.rightImage"); break;
                case SwingConstants.NORTH : image = (Image) UIManager.get("ScrollBar.upImage");    break;
                case SwingConstants.SOUTH : image = (Image) UIManager.get("ScrollBar.downImage");  break;
            }
        }
        
        public void paint(Graphics g) {
            if (image != null) {
                g.drawImage(image, 0, 0, null);
            }
        }
        
        private Image image = null;
  
    }
    
    
}
