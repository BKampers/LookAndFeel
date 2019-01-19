/*
** Copyright © Bart Kampers
*/

package bka.swing;

import java.awt.*;
import javax.swing.*;


public class Popup {
    
    public interface ModelListener {
        void apply();
    }
    
    public interface Model<T> {
        Component getComponent();
        Rectangle getBounds();
        void setListener(ModelListener listener);
        T getInitialValue();
        void applyNewValue();
    }
    
    
    public Popup(Model model) {
        this.model = model;
        container.setBorder(BorderFactory.createEmptyBorder());
        container.setPreferredSize(model.getBounds().getSize());
        container.add(model.getComponent());
        model.setListener(this::apply);
    }
    
    
    public void show(Component parent) {
        container.show(parent, model.getBounds().x,model.getBounds().y);
        model.getComponent().requestFocus();
    }
    
    
    public void hide() {
        container.setVisible(false);
    }
    
    
    private void apply() {
        model.applyNewValue();
        container.setVisible(false);
    }
    
    
    private final JPopupMenu container = new JPopupMenu();
    private final Model model;

}
