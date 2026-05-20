package com.quantity.measurement.controller;

import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.dto.QuantityInputDTO;
import com.quantity.measurement.dto.QuantityMeasurementDTO;
import com.quantity.measurement.service.IQuantityMeasurementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// =============================================================
// UC17 + UC18 – REST Controller
// =============================================================
// Additions over the original:
//   1. POST /subtract  – exposes UC12 subtraction
//   2. POST /multiply  – exposes multiplication
//   3. POST /divide    – exposes UC12 division
//   4. GET  /history/errored  – returns all error records;
//      uses repo.findByIsErrorTrue() via service
//   5. @SecurityRequirement("BearerAuth") on class –
//      Swagger UI shows the padlock on every endpoint (UC18)
// =============================================================

@RestController
@RequestMapping("/api/v1/quantities")
@RequiredArgsConstructor

@Tag(name = "Quantity Measurement APIs",
        description = "Secured endpoints – JWT Bearer token required")

@SecurityRequirement(name = "BearerAuth")   // UC18 – Swagger auth

public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;

    // =========================================================
    // COMPARE QUANTITIES
    // =========================================================

    @Operation(summary = "Compare two quantities")

    @PostMapping("/compare")

    public ResponseEntity<QuantityMeasurementDTO>
    compareQuantities(
            @Valid @RequestBody QuantityInputDTO inputDTO) {

        QuantityDTO q1 = inputDTO.getThisQuantityDTO();
        QuantityDTO q2 = inputDTO.getThatQuantityDTO();

        return ResponseEntity.ok(
                service.compareQuantities(q1, q2)
        );
    }

    // =========================================================
    // CONVERT QUANTITY
    // =========================================================

    @Operation(summary = "Convert a quantity to another unit")

    @PostMapping("/convert")

    public ResponseEntity<QuantityMeasurementDTO>
    convertQuantity(
            @Valid @RequestBody QuantityInputDTO inputDTO) {

        QuantityDTO q1 = inputDTO.getThisQuantityDTO();
        QuantityDTO q2 = inputDTO.getThatQuantityDTO();

        return ResponseEntity.ok(
                service.convertQuantity(q1, q2)
        );
    }

    // =========================================================
    // ADD QUANTITIES
    // =========================================================

    @Operation(summary = "Add two quantities")

    @PostMapping("/add")

    public ResponseEntity<QuantityMeasurementDTO>
    addQuantities(
            @Valid @RequestBody QuantityInputDTO inputDTO) {

        QuantityDTO q1 = inputDTO.getThisQuantityDTO();
        QuantityDTO q2 = inputDTO.getThatQuantityDTO();

        return ResponseEntity.ok(
                service.addQuantities(q1, q2)
        );
    }

    // =========================================================
    // SUBTRACT QUANTITIES   (UC17 – was missing)
    // =========================================================

    @Operation(summary = "Subtract two quantities")

    @PostMapping("/subtract")

    public ResponseEntity<QuantityMeasurementDTO>
    subtractQuantities(
            @Valid @RequestBody QuantityInputDTO inputDTO) {

        QuantityDTO q1 = inputDTO.getThisQuantityDTO();
        QuantityDTO q2 = inputDTO.getThatQuantityDTO();

        return ResponseEntity.ok(
                service.subtractQuantities(q1, q2)
        );
    }

    // =========================================================
    // MULTIPLY QUANTITIES   (UC17 – was missing)
    // =========================================================

    @Operation(summary = "Multiply two quantities")

    @PostMapping("/multiply")

    public ResponseEntity<QuantityMeasurementDTO>
    multiplyQuantities(
            @Valid @RequestBody QuantityInputDTO inputDTO) {

        QuantityDTO q1 = inputDTO.getThisQuantityDTO();
        QuantityDTO q2 = inputDTO.getThatQuantityDTO();

        return ResponseEntity.ok(
                service.multiplyQuantities(q1, q2)
        );
    }

    // =========================================================
    // DIVIDE QUANTITIES     (UC17 – was missing)
    // =========================================================

    @Operation(summary = "Divide two quantities (returns ratio)")

    @PostMapping("/divide")

    public ResponseEntity<QuantityMeasurementDTO>
    divideQuantities(
            @Valid @RequestBody QuantityInputDTO inputDTO) {

        QuantityDTO q1 = inputDTO.getThisQuantityDTO();
        QuantityDTO q2 = inputDTO.getThatQuantityDTO();

        return ResponseEntity.ok(
                service.divideQuantities(q1, q2)
        );
    }

    // =========================================================
    // GET HISTORY BY OPERATION
    // =========================================================

    @Operation(summary = "Get history by operation type")

    @GetMapping("/history/operation/{operation}")

    public ResponseEntity<List<QuantityMeasurementDTO>>
    getHistoryByOperation(
            @PathVariable String operation) {

        return ResponseEntity.ok(
                service.getHistoryByOperation(operation)
        );
    }

    // =========================================================
    // GET HISTORY BY MEASUREMENT TYPE
    // =========================================================

    @Operation(summary = "Get history by measurement type")

    @GetMapping("/history/type/{type}")

    public ResponseEntity<List<QuantityMeasurementDTO>>
    getHistoryByType(
            @PathVariable String type) {

        return ResponseEntity.ok(
                service.getHistoryByType(type)
        );
    }

    // =========================================================
    // GET ERROR HISTORY     (UC17 – was missing endpoint)
    // =========================================================

    @Operation(summary = "Get all errored operations")

    @GetMapping("/history/errored")

    public ResponseEntity<List<QuantityMeasurementDTO>>
    getErrorHistory() {

        return ResponseEntity.ok(
                service.getErrorHistory()
        );
    }

    // =========================================================
    // COUNT BY OPERATION
    // =========================================================

    @Operation(summary = "Count successful records by operation")

    @GetMapping("/count/{operation}")

    public ResponseEntity<Long>
    countByOperation(
            @PathVariable String operation) {

        return ResponseEntity.ok(
                service.countByOperation(operation)
        );
    }

    // =========================================================
    // GET ALL MEASUREMENTS
    // =========================================================

    @Operation(summary = "Get all measurement records")

    @GetMapping

    public ResponseEntity<List<QuantityMeasurementDTO>>
    getAllMeasurements() {

        return ResponseEntity.ok(
                service.getAllMeasurements()
        );
    }
}