package com.aman.ecommerce.productservice.dto;


import com.aman.ecommerce.productservice.enums.Role;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String name;

    private String email;

    private Role role;
}
