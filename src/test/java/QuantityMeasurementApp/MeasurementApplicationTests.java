package QuantityMeasurementApp;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.quantity.measurement.enums.LengthUnit;
import com.quantity.measurement.model.QuantityLength;

class MeasurementApplicationTests {
/* 
    @Test
	void testFeetEquality_SameValue(){
		MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(89.6);
		MeasurementApplication.Feet f2 = new MeasurementApplication.Feet(89.6);

		assertEquals(f1, f2);
	}
	@Test
	void testFeetEquality_DifferValue(){
		MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(89.6);
		MeasurementApplication.Feet f2 = new MeasurementApplication.Feet(34.6); 
		
		assertNotEquals(f1,f2);
	}
	@Test
	void testFeetNullable_NullValue(){
		MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(55.0);
		
		assertFalse(f1.equals(null));
	}
	@Test
	void testFeetEquality_ClassComparison(){
		MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(89.6);
		
		assertFalse(f1.equals("Some String"));
	}
	@Test
	void testFeetEquality_SameReference(){
		MeasurementApplication.Feet f1 = new MeasurementApplication.Feet(89.6);
		
		assertTrue(f1.equals(f1));
	}
	@Test
void testInchEquality_SameValue(){
    MeasurementApplication.Inch i1 = new MeasurementApplication.Inch(12.5);
    MeasurementApplication.Inch i2 = new MeasurementApplication.Inch(12.5);

    assertEquals(i1, i2);
}

@Test
void testInchEquality_DifferValue(){
    MeasurementApplication.Inch i1 = new MeasurementApplication.Inch(12.5);
    MeasurementApplication.Inch i2 = new MeasurementApplication.Inch(10.0); 
    
    assertNotEquals(i1, i2);
}

@Test
void testInchNullable_NullValue(){
    MeasurementApplication.Inch i1 = new MeasurementApplication.Inch(20.0);
    
    assertFalse(i1.equals(null));
}

@Test
void testInchEquality_ClassComparison(){
    MeasurementApplication.Inch i1 = new MeasurementApplication.Inch(15.0);
    
    assertFalse(i1.equals("Some String"));
}

@Test
void testInchEquality_SameReference(){
    MeasurementApplication.Inch i1 = new MeasurementApplication.Inch(18.0);
    
    assertTrue(i1.equals(i1));
}
*/

    @Test
    void testLengthEquality_SameFeetValue() {
        QuantityLength l1 = new QuantityLength(10.0, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(10.0, LengthUnit.FEET);

        assertEquals(l1, l2);
    }

    @Test
    void testLengthEquality_DifferentFeetValue() {
        QuantityLength l1 = new QuantityLength(10.0, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(5.0, LengthUnit.FEET);

        assertNotEquals(l1, l2);
    }

    @Test
    void testLengthEquality_NullComparison() {
        QuantityLength l1 = new QuantityLength(10.0, LengthUnit.FEET);

        assertFalse(l1.equals(null));
    }

    @Test
    void testLengthEquality_ClassComparison() {
        QuantityLength l1 = new QuantityLength(10.0, LengthUnit.FEET);

        assertFalse(l1.equals("Some String"));
    }

    @Test
    void testLengthEquality_SameReference() {
        QuantityLength l1 = new QuantityLength(10.0, LengthUnit.FEET);

        assertTrue(l1.equals(l1));
    }

    @Test
    void testLengthEquality_FeetAndInch_Equal() {
        QuantityLength feet = new QuantityLength(1.0, LengthUnit.FEET);
        QuantityLength inch = new QuantityLength(12.0, LengthUnit.INCH);

        assertEquals(feet, inch);
    }

    @Test
    void testLengthEquality_FeetAndInch_NotEqual() {
        QuantityLength feet = new QuantityLength(2.0, LengthUnit.FEET);
        QuantityLength inch = new QuantityLength(12.0, LengthUnit.INCH);

        assertNotEquals(feet, inch);
    }

    @Test
    void testLengthEquality_ZeroValues() {
        QuantityLength l1 = new QuantityLength(0.0, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(0.0, LengthUnit.INCH);

        assertEquals(l1, l2);
    }

    @Test
    void testLengthEquality_NegativeValues() {
        QuantityLength l1 = new QuantityLength(-1.0, LengthUnit.FEET);
        QuantityLength l2 = new QuantityLength(-12.0, LengthUnit.INCH);

        assertEquals(l1, l2);
    }

    @Test
    void testConstructor_NullUnit_ShouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            new QuantityLength(10.0, null);
        });
    }
}

