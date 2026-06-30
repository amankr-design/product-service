package com.aman.ecommerce.productservice.service;


import com.aman.ecommerce.productservice.dto.RegisterRequest;
import com.aman.ecommerce.productservice.dto.LoginRequest;
import com.aman.ecommerce.productservice.dto.LoginResponse;
import com.aman.ecommerce.productservice.auth.JwtService;
import com.aman.ecommerce.productservice.dto.UserDto;
import com.aman.ecommerce.productservice.enums.Role;
import com.aman.ecommerce.productservice.exception.UserAlreadyExistsException;
import com.aman.ecommerce.productservice.repository.UserRepository;
import com.aman.ecommerce.productservice.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, JwtService jwtService){

        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;

        this.modelMapper = modelMapper;

        this.jwtService = jwtService;
    }

    @Override
    public UserDto register(RegisterRequest registerRequest){

        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new UserAlreadyExistsException("Email already exists");
        }

        User user = modelMapper.map(registerRequest, User.class);

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        user.setRole(Role.USER);

        User savedUser = userRepository.save(user);

        return  modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public LoginResponse login(LoginRequest request){

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())){

            throw new RuntimeException("Invalid email or password");

        }


        String token = jwtService.generateToken(user.getEmail());

        return  new LoginResponse(token, "Login Successful");
    }


}
