package bka.swing.clock;

import java.awt.*;

public class ImageTickRing extends TickRing {

    public ImageTickRing() {
    }
    
    
    public ImageTickRing(Image minorImage, Dimension minorDimension, Image majorImage, Dimension majorDimension, Point center, int radius) {
        this.minorImage = minorImage;
        this.minorDimension = minorDimension;
        this.majorImage = majorImage;
        this.majorDimension = majorDimension;
        setCenter(center);
        setRadius(radius);
    }
    
    
    public void setMinorImage(Image image, Dimension dimension) {
        minorImage = image;
        minorDimension = dimension;
    }

    
    public void setMajorImage(Image image, Dimension dimension) {
        this.majorImage = image;
        majorDimension = dimension;
    }

    
    public void paintMinorTick(Graphics2D g2d) {
        if (minorImage != null) {
            g2d.drawImage(minorImage, minorDimension.width/-2, minorDimension.height/2, null);
        }
    }

    
    public void paintMajorTick(Graphics2D g2d) {
        if (majorImage != null) {
            g2d.drawImage(majorImage, majorDimension.width/-2, majorDimension.height/2, null);
        }
    }

    
    private Image minorImage = null;
    private Image majorImage = null;
    
    private Dimension minorDimension = null;
    private Dimension majorDimension = null;
    
}
