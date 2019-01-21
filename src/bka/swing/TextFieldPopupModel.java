/*
** © Bart Kampers
*/

package bka.swing;

import java.awt.*;
import javax.swing.*;


public abstract class TextFieldPopupModel implements Popup.Model<String> {
        
    
    @Override
    public void bindListener(Popup.ModelListener listener) {
        textField.addActionListener(evt -> listener.apply());
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
