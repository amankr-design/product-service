package com.aman.ecommerce.productservice.controller;


import com.aman.ecommerce.productservice.dto.LoginRequest;
import com.aman.ecommerce.productservice.dto.LoginResponse;
import com.aman.ecommerce.productservice.dto.RegisterRequest;
import com.aman.ecommerce.productservice.dto.UserDto;
import com.aman.ecommerce.productservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController { //userController

    private final UserService userService;

    public AuthController(UserService userService){

        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest request){
        return  ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request){

        return ResponseEntity.ok(userService.login(request));
    }
}
