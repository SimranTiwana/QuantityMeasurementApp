package com.quantity.measurement.serviceImpl;

import com.quantity.measurement.dto.QuantityMeasurementDTO;
import com.quantity.measurement.model.QuantityMeasurementEntity;
import com.quantity.measurement.repository.QuantityMeasurementRepository;
import com.quantity.measurement.service.IQuantityMeasurementService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class QuantityMeasurementServiceImpl
        implements IQuantityMeasurementService {

    private final QuantityMeasurementRepository repository;

    @Override
    public QuantityMeasurementDTO saveMeasurement(
            QuantityMeasurementDTO dto) {

        QuantityMeasurementEntity entity =
                new QuantityMeasurementEntity();

        entity.setOperand1Value(dto.getOperand1Value());
        entity.setOperand1Unit(dto.getOperand1Unit());

        entity.setOperand2Value(dto.getOperand2Value());
        entity.setOperand2Unit(dto.getOperand2Unit());

        entity.setMeasurementType(dto.getMeasurementType());
        entity.setOperationType(dto.getOperationType());

        entity.setResultValue(dto.getResultValue());
        entity.setResultUnit(dto.getResultUnit());

        QuantityMeasurementEntity savedEntity =
                repository.save(entity);

        return QuantityMeasurementDTO
                .fromEntity(savedEntity);
    }

    @Override
    public List<QuantityMeasurementDTO>
    getAllMeasurements() {

        return repository.findAll()
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    @Override
    public List<QuantityMeasurementDTO>
    getMeasurementsByOperation(String operationType) {

        return repository
                .findByOperationType(operationType)
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    @Override
    public List<QuantityMeasurementDTO>
    getMeasurementsByType(String measurementType) {

        return repository
                .findByMeasurementType(measurementType)
                .stream()
                .map(QuantityMeasurementDTO::fromEntity)
                .toList();
    }

    @Override
    public long getTotalCount() {
        return repository.count();
    }
}