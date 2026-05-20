package com.quantity.measurement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.quantity.measurement.config.JwtAuthenticationFilter;
import com.quantity.measurement.dto.QuantityDTO;
import com.quantity.measurement.dto.QuantityInputDTO;
import com.quantity.measurement.dto.QuantityMeasurementDTO;
import com.quantity.measurement.enums.OperationType;
import com.quantity.measurement.service.IQuantityMeasurementService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

import static org.springframework.security.test.web.servlet.request
        .SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// =============================================================
// UC17 Step 14 – MockMvc Controller Tests
// =============================================================

@WebMvcTest(
        value = QuantityMeasurementController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                value = JwtAuthenticationFilter.class
        )
)

class QuantityMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private IQuantityMeasurementService service;

    // =========================================================
    // TEST DATA
    // =========================================================

    private QuantityInputDTO inputDTO;

    private QuantityMeasurementDTO mockDTO;

    @BeforeEach
    void setup() {

        QuantityDTO q1 = new QuantityDTO(
                1.0,
                "FEET",
                "LengthUnit"
        );

        QuantityDTO q2 = new QuantityDTO(
                12.0,
                "INCH",
                "LengthUnit"
        );

        inputDTO = new QuantityInputDTO(q1, q2);

        mockDTO = QuantityMeasurementDTO.builder()
                .id(1L)
                .operand1Value(1.0)
                .operand1Unit("FEET")
                .operand2Value(12.0)
                .operand2Unit("INCH")
                .measurementType("LengthUnit")
                .operationType(OperationType.COMPARE)
                .resultValue(1.0)
                .resultUnit("BOOLEAN")
                .isError(false)
                .build();
    }

    // =========================================================
    // COMPARE
    // =========================================================

    @Test
    @WithMockUser
    void testCompare_ReturnsOk() throws Exception {

        Mockito.when(
                service.compareQuantities(any(), any())
        ).thenReturn(mockDTO);

        mockMvc.perform(
                post("/api/v1/quantities/compare")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultUnit")
                        .value("BOOLEAN"))
                .andExpect(jsonPath("$.isError")
                        .value(false));

        Mockito.verify(service)
                .compareQuantities(any(), any());
    }

    // =========================================================
    // CONVERT
    // =========================================================

    @Test
    @WithMockUser
    void testConvert_ReturnsOk() throws Exception {

        QuantityMeasurementDTO convertDTO =
                QuantityMeasurementDTO.builder()
                        .id(2L)
                        .operationType(OperationType.CONVERT)
                        .resultValue(12.0)
                        .resultUnit("INCH")
                        .isError(false)
                        .build();

        Mockito.when(
                service.convertQuantity(any(), any())
        ).thenReturn(convertDTO);

        mockMvc.perform(
                post("/api/v1/quantities/convert")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue")
                        .value(12.0));
    }

    // =========================================================
    // ADD
    // =========================================================

    @Test
    @WithMockUser
    void testAdd_ReturnsOk() throws Exception {

        QuantityMeasurementDTO addDTO =
                QuantityMeasurementDTO.builder()
                        .id(3L)
                        .operationType(OperationType.ADD)
                        .resultValue(2.0)
                        .resultUnit("FEET")
                        .isError(false)
                        .build();

        Mockito.when(
                service.addQuantities(any(), any())
        ).thenReturn(addDTO);

        mockMvc.perform(
                post("/api/v1/quantities/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultValue")
                        .value(2.0));
    }

    // =========================================================
    // SUBTRACT
    // =========================================================

    @Test
    @WithMockUser
    void testSubtract_ReturnsOk() throws Exception {

        QuantityMeasurementDTO subtractDTO =
                QuantityMeasurementDTO.builder()
                        .id(4L)
                        .operationType(OperationType.SUBTRACT)
                        .resultValue(0.0)
                        .resultUnit("FEET")
                        .isError(false)
                        .build();

        Mockito.when(
                service.subtractQuantities(any(), any())
        ).thenReturn(subtractDTO);

        mockMvc.perform(
                post("/api/v1/quantities/subtract")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.operationType")
                        .value("SUBTRACT"));
    }

    // =========================================================
    // DIVIDE
    // =========================================================

    @Test
    @WithMockUser
    void testDivide_ReturnsOk() throws Exception {

        QuantityMeasurementDTO divideDTO =
                QuantityMeasurementDTO.builder()
                        .id(5L)
                        .operationType(OperationType.DIVIDE)
                        .resultValue(1.0)
                        .resultUnit("RATIO")
                        .isError(false)
                        .build();

        Mockito.when(
                service.divideQuantities(any(), any())
        ).thenReturn(divideDTO);

        mockMvc.perform(
                post("/api/v1/quantities/divide")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDTO))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultUnit")
                        .value("RATIO"));
    }

    // =========================================================
    // HISTORY BY OPERATION
    // =========================================================

    @Test
    @WithMockUser
    void testGetHistoryByOperation_ReturnsOk() throws Exception {

        Mockito.when(
                service.getHistoryByOperation("COMPARE")
        ).thenReturn(List.of(mockDTO));

        mockMvc.perform(
                get("/api/v1/quantities/history/operation/COMPARE")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].operationType")
                        .value("COMPARE"));
    }

    // =========================================================
    // ERROR HISTORY
    // =========================================================

    @Test
    @WithMockUser
    void testGetErrorHistory_ReturnsOk() throws Exception {

        QuantityMeasurementDTO errDTO =
                QuantityMeasurementDTO.builder()
                        .id(99L)
                        .isError(true)
                        .errorMessage("test error")
                        .build();

        Mockito.when(
                service.getErrorHistory()
        ).thenReturn(List.of(errDTO));

        mockMvc.perform(
                get("/api/v1/quantities/history/errored")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].isError")
                        .value(true));
    }

    // =========================================================
    // COUNT BY OPERATION
    // =========================================================

    @Test
    @WithMockUser
    void testCountByOperation_ReturnsOk() throws Exception {

        Mockito.when(
                service.countByOperation("COMPARE")
        ).thenReturn(5L);

        mockMvc.perform(
                get("/api/v1/quantities/count/COMPARE")
        )
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    // =========================================================
    // INVALID INPUT – 400
    // =========================================================

    @Test
    @WithMockUser
    void testCompare_InvalidInput_Returns400() throws Exception {

        mockMvc.perform(
                post("/api/v1/quantities/compare")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
        )
                .andExpect(status().isBadRequest());
    }
}