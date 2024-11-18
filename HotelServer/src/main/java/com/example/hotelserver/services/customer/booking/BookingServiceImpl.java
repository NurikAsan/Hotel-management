package com.example.hotelserver.services.customer.booking;

import com.example.hotelserver.dto.ReservationDto;
import com.example.hotelserver.dto.ReservationResponseDto;
import com.example.hotelserver.entity.Reservation;
import com.example.hotelserver.entity.Room;
import com.example.hotelserver.entity.User;
import com.example.hotelserver.enums.ReservationStatus;
import com.example.hotelserver.repository.ReservationRepository;
import com.example.hotelserver.repository.RoomRepository;
import com.example.hotelserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService{
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public boolean postReservation(ReservationDto dto){
        Optional<User> user = userRepository.findById(dto.getUserId());
        Optional<Room> room = roomRepository.findById(dto.getRoomId());

        if(user.isPresent() && room.isPresent()){
            Reservation reservation = new Reservation();

            reservation.setRoom(room.get());
            reservation.setUser(user.get());
            reservation.setCheckInDate(dto.getCheckInDate());
            reservation.setCheckOutDate(dto.getCheckOutDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);

            Long days = ChronoUnit.DAYS.between(dto.getCheckInDate(), dto.getCheckOutDate());
            reservation.setPrice(room.get().getPrice() * days);

            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    public ReservationResponseDto getAllReservationByUserId(Long userId, int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Reservation> reservationPage = reservationRepository.findAllByUserId(pageable, userId);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();
        reservationResponseDto.setReservationDtoList(reservationPage
                .stream().map(Reservation::getReservationDto).collect(Collectors.toList()));

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPage(reservationPage.getTotalPages());

        return reservationResponseDto;
    }
}
