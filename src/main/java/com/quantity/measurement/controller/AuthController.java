package com.quantity.measurement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

// =============================================================
// UC18 – Auth Controller
// =============================================================
// Provides the public /api/auth/login endpoint that redirects
// the user to Google's OAuth2 authorisation endpoint.
// After Google authentication, Spring Security's built-in
// /login/oauth2/code/google handles the callback and invokes
// OAuth2LoginSuccessHandler which issues the JWT.
// =============================================================

@RestController
@RequestMapping("/api/auth")

@Tag(name = "Authentication",
        description = "Google OAuth2 login entry point")

public class AuthController {

    // =========================================================
    // LOGIN – redirect to Google
    // =========================================================

    @Operation(
            summary = "Initiate Google OAuth2 login",
            description = "Redirects the browser to Google's " +
                    "consent screen. On success a JWT is returned."
    )

    @GetMapping("/login")

    public void login(
            HttpServletResponse response
    ) throws IOException {

        // Spring Security's built-in OAuth2 authorisation
        // redirect endpoint for the 'google' registration
        response.sendRedirect(
                "/oauth2/authorization/google"
        );
    }
}