package com.quantity.measurement.enums;

import java.util.function.Function;

/**
 * UC14 — Temperature measurement unit enum.
 *
 * Base unit : CELSIUS  (all internal base-unit math goes through Celsius).
 *
 * Conversions use non-linear offset formulas stored as
 * Function<Double,Double> lambdas — one pair per enum constant.
 *
 * Arithmetic (add / subtract / divide) is NOT meaningful for absolute
 * temperature values and is blocked via validateOperationSupport().
 */
public enum TemperatureUnit implements IMeasurable {

    CELSIUS(
            celsius     -> celsius,                              // to   Celsius (identity)
            celsius     -> celsius                               // from Celsius (identity)
    ),

    FAHRENHEIT(
            fahrenheit  -> (fahrenheit - 32.0) * 5.0 / 9.0,    // to   Celsius
            celsius     -> (celsius * 9.0 / 5.0) + 32.0         // from Celsius
    ),

    KELVIN(
            kelvin      -> kelvin - 273.15,                      // to   Celsius
            celsius     -> celsius + 273.15                      // from Celsius
    );

    /** Converts a value in this unit → Celsius (base unit). */
    private final Function<Double, Double> toCelsius;

    /** Converts a Celsius (base) value → value in this unit. */
    private final Function<Double, Double> fromCelsius;

    TemperatureUnit(Function<Double, Double> toCelsius,
                    Function<Double, Double> fromCelsius) {
        this.toCelsius   = toCelsius;
        this.fromCelsius = fromCelsius;
    }

    // ================================================================
    // IMeasurable — conversion contract
    // ================================================================

    /**
     * Temperature has no single multiplication factor.
     * Returns 1.0 as a stub; real conversion is done by the lambdas.
     */
    @Override
    public double getConversionFactor() {
        return 1.0;
    }

    /** Converts value in this unit → Celsius (base). */
    @Override
    public double convertToBaseUnit(double value) {
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid temperature value: " + value);
        return toCelsius.apply(value);
    }

    /** Converts a Celsius base value → value in this unit. */
    @Override
    public double convertFromBaseUnit(double baseValue) {
        if (!Double.isFinite(baseValue))
            throw new IllegalArgumentException("Invalid temperature value: " + baseValue);
        return fromCelsius.apply(baseValue);
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    // ================================================================
    // IMeasurable — arithmetic support  (UC14 ISP)
    // ================================================================

    /**
     * Temperature does NOT support arithmetic.
     * Overrides the default true from IMeasurable.
     */
    @Override
    public boolean supportsArithmetic() {
        return false;
    }

    /**
     * Called by Quantity before any arithmetic operation.
     * Always throws UnsupportedOperationException for temperature.
     */
    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
                "Operation '" + operation + "' is not supported for TemperatureUnit. " +
                "Temperature supports equality and conversion only."
        );
    }
}