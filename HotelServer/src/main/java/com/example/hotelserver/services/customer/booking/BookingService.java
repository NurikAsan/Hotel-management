package com.example.hotelserver.services.customer.booking;

import com.example.hotelserver.dto.ReservationDto;

public interface BookingService {
    boolean postReservation(ReservationDto dto);
}
