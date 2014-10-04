package bka.swing;


import java.awt.*;
import java.io.*;


import java.util.Hashtable;


public abstract class AbstractDialog extends javax.swing.JDialog {

    
    public AbstractDialog(FrameApplication parent, boolean modal) {
        super(parent, modal);
        this.parent = parent;
        type = getClass().getName();
        if (locations == null || sizes == null) {
            load();
        }
    }
    
    
    public AbstractDialog(FrameApplication parent) {
        this(parent, true);
    }


    public void dispose() {
        if (isVisible()) {
            locations.put(type, getLocation());
            sizes.put(type, getSize());
            store();
        }
        super.dispose();
    }


    public void setVisible(boolean visible) {
        if (visible) {
            Dimension size = sizes.get(type);
            Dimension minimumSize = getSize();
            if (size != null) {
                size.width = Math.max(minimumSize.width, size.width);
                size.height = Math.max(minimumSize.height, size.height);
            }
            else {
                size = minimumSize;
            }
            Point location = locations.get(type);
            if (location == null) {
                java.awt.Point parentLocation = getParent().getLocation();
                java.awt.Dimension parentSize = getParent().getSize();
                location = new Point(
                    parentLocation.x + (parentSize.width - size.width) / 2,
                    parentLocation.y + (parentSize.height - size.height) / 2);
                locations.put(type, location);
            }
            setLocation(location);
            if (isResizable()) {
                setSize(size);
            }
        }
        super.setVisible(visible);
    }


    public java.util.Locale getLocale() {
        return parent.getLocale();
    }


    public String language(String tag, Object... args) {
        return parent.language(tag, args);
    }


    protected FrameApplication frameApplication() {
        return parent;
    }


    protected void setSetting(String name, Object object) {
        parent.setSetting(name, object);
    }


    protected Object getSetting(String name) {
        return parent.getSetting(name);
    }


    protected Object getSetting(String name, Object defaultValue) {
        return parent.getSetting(name, defaultValue);
    }


    private void store() {
        try {
            File file = parent.applicationFile(FILE_NAME);
            FileOutputStream fileStream = new FileOutputStream(file);
            ObjectOutputStream objectStream = new ObjectOutputStream(fileStream);
            objectStream.writeObject(locations);
            objectStream.writeObject(sizes);
            objectStream.close();
        }
        catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }


    @SuppressWarnings("unchecked")
    private void load() {
        try {
            File file = parent.applicationFile(FILE_NAME);
            FileInputStream fileStream = new FileInputStream(file);
            ObjectInputStream objectStream = new ObjectInputStream(fileStream);
            locations = (Hashtable<String, Point>) objectStream.readObject();
            sizes = (Hashtable<String, Dimension>) objectStream.readObject();
            objectStream.close();
        }
        catch (Exception ex) {
            if (locations == null) {
                locations = new Hashtable<String, Point>();
            }
            if (sizes == null) {
                sizes = new Hashtable<String, Dimension>();
            }
        }
    }


    private FrameApplication parent;
    private String type;


    private static Hashtable<String, Point> locations = null;
    private static Hashtable<String, Dimension> sizes = null;

    private static final String FILE_NAME = "dialogs";

}
