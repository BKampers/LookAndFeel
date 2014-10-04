/*
** Copyright © Bart Kampers
*/

package bka.plaf;


import java.awt.*;
import javax.swing.border.*;
import javax.swing.plaf.*;
import javax.swing.plaf.metal.*;

public class Theme extends OceanTheme {


    public String getName() {
        return "Ellips Theme";
    }


    public void addCustomEntriesToTable(javax.swing.UIDefaults table) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();

        CheckIcon checkBoxIcon = new CheckIcon(
            bka.swing.Images.getImage(loader.getResource("Images/CheckBoxChecked.png")),
            bka.swing.Images.getImage(loader.getResource("Images/CheckBoxUnchecked.png")),
            bka.swing.Images.getImage(loader.getResource("Images/CheckBoxDisabledChecked.png")),
            bka.swing.Images.getImage(loader.getResource("Images/CheckBoxDisabledUnchecked.png")),
            bka.swing.Images.getImage(loader.getResource("Images/CheckBoxHoveredChecked.png")),
            bka.swing.Images.getImage(loader.getResource("Images/CheckBoxHoveredUnchecked.png")));

        CheckIcon radioButtonIcon = new CheckIcon(
            bka.swing.Images.getImage(loader.getResource("Images/RadioChecked.png")),
            bka.swing.Images.getImage(loader.getResource("Images/RadioUnchecked.png")),
            bka.swing.Images.getImage(loader.getResource("Images/RadioDisabledChecked.png")),
            bka.swing.Images.getImage(loader.getResource("Images/RadioDisabledUnchecked.png")),
            bka.swing.Images.getImage(loader.getResource("Images/RadioHoveredChecked.png")),
            bka.swing.Images.getImage(loader.getResource("Images/RadioHoveredUnchecked.png")));

