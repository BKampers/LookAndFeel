package bka.swing.animation;


import java.awt.*;


public class ArrayAnimation extends AbstractAnimation {
    
    public ArrayAnimation(Image[] images) {
        super();
        this.images = images;
    }

    
    protected Image next() {
        if (index == 0)
        {
            increment = 1;
        }
        else if (index == images.length - 1) {
            increment = -1;
        }
        index += increment;
        return images[index];
    }
    
    
    Image[] images;
    
    int index = 0;
    int increment = 1;
    
}
