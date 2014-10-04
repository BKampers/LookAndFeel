/*
** Copyright © Bart Kampers
*/

package bka.swing;

import java.awt.Component;
import javax.swing.*;
import javax.swing.table.*;


public class TableRowHeaders {


    public String cornerName() {
        return null;
    }
    
        
    public Object rowName(int row) {
        return row + 1;
    }

    
    public void initialize(TableModel tableModel, JScrollPane scrollPane) {
        this.tableModel = tableModel;
        
        Table headerColumn = new Table();
        headerColumn.createDefaultColumnsFromModel();
        headerColumn.setColumnSelectionAllowed(false);
        headerColumn.setCellSelectionEnabled(false);
        headerColumn.setDefaultRenderer(Object.class, new CellRenderer());

        JViewport viewport = new JViewport();
        viewport.setView(headerColumn);
        viewport.setPreferredSize(headerColumn.getMaximumSize());
        
        scrollPane.setRowHeader(viewport);
        JTableHeader tableHeader = headerColumn.getTableHeader();
        tableHeader.setEnabled(false);
        scrollPane.setCorner(javax.swing.ScrollPaneConstants.UPPER_LEFT_CORNER, tableHeader);
    }
    
    
    private class Table extends JTable {
        
        Table() {
            super(new Model(), new ColumnModel()); 
        }
        
        public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
            return getTableHeader().getDefaultRenderer().getTableCellRendererComponent(this, getValueAt(row, col), false, false, row, col);
        }
        
    }
    
    
    private class Model extends DefaultTableModel {
        
        public int getColumnCount() {
            return 1;
        }
        
        public int getRowCount() {
            return tableModel.getRowCount(); 
        }
        
        public String getColumnName(int column) {
            return cornerName();
        }
        
        public Object getValueAt(int row, int column) {
            return rowName(row);
        }
        
        public boolean isCellEditable(int row, int column) {
            return false;
        }
        
    }

    
    private class ColumnModel extends DefaultTableColumnModel {

        public void addColumn(TableColumn column) {
            column.setMaxWidth(column.getPreferredWidth());
            super.addColumn(column);
        }
        
    }
    
    
    private class CellRenderer extends DefaultTableCellRenderer {
        
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JTableHeader header = new JTableHeader();
            header.setTable(table);
            return header;
        }
        
    }

    
    private TableModel tableModel = null;
    
}
