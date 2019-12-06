/*
** Copyright © Bart Kampers
*/

package bka.swing.chart.render;


import bka.swing.chart.*;
import bka.swing.chart.custom.*;
import bka.swing.chart.geometry.*;
import java.awt.*;
import java.awt.geom.*;


public abstract class LineRenderer extends AbstractDataAreaRenderer<PixelAreaGeometry> {


    LineRenderer(LineDrawStyle drawStyle, int markerWidth, int markerHeight) {
        super(drawStyle.getAreaDrawStyle());
        this.lineDrawStyle = drawStyle;
        this.markerWidth = markerWidth;
        this.markerHeight = markerHeight;
    }

    
    @Override
    public GraphGeometry createGraphGeomerty(ChartData<Number, Number> chart) {
        GraphGeometry<AreaGeometry> dataGeometry = new GraphGeometry<>();
        for (int i = 1; i < chart.size(); ++i) {
            ChartDataElement<Number, Number> p0 = chart.get(i - 1);
            ChartDataElement<Number, Number> p1 = chart.get(i);
            add(p0, dataGeometry);
            add(p1, dataGeometry);
        }
        return dataGeometry;
    }


    @Override
    public void addPointsInWindow(Object key, ChartData<Number, Number> chartData) {
        ChartGeometry.Window window = getWindow();
        ChartDataElement<Number, Number>[] elements = new ChartDataElement[chartData.size()];
        int i = 0;
        for (ChartDataElement<Number, Number> element : chartData) {
            Number x = element.getKey();
            Number y = element.getValue();
            boolean inRange = window.inXRange(x) && window.inYRange(y);
            elements[i] = new ChartDataElement<>(x, y, ! inRange);
            i++;
        }
        ChartData<Number, Number> graphPointsInWindow = new ChartData<>();
        int start = xRangeStartIndex(elements, window);
        if (start >= 0) {
            int end = xRangeEndIndex(elements, window, start);
            if (start > 0) {
                graphPointsInWindow.add(elements[start - 1]);
            }
            for (i = start; i <= end; ++i) {
                ChartDataElement<Number, Number> element = elements[i];
                if (i == start || i == end || ! element.isOutsideWindow() || ! elements[i - 1].isOutsideWindow() || ! elements[i + 1].isOutsideWindow()) {
                    graphPointsInWindow.add(element);
                    if (! element.isOutsideWindow()) {
                        window.adjustBounds(element.getKey(), element.getValue());
                    }
                }
            }
            if (end < elements.length - 1) {
                graphPointsInWindow.add(elements[end + 1]);
            }
        }
        window.putPoints(key, graphPointsInWindow);
    }


    private static int xRangeStartIndex(ChartDataElement<Number, Number>[] elements, ChartGeometry.Window window) {
        for (int i = 0; i < elements.length; ++i) {
            if (window.inXRange(elements[i].getKey())) {
                return i;
            }
        }
        return -1;
    }


    private static int xRangeEndIndex(ChartDataElement<Number, Number>[] elements, ChartGeometry.Window window, int startIndex) {
        for (int i = startIndex; i < elements.length; ++i) {
            if (! window.inXRange(elements[i].getKey())) {
                return i - 1;
            }
        }
        return elements.length - 1;
    }


    private void add(ChartDataElement<Number, Number> element, GraphGeometry<AreaGeometry> dataGeometry) {
        add(element.getKey(), element.getValue(), dataGeometry, ! element.isOutsideWindow());
    }


    private void add(Number x, Number y, GraphGeometry<AreaGeometry> dataGeometry, boolean createArea) {
        int xPixel = getWindow().xPixel(x);
        int yPixel = getWindow().yPixel(y);
        RectangularShape area = (createArea) ? createSymbolArea(xPixel, yPixel) : null;
        dataGeometry.add(new PixelAreaGeometry(x, y, area, new Point(xPixel, yPixel)));
    }

    
    protected RectangularShape createSymbolArea(int x, int y) {
        return new Rectangle(x - markerWidth / 2, y - markerHeight / 2, markerWidth, markerHeight);
    }


    protected final LineDrawStyle lineDrawStyle;
    protected final int markerWidth;
    protected final int markerHeight;
    
}
