package com.quantity.measurement.repository;

import com.quantity.measurement.enums.OperationType;
import com.quantity.measurement.model.QuantityMeasurementEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository

public interface QuantityMeasurementRepository
        extends JpaRepository<
        QuantityMeasurementEntity,
        Long> {

    // =========================================
    // FIND BY OPERATION TYPE
    // =========================================

    List<QuantityMeasurementEntity>
    findByOperationType(
            OperationType operationType
    );

    // =========================================
    // FIND BY MEASUREMENT TYPE
    // =========================================

    List<QuantityMeasurementEntity>
    findByMeasurementType(
            String measurementType
    );

    // =========================================
    // FIND CREATED AFTER DATE
    // =========================================

    List<QuantityMeasurementEntity>
    findByCreatedAtAfter(
            LocalDateTime date
    );

    // =========================================
    // FIND ERROR RECORDS
    // =========================================

    List<QuantityMeasurementEntity>
    findByIsErrorTrue();

    // =========================================
    // COUNT SUCCESSFUL OPERATIONS
    // =========================================

    Long countByOperationTypeAndIsErrorFalse(
            OperationType operationType
    );

    // =========================================
    // CUSTOM QUERY
    // =========================================

    @Query("""
            SELECT q
            FROM QuantityMeasurementEntity q
            WHERE q.operationType = :operationType
            AND q.isError = false
            """)

    List<QuantityMeasurementEntity>
    findSuccessfulOperations(
            @Param("operationType")
            OperationType operationType
    );
}