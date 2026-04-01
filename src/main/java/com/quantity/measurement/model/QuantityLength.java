package com.quantity.measurement.model;

import com.quantity.measurement.enums.LengthUnit;

public class QuantityLength {
    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if(unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }
        this.value = value;
        this.unit = unit;

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;
        
    double thisValue = this.unit.toBase(this.value);
    double otherValue = other.unit.toBase(other.value);

    return Math.abs(thisValue - otherValue) < 0.0001;
    }
}
