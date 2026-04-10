/*package com.quantity.measurement.model;

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

    // ================= UC8 NEW METHOD =================
    public QuantityLength convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit null");
        }

        double base = unit.convertToBaseUnit(value);
        double result = targetUnit.convertFromBaseUnit(base);

        result = Math.round(result * 100.0) / 100.0;

        return new QuantityLength(result, targetUnit);
    }

    // ================= UC6 =================
    public QuantityLength add(QuantityLength other) {
        return add(this, other, this.unit);
    }

    // ================= UC7 =================
    public static QuantityLength add(QuantityLength q1,
                                     QuantityLength q2,
                                     LengthUnit targetUnit) {

        if (q1 == null || q2 == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double base1 = q1.unit.convertToBaseUnit(q1.value);
        double base2 = q2.unit.convertToBaseUnit(q2.value);

        double sum = base1 + base2;

        double result = targetUnit.convertFromBaseUnit(sum);
        result = Math.round(result * 100.0) / 100.0;

        return new QuantityLength(result, targetUnit);
    }

    // ================= EQUALS =================
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof QuantityLength)) return false;

        QuantityLength other = (QuantityLength) obj;

        double base1 = this.unit.convertToBaseUnit(this.value);
        double base2 = other.unit.convertToBaseUnit(other.value);

        return Math.abs(base1 - base2) < EPSILON;
    }
        
}
*/

package com.quantity.measurement.model;

import com.quantity.measurement.enums.LengthUnit;

public class QuantityLength {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final LengthUnit unit;

    // Constructor
    public QuantityLength(double value, LengthUnit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit should not be null");
        }

        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }

        this.value = value;
        this.unit = unit;
    }

    // Getters
    public double getValue() {
        return value;
    }

    public LengthUnit getUnit() {
        return unit;
    }

    // ========================
    // ADD with target unit (UC7)
    // ========================
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {

        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Other quantity and target unit must not be null");
        }

        if (!Double.isFinite(other.value)) {
            throw new IllegalArgumentException("Invalid value in other quantity");
        }

        double thisInFeet = this.unit.convertToBaseUnit(this.value);
        double otherInFeet = other.unit.convertToBaseUnit(other.value);

        double sumInFeet = thisInFeet + otherInFeet;

        double result = targetUnit.convertFromBaseUnit(sumInFeet);

        return new QuantityLength(result, targetUnit);
    }

    // ========================
    // ADD (UC6)
    // ========================
    public QuantityLength add(QuantityLength other) {
        return add(other, this.unit);
    }

    // ========================
    // CONVERT (UC5 / UC8)
    // Original -> Feet(Base) -> TargetUnit
    // ========================
    public QuantityLength toConvert(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("target Unit should not null");
        }

        double thisInFeet = unit.convertToBaseUnit(this.value);
        double targetValue = targetUnit.convertFromBaseUnit(thisInFeet);

        return new QuantityLength(targetValue, targetUnit);
    }

    // ========================
    // EQUALS (UC4)
    // ========================
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;

        double thisInFeet = this.unit.convertToBaseUnit(this.value);
        double otherInFeet = other.unit.convertToBaseUnit(other.value);

        return Math.abs(thisInFeet - otherInFeet) < EPSILON;
    }
}