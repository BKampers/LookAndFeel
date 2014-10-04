package bka.swing.validators;


import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.util.*;


public class HostValidator {
    
    
    public HostValidator(JTextComponent component, int defaultPort) {
        this.component = component;
        this.defaultPort = defaultPort;
        validate();
        component.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                validate();
            }
        });
    }
    
    
    public HostValidator(JComboBox comboBox, int defaultPort) {
        this((javax.swing.text.JTextComponent) comboBox.getEditor().getEditorComponent(), defaultPort);
        comboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                validate();
            }
        });
    }
    
    
    public void addListener(ValidatorListener listener) {
        synchronized (listeners) {
            listeners.add(listener);
        }
    }
    
    
    public void removeListener(ValidatorListener listener) {
        synchronized (listeners) {
            listeners.remove(listener);
        }
    }
    
    
    public void set(String address, int port) {
        if (port == defaultPort) {
            component.setText(address);
        }
        else {
            component.setText(address + SEPARATOR + port);
        }
        validate();
    }
    
    
    public String getHostString() {
        return address + SEPARATOR + port;
    }
    
    
    public String getAddress() {
        return address;
    }
    
    
    public int getPort() {
        return port;
    }
    
        
    private void validate() {
        String addressText = component.getText();
        address = addressText;
        port = defaultPort;
        int separatorIndex = addressText.indexOf(SEPARATOR);
        if (separatorIndex >= 0) {
            address = addressText.substring(0, separatorIndex);
            try {
                String portString = addressText.substring(separatorIndex + 1);
                port = Integer.parseInt(portString);
            }
            catch (NumberFormatException ex) {
                port = INVALID_PORT;
            }
        }
        boolean valid = 0 <= port && port <= 0xFFFF && ! address.isEmpty();
        if (valid) {
            component.setBackground(javax.swing.UIManager.getColor("TextField.background"));
        }
        else {
            component.setBackground(javax.swing.UIManager.getColor("errorBackground"));      
        }
        notifyListeners(valid);
    }
    
    
    private void notifyListeners(boolean valid) {
        synchronized (listeners) {
            for (ValidatorListener listener : listeners) {
                listener.verified(valid);
            }
        }
    }
    
    
    private final JTextComponent component;
    private final int defaultPort;
    
    private String address;
    private int port;
    
    
    private final ArrayList<ValidatorListener> listeners = new ArrayList<ValidatorListener>();
            
    
    private static final int INVALID_PORT = -1;
    private static final char SEPARATOR = ':';
    
}
