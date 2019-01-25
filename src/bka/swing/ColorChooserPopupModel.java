/*
** © Bart Kampers
*/

package bka.swing;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;


public abstract class ColorChooserPopupModel implements Popup.Model<Color> {
        
        @Override
        public Component getComponent() {
            colorChooser.setPreviewPanel(getPreviewPanel());
            colorChooser.setColor(getInitialValue());
            return colorChooser;
        }

       
        @Override
        public void bindListener(Popup.ModelListener listener) {
            colorChooser.getSelectionModel().addChangeListener(new ColorChangeListener(listener));
        }
        
        
        protected Color getColor() {
            return colorChooser.getColor();
        }

        
        protected JPanel getPreviewPanel() {
            return new JPanel();
        }
        
        
        private class ColorChangeListener implements ChangeListener {

            ColorChangeListener(Popup.ModelListener listener) {
                this.modelListener = listener;
            }
            
            @Override
            public void stateChanged(ChangeEvent evt) {
                System.out.println(evt);
                modelListener.apply();
            }
            
            private final Popup.ModelListener modelListener;
            
        }
        

        private final JColorChooser colorChooser = new JColorChooser();

}
