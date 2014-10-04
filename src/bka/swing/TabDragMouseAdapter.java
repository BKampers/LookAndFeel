package bka.swing;


import java.awt.*;
import javax.swing.*;


public class TabDragMouseAdapter extends java.awt.event.MouseAdapter {


    public void mousePressed(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
            JTabbedPane tabbedPane = (JTabbedPane) evt.getSource();
            dragIndex = tabbedPane.indexAtLocation(evt.getX(), evt.getY());
            if (dragIndex >= 0) {
                previousCursor = tabbedPane.getCursor();
                tabbedPane.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        }
    }


    public void mouseReleased(java.awt.event.MouseEvent evt) {
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON1) {
            JTabbedPane tabbedPane = (JTabbedPane) evt.getSource();
            if (dragIndex >= 0) {
                int dropIndex = tabbedPane.indexAtLocation(evt.getX(), evt.getY());
                if (dropIndex >= 0 && dragIndex != dropIndex) {
                    Rectangle dropBounds = tabbedPane.getBoundsAt(dropIndex);
                    int x = evt.getX();
                    int left = dropBounds.x;
                    int right = dropBounds.width + left;
                    if (right - x < x - left) {
                        dropIndex++;
                    }
                }
                if (dragIndex < dropIndex) {
                    dropIndex--;
                }
                if (dragIndex != dropIndex && dragIndex >= 0 && dropIndex >= 0) {
                    tabDragged(tabbedPane, dragIndex, dropIndex);
                }
                dragIndex = -1;
            }
            if (previousCursor != null) {
                tabbedPane.setCursor(previousCursor);
                previousCursor = null;
            }
        }
    }


    public void mouseEntered(java.awt.event.MouseEvent evt) {
        JTabbedPane pane = (JTabbedPane) evt.getSource();
        pane.setCursor(pane.getCursor());
    }


    protected void tabDragged(JTabbedPane tabbedPane, int oldIndex, int newIndex) {
        Component dragComponent = tabbedPane.getComponentAt(oldIndex);
        tabbedPane.removeTabAt(oldIndex);
        tabbedPane.add(dragComponent, newIndex);
        tabbedPane.setSelectedIndex(newIndex);
    }


    private int dragIndex = -1;

    private Cursor previousCursor = null;

}
