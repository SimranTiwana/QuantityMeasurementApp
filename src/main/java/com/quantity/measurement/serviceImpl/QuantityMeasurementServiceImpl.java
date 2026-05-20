package com.quantity.measurement.serviceImpl;

import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.dto.QuantityMeasurementDTO;

import com.quantity.measurement.enums.IMeasurable;
import com.quantity.measurement.enums.OperationType;

import com.quantity.measurement.enumsImpl.LengthUnit;
import com.quantity.measurement.enumsImpl.TemperatureUnit;
import com.quantity.measurement.enumsImpl.VolumeUnit;
import com.quantity.measurement.enumsImpl.WeightUnit;

import com.quantity.measurement.exception.QuantityMeasurementException;

import com.quantity.measurement.model.Quantity;
import com.quantity.measurement.model.QuantityMeasurementEntity;

import com.quantity.measurement.repository.QuantityMeasurementRepository;

import com.quantity.measurement.service.IQuantityMeasurementService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import java.util.List;

// =============================================================
// UC17 – Service Implementation
// =============================================================
// Fixes / additions over the original:
//   1. persistError()  – helper that saves an error entity so
//      that exception paths also leave an audit trail in the DB.
//      Original catch blocks only re-threw; nothing was saved.
//   2. subtractQuantities – new method (UC12 logic → service)
//   3. multiplyQuantities – new method
//   4. divideQuantities   – new method (UC12 logic → service)
//   5. getErrorHistory    – delegates to findByIsErrorTrue()
//      (method existed in repo but had no service / endpoint)
// =============================================================

@Service
@RequiredArgsConstructor

