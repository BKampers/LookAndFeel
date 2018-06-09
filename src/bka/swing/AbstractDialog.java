package bka.swing;


import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.logging.*;


public abstract class AbstractDialog extends javax.swing.JDialog {

    
    public AbstractDialog(FrameApplication parent, String title, boolean modal) {
        super(parent, title, modal);
        this.parent = parent;
        if (locations == null || sizes == null) {
            load();
        }
    }


    @Override
    public void dispose() {
        if (isVisible()) {
            String type = persistencyKey();
            locations.put(type, getLocation());
            sizes.put(type, getSize());
            store();
        }
        super.dispose();
    }


    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            String key = persistencyKey();
            Dimension size = determineSize(key);
            setLocation(determineLocation(key, size));
            if (isResizable()) {
                setSize(size);
            }
        }
        super.setVisible(visible);
    }
    

    @Override
    public java.util.Locale getLocale() {
        return parent.getLocale();
    }


    public String language(String tag, Object... args) {
        return parent.language(tag, args);
    }
    
    
    protected String persistencyKey() {
        return getClass().getName();
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
        File file = parent.applicationFile(FILE_NAME);
        try (ObjectOutputStream objectStream = new ObjectOutputStream(new FileOutputStream(file))) {
            objectStream.writeObject(locations);
            objectStream.writeObject(sizes);
        }
        catch (IOException ex) {
            Logger.getLogger(AbstractDialog.class.getName()).log(Level.WARNING, "Cannot store dialog properties", ex);
        }
    }


    @SuppressWarnings("unchecked")
    private void load() {
        File file = parent.applicationFile(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream objectStream = new ObjectInputStream(new FileInputStream(file))) {
                locations = (Map<String, Point>) objectStream.readObject();
                sizes = (Map<String, Dimension>) objectStream.readObject();           
            }
            catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(AbstractDialog.class.getName()).log(Level.WARNING, "Cannot load dialog properties", ex);
            }
        }
        if (locations == null) {
            locations = new HashMap<>();
        }
        if (sizes == null) {
            sizes = new HashMap<>();
        }
    }


    private Point determineLocation(String key, Dimension size) {
        Point location = locations.get(key);
        if (location == null) {
            java.awt.Point parentLocation = getParent().getLocation();
            java.awt.Dimension parentSize = getParent().getSize();
            location = new Point(
                parentLocation.x + (parentSize.width - size.width) / 2,
                parentLocation.y + (parentSize.height - size.height) / 2);
            locations.put(key, location);
        }
        return location;
    }

    
    private Dimension determineSize(String key) {
        Dimension size = sizes.get(key);
        Dimension minimumSize = getSize();
        if (size != null) {
            size.width = Math.max(minimumSize.width, size.width);
            size.height = Math.max(minimumSize.height, size.height);
        }
        else {
            size = minimumSize;
        }
        return size;
    }


    private final FrameApplication parent;

    private static Map<String, Point> locations;
    private static Map<String, Dimension> sizes;

    private static final String FILE_NAME = "dialogs";

}
