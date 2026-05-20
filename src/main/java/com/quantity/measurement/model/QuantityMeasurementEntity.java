package com.quantity.measurement.model;

import com.quantity.measurement.enums.OperationType;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity

@Table(name = "quantity_measurement_entity")

@Data
@NoArgsConstructor
@AllArgsConstructor

public class QuantityMeasurementEntity
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // =========================================
    // PRIMARY KEY
    // =========================================

    @Id

    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )

    private Long id;

    // =========================================
    // OPERAND 1
    // =========================================

    @Column(nullable = false)

    private double operand1Value;

    @Column(nullable = false)

    private String operand1Unit;

    // =========================================
    // OPERAND 2
    // =========================================

    @Column(nullable = false)

    private double operand2Value;

    @Column(nullable = false)

    private String operand2Unit;

    // =========================================
    // MEASUREMENT DETAILS
    // =========================================

    @Column(nullable = false)

    private String measurementType;

    // =========================================
    // OPERATION TYPE ENUM
    // =========================================

    @Enumerated(EnumType.STRING)

    @Column(nullable = false)

    private OperationType operationType;

    // =========================================
    // RESULT
    // =========================================

    @Column(nullable = false)

    private double resultValue;

    @Column(nullable = false)

    private String resultUnit;

    // =========================================
    // ERROR TRACKING
    // =========================================

    private Boolean isError = false;

    private String errorMessage;

    // =========================================
    // TIMESTAMPS
    // =========================================

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // =========================================
    // PRE PERSIST
    // =========================================

    @PrePersist

    public void prePersist() {

        createdAt = LocalDateTime.now();
    }

    // =========================================
    // PRE UPDATE
    // =========================================

    @PreUpdate

    public void preUpdate() {

        updatedAt = LocalDateTime.now();
    }
}