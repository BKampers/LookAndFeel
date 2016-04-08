/*
** Copyright © Bart Kampers
*/

package bka.swing.chart;


import java.awt.*;


public class LineLooks<S extends Shape> {//implements AreaLooks<S> {


    LineLooks(Paint linePaint, Stroke lineStroke, AreaLooks areaLooks) {
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


    public AreaLooks<S> getAreaLooks() {
        return areaLooks;
    }


    private final Paint linePaint;
    private final Stroke lineStroke;
    private final AreaLooks areaLooks;

}
