package com.quantity.measurement.model;

import com.quantity.measurement.enums.LengthUnit;

public class QuantityLength {

    private final double value;
    private final LengthUnit unit;
    private final double EPSILON = 1e-6;

    // Constructor
    public QuantityLength(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be finite");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    // STATIC CONVERSION METHOD 
    public static double convert(double value, LengthUnit source, LengthUnit target) {

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid Numeric Value");
        }

        if (source == null || target == null) {
            throw new IllegalArgumentException("Units shouldn't be empty!!!");
        }

        // Step 1: Convert to base (inches or base unit defined in enum)
        double baseValue = source.toBase(value);

        // Step 2: Convert base → target
        return baseValue / target.getConversionFactor();
    }

   
    public QuantityLength convertTo(LengthUnit targetUnit) {
        double convertedValue = convert(this.value, this.unit, targetUnit);
        return new QuantityLength(convertedValue, targetUnit);
    }

    // EQUALITY CHECK 
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;

        double thisBase = this.unit.toBase(this.value);
        double otherBase = other.unit.toBase(other.value);

        // epsilon comparison for precision
        return Math.abs(thisBase - otherBase) < EPSILON;
    }

    public QuantityLength add(QuantityLength other) {

    // Step 1: Validation
    if (other == null) {
        throw new IllegalArgumentException("Other quantity cannot be null");
    }

    if (this.unit == null || other.unit == null) {
        throw new IllegalArgumentException("Unit cannot be null");
    }

    if (!Double.isFinite(this.value) || !Double.isFinite(other.value)) {
        throw new IllegalArgumentException("Invalid numeric value");
    }

    // Step 2: Convert both to base (feet)
    double baseValue1 = this.unit.toBase(this.value);
    double baseValue2 = other.unit.toBase(other.value);

    // Step 3: Add
    double sum = baseValue1 + baseValue2;

    // Step 4: Convert back to FIRST UNIT (IMPORTANT)
    double result = this.unit.fromBase(sum);

    // Step 5: Return new object (immutability)
    return new QuantityLength(result, this.unit);
}
 public static QuantityLength add(QuantityLength q1, QuantityLength q2) {
        if (q1 == null) {
            throw new IllegalArgumentException("First quantity cannot be null");
        }
        return q1.add(q2);
    }

    @Override
    public String toString() {
        return "QuantityLength{" +
                "value=" + value +
                ", unit=" + unit +
                '}';
    }
}

