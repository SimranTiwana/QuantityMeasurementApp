package com.quantity.measurement.dto;

import com.quantity.measurement.enums.OperationType;
import com.quantity.measurement.model.QuantityMeasurementEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class QuantityMeasurementDTO {

    // =========================================
    // ID
    // =========================================

    private Long id;

    // =========================================
    // OPERAND 1
    // =========================================

    @NotNull
    @PositiveOrZero

    private Double operand1Value;

    @NotBlank

    private String operand1Unit;

    // =========================================
    // OPERAND 2
    // =========================================

    @NotNull
    @PositiveOrZero

    private Double operand2Value;

    @NotBlank

    private String operand2Unit;

    // =========================================
    // MEASUREMENT DETAILS
    // =========================================

    @NotBlank

    private String measurementType;

    @NotNull

    private OperationType operationType;

    // =========================================
    // RESULT
    // =========================================

    @NotNull

    private Double resultValue;

    @NotBlank

    private String resultUnit;

    // =========================================
    // ERROR TRACKING
    // =========================================

    private Boolean isError;

    private String errorMessage;

    // =========================================
    // TIMESTAMPS
    // =========================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // =========================================
    // ENTITY -> DTO
    // =========================================

    public static QuantityMeasurementDTO fromEntity(
            QuantityMeasurementEntity entity) {

        return QuantityMeasurementDTO.builder()

                .id(entity.getId())

                .operand1Value(
                        entity.getOperand1Value()
                )

                .operand1Unit(
                        entity.getOperand1Unit()
                )

                .operand2Value(
                        entity.getOperand2Value()
                )

                .operand2Unit(
                        entity.getOperand2Unit()
                )

                .measurementType(
                        entity.getMeasurementType()
                )

                .operationType(
                        entity.getOperationType()
                )

                .resultValue(
                        entity.getResultValue()
                )

                .resultUnit(
                        entity.getResultUnit()
                )

                .isError(
                        entity.getIsError()
                )

                .errorMessage(
                        entity.getErrorMessage()
                )

                .createdAt(
                        entity.getCreatedAt()
                )

                .updatedAt(
                        entity.getUpdatedAt()
                )

                .build();
    }

    // =========================================
    // ENTITY LIST -> DTO LIST
    // =========================================

    public static List<QuantityMeasurementDTO>
    fromEntityList(
            List<QuantityMeasurementEntity> entities) {

        return entities.stream()
                .map(
                        QuantityMeasurementDTO::fromEntity
                )
                .toList();
    }

    // =========================================
    // DTO -> ENTITY
    // =========================================

    public QuantityMeasurementEntity toEntity() {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        entity.setId(this.id);

        entity.setOperand1Value(
                this.operand1Value
        );

        entity.setOperand1Unit(
                this.operand1Unit
        );

        entity.setOperand2Value(
                this.operand2Value
        );

        entity.setOperand2Unit(
                this.operand2Unit
        );

        entity.setMeasurementType(
                this.measurementType
        );

        entity.setOperationType(
                this.operationType
        );

        entity.setResultValue(
                this.resultValue
        );

        entity.setResultUnit(
                this.resultUnit
        );

        entity.setIsError(
                this.isError
        );

        entity.setErrorMessage(
                this.errorMessage
        );

        entity.setCreatedAt(
                this.createdAt
        );

        entity.setUpdatedAt(
                this.updatedAt
        );

        return entity;
    }

    // =========================================
    // DTO LIST -> ENTITY LIST
    // =========================================

    public static List<QuantityMeasurementEntity>
    toEntityList(
            List<QuantityMeasurementDTO> dtos) {

        return dtos.stream()
                .map(
                        QuantityMeasurementDTO::toEntity
                )
                .toList();
    }
}