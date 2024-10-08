package com.example.hotelserver.services.customer.room;

import com.example.hotelserver.dto.RoomsResponseDto;

public interface RoomService {
    RoomsResponseDto getAvailableRooms(int pageNumber);
}
