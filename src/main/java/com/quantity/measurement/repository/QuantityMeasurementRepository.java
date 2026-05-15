package com.quantity.measurement.repository;

import com.quantity.measurement.model.QuantityMeasurementEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity>
    findByOperationType(String operationType);

    List<QuantityMeasurementEntity>
    findByMeasurementType(String measurementType);

    List<QuantityMeasurementEntity>
    findByCreatedAtAfter(LocalDateTime date);
}