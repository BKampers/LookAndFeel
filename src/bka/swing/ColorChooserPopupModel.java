/*
** © Bart Kampers
*/

package bka.swing;

import java.awt.*;
import java.util.function.*;
import javax.swing.*;
import javax.swing.event.*;


public abstract class ColorChooserPopupModel implements Popup.Model<Color> {
 
    
    public static ColorChooserPopupModel create(Rectangle bounds, Color initialValue, Consumer<Color> onApply) {
        return new ColorChooserPopupModel() {
            @Override
            public Point getLocation() {
                return bounds.getLocation();
            }
            @Override
            public Dimension getSize() {
                return bounds.getSize();
            }
            @Override
            public Color getInitialValue() {
                return initialValue;
            }
            @Override
            public void applyNewValue() {
                onApply.accept(getColor());
            }
        };
    }
    
    
    @Override
    public Component getComponent() {
        colorChooser.setPreviewPanel(getPreviewPanel());
        colorChooser.setColor(getInitialValue());
        return colorChooser;
    }


    @Override
    public void bindListener(Runnable whenReady) {
        colorChooser.getSelectionModel().addChangeListener(new ColorChangeListener(whenReady));
    }


    protected Color getColor() {
        return colorChooser.getColor();
    }


    protected JPanel getPreviewPanel() {
        return new JPanel();
    }


    private class ColorChangeListener implements ChangeListener {

        ColorChangeListener(Runnable whenReady) {
            this.whenReady = whenReady;
        }

        @Override
        public void stateChanged(ChangeEvent evt) {
            whenReady.run();
        }

        private final Runnable whenReady;

    }


    private final JColorChooser colorChooser = new JColorChooser();

}
