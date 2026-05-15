package com.quantity.measurement.entity;

import java.io.Serial;
import java.io.Serializable;

//While implementing Serializable must need to rememember that :
//Warning : If you read object data from an untrusted source, it can be dangerous...
//          Don’t open boxes from strangers without checking...

public class Entity
        implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private double operand1Value;
    private String operand1Unit;

    private double operand2Value;
    private String operand2Unit;

    private String measurementType;
    private String operationType;

    private double resultValue;
    private String resultUnit;

    // DEFAULT CONSTRUCTOR
    public Entity() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getOperand1Value() {
        return operand1Value;
    }

    public void setOperand1Value(double operand1Value) {
        this.operand1Value = operand1Value;
    }

    public String getOperand1Unit() {
        return operand1Unit;
    }

    public void setOperand1Unit(String operand1Unit) {
        this.operand1Unit = operand1Unit;
    }

    public double getOperand2Value() {
        return operand2Value;
    }

    public void setOperand2Value(double operand2Value) {
        this.operand2Value = operand2Value;
    }

    public String getOperand2Unit() {
        return operand2Unit;
    }

    public void setOperand2Unit(String operand2Unit) {
        this.operand2Unit = operand2Unit;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(String measurementType) {
        this.measurementType = measurementType;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public double getResultValue() {
        return resultValue;
    }

    public void setResultValue(double resultValue) {
        this.resultValue = resultValue;
    }

    public String getResultUnit() {
        return resultUnit;
    }

    public void setResultUnit(String resultUnit) {
        this.resultUnit = resultUnit;
    }
}