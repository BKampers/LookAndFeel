package bka.swing;


import java.util.*;


public class LanguageResourceBundle {


    static LanguageResourceBundle getBundle(String baseName, Locale locale) {
        return new LanguageResourceBundle(baseName, locale);
    }


    public String getString(String key, Object... args) {
        if (resourceBundle != null) {
            try {
                String string = resourceBundle.getString(key);
                Formatter formatter = new Formatter();
                formatter.format(string, args);
                return formatter.toString();
            }
            catch (MissingResourceException ex) {
                return ex.getKey();
            }
        }
        else {
            return key;
        }

    }


    private LanguageResourceBundle(String baseName, Locale locale) {
        resourceBundle = ResourceBundle.getBundle(baseName, locale);
    }

    
    private ResourceBundle resourceBundle;

}
