package com.aman.ecommerce.productservice.service;

import com.aman.ecommerce.productservice.dto.LoginRequest;
import com.aman.ecommerce.productservice.dto.LoginResponse;
import com.aman.ecommerce.productservice.dto.RegisterRequest;
import com.aman.ecommerce.productservice.dto.UserDto;

public interface UserService {

    UserDto register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest request);
}