public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    QuantityMeasurementServiceImpl.class
            );

    private final QuantityMeasurementRepository repository;

    // =========================================================
    // PRIVATE HELPERS
    // =========================================================

    private IMeasurable getUnit(
            String unit,
            String type) {

        logger.debug(
                "Resolving unit {} for type {}",
                unit,
                type
        );

        return switch (type.toUpperCase()) {

            case "LENGTHUNIT" ->
                    LengthUnit.valueOf(unit);

            case "WEIGHTUNIT" ->
                    WeightUnit.valueOf(unit);

            case "VOLUMEUNIT" ->
                    VolumeUnit.valueOf(unit);

            case "TEMPERATUREUNIT" ->
                    TemperatureUnit.valueOf(unit);

            default ->
                    throw new QuantityMeasurementException(
                            "Invalid measurement type: " + type
                    );
        };
    }

    /**
     * UC17 FIX – persist an error entity so that every failed
     * operation is traceable in the audit history.
     */
    private void persistError(
            QuantityDTO q1,
            QuantityDTO q2,
            OperationType operationType,
            String errorMessage
    ) {

        try {

            QuantityMeasurementEntity errorEntity =
                    new QuantityMeasurementEntity();

            errorEntity.setOperand1Value(
                    q1.getValue() != null
                            ? q1.getValue() : 0.0
            );
            errorEntity.setOperand1Unit(
                    q1.getUnit() != null
                            ? q1.getUnit() : "UNKNOWN"
            );
            errorEntity.setOperand2Value(
                    q2.getValue() != null
                            ? q2.getValue() : 0.0
            );
            errorEntity.setOperand2Unit(
                    q2.getUnit() != null
                            ? q2.getUnit() : "UNKNOWN"
            );
            errorEntity.setMeasurementType(
                    q1.getMeasurementType() != null
                            ? q1.getMeasurementType() : "UNKNOWN"
            );
            errorEntity.setOperationType(operationType);
            errorEntity.setResultValue(0.0);
            errorEntity.setResultUnit("ERROR");
            errorEntity.setIsError(true);
            errorEntity.setErrorMessage(errorMessage);

            repository.save(errorEntity);

        } catch (Exception persistEx) {

            // Log but don't swallow the original exception
            logger.error(
                    "Failed to persist error entity: {}",
                    persistEx.getMessage()
            );
        }
    }

    // =========================================================
    // COMPARE QUANTITIES
    // =========================================================

    @Override
    public QuantityMeasurementDTO compareQuantities(
            QuantityDTO q1,
            QuantityDTO q2) {

        logger.info("COMPARE operation started");

        try {

            IMeasurable u1 =
                    getUnit(q1.getUnit(), q1.getMeasurementType());

            IMeasurable u2 =
                    getUnit(q2.getUnit(), q2.getMeasurementType());

            boolean result =
                    new Quantity<>(q1.getValue(), u1)
                            .equals(new Quantity<>(q2.getValue(), u2));

            QuantityMeasurementEntity entity =
                    buildEntity(q1, q2, OperationType.COMPARE,
                            result ? 1.0 : 0.0, "BOOLEAN", false, null);

            logger.info("COMPARE operation successful");

            return QuantityMeasurementDTO.fromEntity(
                    repository.save(entity)
            );

        } catch (Exception e) {

            logger.error("COMPARE operation failed", e);
            persistError(q1, q2, OperationType.COMPARE, e.getMessage());
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    // =========================================================
    // CONVERT QUANTITY
    // =========================================================

    @Override
    public QuantityMeasurementDTO convertQuantity(
            QuantityDTO q1,
            QuantityDTO q2) {

        logger.info("CONVERT operation started");

        try {

            IMeasurable unit =
                    getUnit(q1.getUnit(), q1.getMeasurementType());

            Quantity<?> result =
                    new Quantity<>(q1.getValue(), unit)
                            .toConvert(
                                    getUnit(q2.getUnit(),
                                            q1.getMeasurementType())
                            );

            QuantityMeasurementEntity entity =
                    buildEntity(q1, q2, OperationType.CONVERT,
                            result.getValue(), q2.getUnit(), false, null);

            logger.info("CONVERT operation successful");

            return QuantityMeasurementDTO.fromEntity(
                    repository.save(entity)
            );

        } catch (Exception e) {

            logger.error("CONVERT operation failed", e);
            persistError(q1, q2, OperationType.CONVERT, e.getMessage());
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    // =========================================================
    // ADD QUANTITIES
    // =========================================================

    @Override
    public QuantityMeasurementDTO addQuantities(
            QuantityDTO q1,
            QuantityDTO q2) {

        logger.info("ADD operation started");

        try {

            IMeasurable u1 =
                    getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 =
                    getUnit(q2.getUnit(), q2.getMeasurementType());

            Quantity<?> result =
                    new Quantity<>(q1.getValue(), u1)
                            .add(
                                    new Quantity<>(q2.getValue(), u2),
                                    getUnit(q1.getUnit(),
                                            q1.getMeasurementType())
                            );

            QuantityMeasurementEntity entity =
                    buildEntity(q1, q2, OperationType.ADD,
                            result.getValue(), q1.getUnit(), false, null);

            logger.info("ADD operation successful");

            return QuantityMeasurementDTO.fromEntity(
                    repository.save(entity)
            );

        } catch (Exception e) {

            logger.error("ADD operation failed", e);
            persistError(q1, q2, OperationType.ADD, e.getMessage());
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    // =========================================================
    // SUBTRACT QUANTITIES   (UC17 – was missing)
    // =========================================================

    @Override
    public QuantityMeasurementDTO subtractQuantities(
            QuantityDTO q1,
            QuantityDTO q2) {

        logger.info("SUBTRACT operation started");

        try {

            IMeasurable u1 =
                    getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 =
                    getUnit(q2.getUnit(), q2.getMeasurementType());

            Quantity<?> result =
                    new Quantity<>(q1.getValue(), u1)
                            .subtract(
                                    new Quantity<>(q2.getValue(), u2),
                                    getUnit(q1.getUnit(),
                                            q1.getMeasurementType())
                            );

            QuantityMeasurementEntity entity =
                    buildEntity(q1, q2, OperationType.SUBTRACT,
                            result.getValue(), q1.getUnit(), false, null);

            logger.info("SUBTRACT operation successful");

            return QuantityMeasurementDTO.fromEntity(
                    repository.save(entity)
            );

        } catch (Exception e) {

            logger.error("SUBTRACT operation failed", e);
            persistError(q1, q2, OperationType.SUBTRACT, e.getMessage());
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    // =========================================================
    // MULTIPLY QUANTITIES   (UC17 – was missing)
    // =========================================================

    @Override
    public QuantityMeasurementDTO multiplyQuantities(
            QuantityDTO q1,
            QuantityDTO q2) {

        logger.info("MULTIPLY operation started");

        try {

            IMeasurable u1 =
                    getUnit(q1.getUnit(), q1.getMeasurementType());

            // Multiply: convert both to base unit, multiply scalars,
            // then convert back to q1's unit
            double base1 = u1.convertToBaseUnit(q1.getValue());
            double base2 = getUnit(q2.getUnit(), q2.getMeasurementType())
                    .convertToBaseUnit(q2.getValue());

            double resultBase = base1 * base2;
            double resultValue = u1.convertFromBaseUnit(resultBase);

            QuantityMeasurementEntity entity =
                    buildEntity(q1, q2, OperationType.MULTIPLY,
                            resultValue, q1.getUnit(), false, null);

            logger.info("MULTIPLY operation successful");

            return QuantityMeasurementDTO.fromEntity(
                    repository.save(entity)
            );

        } catch (Exception e) {

            logger.error("MULTIPLY operation failed", e);
            persistError(q1, q2, OperationType.MULTIPLY, e.getMessage());
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    // =========================================================
    // DIVIDE QUANTITIES     (UC17 – was missing)
    // =========================================================

    @Override
    public QuantityMeasurementDTO divideQuantities(
            QuantityDTO q1,
            QuantityDTO q2) {

        logger.info("DIVIDE operation started");

        try {

            IMeasurable u1 =
                    getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 =
                    getUnit(q2.getUnit(), q2.getMeasurementType());

            double result =
                    new Quantity<>(q1.getValue(), u1)
                            .divide(new Quantity<>(q2.getValue(), u2));

            QuantityMeasurementEntity entity =
                    buildEntity(q1, q2, OperationType.DIVIDE,
                            result, "RATIO", false, null);

            logger.info("DIVIDE operation successful");

            return QuantityMeasurementDTO.fromEntity(
                    repository.save(entity)
            );

        } catch (Exception e) {

            logger.error("DIVIDE operation failed", e);
            persistError(q1, q2, OperationType.DIVIDE, e.getMessage());
            throw new QuantityMeasurementException(e.getMessage());
        }
    }

    // =========================================================
    // GET HISTORY BY OPERATION
    // =========================================================

    @Override
    public List<QuantityMeasurementDTO> getHistoryByOperation(
            String operation) {

        OperationType operationType =
                OperationType.valueOf(operation.toUpperCase());

        return repository
                .findByOperationType(operationType)
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    // =========================================================
    // GET HISTORY BY TYPE
    // =========================================================

    @Override
    public List<QuantityMeasurementDTO> getHistoryByType(
            String type) {

        return repository
                .findByMeasurementType(type)
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    // =========================================================
    // GET ERROR HISTORY     (UC17 – was missing)
    // =========================================================

    @Override
    public List<QuantityMeasurementDTO> getErrorHistory() {

        return repository
                .findByIsErrorTrue()
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    // =========================================================
    // COUNT BY OPERATION
    // =========================================================

    @Override
    public Long countByOperation(String operation) {

        OperationType operationType =
                OperationType.valueOf(operation.toUpperCase());

        return repository
                .countByOperationTypeAndIsErrorFalse(operationType);
    }

    // =========================================================
    // GET ALL MEASUREMENTS
    // =========================================================

    @Override
    public List<QuantityMeasurementDTO> getAllMeasurements() {

        return repository
                .findAll()
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    // =========================================================
    // PRIVATE – ENTITY BUILDER  (eliminates code duplication)
    // =========================================================

    private QuantityMeasurementEntity buildEntity(
            QuantityDTO q1,
            QuantityDTO q2,
            OperationType operationType,
            double resultValue,
            String resultUnit,
            boolean isError,
            String errorMessage
    ) {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        entity.setOperand1Value(q1.getValue());
        entity.setOperand1Unit(q1.getUnit());
        entity.setOperand2Value(q2.getValue());
        entity.setOperand2Unit(q2.getUnit());
        entity.setMeasurementType(q1.getMeasurementType());
        entity.setOperationType(operationType);
        entity.setResultValue(resultValue);
        entity.setResultUnit(resultUnit);
        entity.setIsError(isError);
        entity.setErrorMessage(errorMessage);

        return entity;
    }
}