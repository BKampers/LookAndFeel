/*
** Copyright © Bart Kampers
*/

package bka.swing;


import java.awt.*;
import java.io.*;
import java.security.*;
import java.util.*;
import java.util.logging.*;
import javax.swing.*;
import org.json.*;


public abstract class FrameApplication extends JFrame {

    
    public FrameApplication() {
        super();
        initializeListeners();
    }

    
    public Object setProperty(String key, String value) {
        if (value == null) {
            properties.remove(key);
            return null;
        }
        return properties.setProperty(key, value);
    }


    public String getProperty(String key) {
        return properties.getProperty(key);
    }


    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }


    public Long getLongProperty(String key) {
        try {
            String value = getProperty(key);
            if (value == null) {
                return null;
            }
            return Long.parseLong(value);
        }
        catch (NumberFormatException ex) {
            Logger.getLogger(FrameApplication.class.getName()).log(Level.WARNING, PROPERTY_TYPE_MISMATCH, ex); 
            return null;
        }
    }


    public long getLongProperty(String key, long defaultValue) {
        Long value = getLongProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }


    public Integer getIntProperty(String key) {
        try {
            String value = getProperty(key);
            if (value == null) {
                return null;
            }
            return Integer.parseInt(value);
        }
        catch (NumberFormatException ex) {
            Logger.getLogger(FrameApplication.class.getName()).log(Level.WARNING, PROPERTY_TYPE_MISMATCH, ex); 
            return null;
        }
    }


    public int getIntProperty(String key, int defaultValue) {
        Integer value = getIntProperty(key);
        if (value != null) {
            return value;
        }
        else {
            return defaultValue;
        }
    }


    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String property = getProperty(key);
        return (property != null) ? Boolean.parseBoolean(getProperty(key)) : defaultValue;
    }


    public boolean getBooleanProperty(String key) {
        return getBooleanProperty(key, false);
    }


    private JSONObject getConfigurationObject(String key) {
        File file = configurationFile();
        try {
            FileInputStream stream = new FileInputStream(file);
            int count = stream.available();
            byte[] buffer = new byte[count];
            stream.read(buffer);
            String source = new String(buffer);
            JSONObject configuration = new JSONObject(source);
            return configuration.getJSONObject(key);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FrameApplication.class.getName()).log(Level.FINEST, "Configuration not available", ex);
        }
        catch (IOException | JSONException ex) {
            Logger.getLogger(FrameApplication.class.getName()).log(Level.WARNING, "Configuration read error", ex);
        }
        return null;
    }


    public LinkedList<String> propertyList(String name, int max) {
        LinkedList<String> list = new LinkedList<>();
        if (name != null && max > 0) {
            for (int i = 1; i <= max; i++) {
                String propertyName = name + Integer.toString(i);
                String property = properties.getProperty(propertyName);
                if (property != null) {
                    list.add(property);
                }
            }
        }
        return list;
    }


    public void addListProperty(String name, int max, String value) {
        if (name != null && max > 0 && value != null) {
            LinkedList<String> list = propertyList(name, max);
            if (! list.contains(value)) {
                while (list.size() > max) {
                    list.removeLast();
                }
                list.addFirst(value);
                int i = 1;
                for (String string : list) {
                    properties.setProperty(name + Integer.toString(i), string);
                    i++;
                }
                properties.setProperty(name, value);
            }
        }
    }


    public void removeListProperty(String name, int max, String value) {
        if (name != null && value != null) {
            LinkedList<String> list = propertyList(name, max);
            if (list.contains(value)) {
                list.remove(value);
                int i = 1;
                for (String string : list) {
                    properties.setProperty(name + Integer.toString(i), string);
                    i++;
                }
                properties.remove(name + Integer.toString(i));
            }
        }
    }


    public void setSetting(String name, Object object) {
        if (object != null) {
            settings.put(name, object);
        }
        else {
            settings.remove(name);
        }
    }


    public Object getSetting(String name) {
        return settings.get(name);
    }


    public Object getSetting(String name, Object defaultValue) {
        Object value = getSetting(name);
        return (value != null) ? value : defaultValue;
    }


    public void setSettings(String prefix,  ButtonGroup buttonGroup) {
        Enumeration<AbstractButton> abstractButtons = buttonGroup.getElements();
        while (abstractButtons.hasMoreElements()) {
            AbstractButton abstractButton = abstractButtons.nextElement();
            settings.put(prefix + "." + abstractButton.getActionCommand(), abstractButton.isSelected());
        }
    }


    public void getSettings(String prefix,  ButtonGroup buttonGroup) {
        Enumeration<AbstractButton> abstractButtons = buttonGroup.getElements();
        while (abstractButtons.hasMoreElements()) {
            AbstractButton abstractButton = abstractButtons.nextElement();
            Boolean selected = (Boolean) settings.get(prefix + "." + abstractButton.getActionCommand());
            if (selected != null) {
                abstractButton.setSelected(selected);
            }
        }
    }


    public void setSettings(String prefix, JTable table) {
        int columnCount = table.getColumnCount();
        int[] columnWidths = new int[columnCount];
        int[] columnIndices = new int[columnCount];
        int i = 0;
        Enumeration<javax.swing.table.TableColumn> columns = table.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            javax.swing.table.TableColumn tableColumn = columns.nextElement();
            int index = tableColumn.getModelIndex();
            columnWidths[index] = tableColumn.getWidth();
            columnIndices[i++] = index;
        }
        setSetting(prefix + ".columnWidths", columnWidths);
        setSetting(prefix + ".columnIndices", columnIndices);
    }


    public void getSettings(String prefix, JTable table, int[] defaultWidths) {
        int[] columnWidths = (int[]) getSetting(prefix + ".columnWidths");
        if (columnWidths != null) {
            Table.setColumnWidths(table, columnWidths);
        }
        else if (defaultWidths != null) {
            Table.setColumnWidths(table, defaultWidths);
        }
        int[] columnIndices = (int[]) getSetting(prefix + ".columnIndices");
        if (columnIndices != null) {
            for (int i = 0; i < columnIndices.length; i++) {
                int currentIndex = table.convertColumnIndexToView(columnIndices[i]);
                table.moveColumn(currentIndex, i);
            }
        }
    }


    public void getSettings(String prefix, JTable table) {
        getSettings(prefix, table, null);
    }


    public void changeLocale(String language, String country) {
        setLocale(language, country);
        setLanguageResourceBundle();
    }


    public void setTexts(Container container) {
        for (Component component : container.getComponents()) {
            if (component instanceof JButton) {
                String actionCommand = ((JButton) component).getActionCommand();
                if (actionCommand != null) {
                    ((JButton) component).setText(language(actionCommand));
                }
            }
            else if(component instanceof Container) {
                setTexts((Container) component);
            }
        }
    }


    public String language(String tag, Object... args) {
        if (languageResourceBundle != null) {
            return languageResourceBundle.getString(tag, args);
        }
        else {
            return tag;
        }
    }
    
    
    public String capitalizedLanguage(String tag, Object... args) {
        String text = language(tag, args);
        if (text != null && ! text.isEmpty()) {
            text = text.substring(0, 1).toUpperCase() + text.substring(1);
        }
        return text;
    }


    public abstract String manufacturerName();
    public abstract String applicationName();


    public String localeDirectory() {
        return "Locale";
    }
    
    
    protected static void setLookAndFeel(String name) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (name.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
        }
        catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            Logger.getLogger(FrameApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    protected boolean loadedFromJar() {
        String className = getClass().getName().replace('.', '/');
        String classJar = getClass().getResource("/" + className + ".class").toString();
        return classJar.startsWith("jar:");
    }
    
    
    protected void opened() {
    }
    
    
    protected void closing() {
    }


    protected File applicationFile(String name) {
        return new File(dataDirectory(), name);
    }


    protected byte[] key() {
        return fullName().getBytes();
    }


    /**
     * Overrule read properties with arguments
     * 
     * @param args 
     */
    protected void parseArguments(String[] args) {
        loadProperties();
        for (String argument : args) {
            int index = argument.indexOf('=');
            if (index > 0) {
                String key = argument.substring(0, index);
                String value = argument.substring(index + 1);
                properties.put(key, value);
            }
        }
    }


    private void loadProperties() {
        if (properties.isEmpty()) {
            try {
                properties.load(new FileInputStream(propertiesFile()));
            }
            catch (FileNotFoundException ex) {
                // Ignore
            }
            catch (IOException ex) {
                Logger.getLogger(FrameApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    private void storeProperties() {
        try {
            properties.store(new FileOutputStream(propertiesFile()), null);
        }
        catch (IOException ex) {
            Logger.getLogger(FrameApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @SuppressWarnings("unchecked")
    private void loadSettings() {
        try {
            HashMap readSettings = (HashMap<String, Object>) decrypt(settingsFile());
            settings.putAll(readSettings);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FrameApplication.class.getName()).log(Level.FINEST, "Settings file not available", ex);
        }        
        catch (IOException | ClassNotFoundException | GeneralSecurityException ex) {
            Logger.getLogger(FrameApplication.class.getName()).log(Level.SEVERE, "Error reading settings file", ex);
        }
    }


    private void storeSettings() {
        try {
            encrypt(settings, settingsFile());
        }
        catch (IOException | ClassNotFoundException | GeneralSecurityException ex) {
            Logger.getLogger(FrameApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    protected void setLocale(String language, String country) {
        setLocale(new Locale(language, country));
    }


    protected void setLanguageResourceBundle() {
        languageResourceBundle = LanguageResourceBundle.getBundle(localeDirectory() + "/" + applicationName(), getLocale());
        UIManager.put("OptionPane.okButtonMnemonic", "0");
        UIManager.put("OptionPane.yesButtonMnemonic", "0");
        UIManager.put("OptionPane.noButtonMnemonic", "0");
        UIManager.put("OptionPane.cancelButtonMnemonic", "0");
        UIManager.put("OptionPane.okButtonText", language("OK"));
        UIManager.put("OptionPane.yesButtonText", language("YES"));
        UIManager.put("OptionPane.noButtonText", language("NO"));
        UIManager.put("OptionPane.cancelButtonText", language("CANCEL"));
        UIManager.put("FileChooser.saveButtonText", language("SAVE"));
        UIManager.put("FileChooser.openButtonText", language("OPEN"));
        UIManager.put("FileChooser.cancelButtonText", language("CANCEL"));
        UIManager.put("FileChooser.saveDialogTitleText", language("FILECHOOSER_SAVE_TITLE"));
        UIManager.put("FileChooser.openDialogTitleText", language("FILECHOOSER_OPEN_TITLE"));
        UIManager.put("FileChooser.saveInLabelText", language("FILECHOOSER_SAVE_IN"));
        UIManager.put("FileChooser.lookInLabelText", language("FILECHOOSER_LOOK_IN"));
        UIManager.put("FileChooser.fileNameLabelText", language("FILECHOOSER_NAME"));
        UIManager.put("FileChooser.filesOfTypeLabelText", language("FILECHOOSER_TYPE"));
        UIManager.put("FileChooser.acceptAllFileFilterText", language("FILECHOOSER_ACCEPT_ALL"));
        UIManager.put("FileChooser.saveButtonToolTipText", language("FILECHOOSER_SAVE_TOOLTIP"));
        UIManager.put("FileChooser.openButtonToolTipText", language("FILECHOOSER_OPEN_TOOLTIP"));
        UIManager.put("FileChooser.cancelButtonToolTipText", language("FILECHOOSER_CANCEL_TOOLTIP"));
        UIManager.put("FileChooser.upFolderToolTipText", language("FILECHOOSER_UP_FOLDER_TOOLTIP"));
        UIManager.put("FileChooser.homeFolderToolTipText", language("FILECHOOSER_HOME_FOLDER_TOOLTIP"));
        UIManager.put("FileChooser.newFolderToolTipText", language("FILECHOOSER_NEW_FOLDER_TOOLTIP"));
        UIManager.put("FileChooser.listViewButtonToolTipText", language("FILECHOOSER_LIST_VIEW_TOOLTIP"));
        UIManager.put("FileChooser.detailsViewButtonToolTipText", language("FILECHOOSER_DETAILS_VIEW_TOOLTIP"));
    }


    protected LanguageResourceBundle getLanguageResourceBundle() {
        return languageResourceBundle;
    }


    protected File dataDirectory() {
        if (dataDirectory == null) {
            String name = System.getenv().get("APPDATA");
            if (name != null) {
                // For windows 7 platform application files need to be in ProgramData directory.
                if (! name.endsWith(File.separator)) {
                    name += File.separator;
                }
                name += fullName();
                File directory = new File(name);
                if (ensureDirectoryExists(directory)) {
                    dataDirectory = directory;
                }
            }
            else {
                String osName = System.getProperties().getProperty("os.name");
                if (osName.startsWith("Mac")) {
                    // Mac OS X
                    java.net.URL locationUrl = getClass().getProtectionDomain().getCodeSource().getLocation();
                    File locationFile = new File(locationUrl.getFile());
                    String locationPath = locationFile.getParent();
                    if (locationPath.contains(".app")) {
                        dataDirectory = new File(locationPath);
                    }
                }
            }
            if (dataDirectory == null) {
                dataDirectory = new File(".");
            }
        }
        return dataDirectory;
    }


    private String fullName() {
        return manufacturerName() + File.separator + applicationName();
    }


    private File propertiesFile() {
        return applicationFile("properties");
    }


    private File settingsFile() {
        return applicationFile("settings");
    }


    private File configurationFile() {
        return applicationFile("configuration.json");
    }


    private boolean ensureDirectoryExists(File directory) {
        boolean success = true;
        File parent = directory.getParentFile();
        if (parent != null) {
            success = ensureDirectoryExists(parent);
        }
        try {
            if (success) {
                directory.mkdir();
            }
        }
        catch (SecurityException ex) {
            Logger.getLogger(FrameApplication.class.getName()).log(Level.SEVERE, null, ex);
            success = false;
        }
        return success;
    }
    
    
    private void encrypt(Object object, File file) throws IOException, GeneralSecurityException, ClassNotFoundException {
        ByteArrayOutputStream aos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(aos);
        oos.writeObject(object);
        oos.flush();
        oos.close();
        ByteArrayInputStream ais = new ByteArrayInputStream(aos.toByteArray());
        FileOutputStream fos = new FileOutputStream(file);

        ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(generateKey()));
        java.security.Key key = (java.security.Key) keyIn.readObject();
        keyIn.close();
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
        cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, key);

        crypt(ais, fos, cipher);
    }


    private Object decrypt(File file) throws IOException, GeneralSecurityException, ClassNotFoundException  {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream aos = new ByteArrayOutputStream();

        ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream(generateKey()));
        java.security.Key key = (java.security.Key) keyIn.readObject();
        keyIn.close();
        javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
        cipher.init(javax.crypto.Cipher.DECRYPT_MODE, key);

        crypt(fis, aos, cipher);

        ByteArrayInputStream ais = new ByteArrayInputStream(aos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(ais);
        return ois.readObject();
    }

    
    private File generateKey() throws IOException, NoSuchAlgorithmException {
        File keyFile = applicationFile("initialized");
        if (! keyFile.exists()) {
            javax.crypto.KeyGenerator generator = javax.crypto.KeyGenerator.getInstance("AES");
            java.security.SecureRandom random = new java.security.SecureRandom();
            generator.init(random);
            javax.crypto.SecretKey key = generator.generateKey();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(keyFile));
            out.writeObject(key);
            out.close();
        }
        return keyFile;
    }
    
    
    private void crypt(InputStream in, OutputStream out, javax.crypto.Cipher cipher) throws IOException, GeneralSecurityException {
        int blockSize = cipher.getBlockSize();
        int outputSize = cipher.getOutputSize(blockSize);
        byte[] inBytes = new byte[blockSize];
        byte[] outBytes = new byte[outputSize];

        int inLength = 0;
        boolean more = true;
        while (more) {
            inLength = in.read(inBytes);
            if (inLength == blockSize) {
                int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
                out.write(outBytes, 0, outLength);
            }
            else {
                more = false;
            }
        }
        if (inLength > 0) {
            outBytes = cipher.doFinal(inBytes, 0, inLength);
        }
        else {
            outBytes = cipher.doFinal();
        }
        out.write(outBytes);
    }
    
    
    private void initializeListeners() {
        SizeAndPositionListener sizeAndPositionListener = new SizeAndPositionListener();
        addWindowListener(sizeAndPositionListener);
        addComponentListener(sizeAndPositionListener);
    }


    private void initializeLogging() {
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT {%2$s}%n[%4$s] %5$s%6$s%n");
        JSONObject loggerConfiguration = getConfigurationObject("Loggers");
        if (loggerConfiguration != null) {
            try {
                LoggingUtil.setup(loggerConfiguration);
            }
            catch (JSONException | IOException ex) {
                Logger.getLogger(FrameApplication.class.getName()).log(Level.WARNING, "Configuration error", ex);
            }
        }
    }


    private class SizeAndPositionListener extends java.awt.event.WindowAdapter implements java.awt.event.ComponentListener {

        @Override
        public void windowOpened(java.awt.event.WindowEvent evt) {
            initializeLogging();
            loadProperties();
            Integer x = getIntProperty("x");
            Integer y = getIntProperty("y");
            Integer width = getIntProperty("width");
            Integer height = getIntProperty("height");
            Integer extended = getIntProperty("extended");
            Dimension size = getSize();
            setSize((width != null) ? width : size.width, (height != null) ? height : size.height);
            Point location = getLocation();
            setLocation((x != null) ? x : location.x, (y != null) ? y : location.y);
            if (extended != null) {
                setExtendedState(extended);
            }
            opened = true;
            loadSettings();
            opened();
        }
        
        @Override
        public void windowClosing(java.awt.event.WindowEvent evt) {
            closing();
            properties.put("extended", Integer.toString(getExtendedState()));
            storeProperties();
            if (! settings.isEmpty()) {
                storeSettings();
            }
        }
        
        @Override
        public void windowClosed(java.awt.event.WindowEvent evt) {
        }

        @Override
        public void componentResized(java.awt.event.ComponentEvent evt) {
            if (opened && getExtendedState() == java.awt.Frame.NORMAL) {
                properties.put("x", Integer.toString(getX()));
                properties.put("y", Integer.toString(getY()));
                properties.put("width", Integer.toString(getWidth()));
                properties.put("height", Integer.toString(getHeight()));
            }
        }

        @Override
        public void componentMoved(java.awt.event.ComponentEvent evt) {
            if (opened) {
                properties.put("x", Integer.toString(getX()));
                properties.put("y", Integer.toString(getY()));
            }
        }

        @Override
        public void componentShown(java.awt.event.ComponentEvent evt) {
        }

        @Override
        public void componentHidden(java.awt.event.ComponentEvent evt) {
        }
        
        private boolean opened = false;
        
    }
    
    
    static {
        javax.swing.UIDefaults defaults = UIManager.getDefaults();
        defaults.put("errorBackground", new java.awt.Color(253, 115, 115));
        defaults.put("highlightBackground", java.awt.Color.YELLOW);
        defaults.put("Border.highlightColor", java.awt.Color.ORANGE);        
    }


    private final Properties properties = new Properties();
    protected final HashMap<String, Object> settings = new HashMap<>();

    
    private File dataDirectory = null;

    private LanguageResourceBundle languageResourceBundle = null;
    
    private static final String PROPERTY_TYPE_MISMATCH = "Property type mismatch";
    
}
