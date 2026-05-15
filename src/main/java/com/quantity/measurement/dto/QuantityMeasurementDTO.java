package com.quantity.measurement.dto;

import com.quantity.measurement.model.QuantityMeasurementEntity;

import lombok.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class QuantityMeasurementDTO {

    private Long id;

    @NotNull
    @PositiveOrZero
    private double operand1Value;

    @NotBlank
    private String operand1Unit;

    @NotNull
    @PositiveOrZero
    private double operand2Value;

    @NotBlank
    private String operand2Unit;

    @NotBlank
    private String measurementType;

    @NotBlank
    private String operationType;

    @NotNull
    private double resultValue;

    @NotBlank
    private String resultUnit;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static QuantityMeasurementDTO fromEntity(
            QuantityMeasurementEntity entity) {

        return QuantityMeasurementDTO.builder()

                .id(entity.getId())

                .operand1Value(entity.getOperand1Value())
                .operand1Unit(entity.getOperand1Unit())

                .operand2Value(entity.getOperand2Value())
                .operand2Unit(entity.getOperand2Unit())

                .measurementType(entity.getMeasurementType())
                .operationType(entity.getOperationType())

                .resultValue(entity.getResultValue())
                .resultUnit(entity.getResultUnit())

                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())

                .build();
    }
}