package bka.swing;


import java.util.Vector;


public class TabbedPane extends javax.swing.JTabbedPane {


    public static final String DRAG_ENABLED = "dragEnabled";
    public static final String TABS_HIDDEN  = "tabsHidden";


    public boolean getTabsVisible() {
        return ! Boolean.TRUE.equals(getClientProperty(TABS_HIDDEN));
    }


    public void setTabsVisible(boolean visible) {
        putClientProperty(TABS_HIDDEN, ! visible);
        if (! visible) {
            java.awt.event.MouseListener[] mouseListeners = getMouseListeners();
            for (java.awt.event.MouseListener mouseListener : mouseListeners) {
                removeMouseListener(mouseListener);
            }
        }
    }


    public void setDragEnabled(boolean dragEnabled) {
        putClientProperty(DRAG_ENABLED, dragEnabled);
    }


//    public void setAddButtonEnabled(boolean addButtonEnabled) {
//        try {
//            ((ellips.plaf.TabbedPaneUI) ui).setAddButtonEnabled(addButtonEnabled);
//        }
//        catch (ClassCastException ex) {
//            throw new UnsupportedOperationException("setAddButtonEnabled is not allowed on " + ui.getClass(), ex);
//        }
//    }
    
    
//    public java.awt.Rectangle getAddButtonBounds() {
//        try {
//            return ((ellips.plaf.TabbedPaneUI) ui).getAddButtonBounds();
//        }
//        catch (ClassCastException ex) {
//            throw new UnsupportedOperationException("getAddButtonBounds is not allowed on " + ui.getClass(), ex);
//        }
//    }


//    public java.awt.Insets getInsets() {
//        java.awt.Insets insets = super.getInsets();
//        if (getAddButtonBounds() != null) {
//            System.out.println(getAddButtonBounds());
//        }
//        return insets;
//    }


    public void addListener(TabbedPaneListener listener) {
        listeners.add(listener);
    }


    public void removeListener(TabbedPaneListener listener) {
        listeners.remove(listener);
    }
    
    
//    private java.awt.Rectangle addButtonRectangle;

    private Vector<TabbedPaneListener> listeners = new Vector<TabbedPaneListener>();

}
