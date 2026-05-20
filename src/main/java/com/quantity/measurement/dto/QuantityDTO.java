package com.quantity.measurement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// =============================================================
// UC17 Step 5B – QuantityDTO with validation
// =============================================================
// Fixes applied over the original plain POJO:
//   1. Added @Data, @NoArgsConstructor, @AllArgsConstructor
//      (Lombok – eliminates manual getters / setters)
//   2. Added @NotNull on value
//   3. Added @NotEmpty on unit and measurementType
// =============================================================

@Data
@NoArgsConstructor
@AllArgsConstructor

public class QuantityDTO {

    // =========================================================
    // VALUE
    // =========================================================

    @NotNull(message = "Value cannot be null")

    private Double value;

    // =========================================================
    // UNIT
    // =========================================================

    @NotEmpty(message = "Unit cannot be empty")

    private String unit;

    // =========================================================
    // MEASUREMENT TYPE
    // =========================================================

    @NotEmpty(message = "Measurement type cannot be empty")

    private String measurementType;

    // =========================================================
    // ERROR TRACKING (not validated – set internally)
    // =========================================================

    private boolean error;

    private String errorMessage;

    // =========================================================
    // CONVENIENCE CONSTRUCTOR (no error fields)
    // =========================================================

    public QuantityDTO(
            double value,
            String unit,
            String measurementType
    ) {
        this.value           = value;
        this.unit            = unit;
        this.measurementType = measurementType;
    }
}