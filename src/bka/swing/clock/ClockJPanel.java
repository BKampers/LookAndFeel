package bka.swing.clock;


import java.awt.*;
import java.util.*;
import javax.swing.*;


public abstract class ClockJPanel extends JPanel {
    
    
    public ClockJPanel() {
        this.setOpaque(false);
    }
    
    
    public void setNeedles(LinkedList<Needle> needles) {
        this.needles = needles;
    }
    
    
    public LinkedList<Needle> getNeedles() {
        return needles;
    }
    
    
    public void addNeedle(Needle needle) {
        needles.add(needle);
    }
    
    
    public void setRings(LinkedList<Ring> rings) {
        this.rings = rings;
    }
    
    
    public LinkedList<Ring> getRings() {
        return rings;
    }
    
    
    public void addRing(Ring ring) {
        rings.add(ring);
    }
    
    
    public void addText(Text text) {
        texts.add(text);
    }
    
    
    public void paint(Graphics g) {
        paintFace((Graphics2D) g);
        Iterator<Text> iText = texts.iterator();
        while (iText.hasNext()) {
            iText.next().paint((Graphics2D) g);
        }
        Iterator<Ring> iRing = rings.iterator();
        while (iRing.hasNext()) {
            iRing.next().paint((Graphics2D) g);
        }
        Iterator<Needle> iNeedle = needles.iterator();
        while (iNeedle.hasNext()) {
            iNeedle.next().paint((Graphics2D) g);
        }
    }
    
    
    public abstract void paintFace(Graphics2D g2d);

    
    private LinkedList<Needle> needles = new LinkedList<Needle>();
    private LinkedList<Ring> rings = new LinkedList<Ring>();
    private LinkedList<Text> texts = new LinkedList<Text>();
    
}
