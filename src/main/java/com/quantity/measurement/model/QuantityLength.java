package com.quantity.measurement.model;

import com.quantity.measurement.enums.LengthUnit;

public class QuantityLength {

    private final double value;
    private final LengthUnit unit;
    private static final double EPSILON = 1e-6;

    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null || !Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid input");
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

    // ================= UC6 METHOD =================
    // Default → result in first operand unit
    public QuantityLength add(QuantityLength other) {
        return add(this, other, this.unit);
    }

    // ================= UC7 METHOD =================
    // Explicit target unit
    public static QuantityLength add(QuantityLength q1,
                                     QuantityLength q2,
                                     LengthUnit targetUnit) {

        if (q1 == null || q2 == null || targetUnit == null) {
            throw new IllegalArgumentException("Null input");
        }

        if (!Double.isFinite(q1.value) || !Double.isFinite(q2.value)) {
            throw new IllegalArgumentException("Invalid number");
        }

        return addHelper(q1, q2, targetUnit);
    }

    // ================= DRY PRIVATE METHOD =================
    private static QuantityLength addHelper(QuantityLength q1,
                                           QuantityLength q2,
                                           LengthUnit targetUnit) {

        // Convert to base (feet)
        double base1 = q1.unit.toBase(q1.value);
        double base2 = q2.unit.toBase(q2.value);

        // Add
        double sumBase = base1 + base2;

        // Convert to target
        double result = targetUnit.fromBase(sumBase);

        // Round to 2 decimal places
        result = Math.round(result * 100.0) / 100.0;

        return new QuantityLength(result, targetUnit);
    }

    // ================= EQUALS =================
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;

        QuantityLength other = (QuantityLength) obj;

        double base1 = this.unit.toBase(this.value);
        double base2 = other.unit.toBase(other.value);

        return Math.abs(base1 - base2) < EPSILON;
    }
}