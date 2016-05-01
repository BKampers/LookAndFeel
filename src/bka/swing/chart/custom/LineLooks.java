/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.custom;


import bka.swing.chart.geometry.AreaGeometry;
import java.awt.*;


public class LineLooks<G extends AreaGeometry> {


    private LineLooks(Paint linePaint, Stroke lineStroke, AreaLooks<G> areaLooks) {
        this.linePaint = linePaint;
        this.lineStroke = lineStroke;
        this.areaLooks = areaLooks;
    }


    public static LineLooks create(Color linePaint, Stroke lineStroke, AreaLooks areaLooks) {
        return new LineLooks(linePaint, lineStroke, areaLooks);
    }


    public static LineLooks create(Color linePaint, Stroke lineStroke) {
        return new LineLooks(linePaint, lineStroke, null);
    }


    public static LineLooks create(Color linePaint) {
        return create(linePaint, new BasicStroke(2.0f));
    }


    public static LineLooks create(Color linePaint, AreaLooks areaLooks) {
        return create(linePaint, new BasicStroke(2.0f), areaLooks);
    }


    public Paint getLinePaint() {
        return linePaint;
    }


    public Stroke getLineStroke() {
        return lineStroke;
    }


    public AreaLooks<G> getAreaLooks() {
        return areaLooks;
    }


    public Paint getBottomAreaPaint() {
        return bottomAreaPaint;
    }


    public void setBottomAreaPaint(Paint bottomAreaPaint) {
        this.bottomAreaPaint = bottomAreaPaint;
    }


    public Paint getTopAreaPaint() {
        return topAreaPaint;
    }


    public void setTopAreaPaint(Paint topAreaPaint) {
        this.topAreaPaint = topAreaPaint;
    }


    private final Paint linePaint;
    private final Stroke lineStroke;
    private final AreaLooks<G> areaLooks;
    private Paint bottomAreaPaint;
    private Paint topAreaPaint;

}
