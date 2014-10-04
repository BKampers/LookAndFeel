package bka.swing.animation;


import java.awt.*;
import javax.swing.*;


abstract class AbstractAnimation extends JPanel {

    
    public void start() {
        if (timer == null) {
            timer = new java.util.Timer();
            timer.schedule(new Task(), 100, 100);
        }
    }


    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        repaint();
    }


    public void paint(Graphics g) {
        super.paint(g);
        Dimension size = getSize();
        if (timer != null) { // That is animation is running
            if (currentImage != null) {
                g.drawImage(currentImage, 0, 0, size.width, size.height, null);
            }
        }
    }
    
    
    protected AbstractAnimation() {
        setOpaque(false);
    }
    
    
    protected abstract Image next();

    
    private class Task extends java.util.TimerTask {

        public void run() {
            currentImage = next();
            repaint();
        }

    }

    
    private Image currentImage = null;
    private java.util.Timer timer = null;

}
