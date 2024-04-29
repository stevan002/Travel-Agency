package com.example.TravelAgency.controller;

import com.example.TravelAgency.model.dto.reservation.CreateReservationDTO;
import com.example.TravelAgency.service.AuthenticationService;
import com.example.TravelAgency.service.JwtService;
import com.example.TravelAgency.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<?> createReservation(@RequestBody CreateReservationDTO reservation, HttpServletRequest request) {
        try {
            String token = authenticationService.extractTokenFromRequest(request);
            String username = jwtService.extractUsername(token);
            return ResponseEntity.ok(reservationService.save(reservation, username));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllByUser")
    public ResponseEntity<?> findAllByUser(HttpServletRequest request) {
        try {
            String token = authenticationService.extractTokenFromRequest(request);
            String username = jwtService.extractUsername(token);
            return ResponseEntity.ok(reservationService.findByUserId(username));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable Long id, HttpServletRequest request) {
        try {
            String token = authenticationService.extractTokenFromRequest(request);
            String username = jwtService.extractUsername(token);
            boolean deleted = reservationService.deleteReservationById(id, username);
            if (deleted) {
                return ResponseEntity.ok("Reservation deleted successfully!");
            } else {
                // Shouldn't reach here, but handle for safety
                return ResponseEntity.internalServerError().body("An error occurred during deletion.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
