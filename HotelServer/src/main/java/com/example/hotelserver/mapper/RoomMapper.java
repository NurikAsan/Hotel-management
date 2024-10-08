package com.example.hotelserver.mapper;

import com.example.hotelserver.dto.RoomDto;
import com.example.hotelserver.entity.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoomMapper extends Mappable<Room, RoomDto> {
}
