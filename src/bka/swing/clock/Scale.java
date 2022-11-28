package bka.swing.clock;

public final class Scale {

    public Scale(double minValue, double maxValue) {
        this(minValue, maxValue, 0.0, 1.0);
    }

    public Scale(double minValue, double maxValue, double minAngle, double maxAngle) {
        setValueRange(minValue, maxValue);
        setAngleRange(minAngle, maxAngle);
    }
    
    public void setValueRange(double min, double max) {
        minValue = min;
        maxValue = max;
    }

    public void setAngleRange(double min, double max) {
        minAngle = min * 2 * Math.PI;
        maxAngle = max * 2 * Math.PI;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double angle(double value) {
        return minAngle + (maxAngle - minAngle) * (value - minValue) / (maxValue - minValue);
    }

    private double minValue;
    private double maxValue;
    private double minAngle;
    private double maxAngle;

}
