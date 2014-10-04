package bka.swing;


import java.awt.*;
import javax.swing.*;
import java.util.*;


public class SeparatorListCellRenderer extends DefaultListCellRenderer {      

    
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (index >= 0 && separatorObjects.contains(value)) {
            separatorPanel.removeAll();
            separatorPanel.add(component, BorderLayout.CENTER);
            separatorPanel.add(separator, BorderLayout.SOUTH);
            return separatorPanel;
        }
        else {
            return component;
        }
    }

    
    
    public void setSeparatorAfter(Object object) {
        separatorObjects.add(object);
    }

        
    private Collection<Object> separatorObjects = new ArrayList<Object>();

    private final JPanel separatorPanel = new JPanel(new BorderLayout());
    private final JSeparator separator = new JSeparator();
    
}
    