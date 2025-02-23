package bka.swing.validators;


import javax.swing.*;
import java.math.BigInteger;


public class BasedValidator extends AbstractValidator {

    public BasedValidator(JFormattedTextField field, BigInteger min, BigInteger max, int radix) {
        super(field, min, max);
        this.radix = radix;
    }

    public BasedValidator(JFormattedTextField field, Integer min, Integer max, int radix) {
        this(field, BigInteger.valueOf(min), BigInteger.valueOf(max), radix);
    }

    public String valueString() {
        Number value = value();
        if (value instanceof BigInteger) {
            return ((BigInteger) value()).toString(radix);
        }
        return null;
    }

    @Override
    public Number value() {
        try {
            String text = field.getText().replaceAll(separatorRegex(), "");
            return new BigInteger(text, radix);
        }
        catch (NumberFormatException ex) {
            return null;
        }
    }
    
    private static String separatorRegex() {
        return "\\" + SEPARATOR;
    }

    @Override
    protected boolean inRange(Number number) {
        BigInteger value = (BigInteger) value();
        return value.compareTo((BigInteger) getMin()) >= 0 && value.compareTo((BigInteger) getMax()) <= 0;
    }

    @Override
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
                offset == 0 && ((BigInteger) getMin()).compareTo(BigInteger.ZERO) < 0 && ch == '-' ||
                ch == SEPARATOR)
            {
                filtered += ch;
            }
        }
        return filtered;
    }


    private final int radix;

    private static final char SEPARATOR = ':';

}
