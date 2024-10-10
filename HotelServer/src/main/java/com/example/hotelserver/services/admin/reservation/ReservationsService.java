package com.example.hotelserver.services.admin.reservation;

import com.example.hotelserver.dto.ReservationResponseDto;

public interface ReservationsService {
    ReservationResponseDto getReservations(int pageNumber);
    boolean changeReservationStatus(Long id, String status);

}
