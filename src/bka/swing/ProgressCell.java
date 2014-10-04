package bka.swing;


import javax.swing.JTable;
import javax.swing.JProgressBar;
import javax.swing.UIManager;


public class ProgressCell extends javax.swing.table.DefaultTableCellRenderer {
    
    
    public ProgressCell(int min, int max, boolean stringPainted, String unit) {
        progressBar = new JProgressBar(min, max);
        progressBar.setBorder(new javax.swing.border.LineBorder(UIManager.getColor("Table.background"), 5));
        progressBar.setStringPainted(stringPainted);
        this.unit = unit;
    }
    
    
    public ProgressCell() {
        this(0, 100, true, "%");
    }
    
    
    public java.awt.Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value != null) {
            progressBar.setValue((Integer) value);
            progressBar.setString(value.toString() + unit);
            return progressBar;
        }
        else {
            return null;
        }
    } 
    
    
    private JProgressBar progressBar;
    
    private String unit;

}
