package com.example.hotelserver.services.customer.booking;

import com.example.hotelserver.dto.ReservationDto;
import com.example.hotelserver.dto.ReservationResponseDto;

public interface BookingService {
    boolean postReservation(ReservationDto dto);
    ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber);
}
