package com.quantity.measurement.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private double operand1Value;

    @Column(nullable = false)
    private String operand1Unit;

    @Column(nullable = false)
    private double operand2Value;

    @Column(nullable = false)
    private String operand2Unit;

    @Column(nullable = false)
    private String measurementType;

    @Column(nullable = false)
    private String operationType;

    @Column(nullable = false)
    private double resultValue;

    @Column(nullable = false)
    private String resultUnit;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}