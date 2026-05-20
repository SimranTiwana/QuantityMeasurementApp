package com.quantity.measurement.service;

import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.dto.QuantityMeasurementDTO;

import java.util.List;

public interface IQuantityMeasurementService {

    // =========================================
    // COMPARE QUANTITIES
    // =========================================

    QuantityMeasurementDTO compareQuantities(
            QuantityDTO q1,
            QuantityDTO q2
    );

    // =========================================
    // CONVERT QUANTITY
    // =========================================

    QuantityMeasurementDTO convertQuantity(
            QuantityDTO q1,
            QuantityDTO q2
    );

    // =========================================
    // ADD QUANTITIES
    // =========================================

    QuantityMeasurementDTO addQuantities(
            QuantityDTO q1,
            QuantityDTO q2
    );

    // =========================================
    // SUBTRACT QUANTITIES
    // =========================================

    QuantityMeasurementDTO subtractQuantities(
            QuantityDTO q1,
            QuantityDTO q2
    );

    // =========================================
    // MULTIPLY QUANTITIES
    // =========================================

    QuantityMeasurementDTO multiplyQuantities(
            QuantityDTO q1,
            QuantityDTO q2
    );

    // =========================================
    // DIVIDE QUANTITIES
    // =========================================

    QuantityMeasurementDTO divideQuantities(
            QuantityDTO q1,
            QuantityDTO q2
    );

    // =========================================
    // GET HISTORY BY OPERATION
    // =========================================

    List<QuantityMeasurementDTO>
    getHistoryByOperation(
            String operation
    );

    // =========================================
    // GET HISTORY BY TYPE
    // =========================================

    List<QuantityMeasurementDTO>
    getHistoryByType(
            String type
    );

    // =========================================
    // GET ERROR HISTORY
    // =========================================

    List<QuantityMeasurementDTO>
    getErrorHistory();

    // =========================================
    // COUNT BY OPERATION
    // =========================================

    Long countByOperation(
            String operation
    );

    // =========================================
    // GET ALL MEASUREMENTS
    // =========================================

    List<QuantityMeasurementDTO>
    getAllMeasurements();
}