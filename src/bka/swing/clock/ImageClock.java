package bka.swing.clock;

import java.awt.*;
import java.awt.image.*;
import java.util.*;


public class ImageClock extends ClockJPanel {
 
    public ImageClock() {
    }

    
    public ImageClock(Image faceImage) {
        setFaceImage(faceImage);
    }

    
    public ImageClock(Image faceImage, double xScale, double yScale) {
        setFaceImage(faceImage);
        setXScale(xScale);
        setYScale(yScale);
    }

    
    public void setFaceImage(Image image) {
        this.image = image;
    }
    
    
    public void setXScale(double d) {
        xScale = d;
    }
    
    
    public void setYScale(double d)  {
        yScale = d;
    }
    
    
    public void paintFace(Graphics2D g2d) {
        g2d.scale(xScale, yScale);
        g2d.drawImage(image, 0, 0, this);
    }

    

    private double xScale = 1.0;
    private double yScale = 1.0;
    private Image image;    
    
}
