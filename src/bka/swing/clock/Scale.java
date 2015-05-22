package bka.swing.clock;

public class Scale {
    
    
    public Scale() {
    }
    
    
    public Scale(double minAngle, double maxAngle) {
        setAngleRange(minAngle, maxAngle);
    }

    
    public Scale(double minValue, double maxValue, double minAngle, double maxAngle) {
        setValueRange(minValue, maxValue);
        setAngleRange(minAngle, maxAngle);
    }

    
    public final void setValueRange(double min, double max) {
        minValue = min;
        maxValue = max;
    }
    
    
    public final void setAngleRange(double min, double max) {
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
    

    private double minAngle = 0.0;
    private double maxAngle = 2 * Math.PI;
    private double minValue = 0.0;
    private double maxValue = 1.0;

}
