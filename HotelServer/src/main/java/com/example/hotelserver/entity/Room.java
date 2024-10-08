package com.example.hotelserver.entity;

import com.example.hotelserver.dto.RoomDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private Long price;
    private boolean available;

    public RoomDto getRoomDto(){
        RoomDto dto = new RoomDto();
        return null;
    }
}
