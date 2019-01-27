/*
** © Bart Kampers
*/
package bka.swing;

import java.awt.*;
import java.util.function.*;
import javax.swing.*;


public abstract class TextFieldPopupModel implements Popup.Model<String> {
        

    public static TextFieldPopupModel create(Rectangle bounds, String initialValue, Consumer<String> onApply) {
        return new TextFieldPopupModel() {
            @Override
            public Point getLocation() {
                return bounds.getLocation();
            }
            @Override
            public Dimension getSize() {
                return bounds.getSize();
            }
            @Override
            public String getInitialValue() {
                return initialValue;
            }
            @Override
            public void applyNewValue() {
                onApply.accept(getText());
            }
        };
    }
    
    
    @Override
    public void bindListener(Runnable whenReady) {
        textField.addActionListener(evt -> whenReady.run());
    }

    
    @Override
    public Component getComponent() {
        textField.setText(getInitialValue());
        return textField;
    }
    
    
    protected String getText() {
        return textField.getText();
    }
    

    private final JTextField textField = new JTextField();;

}
