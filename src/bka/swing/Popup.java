/*
** Copyright © Bart Kampers
*/

package bka.swing;

import java.awt.*;
import java.util.function.*;
import javax.swing.*;


public final class Popup {

    
    public interface Model<T> {
        Component getComponent();
        Point getLocation();
        Dimension getSize();
        void bindListener(Runnable whenReady);
        T getInitialValue();
        void applyNewValue();
    }
    
    
    public static void show(Component parent, Model model) {
        Popup popup = new Popup(model);
        popup.show(parent);
    }
    
    
    private Popup(Model model) {
        this.model = model;
        container.setBorder(BorderFactory.createEmptyBorder());
        container.setPreferredSize(model.getSize());
        container.add(model.getComponent());
        model.bindListener(this::apply);
    }
    
    
    private void show(Component parent) {
        Point location = model.getLocation();
        container.show(parent, location.x, location.y);
        model.getComponent().requestFocus();
    }
    
    
    private void apply() {
        model.applyNewValue();
        container.setVisible(false);
    }
    
    
    private final JPopupMenu container = new JPopupMenu();
    private final Model model;

}
