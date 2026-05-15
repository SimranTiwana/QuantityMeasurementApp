package com.quantity.measurement.dump.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.enumsImpl.LengthUnit;
import com.quantity.measurement.enumsImpl.VolumeUnit;
import com.quantity.measurement.enumsImpl.WeightUnit;
import com.quantity.measurement.enumsImpl.TemperatureUnit;
import com.quantity.measurement.enums.IMeasurable;
import com.quantity.measurement.exception.Exception;
import com.quantity.measurement.model.QuantityMeasurementEntity;
import com.quantity.measurement.model.Quantity;
import com.quantity.measurement.repository.Repository;
import com.quantity.measurement.service.Service;

public class ServiceImpl implements Service{

    private static final Logger logger =
            LoggerFactory.getLogger(ServiceImpl.class);

    private  Repository repository;

    //DI using Constructor
    public ServiceImpl(Repository repository2) {
        this.repository = repository2;

        logger.info("QuantityMeasurementService initialized");

    }

    private IMeasurable getUnit(String unit, String type) {

        logger.debug("Resolving unit {} for type {}", unit, type);

        return switch (type) {
            case "LENGTH" -> LengthUnit.valueOf(unit);
            case "WEIGHT" -> WeightUnit.valueOf(unit);
            case "VOLUME" -> VolumeUnit.valueOf(unit);
            case "TEMPERATURE" -> TemperatureUnit.valueOf(unit);
            default -> throw new Exception("Invalid type");
        };
    }

    @Override
    public QuantityDTO add(QuantityDTO q1, QuantityDTO q2, String targetUnit) {

        logger.info("ADD operation started");

        try {

            IMeasurable u1 = getUnit( q1.getUnit(), q1.getMeasurementType());

            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());

            Quantity<?> result = new Quantity<>(q1.getValue(), u1).add(new Quantity<>(q2.getValue(), u2),
                    getUnit( targetUnit, q1.getMeasurementType()));

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

            entity.setOperand1Value(q1.getValue());

            entity.setOperand1Unit(q1.getUnit());

            entity.setOperand2Value(q2.getValue());

            entity.setOperand2Unit(q2.getUnit());

            entity.setMeasurementType(q1.getMeasurementType());

            entity.setOperationType("ADD");

            entity.setResultValue(result.getValue());

            entity.setResultUnit(targetUnit);

            repository.save(entity);

            logger.info("ADD operation successful");

            return new QuantityDTO(result.getValue(), targetUnit, q1.getMeasurementType());

        } catch (java.lang.Exception e) {

            logger.error("ADD operation failed", e);

            return new QuantityDTO(true, e.getMessage());
        }
    }


    @Override
    public QuantityDTO subtract(QuantityDTO q1, QuantityDTO q2, String targetUnit) {

        logger.info("SUBTRACT operation started");

        try {

            IMeasurable u1 = getUnit(q1.getUnit(), q1.getMeasurementType());

            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());

            Quantity<?> result = new Quantity<>(q1.getValue(), u1)
                    .subtract(
                            new Quantity<>(q2.getValue(), u2),
                            getUnit(targetUnit, q1.getMeasurementType()));

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

            entity.setOperand1Value(q1.getValue());

            entity.setOperand1Unit(q1.getUnit());

            entity.setOperand2Value(q2.getValue());

            entity.setOperand2Unit(q2.getUnit());

            entity.setMeasurementType(q1.getMeasurementType());

            entity.setOperationType("SUBTRACT");

            entity.setResultValue(result.getValue());

            entity.setResultUnit(targetUnit);

            repository.save(entity);

            logger.info("SUBTRACT operation successful");

            return new QuantityDTO(result.getValue(), targetUnit, q1.getMeasurementType());

        } catch (java.lang.Exception e) {

            logger.error("SUBTRACT operation failed",e);

            return new QuantityDTO(true, e.getMessage());
        }
    }

    @Override
    public QuantityDTO divide(QuantityDTO q1, QuantityDTO q2) {

        logger.info("DIVIDE operation started");

        try {

            IMeasurable u1 = getUnit(q1.getUnit(), q1.getMeasurementType());

            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());

            double result = new Quantity<>(q1.getValue(), u1)
                    .divide(new Quantity<>(q2.getValue(),u2));

            QuantityMeasurementEntity entity = new QuantityMeasurementEntity();

            entity.setOperand1Value(q1.getValue());

            entity.setOperand1Unit(q1.getUnit());

            entity.setOperand2Value(q2.getValue());

            entity.setOperand2Unit(q2.getUnit());

            entity.setMeasurementType(q1.getMeasurementType());

            entity.setOperationType("DIVIDE");

            entity.setResultValue(result);

            entity.setResultUnit("SCALAR");

            repository.save(entity);

            logger.info("DIVIDE operation successful");

            return new QuantityDTO(result, "SCALAR", q1.getMeasurementType());

        } catch (java.lang.Exception e) {

            logger.error("DIVIDE operation failed", e);

            return new QuantityDTO(true,e.getMessage());
        }
    }

    @Override
    public QuantityDTO convert(QuantityDTO q, String targetUnit) {

        logger.info("CONVERT operation started");

        try {
            IMeasurable u = getUnit(q.getUnit(), q.getMeasurementType());

            Quantity<?> result = new Quantity<>(q.getValue(), u)
                    .toConvert(getUnit(targetUnit, q.getMeasurementType()));

            logger.info("CONVERT operation successful");

            return new QuantityDTO(result.getValue(), targetUnit, q.getMeasurementType());

        } catch (java.lang.Exception e) {

            logger.error("CONVERT operation failed : {}", e.getMessage());

            return new QuantityDTO(true, e.getMessage());
        }
    }

    @Override
    public QuantityDTO compare(QuantityDTO q1, QuantityDTO q2) {

        logger.info("COMPARE operation started");

        try {
            IMeasurable u1 = getUnit(q1.getUnit(), q1.getMeasurementType());
            IMeasurable u2 = getUnit(q2.getUnit(), q2.getMeasurementType());

            if (!u1.getClass().equals(u2.getClass())) {
                throw new IllegalArgumentException("Different measurement types");
            }

            boolean result = new Quantity<>(q1.getValue(), u1)
                    .equals(new Quantity<>(q2.getValue(), u2));

            logger.info("COMPARE operation successful");

            return new QuantityDTO(result ? 1 : 0, "BOOLEAN", q1.getMeasurementType());

        } catch (java.lang.Exception e) {

            logger.error("COMPARE operation failed : {}", e.getMessage());

            return new QuantityDTO(true, e.getMessage());
        }
    }
}