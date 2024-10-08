package com.example.hotelserver.mapper;

import com.example.hotelserver.dto.UserDto;
import com.example.hotelserver.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends Mappable<User, UserDto> {
}
