package bka.swing;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;


public class ComboCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    public ComboCell() {
    }


//    public ComboCell(Object[] items, JTable table) {
//        this.table = table;
//        setComboBox(new JComboBox(items));
//    }
//
//
//    public ComboCell(java.util.Collection items, JTable table) {
//        this(items.toArray(), table);
//    }


    public Object getCellEditorValue() {
        return comboBox.getSelectedItem();
    }


    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        comboBox.setSelectedItem(value);
        setColors(false);
        return comboBox;
    }


    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        comboBox.setSelectedItem(value);
        setColors(isSelected);
        return comboBox;
    }


    public void setHorizontalAlignment(int alignment) {
        ListCellRenderer renderer = comboBox.getRenderer();
        ((javax.swing.plaf.basic.BasicComboBoxRenderer.UIResource) renderer).setHorizontalAlignment(alignment);
    }


    public boolean isValid() {
        return valid;
    }


    public void setValid(boolean valid) {
        this.valid = valid;
    }


//    protected final void setComboBox(JComboBox comboBox) {
//        int horizontalAlignment = DefaultTableCellRenderer.LEADING;
//        if (this.comboBox != null) {
//            this.comboBox.removeActionListener(ACTION_LISTENER);
//            horizontalAlignment = ((javax.swing.plaf.basic.BasicComboBoxRenderer.UIResource) this.comboBox.getRenderer()).getHorizontalAlignment();
//        }
//        this.comboBox = comboBox;
//        ((ellips.plaf.ComboBoxUI) comboBox.getUI()).setInTable(true);
//        setHorizontalAlignment(horizontalAlignment);
//        comboBox.addActionListener(ACTION_LISTENER);
//    }


    protected JComboBox getComboBox() {
        return comboBox;
    }


    protected void setTable(JTable table) {
        this.table = table;
    }


    protected JTable getTable() {
        return table;
    }


    protected void applySelectedItem(Object selectedItem, int rowIndex, int columnIndex) {
        table.setValueAt(selectedItem, rowIndex, columnIndex);
    }


    protected void setColors(boolean isSelected) {
        if (isSelected) {
            comboBox.setBackground(table.getSelectionBackground());
            comboBox.setForeground(table.getSelectionForeground());
        }
        else {
            comboBox.setBackground(table.getBackground());
            comboBox.setForeground(table.getForeground());
        }
    }


    protected boolean editing() {
        return table.getCellEditor() == this;
    }


    private final java.awt.event.ActionListener ACTION_LISTENER = new java.awt.event.ActionListener() {

        public void actionPerformed(java.awt.event.ActionEvent e) {
            TableCellEditor editor = table.getCellEditor();
            if (editing()) {
                applySelectedItem(comboBox.getSelectedItem(), table.getEditingRow(), table.getEditingColumn());
                editor.stopCellEditing();
                table.repaint();
            }
        }
        
    };


    private JComboBox comboBox = null;
    private JTable table = null;

    private boolean valid = true;

}


