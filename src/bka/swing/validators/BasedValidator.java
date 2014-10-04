package bka.swing.validators;


import javax.swing.*;
import java.math.BigInteger;


public class BasedValidator extends AbstractValidator {

    public BasedValidator(JFormattedTextField field, BigInteger min, BigInteger max, int radix) {
        super(field, min, max);
        this.radix = radix;
    }


    public BasedValidator(JFormattedTextField field, Integer min, Integer max, int radix) {
        this(field, new BigInteger(min.toString()), new BigInteger(max.toString()), radix);
    }


    public String valueString() {
        Number value = value();
        if (value instanceof BigInteger) {
            return ((BigInteger) value()).toString(radix);
        }
        else {
            return null;
        }
    }


    public Number value() {
        BigInteger value = null;
        String text = field.getText();
        text = text.replaceAll(SEPARATOR_STRING, "");
        try {
            value = new BigInteger(text, radix);
        }
        catch (NumberFormatException ex) {
        }
        return value;
    }


    protected boolean inRange(Number number) {
        BigInteger value = (BigInteger) value();
        return value.compareTo((BigInteger) min) >= 0 && value.compareTo((BigInteger) max) <= 0;
    }


    protected String filter(String text, int offset) {
        String filtered = new String();
        for (char ch : text.toCharArray()) {
            if (ch == ' ' || ch == '.' || ch == ',') {
                ch = SEPARATOR;
            }
            else {
                ch = Character.toUpperCase(ch);
            }
            if ('0' <= ch && ch <= '9' || 'A' <= ch && ch <= (char) ('A' + radix - 11) || 
                offset == 0 && ((BigInteger) min).compareTo(BigInteger.ZERO) < 0 && ch == '-' ||
                ch == SEPARATOR)
            {
                filtered += ch;
            }
        }
        return filtered;
    }


    private int radix;

    private static final char SEPARATOR = ':';
    private static final String SEPARATOR_STRING = "" + SEPARATOR;

}
