package bka.swing.clock;

import java.awt.*;


public class ImageNeedle extends Needle {

    public ImageNeedle() {
    }
    
    
    public ImageNeedle(Image image, Point turningPoint, Point turningOffset) {
        setImage(image);
        setTurningPoint(turningPoint);
        setTurningOffset(turningOffset);
    }
    
    
    public ImageNeedle(Image image, Point turningPoint, Point turningOffset, Scale scale) {
        setImage(image);
        setTurningPoint(turningPoint);
        setTurningOffset(turningOffset);
        setScale(scale);
    }
    
    
    public void setImage(Image image) {
        this.image = image;
    }

    
    public void setTurningOffset(Point point) {
        turningOffset = point;
    }
    
    
    public void paintNeedle(Graphics2D g2d) {
        g2d.drawImage(image, turningPoint.x-turningOffset.x, turningPoint.y-turningOffset.y, null);
    }

    
    private Image image;
    private Point turningOffset;

}
