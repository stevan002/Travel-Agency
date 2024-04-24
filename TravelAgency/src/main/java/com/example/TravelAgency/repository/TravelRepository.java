package com.example.TravelAgency.repository;

import com.example.TravelAgency.model.Travel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelRepository extends JpaRepository<Travel, Integer> {
}
