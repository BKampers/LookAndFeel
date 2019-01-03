/*
** Copyright © Bart Kampers
*/

package bka.swing;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class PopupTextField {
    
    
    public interface Listener {
        public void textChanged(String text);
    }

    
    public PopupTextField(String text, Rectangle bounds) {
        this(text, bounds, 0);
    }
    
    
    public PopupTextField(String text, Rectangle bounds, int minWidth) {
        this.bounds = bounds;
        Dimension minimumSize = textField.getPreferredSize();
        textField.addActionListener((ActionEvent evt) -> {
            popup.setVisible(false);
        });
        popup.setBorder(BorderFactory.createEmptyBorder());
        popup.setPreferredSize(new Dimension(Math.max(minWidth, bounds.width), Math.max(minimumSize.height, bounds.height)));
        textField.setText(text);
        popup.add(textField);
    }
    
    
    public void show(Component parent) {
        popup.show(parent, bounds.x, bounds.y);
        textField.requestFocus();
    }
    
    
    public String getText() {
        return textField.getText();
    }


    public void addListener(final Listener listener) {
        textField.addActionListener((ActionEvent evt) -> {
            listener.textChanged(textField.getText());
        });
    }

    
    final private JPopupMenu popup = new JPopupMenu();
    final private JTextField textField = new JTextField();
    private Rectangle bounds;


}
