package bka.swing.clock;

import java.awt.*;

public class ImageTickRing extends TickRing {

    public ImageTickRing(Point center, int radius, Scale scale) {
        this(center, radius, scale, null, null, null, null);
    }

    public ImageTickRing(Point center, int radius, Scale scale, double interval) {
        this(center, radius, scale, interval, null, null, null, null);
    }

    public ImageTickRing(Point center, int radius, Scale scale, double interval, boolean itemsRotated) {
        this(center, radius, scale, interval, itemsRotated, null, null, null, null);
    }

    public ImageTickRing(Point center, int radius, Scale scale, Image minorImage, Dimension minorDimension, Image majorImage, Dimension majorDimension) {
        this(center, radius, scale, 1.0, true, minorImage, minorDimension, majorImage, majorDimension);
    }

    public ImageTickRing(Point center, int radius, Scale scale, double interval, Image minorImage, Dimension minorDimension, Image majorImage, Dimension majorDimension) {
        this(center, radius, scale, interval, true, minorImage, minorDimension, majorImage, majorDimension);
    }

    public ImageTickRing(Point center, int radius, Scale scale, double interval, boolean itemsRotated, Image minorImage, Dimension minorDimension, Image majorImage, Dimension majorDimension) {
        super(center, radius, scale, interval, itemsRotated);
        setMajorImage(majorImage, majorDimension);
        setMinorImage(minorImage, minorDimension);
    }
    
    public final void setMajorImage(Image image, Dimension dimension) {
        if (image != null && dimension == null) {
            throw new IllegalArgumentException("Major image requires dimension");
        }
        majorImage = image;
        majorDimension = dimension;
    }

    public final void setMinorImage(Image image, Dimension dimension) {
        if (image != null && dimension == null) {
            throw new IllegalArgumentException("Minor image requires dimension");
        }
        minorImage = image;
        minorDimension = dimension;
    }

    @Override
    public void paintMajorTick(Graphics2D g2d) {
        if (majorImage != null) {
            paintTick(g2d, majorImage, majorDimension);
        }
    }

    @Override
    public void paintMinorTick(Graphics2D g2d) {
        if (minorImage != null) {
            paintTick(g2d, minorImage, minorDimension);
        }
    }
    
    private void paintTick(Graphics2D g2d, Image image, Dimension dimension) {
        g2d.drawImage(image, dimension.width / -2, dimension.height / 2, null);
    }

    private Image minorImage;
    private Image majorImage;
    private Dimension minorDimension;
    private Dimension majorDimension;
    
}
