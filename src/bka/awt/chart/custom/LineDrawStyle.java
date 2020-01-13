/*
** Copyright © Bart Kampers
*/

package bka.awt.chart.custom;


import bka.awt.chart.geometry.*;
import java.awt.*;


public class LineDrawStyle<G extends AreaGeometry> {


    private LineDrawStyle(Paint linePaint, Stroke lineStroke, AreaDrawStyle<G> areaDrawStyle) {
        this.linePaint = linePaint;
        this.lineStroke = lineStroke;
        this.areaDrawStyle = areaDrawStyle;
    }


    public static LineDrawStyle create(Color linePaint, Stroke lineStroke, AreaDrawStyle areaDrawStyle) {
        return new LineDrawStyle(linePaint, lineStroke, areaDrawStyle);
    }


    public static LineDrawStyle create(Color linePaint, Stroke lineStroke) {
        return new LineDrawStyle(linePaint, lineStroke, null);
    }


    public static LineDrawStyle create(Color linePaint) {
        return create(linePaint, new BasicStroke(2.0f));
    }


    public static LineDrawStyle create(Color linePaint, AreaDrawStyle areaDrawStyle) {
        return create(linePaint, new BasicStroke(2.0f), areaDrawStyle);
    }


    public Paint getLinePaint() {
        return linePaint;
    }


    public Stroke getLineStroke() {
        return lineStroke;
    }


    public AreaDrawStyle<G> getAreaDrawStyle() {
        return areaDrawStyle;
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
    private final AreaDrawStyle<G> areaDrawStyle;
    private Paint bottomAreaPaint;
    private Paint topAreaPaint;

}
