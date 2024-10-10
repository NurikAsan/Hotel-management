package com.example.hotelserver.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReservationResponseDto {
    private Integer totalPage;
    private Integer pageNumber;
    private List<ReservationDto> reservationDtoList;
}
