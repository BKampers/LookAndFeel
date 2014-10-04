package bka.swing;


import java.util.*;


public class ComboBoxModel<T> extends javax.swing.DefaultComboBoxModel {
    
    
    public ComboBoxModel(Collection<T> collection) {
        List<Item> list = new ArrayList<Item>();
        for (T object : collection) {
            list.add(new Item(object));
        }
        Collections.sort(list);
        for (Item item : list) {
            addElement(item);
        }
    }
    
    
    public void setSelectedObject(T object) {
        setSelectedItem(new Item(object));
    }
    
    
    public T getSelectedObject() {
        Item selectedItem = (Item) getSelectedItem();
        return (selectedItem != null) ? selectedItem.object : null;
    }
    
    
    protected String itemString(T object) {
        return object.toString();
    }
    
    
    private class Item implements Comparable<Item> {
        
        Item(T object) {
            this.object = object;
        }
        
        public String toString() {
            return (object != null) ? itemString(object) : null;
        }
        
        public boolean equals(Object other) {
            return other instanceof ComboBoxModel.Item && ((Item) other).object == object;
        }
        
        public int compareTo(Item other) {
            String thisString = toString();
            String otherString = other.toString();
            if (thisString == null) {
                return (otherString == null) ? 0 : -1;
            }
            else {
                return thisString.compareTo(otherString);
            }
        }
        
        T object;
    }
    
}