        Object[] defaults = new Object[] {
            "activeCaption"                   , CAPTION_BACKGROUND,
            "activeCaptionBorder"             , Color.WHITE,
            "activeCaptionText"               , CAPTION_TEXT_COLOR,
            "inactiveCaption"                 , new Color(235, 235, 235),
            "inactiveCaptionText"             , new Color(103, 103, 103),
            "inactiveCaptionBorder"           , Color.WHITE,
            "errorBackground"                 , new Color(255, 132, 140),
            "Button.background"               , new Color(254, 254, 254),
            "Button.defaultSize"              , new java.awt.Dimension(125, 25),
            "Button.font"                     , DEFAULT_FONT,
            "Button.normalBorderColor"        , new Color(178, 178, 178),
            "Button.hoverBorderColor"         , new Color( 62,  91, 161),
            "Button.normalGradient"           , new Color(236, 236, 236),
            "Button.pressedGradient"          , new Color(118, 192, 225),
            "CheckBox.font"                   , DEFAULT_FONT,
            "CheckBox.icon"                   , checkBoxIcon,
            "CheckBoxMenuItem.font"           , DEFAULT_FONT,
            "CheckBoxMenuItem.acceleratorFont", DEFAULT_FONT,
            "ColorChooser.font"               , DEFAULT_FONT,
            "ComboBox.font"                   , DEFAULT_FONT,
            "EditorPane.font"                 , DEFAULT_FONT,
            "FileView.directoryIcon"          , bka.swing.Images.getIcon(loader.getResource("Images/TreeClosed.png")),
            "FormattedTextField.label"        , new Color(112, 112, 112),
            "InternalFrame.titleFont"         , BOLD_FONT,
            "Label.attentionForeground"       , new Color(  1, 116,  39),
            "Label.font"                      , DEFAULT_FONT,
            "List.font"                       , DEFAULT_FONT,
            "MenuBar.font"                    , DEFAULT_FONT,
            "Menu.font"                       , DEFAULT_FONT,
            "MenuItem.font"                   , DEFAULT_FONT,
            "MenuItem.acceleratorFont"        , DEFAULT_FONT,
            "OptionPane.background"           , PANEL_BACKGROUND,
            "OptionPane.font"                 , DEFAULT_FONT,
            "OptionPane.errorIcon"            , icon(bka.swing.Images.Resource.ERROR.image()),
            "OptionPane.informationIcon"      , icon(bka.swing.Images.Resource.INFORMATION.image()),
            "OptionPane.questionIcon"         , icon(bka.swing.Images.Resource.QUESTION.image()),
            "OptionPane.warningIcon"          , icon(bka.swing.Images.Resource.WARNING.image()),
            "Panel.background"                , PANEL_BACKGROUND,
            "Panel.font"                      , DEFAULT_FONT,
            "PasswordField.font"              , DEFAULT_FONT,
            "PopupMenu.font"                  , DEFAULT_FONT,
            "ProgressBar.font"                , DEFAULT_FONT,
            "ProgressBar.foreground"          , Color.GREEN,
            "ProgressBar.selectionBackground" , CAPTION_TEXT_COLOR,
            "ProgressBar.selectionForeground" , CAPTION_TEXT_COLOR,
            "RadioButton.font"                , DEFAULT_FONT,
            "RadioButton.icon"                , radioButtonIcon,
            "RootPane.frameBorder"            , FRAME_BORDER,
            "RootPane.plainDialogBorder"      , DIALOG_BORDER,
            "ScrollPane.font"                 , DEFAULT_FONT,
	    "ScrollBar.thumbDarkShadow"       , new Color(192, 192, 192),
	    "ScrollBar.thumb"                 , new Color(244, 244, 244),
	    "ScrollBar.track"                 , new Color(245, 245, 245),
//            "ScrollBar.leftImage"             , bka.swing.Images.getImage(loader.getResource("Images/ScrollLeft.png")),
//            "ScrollBar.rightImage"            , bka.swing.Images.getImage(loader.getResource("Images/ScrollRight.png")),
//            "ScrollBar.upImage"               , bka.swing.Images.getImage(loader.getResource("Images/ScrollUp.png")),
//            "ScrollBar.downImage"             , bka.swing.Images.getImage(loader.getResource("Images/ScrollDown.png")),
            "SplitPane.background"            , PANEL_BACKGROUND,
//            "TabbedPane.addButtonImage"       , bka.swing.Images.getImage(loader.getResource("Images/TabbedPaneAddButton.png")),
//            "TabbedPane.addButtonHoveredImage", bka.swing.Images.getImage(loader.getResource("Images/TabbedPaneAddButtonHovered.png")),
            "TabbedPane.font"                 , DEFAULT_FONT,
//            "TabbedPane.tabsOverlapBorder"    , Boolean.TRUE,
            "TabbedPane.contentBorderInsets"  , new Insets(1, 2, 2, 2),
            "TabbedPane.shadow"               , Color.WHITE,
            "TabbedPane.darkShadow"           , Color.WHITE,
            "Table.alternateRowColor"         , new Color(240, 248, 255),
            "Table.font"                      , DEFAULT_FONT,
            "Table.gridColor"                 , new Color(240, 240, 240),
            "Table.selectionBackground"       , CELL_SELECTION_BACKGROUND,
            "Table.selectionForeground"       , CELL_SELECTION_FOREGROUND,
            "TableHeader.font"                , DEFAULT_FONT,
            "TextArea.font"                   , DEFAULT_FONT,
            "TextField.font"                  , DEFAULT_FONT,
            "TextPane.font"                   , DEFAULT_FONT,
            "TitledBorder.font"               , DEFAULT_FONT,
            "ToggleButton.font"               , DEFAULT_FONT,
            "ToolBar.font"                    , DEFAULT_FONT,
            "ToolBarButton.hoverBorderColor"  , SELECTION_BACKGROUND,
            "ToolTip.background"              , new Color(255, 255, 225),
            "ToolTip.border"                  , new LineBorder(new Color(105, 105, 105)),
            "ToolTip.font"                    , DEFAULT_FONT,
            "ToolTip.foreground"              , Color.BLACK,
            "Tree.closedIcon"                 , bka.swing.Images.getIcon(loader.getResource("Images/TreeClosed.png")),
            "Tree.collapsedIcon"              , bka.swing.Images.getIcon(loader.getResource("Images/TreeCollapsed.png")),
            "Tree.expandedIcon"               , bka.swing.Images.getIcon(loader.getResource("Images/TreeExpanded.png")),
//            "Tree.leafIcon"                   , bka.swing.Images.getIcon(loader.getResource("Images/TreeLeaf.png")),
            "Tree.openIcon"                   , bka.swing.Images.getIcon(loader.getResource("Images/TreeClosed.png")),
            "Tree.font"                       , DEFAULT_FONT,
            "Tree.selectionBackground"        , CELL_SELECTION_BACKGROUND,
            "Tree.selectionForeground"        , CELL_SELECTION_FOREGROUND
        };
        table.putDefaults(defaults);
    }


