
package bka.swing.validators;


import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;
import javax.swing.text.*;


public abstract class AbstractValidator extends JFormattedTextField.AbstractFormatterFactory {

    @SuppressWarnings("LeakingThisInConstructor")
    public AbstractValidator(JFormattedTextField field, Number min, Number max) {
        this.field = Objects.requireNonNull(field);
        this.min = Objects.requireNonNull(min);
        this.max = Objects.requireNonNull(max);
        formatter = new Formatter();
        field.setFormatterFactory(this);
        field.addFocusListener(FOCUS_ADAPTER);
        field.addKeyListener(KEY_ADAPTER);
        field.addPropertyChangeListener(PROPERTY_CHANGE_LISTENER);
        verify();
    }

    public void setListener(ValidatorListener listener) {
        this.listener = listener;
    }

    final public void verify() {
        Number value = value();
        if (value != null) {
            valid = inRange(value);
        }
        else {
            valid = false;
        }
        formatter.changeEditValid();
        if (listener != null) {
            listener.verified(valid);
        }
    }

    @Override
    public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
        return formatter;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public boolean isSilent() {
        return silent;
    }
    
    protected Number getMin() {
        return min;
    }

    protected Number getMax() {
        return max;
    }

    public abstract Number value();

    protected abstract boolean inRange(Number number);
    protected abstract String filter(String text, int offset);


    protected boolean filterError(String text, String filtered) {
        return ! text.equals(filtered);
    }

    private void setBackground() {
        if (valid || ! field.isEnabled()) {
            field.setBackground(UIManager.getColor("FormattedTextField.background"));
        }
        else {
            field.setBackground(UIManager.getColor("errorBackground"));
        }
        field.invalidate();
    }

    
    private class Formatter extends DefaultFormatter {

        void changeEditValid() {
            setEditValid(valid);
            setBackground();
        }

        @Override
        protected void setEditValid(boolean valid) {
            JFormattedTextField ftf = getFormattedTextField();
            if (ftf != null) {
                super.setEditValid(valid);
            }
        }

        @Override
        protected javax.swing.text.DocumentFilter getDocumentFilter() {
            return FILTER;
        }

        private final DocumentFilter FILTER = new DocumentFilter() {
            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String filtered = filter(text, offset);
                if (!silent && filterError(text, filtered)) {
                    invalidEdit();
                }
                super.replace(fb, offset, length, filtered, attrs);
            }
        };

    }

    
    private final FocusAdapter FOCUS_ADAPTER = new FocusAdapter() {

        @Override
        public void focusLost(java.awt.event.FocusEvent evt) {
            formatter.changeEditValid();
        }

    };


    private final KeyAdapter KEY_ADAPTER = new KeyAdapter() {

        @Override
        public void keyReleased(KeyEvent evt) {
            verify();
        }

    };


    private final java.beans.PropertyChangeListener PROPERTY_CHANGE_LISTENER = new java.beans.PropertyChangeListener() {

        @Override
        public void propertyChange(java.beans.PropertyChangeEvent evt) {
            String name = evt.getPropertyName();
            if ("enabled".equals(name)) {
                setBackground();
            }
        }

    };


    protected JFormattedTextField field;

    private final Number min;
    private final Number max;
    private final Formatter formatter;

    private boolean valid = false;
    private boolean silent = true;
    private ValidatorListener listener = null;

}
