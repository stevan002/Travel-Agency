package com.example.TravelAgency.repository;

import com.example.TravelAgency.model.Reservation;
import com.example.TravelAgency.model.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUserId(Long userId);
    void deleteById(Long id);
    Long countByTravel(Travel travel);
}
