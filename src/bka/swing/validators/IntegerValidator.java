package bka.swing.validators;


import javax.swing.*;


public class IntegerValidator extends AbstractValidator {

    public IntegerValidator(JFormattedTextField field, Long min, Long max) {
        super(field, min, max);
    }


    public IntegerValidator(JFormattedTextField field, Integer min, Integer max) {
        super(field, new Long(min), new Long(max));
    }


    public Number value() {
        try {
            return Long.parseLong(field.getText());
        }
        catch (NumberFormatException ex) {
            return null;
        }
    }


    protected boolean inRange(Number number) {
        long value = number.longValue();
        return min.longValue() <= value && value <= max.longValue();
    }


    protected String filter(String text, int offset) {
        java.text.DecimalFormatSymbols symbols = new java.text.DecimalFormatSymbols();
        String filtered = new String();
        char[] charArray = text.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            if (Character.isDigit(ch) || min.longValue() < 0 && offset == 0 && i == 0 && ch == symbols.getMinusSign()) {
                filtered += ch;
            }
        }
        return filtered;
    }

}
