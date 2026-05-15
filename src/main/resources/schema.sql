CREATE TABLE IF NOT EXISTS quantity_measurement_entity (

                                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,

                                                           operand1_value DOUBLE NOT NULL,

                                                           operand1_unit VARCHAR(50) NOT NULL,

    operand2_value DOUBLE NOT NULL,

    operand2_unit VARCHAR(50) NOT NULL,

    measurement_type VARCHAR(50) NOT NULL,

    operation_type VARCHAR(50) NOT NULL,

    result_value DOUBLE NOT NULL,

    result_unit VARCHAR(50) NOT NULL,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP

    );