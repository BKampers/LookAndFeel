/*
** Copyright © Bart Kampers
*/

package bka.plaf;


public class LookAndFeel extends javax.swing.plaf.metal.MetalLookAndFeel {

    public static void install() {
        javax.swing.JFrame.setDefaultLookAndFeelDecorated(true);
        javax.swing.JDialog.setDefaultLookAndFeelDecorated(true);
        javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme(new bka.plaf.Theme());
        try {
            javax.swing.UIManager.setLookAndFeel("bka.plaf.LookAndFeel");
	}
        catch(ClassNotFoundException x) {
            x.printStackTrace(System.err);
        }
        catch(InstantiationException x) {
            x.printStackTrace(System.err);
	}
        catch(IllegalAccessException x) {
            x.printStackTrace(System.err);
	}
        catch(javax.swing.UnsupportedLookAndFeelException x) {
            x.printStackTrace(System.err);
	}
    }


    public String getName() {
        return "Ellips";
    }


    public String getDescription() {
        return "The Ellips House Style";
    }


    public String getID() {
       return "Ellips";
    }


    public boolean isNativeLookAndFeel() {
        return false;
    }


    public boolean isSupportedLookAndFeel() {
        return true;
    }


    protected void initClassDefaults(javax.swing.UIDefaults table) {
        super.initClassDefaults(table);
        table.putDefaults(uiDefaults(new String[] {
            "ButtonUI", 
            "ToolBarButtonUI", 
            "ComboBoxUI", 
            "SplitPaneUI",  
            "TabbedPaneUI", 
            "ScrollBarUI",
            "FormattedTextFieldUI"}));
    }

    
    private Object[] uiDefaults(String[] uiNames) {
        final String packageName = "bka.plaf.";
        Object[] defaults = new Object[uiNames.length * 2];
        for (int i = 0; i < uiNames.length; i++) {
            defaults[i * 2] = uiNames[i];
            defaults[i * 2 + 1] = packageName + uiNames[i];
        }
        return defaults;
    } 

}
