package com.quantity.measurement;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class    MeasurementApplicationTests {

    // ================= UC1: EQUALITY =================

    @Test
    void testZeroEquality() {
        assertEquals(new QuantityLength(0, LengthUnit.FEET),
                new QuantityLength(0, LengthUnit.FEET));
    }

    @Test
    void testSameValues() {
        assertEquals(new QuantityLength(5, LengthUnit.FEET),
                new QuantityLength(5, LengthUnit.FEET));
    }

    @Test
    void testDifferentValues() {
        assertNotEquals(new QuantityLength(5, LengthUnit.FEET),
                new QuantityLength(6, LengthUnit.FEET));
    }

    @Test
    void testFeetToInchEquality() {
        assertEquals(new QuantityLength(1, LengthUnit.FEET),
                new QuantityLength(12, LengthUnit.INCH));
    }

    @Test
    void testFeetToYardEquality() {
        assertEquals(new QuantityLength(3, LengthUnit.FEET),
                new QuantityLength(1, LengthUnit.YARDS));
    }

    @Test
    void testInchToCmEquality() {
        assertEquals(new QuantityLength(1, LengthUnit.INCH),
                new QuantityLength(2.54, LengthUnit.CENTIMETERS));
    }

    @Test
    void testReflexiveEquality() {
        QuantityLength q = new QuantityLength(5, LengthUnit.FEET);
        assertEquals(q, q);
    }

    @Test
    void testNullComparison() {
        QuantityLength q = new QuantityLength(5, LengthUnit.FEET);
        assertNotEquals(q, null);
    }

    // ================= UC2/UC3: CONVERSIONS =================

    @Test
    void testFeetToInchesConversion() {
        QuantityLength q = new QuantityLength(1, LengthUnit.FEET);
        assertEquals(new QuantityLength(12, LengthUnit.INCH),
                QuantityLength.add(q, new QuantityLength(0, LengthUnit.INCH), LengthUnit.INCH));
    }

    @Test
    void testFeetToYardsConversion() {
        assertEquals(new QuantityLength(1, LengthUnit.YARDS),
                new QuantityLength(3, LengthUnit.FEET));
    }

    @Test
    void testYardsToFeetConversion() {
        assertEquals(new QuantityLength(3, LengthUnit.FEET),
                new QuantityLength(1, LengthUnit.YARDS));
    }

    @Test
    void testCmToInchConversion() {
        assertEquals(new QuantityLength(1, LengthUnit.INCH),
                new QuantityLength(2.54, LengthUnit.CENTIMETERS));
    }

    @Test
    void testInchToCmConversion() {
        assertEquals(new QuantityLength(2.54, LengthUnit.CENTIMETERS),
                new QuantityLength(1, LengthUnit.INCH));
    }

    @Test
    void testFeetToCmConversion() {
        QuantityLength q = new QuantityLength(1, LengthUnit.FEET);
        assertEquals(new QuantityLength(30.48, LengthUnit.CENTIMETERS),
                QuantityLength.add(q, new QuantityLength(0, LengthUnit.CENTIMETERS), LengthUnit.CENTIMETERS));
    }

    @Test
    void testZeroConversion() {
        assertEquals(new QuantityLength(0, LengthUnit.INCH),
                new QuantityLength(0, LengthUnit.FEET));
    }

    @Test
    void testLargeConversion() {
        assertEquals(new QuantityLength(1200, LengthUnit.INCH),
                new QuantityLength(100, LengthUnit.FEET));
    }

    // ================= UC6: ADDITION =================

    @Test
    void testAdditionSameUnit() {
        QuantityLength q1 = new QuantityLength(2, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(3, LengthUnit.FEET);
        assertEquals(new QuantityLength(5, LengthUnit.FEET), q1.add(q2));
    }

    @Test
    void testAdditionDifferentUnits() {
        QuantityLength q1 = new QuantityLength(1, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12, LengthUnit.INCH);
        assertEquals(new QuantityLength(2, LengthUnit.FEET), q1.add(q2));
    }

    @Test
    void testAdditionZero() {
        QuantityLength q1 = new QuantityLength(5, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(0, LengthUnit.INCH);
        assertEquals(new QuantityLength(5, LengthUnit.FEET), q1.add(q2));
    }

    @Test
    void testAdditionNegative() {
        QuantityLength q1 = new QuantityLength(5, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(-2, LengthUnit.FEET);
        assertEquals(new QuantityLength(3, LengthUnit.FEET), q1.add(q2));
    }

    @Test
    void testAdditionCommutativityUC6() {
        QuantityLength q1 = new QuantityLength(1, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12, LengthUnit.INCH);
        assertEquals(q1.add(q2), q2.add(q1));
    }

    @Test
    void testAdditionLargeNumbers() {
        QuantityLength q1 = new QuantityLength(1000, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(2000, LengthUnit.FEET);
        assertEquals(new QuantityLength(3000, LengthUnit.FEET), q1.add(q2));
    }

    // ================= UC7 =================

    @Test
    void testTargetFeet() {
        assertEquals(new QuantityLength(2, LengthUnit.FEET),
                QuantityLength.add(new QuantityLength(1, LengthUnit.FEET),
                        new QuantityLength(12, LengthUnit.INCH),
                        LengthUnit.FEET));
    }

    @Test
    void testTargetInch() {
        assertEquals(new QuantityLength(24, LengthUnit.INCH),
                QuantityLength.add(new QuantityLength(1, LengthUnit.FEET),
                        new QuantityLength(12, LengthUnit.INCH),
                        LengthUnit.INCH));
    }

    @Test
    void testTargetYard() {
        assertEquals(new QuantityLength(0.67, LengthUnit.YARDS),
                QuantityLength.add(new QuantityLength(1, LengthUnit.FEET),
                        new QuantityLength(12, LengthUnit.INCH),
                        LengthUnit.YARDS));
    }

    @Test
    void testTargetCm() {
        assertEquals(new QuantityLength(5.08, LengthUnit.CENTIMETERS),
                QuantityLength.add(new QuantityLength(2.54, LengthUnit.CENTIMETERS),
                        new QuantityLength(1, LengthUnit.INCH),
                        LengthUnit.CENTIMETERS));
    }

    @Test
    void testTargetSameAsFirst() {
        assertEquals(new QuantityLength(2, LengthUnit.YARDS),
                QuantityLength.add(new QuantityLength(1, LengthUnit.YARDS),
                        new QuantityLength(3, LengthUnit.FEET),
                        LengthUnit.YARDS));
    }

    @Test
    void testTargetSameAsSecond() {
        assertEquals(new QuantityLength(9, LengthUnit.FEET),
                QuantityLength.add(new QuantityLength(6, LengthUnit.FEET),
                        new QuantityLength(3, LengthUnit.FEET),
                        LengthUnit.FEET));
    }

    @Test
    void testCommutativityUC7() {
        QuantityLength q1 = new QuantityLength(1, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12, LengthUnit.INCH);
        assertEquals(
                QuantityLength.add(q1, q2, LengthUnit.INCH),
                QuantityLength.add(q2, q1, LengthUnit.INCH)
        );
    }

    @Test
    void testNullTarget() {
        assertThrows(IllegalArgumentException.class,
                () -> QuantityLength.add(
                        new QuantityLength(1, LengthUnit.FEET),
                        new QuantityLength(1, LengthUnit.FEET),
                        null));
    }

    @Test
    void testNullOperands() {
        assertThrows(IllegalArgumentException.class,
                () -> QuantityLength.add(null, null, LengthUnit.FEET));
    }

    @Test
    void testNaNValue() {
        assertThrows(IllegalArgumentException.class,
                () -> QuantityLength.add(
                        new QuantityLength(Double.NaN, LengthUnit.FEET),
                        new QuantityLength(1, LengthUnit.FEET),
                        LengthUnit.FEET));
    }

    @Test
    void testInfinityValue() {
        assertThrows(IllegalArgumentException.class,
                () -> QuantityLength.add(
                        new QuantityLength(Double.POSITIVE_INFINITY, LengthUnit.FEET),
                        new QuantityLength(1, LengthUnit.FEET),
                        LengthUnit.FEET));
    }

    @Test
    void testImmutability() {
        QuantityLength q1 = new QuantityLength(1, LengthUnit.FEET);
        QuantityLength q2 = new QuantityLength(12, LengthUnit.INCH);

        QuantityLength.add(q1, q2, LengthUnit.FEET);

        assertEquals(new QuantityLength(1, LengthUnit.FEET), q1);
        assertEquals(new QuantityLength(12, LengthUnit.INCH), q2);
    }

    @Test
    void testResultUnitAlwaysTarget() {
        QuantityLength result = QuantityLength.add(
                new QuantityLength(1, LengthUnit.FEET),
                new QuantityLength(12, LengthUnit.INCH),
                LengthUnit.YARDS);

        assertEquals(LengthUnit.YARDS, result.getUnit());
    }

    @Test
    void testPrecision() {
        assertEquals(new QuantityLength(0.3, LengthUnit.FEET),
                QuantityLength.add(new QuantityLength(0.1, LengthUnit.FEET),
                        new QuantityLength(0.2, LengthUnit.FEET),
                        LengthUnit.FEET));
    }

    @Test
    void testAllUnitCombinations() {
        for (LengthUnit u1 : LengthUnit.values()) {
            for (LengthUnit u2 : LengthUnit.values()) {
                for (LengthUnit t : LengthUnit.values()) {
                    assertNotNull(
                            QuantityLength.add(
                                    new QuantityLength(1, u1),
                                    new QuantityLength(1, u2),
                                    t
                            )
                    );
                }
            }
        }
    }
}