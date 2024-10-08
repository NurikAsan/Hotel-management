package com.example.hotelserver.dto;

import com.example.hotelserver.enums.UserRole;
import lombok.Data;

@Data
public class RequestDto {
    private String username;
    private String password;
    private String name;
}
