package com.example.hotelserver.controllers.admin;

import com.example.hotelserver.services.admin.reservation.ReservationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationsService reservationsService;

    @GetMapping("/reservations/{id}")
    public ResponseEntity<?> getAllReservations(@PathVariable int id){
        try {
            return ResponseEntity.ok(reservationsService.getReservations(id));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something went wrong");
        }
    }

    @GetMapping("/reservation/{id}/{status}")
    public ResponseEntity<?> changeReservationStatus(@PathVariable Long id, @PathVariable String status){
        boolean success = reservationsService.changeReservationStatus(id, status);

        if (success)
            return ResponseEntity.ok().build();
        else
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
    }
}
