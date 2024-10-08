package com.example.hotelserver.services.admin.rooms;

import com.example.hotelserver.dto.RoomDto;
import com.example.hotelserver.dto.RoomsResponseDto;

public interface RoomService {
    boolean postRoom(RoomDto dto);
    RoomsResponseDto getAllRooms(int pageNumber);
    RoomDto getRoomById(Long id);
    boolean update(Long id, RoomDto dto);
    void deleteRoom(Long id);

}
