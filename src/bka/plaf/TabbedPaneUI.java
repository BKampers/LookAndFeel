/*
** Copyright © Bart Kampers
*/

package bka.plaf;


import java.awt.*;
import javax.swing.*;


public class TabbedPaneUI extends javax.swing.plaf.metal.MetalTabbedPaneUI {


    public static javax.swing.plaf.ComponentUI createUI(javax.swing.JComponent c) {
        TabbedPaneUI ui = new TabbedPaneUI(c);
        c.addMouseMotionListener(ui.mouseAdapter);
        c.addMouseListener(ui.mouseAdapter);
//        if (addButtonImage != null) {
//            ui.addButtonRectangle = new Rectangle(0, 0, addButtonImage.getWidth(null), addButtonImage.getHeight(null));
//        }
        return ui;
    }


//    public Rectangle getAddButtonBounds() {
//        return new Rectangle(addButtonRectangle);
//    }


    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        Rectangle clipRect = g.getClipBounds();
        g.setColor(BACKGROUND_SELECTED);
        g.fillRect(clipRect.x, clipRect.height - 2, clipRect.width, 2);
        super.paintTabArea(g, tabPlacement, selectedIndex);
        paintDragHighlight(g);
//        if (addButtonEnabled) {
//            paintAddButton(g);
//        }
    }


    protected void paintTopTabBorder(int tabIndex, Graphics g, int x, int y, int w, int h, int btm, int rght, boolean isSelected) {
        if (isSelected) {
            g.setColor(BORDER_LIGHT);
            g.drawLine(rght, y+1, rght, btm);
            g.drawLine(x+1, y, rght-1, y);
            g.drawLine(x, y+1, x, btm);
        }
        else {
            g.setColor((tabIndex == getRolloverTab()) ? BORDER_HIGHLIGHT : BORDER_LIGHT);
            g.drawLine(rght, y+1, rght, btm);
            g.drawLine(x, y+1, x, btm);
            g.drawLine(x+1, y, rght-1, y);
            g.drawLine(x, btm, rght, btm);
        }
    }
//
//
//    protected void paintBottomTabBorder(int tabIndex, Graphics g, int x, int y, int w, int h, int btm, int rght, boolean isSelected ) {
//        super.paintBottomTabBorder(tabIndex, g, x, y, w, h, btm, rght, isSelected); // TBD
//    }
//
//
//    protected void paintLeftTabBorder(int tabIndex, Graphics g, int x, int y, int w, int h, int btm, int rght, boolean isSelected ) {
//        super.paintLeftTabBorder(tabIndex, g, x, y, w, h, btm, rght, isSelected); // TBD
//    }
//
//
//    protected void paintRightTabBorder(int tabIndex, Graphics g, int x, int y, int w, int h, int btm, int rght, boolean isSelected ) {
//        super.paintRightTabBorder(tabIndex, g, x, y, w, h, btm, rght, isSelected); // TBD
//    }


    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        g.setColor(isSelected ? BACKGROUND_SELECTED : BACKGROUND_NORMAL);
        switch(tabPlacement) {
          case LEFT:
              g.fillRect(x+1, y+1, w-1, h-3); // TBD
              break;
          case RIGHT:
              g.fillRect(x, y+1, w-2, h-3); // TBD
              break;
          case BOTTOM:
              g.fillRect(x+1, y, w-3, h-1); // TBD
              break;
          case TOP:
          default:
              x += 1;
              y += 1;
              w -= 2;
              h -= 2;
              g.fillRect(x, y, w, h);
        }
    }


    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        // Do not paint a focus indicator
    }


    protected void paintDragHighlight(Graphics g) {
        if (dragIndex >= 0) {
            Rectangle rectangle = rects[dragIndex];
            int x = rectangle.x;
            if (dragNeighborIndex > dragIndex || dragNeighborIndex < 0 && dragIndex != 0) {
                x += rectangle.width;
            }
            g.setColor(DRAG_HIGHLIGHT);
            g.fillRect(x - 1, rectangle.y, 3, rectangle.height);
        }
    }


