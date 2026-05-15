package com.quantity.measurement.service;

import com.quantity.measurement.dto.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {

    QuantityMeasurementDTO saveMeasurement(
            QuantityMeasurementDTO dto
    );

    List<QuantityMeasurementDTO> getAllMeasurements();

    List<QuantityMeasurementDTO>
    getMeasurementsByOperation(String operationType);

    List<QuantityMeasurementDTO>
    getMeasurementsByType(String measurementType);

    long getTotalCount();
}