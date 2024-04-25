package com.example.TravelAgency.service;

import com.example.TravelAgency.model.Category;
import com.example.TravelAgency.model.Travel;
import com.example.TravelAgency.model.dto.travel.CreateTravelDTO;
import com.example.TravelAgency.repository.TravelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final CategoryService categoryService;

    @Transactional
    public Travel save(CreateTravelDTO createTravel) {
        validateTravelDTO(createTravel);

        Category category = categoryService.findById(createTravel.getCategoryId());
        if(category == null) {
            throw new IllegalArgumentException("Category not found for id: " + createTravel.getCategoryId());
        }

        Travel travel = Travel.builder()
                .vehicle(createTravel.getVehicle())
                .accommodationUnit(createTravel.getAccommodationUnit())
                .destination(createTravel.getDestination())
                .departureDateTime(createTravel.getDepartureDateTime())
                .returnDateTime(createTravel.getReturnDateTime())
                .numberOfNights(createTravel.getNumberOfNights())
                .price(createTravel.getPrice())
                .totalSeats(createTravel.getTotalSeats())
                .availableSeats(createTravel.getAvailableSeats())
                .category(category)
                .build();

        return travelRepository.save(travel);
    }

    private void validateTravelDTO(CreateTravelDTO createTravel) {
        if (createTravel == null ||
                createTravel.getVehicle() == null ||
                createTravel.getAccommodationUnit() == null ||
                createTravel.getDestination() == null ||
                createTravel.getDepartureDateTime() == null ||
                createTravel.getReturnDateTime() == null ||
                createTravel.getNumberOfNights() <= 0 ||
                createTravel.getPrice() == null ||
                createTravel.getPrice().compareTo(BigDecimal.ZERO) <= 0 ||
                createTravel.getTotalSeats() <= 0 ||
                createTravel.getAvailableSeats() <= 0 ||
                createTravel.getTotalSeats() <= createTravel.getAvailableSeats()) {
            throw new IllegalArgumentException("Some of the travel attributes are invalid.");
        }
    }


    public List<Travel> findAll(){
        List<Travel> travels = travelRepository.findAll();
        if (travels.isEmpty()) {
            throw new IllegalStateException("There are no travels created.");
        }
        return travels;
    }

    @Transactional
    public Travel update(Travel updatedTravel, Long id) {
        Travel existingTravel = travelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Travel with ID " + updatedTravel.getId() + " not found."));

        if (updatedTravel.getVehicle() != null) {
            existingTravel.setVehicle(updatedTravel.getVehicle());
        }
        if (updatedTravel.getAccommodationUnit() != null) {
            existingTravel.setAccommodationUnit(updatedTravel.getAccommodationUnit());
        }
        if (updatedTravel.getDestination() != null) {
            existingTravel.setDestination(updatedTravel.getDestination());
        }
        if (updatedTravel.getDepartureDateTime() != null) {
            existingTravel.setDepartureDateTime(updatedTravel.getDepartureDateTime());
        }
        if (updatedTravel.getReturnDateTime() != null) {
            existingTravel.setReturnDateTime(updatedTravel.getReturnDateTime());
        }
        if (updatedTravel.getNumberOfNights() > 0) {
            existingTravel.setNumberOfNights(updatedTravel.getNumberOfNights());
        }
        if (updatedTravel.getPrice() != null && updatedTravel.getPrice().compareTo(BigDecimal.ZERO) > 0) {
            existingTravel.setPrice(updatedTravel.getPrice());
        }
        if (updatedTravel.getTotalSeats() > 0 && updatedTravel.getAvailableSeats() > 0) {
            if (updatedTravel.getTotalSeats() >= updatedTravel.getAvailableSeats()) {
                existingTravel.setTotalSeats(updatedTravel.getTotalSeats());
                existingTravel.setAvailableSeats(updatedTravel.getAvailableSeats());
            } else {
                throw new IllegalArgumentException("Total seats cannot be less than available seats.");
            }
        } else {
            throw new IllegalArgumentException("Total seats cannot be less than 0");
        }

        if (updatedTravel.getCategory() != null) {
            existingTravel.setCategory(updatedTravel.getCategory());
        }

        return travelRepository.save(existingTravel);
    }

    public Travel findById(Long id) {
        return travelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Travel with ID " + id + " not found."));
    }

    public Travel deleteTravel(Long id){
        Travel deletedTravel = findById(id);
        travelRepository.delete(deletedTravel);
        return deletedTravel;
    }
}
