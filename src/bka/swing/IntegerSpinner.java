package bka.swing;


import javax.swing.*;
import javax.swing.text.*;
import bka.swing.validators.IntegerValidator;


public class IntegerSpinner extends JSpinner {

    public IntegerSpinner(String format, boolean editable) {
        editor = new JSpinner.NumberEditor(this, format);
        editor.setEnabled(false);
        DefaultFormatter formatter = (DefaultFormatter) editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false);

        Document document = editor.getTextField().getDocument();
        if (document instanceof PlainDocument) {
            AbstractDocument abstractDocument = new PlainDocument() /*{
                public void setDocumentFilter(DocumentFilter filter) {
                    if (filter instanceof Filter) {
                        super.setDocumentFilter(filter);
                    }
                }
            }*/;
            abstractDocument.setDocumentFilter(new Filter());
            editor.getTextField().setDocument(abstractDocument);
        }

        JTextField tf = editor.getTextField();
        tf.setEditable(editable);
        setEditor(editor);
        addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                if (validator != null) {
                    editor.getTextField().setText(IntegerSpinner.this.getValue().toString());
                    validator.verify();
                }
            }
        });
    }


    public IntegerSpinner() {
        this("#", false);
    }


    public void initialize(int initial, int minimum, int maximum, int stepSize) {
        SpinnerNumberModel model = new SpinnerNumberModel(initial, minimum, maximum, stepSize);
        setModel(model);
        JFormattedTextField field = editor.getTextField();
        field.setValue(initial);
        validator = new IntegerValidator(field, minimum, maximum);
    }


    private class Filter extends DocumentFilter {

        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            super.insertString(fb, offset, string, attr);
            if (validator != null) {
                validator.verify();
            }
        }

        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            super.remove(fb, offset, length);
            if (validator != null) {
                validator.verify();
            }
        }

        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            super.replace(fb, offset, length, text, attrs);
            if (validator != null) {
                validator.verify();
            }
        }

    }


    private JSpinner.NumberEditor editor;

    private IntegerValidator validator = null;

}

