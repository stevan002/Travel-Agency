package com.example.TravelAgency.service;

import com.example.TravelAgency.model.Reservation;
import com.example.TravelAgency.model.Travel;
import com.example.TravelAgency.model.User;
import com.example.TravelAgency.model.dto.reservation.CreateReservationDTO;
import com.example.TravelAgency.repository.ReservationRepository;
import com.example.TravelAgency.repository.TravelRepository;
import com.example.TravelAgency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final TravelService travelService;
    private final TravelRepository travelRepository;

    public Reservation save(CreateReservationDTO createReservation, String username) {
        if(createReservation.getNumberOfPassengers() <= 0 || createReservation.getTravelID() == null){
            throw new IllegalArgumentException("Number of passengers must be greater than 0, and must chose travel for trip");
        }
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        Travel travel = travelService.findById(createReservation.getTravelID());

        if((travel.getAvailableSeats() - createReservation.getNumberOfPassengers()) < 0){
            throw new IllegalArgumentException("You can't reserve a passenger that does not have enough seats");
        }

        travel.setAvailableSeats(travel.getAvailableSeats() - createReservation.getNumberOfPassengers());
        Reservation reservation = Reservation.builder()
                .numberOfPassengers(createReservation.getNumberOfPassengers())
                .user(user)
                .travel(travel)
                .build();

        travelRepository.save(travel);
        return reservationRepository.save(reservation);
    }


    public List<Reservation> findByUserId(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        List<Reservation> reservations = reservationRepository.findByUserId(user.getId());
        if(reservations == null){
            throw new IllegalArgumentException("Reservation not found");
        }

        return reservations;
    }

    public boolean deleteReservationById(Long reservationId, String username) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new IllegalArgumentException("Not found reservation with given id."));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departureDateTimeAfter48Hours = now.plusHours(48);

        // Check departure time before deletion
        if (reservation.getTravel().getDepartureDateTime().isBefore(departureDateTimeAfter48Hours)) {
            throw new IllegalArgumentException("Departure time is within 48 hours. Cannot delete.");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found."));

        if (reservation.getUser() == null || !reservation.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You are not allowed to delete someone else's reservation");
        }

        int guestCount = reservation.getNumberOfPassengers();
        Travel travel = travelService.findById(reservation.getTravel().getId());
        if (travel == null) {
            throw new IllegalArgumentException("Travel not found");
        }

        travel.setAvailableSeats(travel.getAvailableSeats() + guestCount);
        travelRepository.save(travel);

        reservationRepository.deleteById(reservationId);
        return true; // Indicate successful deletion (can be removed if not using return value)
    }

}
