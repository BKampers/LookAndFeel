package bka.swing.clock;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

public class ArcRing extends Ring {

    public static class Arc {

        public Arc(double start, double end, Paint paint, Stroke stroke) {
            this.start = start;
            this.end = end;
            setPaint(paint);
            setStroke(stroke);
        }

        public void setStart(double start) {
            this.start = start;
        }

        public void setEnd(double end) {
            this.end = end;
        }

        public final void setPaint(Paint paint) {
            this.paint = Objects.requireNonNull(paint);
        }

        public final void setStroke(Stroke stroke) {
            this.stroke = Objects.requireNonNull(stroke);
        }

        @Override
        public String toString() {
            return "Arc " + start + " .. " + end;
        }

        private double start;
        private double end;
        private Paint paint;
        private Stroke stroke;

    }

    public ArcRing(Point center, int radius, Scale scale, Collection<Arc> arcs) {
        super(center, radius, scale);
        this.arcs = arcs;
    }

    @Override
    public void paint(Graphics2D g2d) {
        Stroke restoreStroke = g2d.getStroke();
        Scale scale = getScale();
        Point center = getCenter();
        int radius = getRadius();
        int x = center.x - radius;
        int y = center.y - radius;
        int size = radius * 2;
        for (Arc arc : arcs) {
            g2d.setPaint(arc.paint);
            g2d.setStroke(arc.stroke);
            double start = Math.toDegrees(scale.angle(arc.start));
            double end = Math.toDegrees(scale.angle(arc.end));
            g2d.draw(new Arc2D.Double(x, y,size,size,angleStart(start), angleExtent(start, end), Arc2D.OPEN));
        }
        g2d.setStroke(restoreStroke);
    }

    private static double angleStart(double start) {
        return 90d - start;
    }

    private static double angleExtent(double start, double end) {
        double arc = start - end;
        return (arc <= 0.0) ? arc : -360d + arc;
    }

    private final Collection<Arc> arcs;

}
