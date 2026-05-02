package com.quantity.measurement.model;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import com.quantity.measurement.enums.IMeasurable;

public class Quantity<U extends IMeasurable> {

    private static final double EPSILON = 1e-6;

    private final double value;
    private final U unit;

    // ================= CONSTRUCTOR =================
    public Quantity(double value, U unit) {
        if (unit == null)
            throw new NullPointerException("Unit shouldn't be null");

        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    // ================= CONVERSION =================

    /**
     * UC1–UC13: linear conversion for Length / Weight / Volume.
     * Kept unchanged for full backward compatibility.
     */
    public Quantity<U> toConvert(U targetUnit) {
        if (targetUnit == null)
            throw new NullPointerException("Target unit cannot be null");

        double baseValue = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);
        return new Quantity<>(converted, targetUnit);
    }

    /**
     * UC14: Universal conversion — works for ALL unit types including
     * non-linear Temperature conversions (lambda offset formulas).
     * For linear units the behaviour is identical to toConvert().
     */
    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null)
            throw new NullPointerException("Target unit cannot be null");

        double baseValue = unit.convertToBaseUnit(value);
        double converted = targetUnit.convertFromBaseUnit(baseValue);
        return new Quantity<>(converted, targetUnit);
    }

    // ================= OPERATIONS ENUM (UC13) =================
    private enum ArithmeticOperation {
        ADD((a, b) -> a + b),

        SUBTRACT((a, b) -> a - b),

        DIVIDE((a, b) -> {
            if (Math.abs(b) < EPSILON)
                throw new ArithmeticException("Division by zero");
            return a / b;
        });

        private final DoubleBinaryOperator op;

        ArithmeticOperation(DoubleBinaryOperator op) {
            this.op = op;
        }

        double apply(double a, double b) {
            return op.applyAsDouble(a, b);
        }
    }

    // ================= GETTERS =================
    public double getValue() { return value; }
    public U      getUnit()  { return unit;  }

    // ================= INTERNAL HELPERS =================
    private double base(U u, double v) {
        return u.convertToBaseUnit(v);
    }

    private double operate(Quantity<U> other, ArithmeticOperation op) {
        double a = base(this.unit, this.value);
        double b = base(other.unit, other.value);
        return op.apply(a, b);
    }

    /**
     * Validates that 'other' is non-null and same category as this.
     * NPE for null; IAE for cross-category mismatch.
     */
    private Quantity<U> validateAndCast(Quantity<?> other) {
        if (other == null)
            throw new NullPointerException("Quantity must not be null");

        if (!this.unit.getClass().equals(other.getUnit().getClass()))
            throw new IllegalArgumentException("Different measurement types");

        @SuppressWarnings("unchecked")
        Quantity<U> compatible = (Quantity<U>) other;
        return compatible;
    }

    // ================= PUBLIC OPERATIONS =================
    //
    // ORDERING RULE (UC14):
    //   1. Null check FIRST         -> NullPointerException
    //                                  (even for Temperature, null always = NPE)
    //   2. Operation support check  -> UnsupportedOperationException
    //                                  (Temperature blocks non-null operands here)
    //   3. Category / cast check    -> IllegalArgumentException

    // -------- ADD --------
    public Quantity<U> add(Quantity<?> other) {
        if (other == null)
            throw new NullPointerException("Quantity must not be null");
        this.unit.validateOperationSupport("ADD");
        return add(other, this.unit);
    }

    public Quantity<U> add(Quantity<?> other, U targetUnit) {
        if (other == null)
            throw new NullPointerException("Quantity must not be null");
        if (targetUnit == null)
            throw new NullPointerException("Target unit must not be null");
        this.unit.validateOperationSupport("ADD");

        Quantity<U> compatible = validateAndCast(other);
        double resultBase = operate(compatible, ArithmeticOperation.ADD);
        double converted  = targetUnit.convertFromBaseUnit(resultBase);
        return new Quantity<>(converted, targetUnit);
    }

    // -------- SUBTRACT --------
    public Quantity<U> subtract(Quantity<?> other) {
        if (other == null)
            throw new NullPointerException("Quantity must not be null");
        this.unit.validateOperationSupport("SUBTRACT");
        return subtractInternal(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<?> other, U targetUnit) {
        if (other == null)
            throw new NullPointerException("Quantity must not be null");
        if (targetUnit == null)
            throw new IllegalArgumentException("Target unit must not be null");
        this.unit.validateOperationSupport("SUBTRACT");
        return subtractInternal(other, targetUnit);
    }

    private Quantity<U> subtractInternal(Quantity<?> other, U targetUnit) {
        Quantity<U> compatible = validateAndCast(other);
        double resultBase = operate(compatible, ArithmeticOperation.SUBTRACT);
        double converted  = targetUnit.convertFromBaseUnit(resultBase);
        return new Quantity<>(converted, targetUnit);
    }

    // -------- DIVIDE --------
    public double divide(Quantity<?> other) {
        if (other == null)
            throw new NullPointerException("Quantity must not be null");
        this.unit.validateOperationSupport("DIVIDE");
        return divideInternal(other);
    }

    private double divideInternal(Quantity<?> other) {
        Quantity<U> compatible = validateAndCast(other);
        return operate(compatible, ArithmeticOperation.DIVIDE);
    }

    // ================= EQUALS =================
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;

        if (!this.unit.getClass().equals(other.unit.getClass())) return false;

        double a = this.unit.convertToBaseUnit(this.value);
        double b = other.unit.convertToBaseUnit(other.value);
        return Math.abs(a - b) < EPSILON;
    }

    // ================= HASHCODE =================
    @Override
    public int hashCode() {
        double baseValue = unit.convertToBaseUnit(value);
        return Objects.hash(Math.round(baseValue * 1e6));
    }
}