package com.example.TravelAgency.service;

import com.example.TravelAgency.model.Category;
import com.example.TravelAgency.model.Travel;
import com.example.TravelAgency.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final CategoryService categoryService;

    public Travel save(Travel travel) {
        if (travel.getVehicle() == null ||
                travel.getAccommodationUnit() == null ||
                travel.getDestination() == null ||
                travel.getDepartureDateTime() == null ||
                travel.getReturnDateTime() == null ||
                travel.getNumberOfNights() <= 0 ||
                travel.getPrice() == null || travel.getPrice().compareTo(BigDecimal.ZERO) <= 0 ||
                travel.getTotalSeats() <= 0 || travel.getAvailableSeats() <= 0 ||
                travel.getTotalSeats() <= travel.getAvailableSeats()) {
            throw new IllegalArgumentException("Some of the travel attributes are invalid.");
        }

        Category category = categoryService.findById(travel.getCategory().getId());
        travel.setCategory(category);

        return travelRepository.save(travel);
    }

    public List<Travel> findAll(){
        List<Travel> travels = travelRepository.findAll();
        if (travels.isEmpty()) {
            throw new IllegalStateException("There are no travels created.");
        }
        return travels;
    }

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
