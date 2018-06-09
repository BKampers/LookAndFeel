/*
** © Bart Kampers
*/

package bka.swing;

import java.util.*;
import javax.swing.*;


public class CustomDialog extends AbstractDialog { 
    
    
    public CustomDialog(FrameApplication parent, String title, JPanel panel, boolean modal) {
        super(parent, title, modal);
        this.panel = Objects.requireNonNull(panel);
        initComponents();
    }
    
    
    @Override
    protected String persistencyKey() {
        return panel.getClass().getName();
    }

    
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(panel.getMinimumSize());
        setPreferredSize(panel.getPreferredSize());
        getContentPane().add(panel);
        pack();
    }
    
    
    private final JPanel panel; 
    
}
