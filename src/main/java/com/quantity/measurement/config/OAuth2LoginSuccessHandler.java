package com.quantity.measurement.config;

import com.quantity.measurement.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler
        implements AuthenticationSuccessHandler {

    private final JwtService jwtService;

    public OAuth2LoginSuccessHandler(
            JwtService jwtService
    ) {
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {

        OAuth2User user =
                (OAuth2User) authentication.getPrincipal();

        String email = user.getAttribute("email");

        String name = user.getAttribute("name");

        String picture = user.getAttribute("picture");

        String token = jwtService.generateToken(
                email,
                name,
                picture
        );

        response.setContentType("text/plain");

        response.getWriter().write(
                "JWT TOKEN:\n\n" + token
        );
    }
}