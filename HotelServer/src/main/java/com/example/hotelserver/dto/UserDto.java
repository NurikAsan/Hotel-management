package com.example.hotelserver.dto;

import com.example.hotelserver.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private UserRole userRole;
}
