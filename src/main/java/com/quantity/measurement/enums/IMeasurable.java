package com.quantity.measurement.enums;

public interface IMeasurable {

    // ================================================================
    // UC14: Functional interface — separates arithmetic capability
    // from the core conversion contract  (Interface Segregation)
    // ================================================================
    @FunctionalInterface
    interface SupportsArithmetic {
        boolean isSupported();
    }

    // ================================================================
    // Abstract methods — implemented by every unit enum
    // ================================================================
    double getConversionFactor();

    double convertToBaseUnit(double value);

    double convertFromBaseUnit(double value);

    String getUnitName();

    // ================================================================
    // UC14: Default methods — LengthUnit / WeightUnit / VolumeUnit
    // inherit these automatically with ZERO code changes needed.
    // TemperatureUnit overrides both.
    // ================================================================

    /**
     * Returns true if this unit supports arithmetic operations.
     * Default: true  — inherited by Length, Weight, Volume.
     * TemperatureUnit overrides to return false.
     */
    default boolean supportsArithmetic() {
        return true;
    }

    /**
     * Called by Quantity before every arithmetic operation.
     * Default: no-op — Length / Weight / Volume pass silently.
     * TemperatureUnit overrides to throw UnsupportedOperationException.
     *
     * @param operation  name of the operation ("ADD", "SUBTRACT", "DIVIDE")
     */
    default void validateOperationSupport(String operation) {
        // no-op for arithmetic-supporting units
    }
}