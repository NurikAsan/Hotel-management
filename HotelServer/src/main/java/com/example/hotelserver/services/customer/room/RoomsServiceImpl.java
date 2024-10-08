package com.example.hotelserver.services.customer.room;

import com.example.hotelserver.dto.RoomsResponseDto;
import com.example.hotelserver.entity.Room;
import com.example.hotelserver.mapper.RoomMapper;
import com.example.hotelserver.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;


    public RoomsResponseDto getAvailableRooms(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Room> roomPage = roomRepository.findByAvailable(true, pageable);

        RoomsResponseDto dto = new RoomsResponseDto();
        dto.setPageNumber(roomPage.getPageable().getPageNumber());
        dto.setTotalPages(roomPage.getTotalPages());
        dto.setRoomDtoList(roomPage.stream().map(roomMapper::toDto).collect(Collectors.toList()));
        return dto;
    }

}
