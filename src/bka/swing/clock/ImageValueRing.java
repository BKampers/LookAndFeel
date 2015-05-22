package bka.swing.clock;

import java.awt.*;

public class ImageValueRing extends ValueRing {

    public ImageValueRing() {
        super();
    }
    

    public ImageValueRing(Image[] images, Dimension dimension, Point center, int radius) {
        super();
        this.images = images;
        setDimensions(dimension);
        setCenter(center);
        setRadius(radius);
    }
    

    public void setImages(Image[] images) {
        this.images = images;
    }
    
    
    public void setDimensions(Dimension[] dimensions) {
        this.dimensions = dimensions;
    }
    
    
    public void setDimensions(Dimension dimension) {
        dimensions = new Dimension[10];
        for (int i = 0; i < 10; i++) {
            dimensions[i] = dimension;
        }
    }

    
    public void paintValue(Graphics2D g2d, String value) {
        int width = 0;
        int height = 0;
        for (int i = 0; i < value.length(); i++) {
            int c = Character.valueOf(value.charAt(i)) - CHARACTER_VALUE_OF_0;
            if (0 <= c && c < 10) {
                width += dimensions[c].width;
                height = Math.max(height, dimensions[c].height);
            }
        }
        width /= -2;
        height /= -2;
        for (int i = 0; i < value.length(); i++) {
            int c = Character.valueOf(value.charAt(i)) - CHARACTER_VALUE_OF_0;
            if (0 <= c && c < 10) {
                g2d.drawImage(images[c], width, height, null);
                width += dimensions[c].width;
            }
        }
    }
    
    
    private static final int CHARACTER_VALUE_OF_0 = Character.valueOf('0');
    
    private Image[] images; 
    private Dimension[] dimensions; 
    
}