//    protected void paintAddButton(Graphics g) {
//        Rectangle lastTabRectangle = rects[rects.length - 1];
//        addButtonRectangle.x = lastTabRectangle.x + lastTabRectangle.width + 1;
//        addButtonRectangle.y = lastTabRectangle.y + 2;
//        if (hoverIndex == ADD_BUTTON_INDEX) {
//            g.drawImage(addButtonHoveredImage, addButtonRectangle.x, addButtonRectangle.y, null);
//        }
//        else {
//            g.drawImage(addButtonImage, addButtonRectangle.x, addButtonRectangle.y, null);
//        }
//    }
    

    protected int calculateTabAreaWidth(int tabPlacement, int vertRunCount, int maxTabWidth) {
        if (tabsHidden()) {
            return 0;
        }
        else {
            return super.calculateTabAreaWidth(tabPlacement, vertRunCount, maxTabWidth);
        }
    }


    protected int calculateTabAreaHeight(int tabPlacement, int horizRunCount, int maxTabHeight) {
        if (tabsHidden()) {
            return 0;
        }
        else {
            return super.calculateTabAreaHeight(tabPlacement, horizRunCount, maxTabHeight);
        }
    }


    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight) + 4;
    }


    protected FontMetrics getFontMetrics() {
        Font font = tabPane.getFont();
        font = new Font(font.getName(), font.getStyle() | Font.BOLD, font.getSize());
        return tabPane.getFontMetrics(font);
    }



    private TabbedPaneUI(javax.swing.JComponent c) {    
        component = c;
    }
    
    
    private boolean tabsHidden() {
        return Boolean.TRUE.equals(tabPane.getClientProperty(bka.swing.TabbedPane.TABS_HIDDEN));
    }


    private final java.awt.event.MouseAdapter mouseAdapter = new java.awt.event.MouseAdapter() {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int oldHoveredIndex = hoverIndex;
            hoverIndex = getRolloverTab();
            if (hoverIndex < 0 && addButtonRectangle.contains(evt.getPoint())) {
                hoverIndex = ADD_BUTTON_INDEX;
            }
            if (hoverIndex != oldHoveredIndex) {
                ((JTabbedPane) evt.getSource()).repaint();
            }
        }

        public void mouseDragged(java.awt.event.MouseEvent evt) {
            if (Boolean.TRUE.equals(component.getClientProperty(bka.swing.TabbedPane.DRAG_ENABLED)) && evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
                JTabbedPane tabbedPane = (JTabbedPane) evt.getSource();
                Point point = evt.getPoint();
                int oldDragIndex = dragIndex;
                int oldDragNeighborIndex = dragNeighborIndex;
                dragIndex = tabForCoordinate(tabbedPane, point.x, point.y);
                if (dragIndex >= 0) {
                    Rectangle rectangle = rects[dragIndex];
                    int xShift = rectangle.x - tabbedPane.getBoundsAt(dragIndex).x;
                    int left = rectangle.x;
                    int right = rectangle.width + left;
                    int x = point.x + xShift;
                    if (x - left < right - x) {
                        dragNeighborIndex = dragIndex - 1;
                    }
                    else {
                        dragNeighborIndex = dragIndex + 1;
                    }
                    if (dragNeighborIndex >= rects.length) {
                        dragNeighborIndex = NO_INDEX;
                    }

                }
                if ((dragIndex != oldDragIndex) || (dragNeighborIndex != oldDragNeighborIndex)) {
                    tabbedPane.repaint();
                }
            }
        }

        public void mouseReleased(java.awt.event.MouseEvent evt) {
            dragIndex = NO_INDEX;
            dragNeighborIndex = NO_INDEX;
            ((JTabbedPane) evt.getSource()).repaint();
        }

        public void mouseExited(java.awt.event.MouseEvent e) {
            if (hoverIndex >= 0) {
                hoverIndex = NO_INDEX;
                ((JTabbedPane) e.getSource()).repaint();
            }
        }

        private int hoverIndex = NO_INDEX;
    };


    private javax.swing.JComponent component;
    
    
    private Rectangle addButtonRectangle = new Rectangle();// = new Rectangle(0, 0, addButtonImage.getWidth(null), addButtonImage.getHeight(null));

    private int dragIndex = NO_INDEX;
    private int dragNeighborIndex = NO_INDEX;

    private static final Color BACKGROUND_NORMAL    = new Color(235, 240, 240);
    private static final Color BACKGROUND_SELECTED  = Color.WHITE;

    private static final Color BORDER_LIGHT     = new Color(192, 192, 192);
    private static final Color BORDER_HIGHLIGHT = new Color(  0,  90, 160);

    private static final Color DRAG_HIGHLIGHT = new Color( 31,  45, 80, 127);

//    private static final Image addButtonImage        = (Image) javax.swing.UIManager.get("TabbedPane.addButtonImage");
//    private static final Image addButtonHoveredImage = (Image) javax.swing.UIManager.get("TabbedPane.addButtonHoveredImage");

    private static final int NO_INDEX =         -1;
    private static final int ADD_BUTTON_INDEX = -2;

}
