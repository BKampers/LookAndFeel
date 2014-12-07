package bka.swing.validators;


import javax.swing.*;
import java.text.*;


public class RealValidator extends AbstractValidator {

    public RealValidator(JFormattedTextField field, Double min, Double max) {
        super(field, min, max);
    }


    public RealValidator(JFormattedTextField field, Double min, Double max, int maxDecimals) {
        super(field, min, max);
        enableDecimalCheck(maxDecimals);
    }


    public final void enableDecimalCheck(int count) {
        checkDecimalCount = true;
        maxDecimalCount = count;
    }


    public Number value() {
        DecimalFormat format = new DecimalFormat();
        try {
            return format.parse(field.getText());
        }
        catch (ParseException ex) {
            return null;
        }
    }


    protected boolean inRange(Number number) {
        double value = number.doubleValue();
        return min.doubleValue() <= value && value <= max.doubleValue();
    }


    protected String filter(String text, int offset) {
        /*
        ** TBD accept minus sign if super.min < 0
        */
        java.text.DecimalFormatSymbols symbols = new java.text.DecimalFormatSymbols();
        String filtered = new String();
        String fieldText = field.getText();
        int separatorIndex = fieldText.indexOf(symbols.getDecimalSeparator());
        boolean hasSeparator = false;
        int decimalCount = 0;
        if (separatorIndex >= 0) {
            hasSeparator = true;
            decimalCount = fieldText.length() - separatorIndex - 1;
        }
        char[] charArray = text.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            boolean decimalAllowed = decimalCount < maxDecimalCount;
            boolean validDigit = Character.isDigit(ch) && (! checkDecimalCount || offset <= separatorIndex || decimalAllowed);
            boolean validSeparator = ! hasSeparator && maxDecimalCount > 0 && ch == symbols.getDecimalSeparator();
            if (validDigit/* || offset == 0 && i == 0 && ch == symbols.getMinusSign()*/ || validSeparator) {
                filtered += ch;
                if (validSeparator) {
                    hasSeparator = true;
                    separatorIndex = i;
                }
                if (checkDecimalCount && hasSeparator && offset > separatorIndex) {
                    decimalCount++;
                }
            }
        }
        return filtered;
    }


    private int maxDecimalCount = 0;
    private boolean checkDecimalCount = false;

}
