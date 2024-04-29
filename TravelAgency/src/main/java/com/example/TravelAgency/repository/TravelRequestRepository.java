package com.example.TravelAgency.repository;

import com.example.TravelAgency.model.TravelRequest;
import com.example.TravelAgency.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelRequestRepository extends JpaRepository<TravelRequest, Long> {
}
