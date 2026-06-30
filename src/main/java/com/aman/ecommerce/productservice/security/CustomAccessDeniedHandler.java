package com.aman.ecommerce.productservice.security;


import com.aman.ecommerce.productservice.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ErrorResponse error = new ErrorResponse(LocalDateTime.now().toString(), HttpServletResponse.SC_FORBIDDEN, "Forbidden","You don't have permission to access this resource", request.getRequestURI());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        response.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        mapper.writeValue(response.getOutputStream(), error);
    }

}

