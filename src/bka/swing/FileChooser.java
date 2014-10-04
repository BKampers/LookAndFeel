package bka.swing;


import java.util.*;

import java.awt.*;
import javax.swing.*;

import java.io.File;


public class FileChooser extends JFileChooser {


    public FileChooser(String currentDirectoryPath) {
        super(currentDirectoryPath);
        setFileView(new IconFileView());
    }


    public static javax.swing.filechooser.FileNameExtensionFilter createCsvFileFilter(String description) {
        if (description == null) {
            description = "Comma separated values";
        }
        return new javax.swing.filechooser.FileNameExtensionFilter(description, "csv");
    }


    public static javax.swing.filechooser.FileNameExtensionFilter createCsvFileFilter() {
        return createCsvFileFilter(null);
    }
    
    
    public void setLocation(int x, int y) {
        location = new Point(x, y);
    }
    
    
    public void setLocation(Point point) {
        location = point;
    }
    
    
    protected JDialog createDialog(Component parent) throws HeadlessException {
        JDialog dlg = super.createDialog(parent);
        if (location != null) {
            dlg.setLocation(location);
        }
        return dlg;
    }
    

    private class IconFileView extends javax.swing.filechooser.FileView {

        public Icon getIcon(File file) {
            Icon icon = null;
            String name = file.getName();
            if (name != null) {
                String[] suffixes = javax.imageio.ImageIO.getReaderFileSuffixes();
                int i = 0;
                while (icon == null && i < suffixes.length) {
                    if (name.endsWith("." + suffixes[i])) {
                        icon = iconTable.get(file);
                        if (icon == null && ! loading.contains(file)) {
                            new Thread(new IconLoader(file)).start();
                        }
                    }
                    i++;
                }
            }
            if (icon == null) {
                icon = super.getIcon(file);
            }
            return icon;
        }

        class IconLoader implements Runnable {
            IconLoader(File file) {
                this.file = file;
            }
            public void run() {
                try {
                    loading.add(file);
                    Icon icon = Images.getIcon(file.toURI().toURL());
                    iconTable.put(file, icon);
                    repaint();
                    loading.remove(file);
                }
                catch (java.net.MalformedURLException ex) {
                    // file is not removed from Vector loading so load will not be retried
                }
            }
            private File file;
        }
    }


    private Hashtable<File, Icon> iconTable = new Hashtable<File, Icon>();
    private Vector<File> loading = new Vector<File>();
    
    private Point location = null;

}
