package com.quantity.measurement.enums;

public enum LengthUnit {

    FEET(1.0),
    INCH(1.0/12),
    YARDS(36.0),         
    CENTIMETERS(0.393701); 
     private final double conversionFactor;

    LengthUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double toBase(double value) {
        return value * conversionFactor;
    }
}