//    protected javax.swing.plaf.ColorUIResource getPrimary1() {
//        return PRIMARY_1;
//    }


    protected javax.swing.plaf.ColorUIResource getPrimary2() {
        return PRIMARY_2;
    }


    protected javax.swing.plaf.ColorUIResource getPrimary3() {
        return PRIMARY_3;
    }


//    protected javax.swing.plaf.ColorUIResource getSecondary1() {
//        return SECONDARY_1;
//    }


    protected javax.swing.plaf.ColorUIResource getSecondary2() {
        return SECONDARY_2;
    }


//    protected javax.swing.plaf.ColorUIResource getSecondary3() {
//        return SECONDARY_3;
//    }


    private javax.swing.ImageIcon icon(Image image) {
        return new javax.swing.ImageIcon(image.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH));
    }


    private static class CheckIcon implements javax.swing.Icon {

        CheckIcon(Image checkedIcon, Image uncheckedIcon, Image checkedDisabledIcon,Image uncheckedDisabledIcon, Image checkedHoveredIcon,Image uncheckedHoveredIcon) {
            this.checkedIcon = checkedIcon;
            this.uncheckedIcon = uncheckedIcon;
            this.checkedDisabledIcon = checkedDisabledIcon;
            this.uncheckedDisabledIcon = uncheckedDisabledIcon;
            this.checkedHoveredIcon = checkedHoveredIcon;
            this.uncheckedHoveredIcon = uncheckedHoveredIcon;
        }

        public void paintIcon(Component component, Graphics g, int x, int y) {
            javax.swing.AbstractButton abstractButton = (javax.swing.AbstractButton) component;
            javax.swing.ButtonModel buttonModel = abstractButton.getModel();
            Image image;
            boolean checked = buttonModel.isSelected();
            if (abstractButton.isEnabled()) {
                if (abstractButton.isRolloverEnabled() && buttonModel.isRollover()) {
                    image = checked ? checkedHoveredIcon : uncheckedHoveredIcon;
                }
                else {
                    image = checked ? checkedIcon : uncheckedIcon;
                }
            }
            else {
                image = checked ? checkedDisabledIcon : uncheckedDisabledIcon;
            }
            g.translate(x, y);
            g.drawImage(image, 0, 0, component);
            g.translate(-x, -y);
        }

        public int getIconWidth() {
            return 15;
        }

        public int getIconHeight() {
            return 15;
        }
        
        private Image checkedIcon;
        private Image uncheckedIcon;
        private Image checkedDisabledIcon;
        private Image uncheckedDisabledIcon;
        private Image checkedHoveredIcon;
        private Image uncheckedHoveredIcon;
    }


    private static final Font DEFAULT_FONT = new Font("Trebuchet MS", Font.PLAIN, 12);
    private static final Font BOLD_FONT    = new Font("Trebuchet MS", Font.BOLD,  12);

    private static final Border FRAME_BORDER  = new LineBorder(Color.GRAY, 3, true);
    private static final Border DIALOG_BORDER = new LineBorder(new Color(0, 60, 190), 2, true);

    private static final Color PANEL_BACKGROUND          = Color.WHITE;
    private static final Color CAPTION_BACKGROUND        = Color.WHITE;
    private static final Color CAPTION_TEXT_COLOR        = new Color( 62, 91, 161);
    private static final Color SELECTION_BACKGROUND      = new Color(214, 224, 229);
    private static final Color CELL_SELECTION_BACKGROUND = new Color( 51, 153, 255);
    private static final Color CELL_SELECTION_FOREGROUND = Color.WHITE;


//    private static final ColorUIResource PRIMARY_1 = new ColorUIResource(Color.YELLOW);
    private static final ColorUIResource PRIMARY_2 = new ColorUIResource(SELECTION_BACKGROUND);
    private static final ColorUIResource PRIMARY_3 = new ColorUIResource(SELECTION_BACKGROUND);

//    private static final ColorUIResource SECONDARY_1 = new ColorUIResource(Color.YELLOW);
    private static final ColorUIResource SECONDARY_2 = new ColorUIResource(Color.LIGHT_GRAY);
//    private static final ColorUIResource SECONDARY_3 = new ColorUIResource(Color.LIGHT_GRAY);

    private static final int ICON_WIDTH  = 50;
    private static final int ICON_HEIGHT = 50;

}
