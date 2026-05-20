package com.quantity.measurement.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.context.annotation.Configuration;

// =============================================================
// UC17 + UC18 – OpenAPI / Swagger Configuration
// =============================================================
// Added over the original:
//   @SecurityScheme  – defines the BearerAuth scheme so that
//   Swagger UI renders the "Authorize" button and all endpoints
//   annotated with @SecurityRequirement(name = "BearerAuth")
//   show the padlock icon.  Users paste their JWT here and
//   Swagger includes it in all Try-It-Out requests.
// =============================================================

@Configuration

@OpenAPIDefinition(
        info = @Info(
                title       = "Quantity Measurement API",
                version     = "1.0",
                description = "REST APIs for Quantity Measurement – " +
                              "all endpoints require a JWT Bearer token " +
                              "(obtain via GET /api/auth/login)"
        )
)

@SecurityScheme(
        name   = "BearerAuth",           // matches @SecurityRequirement in controller
        type   = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)

public class OpenApiConfig {
}