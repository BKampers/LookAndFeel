/*
** Copyright © Bart Kampers
*/

package bka.swing;


import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;


public class Table {

    
    /**
     * Set widths of table columns
     * @param table  : table to set the widths for
     * @param widths : contains preferred widths for columns. If the length of
     *                 this array is smaller than the number of columns the last
     *                 columns will be sized to fit the table according to the
     *                 table's parent's width. 
     *                 If a field of this array holds a negative value the 
     *                 corresponding column will also be sized to fit the table
     *                 according to the table's parent's width.
     */
    public static void setColumnWidths(JTable table, int[] widths) {
        TableColumnModel columnModel = table.getColumnModel();
        int remainingWidth = table.getParent().getWidth();
        int fitCount = 0;
        //* Set the given column widths
        for (int col = 0; col < table.getColumnCount(); col++) {
            TableColumn column = columnModel.getColumn(col);
            int width = (col < widths.length) ? widths[col] : -1;
            if (width >= 0) {
                column.setPreferredWidth(width);
                remainingWidth -= width;
            }
            else {
                fitCount++;
            }
        }
        if (fitCount > 0) {
            //* Fit the columns with no given width
            int fitWidth = remainingWidth / fitCount;
            int fitRemainder = remainingWidth % fitCount;
            for (int col = 0; col < table.getColumnCount(); col++) {
                TableColumn column = columnModel.getColumn(col);
                int width = (col < widths.length) ? widths[col] : -1;
                if (width < 0) {
                    column.setPreferredWidth(fitWidth + fitRemainder);
                    fitRemainder = 0;
                }
            }
        }
    }
    
    
    public static java.awt.Color background(JTable table, boolean isSelected, int row) {
        if (isSelected) {
            return table.getSelectionBackground();
        }
        else if (row % 2 == 0) {
            return javax.swing.UIManager.getColor("Table.alternateRowColor");
        }
        else {
            return table.getBackground();
        }
    }


    public static final DefaultTableCellRenderer HEADER_RENDERER = new DefaultTableCellRenderer() {

        public Color getBackground() {
            return UIManager.getColor("TableHeader.background");
        }

        public Color getForeground() {
            return UIManager.getColor("TableHeader.foreground");
        }

        public Font getFont() {
            return UIManager.getFont("TableHeader.font");
        }

        public javax.swing.border.Border getBorder() {
            return UIManager.getBorder("TableHeader.cellBorder");
        }

    };

}
