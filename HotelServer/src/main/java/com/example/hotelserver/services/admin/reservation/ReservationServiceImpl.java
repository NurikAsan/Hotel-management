package com.example.hotelserver.services.admin.reservation;


import com.example.hotelserver.dto.ReservationResponseDto;
import com.example.hotelserver.entity.Reservation;
import com.example.hotelserver.entity.Room;
import com.example.hotelserver.enums.ReservationStatus;
import com.example.hotelserver.repository.ReservationRepository;
import com.example.hotelserver.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationsService{
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    public static final int SEARCH_RESULT_PER_PAGE = 4;

    public ReservationResponseDto getReservations(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber, SEARCH_RESULT_PER_PAGE);
        Page<Reservation> reservationPage = reservationRepository.findAll(pageable);

        ReservationResponseDto reservationResponseDto = new ReservationResponseDto();

        reservationResponseDto.setPageNumber(reservationPage.getPageable().getPageNumber());
        reservationResponseDto.setTotalPage(reservationPage.getTotalPages());
        reservationResponseDto.setReservationDtoList(reservationPage
                .stream()
                .map(Reservation::getReservationDto)
                .collect(Collectors.toList())
        );
        return reservationResponseDto;
    }

    public boolean changeReservationStatus(Long id, String status){
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if(optionalReservation.isPresent()){
            Reservation reservation = optionalReservation.get();

            if(Objects.equals(status, "Approve"))
                reservation.setReservationStatus(ReservationStatus.APPROVED);
            else
                reservation.setReservationStatus(ReservationStatus.REJECTED);

            reservationRepository.save(reservation);

            Room room = reservation.getRoom();
            room.setAvailable(true);
            roomRepository.save(room);

            return true;
        }
        return false;
    }
}
