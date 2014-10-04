package bka.swing;


import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

import java.io.*;
import javax.imageio.*;

import java.util.*;

import java.net.URL;


public class Images {
    
    
    public enum Resource {
        
        OK("Ok.png"),
        QUESTION("Question.png"),
        INFORMATION("Information.png"),
        WARNING("Warning.png"),
        ERROR("Error.png"),
        UP("Up.png"),
        DOWN("Down.png"),
        LEFT("Left.png"),
        RIGHT("Right.png"),
        UP_HIGHLIGHT("Up (highlight).png"),
        DOWN_HIGHLIGHT("Down (highlight).png"),
        LEFT_HIGHLIGHT("Left (highlight).png"),
        RIGHT_HIGHLIGHT("Right (highlight).png");
        
        Resource(String fileName) {
            this.fileName = fileName;
        }
        
        public Image image() {
            return getImage(getURL("Images/" + fileName));
        }
        
        public ImageIcon icon() {
            return getIcon(image());
        }
        
        private String fileName;
        
    }


    public static class FileFilter  extends javax.swing.filechooser.FileFilter {

        public FileFilter() {
        }

        public FileFilter(String description) {
            this.description = description;
        }

        public boolean accept(java.io.File pathname) {
            boolean acc = pathname.isDirectory();
            if (! acc) {
                String fileSuffix = suffix(pathname.getName());
                String[] suffixes = ImageIO.getReaderFileSuffixes();
                int i = 0;
                while (! acc && i < suffixes.length) {
                    if (suffixes[i].equalsIgnoreCase(fileSuffix)) {
                        acc = true;
                    }
                    i++;
                }
            }
            return acc;
        }

        public String getDescription() {
            return description;
        }
        
        private String description = "Image files";

    };


    public static Image get(URL url) throws Exception {
        Image image = null;
        if (url != null) {
            String key = url.toString();
            image = images.get(key);
            if (image == null) {
                image = ImageIO.read(url);
                images.put(key, image);
            }
        }
        return image;
    }


    public static Image get(File file) throws Exception {
        Image image = null;
        if (file != null) {
            String key = file.toString();
            image = images.get(key);
            if (image == null) {
                image = ImageIO.read(file);
                images.put(key, image);
            }
        }
        return image;
    }


    public static Image get(URL baseFileUrl, URL overlayFileUrl) throws Exception {
        String key = baseFileUrl.toString() + "∪" + overlayFileUrl.toString();
        Image image = images.get(key);
        if (image == null) {
            image = get(baseFileUrl);
            if (image != null) {
                Image overlay = get(overlayFileUrl);
                if (overlay != null) {
                    image = overlayed(image, overlay);
                }
            }
            images.put(key, image);
        }
        return image;
    }


    public static Image getImage(URL url) {
        try {
            return get(url);
        }
        catch (Exception x) {
            return null;
        }
    }


    public static ImageIcon getIcon(URL url) {
        try {
            return getIcon(get(url));
        }
        catch (Exception x) {
            return null;
        }
    }


    public static ImageIcon getIcon(URL baseFileUrl, URL overlayFileUrl) {
        try {
            Image image = get(baseFileUrl, overlayFileUrl);
            return getIcon(image);
        }
        catch (Exception x) {
            return null;
        }
    }


    public static ImageIcon getIcon(Image image) {
        ImageIcon icon = null;
        if (image != null) {
            icon = icons.get(image);
            if (icon == null) {
                icon = new ImageIcon(image.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH));
            }
        }
        return icon;
    }


    protected static Image overlayed(Image base, Image overlay) throws Exception {
        int baseWidth = base.getWidth(null);
        int baseHeight = base.getHeight(null);
        int overlayWidth = overlay.getWidth(null);
        int overlayHeight = overlay.getHeight(null);
        Image image = new BufferedImage(baseWidth, baseHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        graphics.drawImage(base, 0, 0, null);
        graphics.drawImage(overlay, baseWidth - overlayWidth, baseHeight - overlayHeight, null);
        return image;
    }


    private static String suffix(String fileName) {
        String string = "";
        int suffixIndex = fileName.lastIndexOf('.') + 1;
        if (0 < suffixIndex && suffixIndex < fileName.length()) {
            string = fileName.substring(suffixIndex);
        }
        return string;
    }


    private static URL getURL(String resourceName) {
        return loader.getResource(resourceName);
    }


    protected static Hashtable<String, Image> images = new Hashtable<String, Image>();
    protected static Hashtable<Image, ImageIcon> icons = new Hashtable<Image, ImageIcon>();


    private static final ClassLoader loader = Thread.currentThread().getContextClassLoader();


    private static final int ICON_WIDTH  = 16;
    private static final int ICON_HEIGHT = 16;

}
