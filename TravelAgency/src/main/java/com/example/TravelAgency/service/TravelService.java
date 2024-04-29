package com.example.TravelAgency.service;

import com.example.TravelAgency.model.Category;
import com.example.TravelAgency.model.Travel;
import com.example.TravelAgency.model.dto.travel.CreateTravelDTO;
import com.example.TravelAgency.model.dto.travel.GetTravelDTO;
import com.example.TravelAgency.repository.ReservationRepository;
import com.example.TravelAgency.repository.TravelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TravelService {

    private final TravelRepository travelRepository;
    private final CategoryService categoryService;
    private final ReservationRepository reservationRepository;

    @Transactional
    public Travel save(CreateTravelDTO createTravel) {
        validateTravelDTO(createTravel);

        Category category = categoryService.findById(createTravel.getCategoryId());
        if(category == null) {
            throw new IllegalArgumentException("Category not found for id: " + createTravel.getCategoryId());
        }

        LocalDateTime departureDateTime = createTravel.getDepartureDateTime();
        LocalDateTime returnDateTime = createTravel.getReturnDateTime();
        long numberOfNights = Duration.between(departureDateTime, returnDateTime).toDays();

        Travel travel = Travel.builder()
                .vehicle(createTravel.getVehicle())
                .accommodationUnit(createTravel.getAccommodationUnit())
                .destination(createTravel.getDestination())
                .departureDateTime(createTravel.getDepartureDateTime())
                .returnDateTime(createTravel.getReturnDateTime())
                .numberOfNights((int) numberOfNights)
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
                createTravel.getDepartureDateTime().isAfter(createTravel.getReturnDateTime().minusHours(24)) ||
                createTravel.getPrice() == null ||
                createTravel.getPrice().compareTo(BigDecimal.ZERO) <= 0 ||
                createTravel.getTotalSeats() <= 0 ||
                createTravel.getAvailableSeats() <= 0 ||
                createTravel.getTotalSeats() < createTravel.getAvailableSeats()) {
            throw new IllegalArgumentException("Some of the travel attributes are invalid.");
        }
    }


    @Transactional(readOnly = true)
    public List<GetTravelDTO> findAll() {
        // Use Stream API for concise mapping
        return travelRepository.findAll().stream()
                .map(travel -> new GetTravelDTO(
                        travel.getVehicle(),
                        travel.getAccommodationUnit(),
                        travel.getDestination(),
                        travel.getDepartureDateTime(),
                        travel.getReturnDateTime(),
                        travel.getNumberOfNights(),
                        travel.getPrice(),
                        travel.getTotalSeats(),
                        travel.getAvailableSeats(),
                        travel.getCategory().getNameCategory()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GetTravelDTO> findAllForUsers(LocalDateTime departureDateTime) {
        int reservationSeats = 1;
        // Use Stream API for concise mapping
        return travelRepository.findByDepartureDateTimeAfterAndAvailableSeatsGreaterThanEqualAndCreateForIsNull(departureDateTime, reservationSeats).stream()
                .map(travel -> new GetTravelDTO(
                        travel.getVehicle(),
                        travel.getAccommodationUnit(),
                        travel.getDestination(),
                        travel.getDepartureDateTime(),
                        travel.getReturnDateTime(),
                        travel.getNumberOfNights(),
                        travel.getPrice(),
                        travel.getTotalSeats(),
                        travel.getAvailableSeats(),
                        travel.getCategory().getNameCategory()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Travel update(CreateTravelDTO updatedTravel, Long id) {
        Travel travel = travelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Travel not found for id: " + id));

        Category category;
        if (updatedTravel.getCategoryId() == null) {
            category = travel.getCategory();
        } else {
            category = categoryService.findById(updatedTravel.getCategoryId());
            if (category == null) {
                throw new IllegalArgumentException("Category not found for id: " + updatedTravel.getCategoryId());
            }
        }

        LocalDateTime departureDateTime = updatedTravel.getDepartureDateTime() != null ? updatedTravel.getDepartureDateTime() : travel.getDepartureDateTime();
        LocalDateTime returnDateTime = updatedTravel.getReturnDateTime() != null ? updatedTravel.getReturnDateTime() : travel.getReturnDateTime();
        long numberOfNights = Duration.between(departureDateTime, returnDateTime).toDays();

        travel.setVehicle(updatedTravel.getVehicle() != null ? updatedTravel.getVehicle() : travel.getVehicle());
        travel.setAccommodationUnit(updatedTravel.getAccommodationUnit() != null ? updatedTravel.getAccommodationUnit() : travel.getAccommodationUnit());
        travel.setDestination(updatedTravel.getDestination() != null ? updatedTravel.getDestination() : travel.getDestination());
        travel.setDepartureDateTime(departureDateTime);
        travel.setReturnDateTime(returnDateTime);
        travel.setNumberOfNights((int) numberOfNights);
        travel.setPrice(updatedTravel.getPrice() != null && updatedTravel.getPrice().compareTo(BigDecimal.ZERO) > 0 ? updatedTravel.getPrice() : travel.getPrice());
        travel.setTotalSeats(updatedTravel.getTotalSeats() > 0 ? updatedTravel.getTotalSeats() : travel.getTotalSeats());
        travel.setAvailableSeats(updatedTravel.getAvailableSeats() > 0 ? updatedTravel.getAvailableSeats() : travel.getAvailableSeats());
        travel.setCategory(category);

        return travelRepository.save(travel);
    }

    @Transactional(readOnly = true)
    public Travel findById(Long id) {
        return travelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Travel with ID " + id + " not found."));
    }

    @Transactional
    public Travel deleteTravel(Long id){
        Travel deletedTravel = findById(id);
        if(reservationRepository.countByTravel(deletedTravel) != 0){
            throw new IllegalArgumentException("Travel cannot be deleted, has reservations");
        }
        travelRepository.delete(deletedTravel);
        return deletedTravel;
    }
}
