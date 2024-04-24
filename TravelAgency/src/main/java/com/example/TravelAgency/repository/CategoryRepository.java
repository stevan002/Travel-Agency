package com.example.TravelAgency.repository;

import com.example.TravelAgency.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
