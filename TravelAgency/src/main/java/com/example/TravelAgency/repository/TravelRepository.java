package com.example.TravelAgency.repository;

import com.example.TravelAgency.model.Travel;
import com.example.TravelAgency.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TravelRepository extends JpaRepository<Travel, Long> {
    List<Travel> findByDepartureDateTimeAfterAndAvailableSeatsGreaterThanEqual(LocalDateTime departureDateTime, int availableSeats);
    List<Travel> findAllByUser(User user);
}
