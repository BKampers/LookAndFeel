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
    
    
    public final void initialize(TableModel tableModel, JScrollPane scrollPane, int rowHeight) {
        this.tableModel = tableModel;
        
        headerColumn.createDefaultColumnsFromModel();
        headerColumn.setColumnSelectionAllowed(false);
        headerColumn.setCellSelectionEnabled(false);
        headerColumn.setDefaultRenderer(Object.class, new RowHeaderRenderer());
        
        headerColumn.setRowHeight(rowHeight);

        JViewport viewport = new JViewport();
        viewport.setView(headerColumn);
        viewport.setPreferredSize(headerColumn.getMaximumSize());
        
        scrollPane.setRowHeader(viewport);
        JTableHeader tableHeader = headerColumn.getTableHeader();
        tableHeader.setEnabled(false);
        scrollPane.setCorner(javax.swing.ScrollPaneConstants.UPPER_LEFT_CORNER, tableHeader);
    }
    
    
    public void setAlignment(int horizontalAlignment, int verticalAlignment) {
        rowHeaderRenderer.setHorizontalAlignment(horizontalAlignment);
        rowHeaderRenderer.setVerticalAlignment(verticalAlignment);
    }
    
    
    private class Table extends JTable {
        
        Table() {
            super(new Model(), new ColumnModel()); 
        }
        
        @Override
        public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
            return getTableHeader().getDefaultRenderer().getTableCellRendererComponent(this, getValueAt(row, col), false, false, row, col);
        }
        
    }
    
    
    private class Model extends DefaultTableModel {
        
        public int getColumnCount() {
            return 1;
        }
        
        public int getRowCount() {
            return (tableModel != null) ? tableModel.getRowCount() : 0; 
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
    
    
    private class RowHeaderRenderer implements TableCellRenderer {

        RowHeaderRenderer() {
            rowHeaderRenderer = (DefaultTableCellRenderer) headerColumn.getTableHeader().getDefaultRenderer();
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            return rowHeaderRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
        }

    }
    
    private final Table headerColumn = new Table();
    
    private TableModel tableModel = null;

    private DefaultTableCellRenderer rowHeaderRenderer;

    
}
