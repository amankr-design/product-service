package com.aman.ecommerce.productservice.security;

import com.aman.ecommerce.productservice.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException ) throws IOException, ServletException{

        ErrorResponse error = new ErrorResponse(LocalDateTime.now().toString(), HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized","JWT token is missing or invalid", request.getRequestURI());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();

        mapper.findAndRegisterModules();

        mapper.writeValue(response.getOutputStream(), error);
    }

}
