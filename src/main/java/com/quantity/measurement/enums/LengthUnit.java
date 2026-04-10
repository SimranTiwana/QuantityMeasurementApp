/*package com.quantity.measurement.enums;

public enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    // UC8: convert to base (feet)
    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    // UC8: convert from base (feet)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactor;
    }
}
    */
   package com.quantity.measurement.enums;

public enum LengthUnit {

    FEET(1.0),
    INCH(1.0 / 12),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value * toFeetFactor;
    }

    public double convertFromBaseUnit(double value) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        return value / toFeetFactor;
    }
}