package com.quantity.measurement.controller;

import com.quantity.measurement.dto.QuantityMeasurementDTO;
import com.quantity.measurement.service.IQuantityMeasurementService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController

@RequestMapping("/api/v1/measurements")

@RequiredArgsConstructor

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    @PostMapping

    public QuantityMeasurementDTO saveMeasurement(
            @Valid @RequestBody QuantityMeasurementDTO dto) {

        return service.saveMeasurement(dto);
    }

    @GetMapping

    public List<QuantityMeasurementDTO>
    getAllMeasurements() {

        return service.getAllMeasurements();
    }

    @GetMapping("/operation/{operationType}")

    public List<QuantityMeasurementDTO>
    getByOperation(
            @PathVariable String operationType) {

        return service
                .getMeasurementsByOperation(operationType);
    }

    @GetMapping("/type/{measurementType}")

    public List<QuantityMeasurementDTO>
    getByType(
            @PathVariable String measurementType) {

        return service
                .getMeasurementsByType(measurementType);
    }

    @GetMapping("/count")

    public long getTotalCount() {

        return service.getTotalCount();
    }
}