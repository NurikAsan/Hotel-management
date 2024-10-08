package com.example.hotelserver.services.admin.rooms;

import com.example.hotelserver.dto.RoomDto;
import com.example.hotelserver.dto.RoomsResponseDto;
import com.example.hotelserver.entity.Room;
import com.example.hotelserver.mapper.RoomMapper;
import com.example.hotelserver.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public boolean postRoom(RoomDto dto){
        try{
            Room room = new Room();
            room.setName(dto.getName());
            room.setId(dto.getId());
            room.setType(dto.getType());
            room.setPrice(dto.getPrice());
            room.setAvailable(true);

            roomRepository.save(room);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public RoomsResponseDto getAllRooms(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, 6);
        Page<Room> roomPage = roomRepository.findAll(pageable);

        RoomsResponseDto dto = new RoomsResponseDto();
        dto.setPageNumber(roomPage.getPageable().getPageNumber());
        dto.setTotalPages(roomPage.getTotalPages());
        dto.setRoomDtoList(roomPage.stream().map(roomMapper::toDto).collect(Collectors.toList()));
        return dto;
    }

    public RoomDto getRoomById(Long id){
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room with " + id + " not found"));
        return roomMapper.toDto(room);
    }

    public boolean update(Long id, RoomDto dto){
        Optional<Room> updatedRoom = roomRepository.findById(id);
        if(updatedRoom.isPresent()) {
            Room room = updatedRoom.get();
            room.setPrice(dto.getPrice());
            room.setType(dto.getType());
            room.setName(dto.getName());
            roomRepository.save(room);
            return true;
        }
        return false;
    }

    public void deleteRoom(Long id){
        Optional<Room> room = roomRepository.findById(id);
        if(room.isPresent())
            roomRepository.deleteById(id);
        else
            throw new EntityNotFoundException("Room not delete");
    }
}
