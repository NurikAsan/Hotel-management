package com.example.hotelserver.dto;

import lombok.Data;

@Data
public class RoomDto {
    private String name;
    private String type;
    private Long price;
    private Long id;
    private boolean available;
}
