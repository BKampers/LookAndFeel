package bka.swing.animation;


import java.awt.*;
import java.awt.image.*;


public class OverlayAnimation extends AbstractAnimation {
    
    
    public enum Type { MOVE_ACROSS, MOVE_IN, MOVE_OUT }
    
    
    public OverlayAnimation(Image base, Image overlay) {
        super();
        setBase(base);
        setOverlay(overlay);
        setType(type.MOVE_ACROSS);
    }

    
    public final void setBase(Image base) {
        this.base = base;
        baseWidth = base.getWidth(null);
        baseHeight = base.getHeight(null);
    }

    
    public final void setOverlay(Image overlay) {
        this.overlay = overlay;
        overlayWidth = overlay.getWidth(null);
        overlayHeight = overlay.getHeight(null);
        point.y = (baseHeight - overlayHeight) / 2;
    }
    
    
    public final void setType(Type type) {
        this.type = type;
        switch (type) {
            case MOVE_IN:
                size = new Dimension(baseWidth, baseHeight);
                point.x = baseWidth / 2 + overlayWidth;
                direction = -1;
                break;
            case MOVE_OUT:
                size = new Dimension(baseWidth, baseHeight);
                point.x = baseWidth / 2;
                direction = 1;
                break;
            case  MOVE_ACROSS: 
            default:
                size = new Dimension(baseWidth, baseHeight);
                point.x = 0;
                direction = 1;
                break;
        }
    }
    
    
    public Dimension getSize() {
        if (size != null) {
            return size;
        }
        else {
            return super.getSize();
        }
    }
    
    
    protected Image next() {
        BufferedImage image = new BufferedImage(baseWidth, baseHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.drawImage(base, 0, 0, null);
        Point position = getOverlayPosition();
        g.drawImage(overlay, position.x, position.y, null);
        return image;
    }
    
    
    private Point getOverlayPosition() {
        switch (type) {
            case MOVE_IN:
                if (point.x == baseWidth / 2) {
                    point.x = baseWidth / 2 + overlayWidth;
                }                
                break;
            case MOVE_OUT:
                if (point.x == baseWidth / 2 + overlayWidth) {
                    point.x = baseWidth / 2;
                }
                break;
            case  MOVE_ACROSS: 
            default:
                if (point.x == 0) {
                    direction = 1;
                }
                if (point.x == baseWidth - overlayWidth) {
                    direction = -1;
                }
                break;
        }
        point.x += direction;
        return point;
    }
    
    
    private Image base;
    private Image overlay;
    
    private Type type = Type.MOVE_ACROSS;
    
    private int baseWidth = 0;
    private int baseHeight = 0;
    private int overlayWidth = 0;
    private int overlayHeight = 0;

    private Point point = new Point(0, 0);
    private int direction = 1;
    
    private Dimension size = null;
        
}
