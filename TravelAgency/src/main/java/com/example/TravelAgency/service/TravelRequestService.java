package com.example.TravelAgency.service;

import com.example.TravelAgency.model.Reservation;
import com.example.TravelAgency.model.Travel;
import com.example.TravelAgency.model.TravelRequest;
import com.example.TravelAgency.model.User;
import com.example.TravelAgency.model.dto.request.CreateRequestDTO;
import com.example.TravelAgency.model.dto.travel.PriceTravelDTO;
import com.example.TravelAgency.model.enums.Role;
import com.example.TravelAgency.repository.ReservationRepository;
import com.example.TravelAgency.repository.TravelRepository;
import com.example.TravelAgency.repository.TravelRequestRepository;
import com.example.TravelAgency.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelRequestService {

    private final TravelRequestRepository travelRequestRepository;
    private final CategoryService categoryService;
    private final UserRepository userRepository;
    private final TravelRepository travelRepository;
    private final TravelService travelService;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;

    public TravelRequest createTravelRequest(CreateRequestDTO travelRequestDTO, String username) {
        if (travelRequestDTO.getDestination() == null ||
                travelRequestDTO.getDepartureDateTime() == null ||
                travelRequestDTO.getReturnDateTime() == null ||
                travelRequestDTO.getVehicle() == null ||
                travelRequestDTO.getAccommodationUnit() == null ||
                travelRequestDTO.getTotalSeats() <= 0) {
            throw new IllegalArgumentException("Missing data to create a travel request.");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        TravelRequest travelRequest = TravelRequest.builder()
                .destination(travelRequestDTO.getDestination())
                .departureDateTime(travelRequestDTO.getDepartureDateTime())
                .returnDateTime(travelRequestDTO.getReturnDateTime())
                .vehicle(travelRequestDTO.getVehicle())
                .accommodationUnit(travelRequestDTO.getAccommodationUnit())
                .totalSeats(travelRequestDTO.getTotalSeats())
                .createUser(user)
                .typeUser(Role.USER) // Setujemo ulogu na USER
                .build();

        return travelRequestRepository.save(travelRequest);
    }

    public List<TravelRequest> getAllRequestForAdmin(){
        return travelRequestRepository.findAllByTypeUser(Role.USER);
    }

    public List<TravelRequest> getAllResponseForUser(){
        return travelRequestRepository.findAllByTypeUser(Role.ADMIN);
    }

    public Travel createTravelForUser(Long travelRequestID, PriceTravelDTO priceDTO){
        TravelRequest travelRequest = travelRequestRepository.findById(travelRequestID)
                .orElseThrow(() -> new IllegalArgumentException("Invalid travel request ID: " + travelRequestID));

        Travel travel = new Travel();
        travel.setVehicle(travelRequest.getVehicle());
        travel.setAccommodationUnit(travelRequest.getAccommodationUnit());
        travel.setDestination(travelRequest.getDestination());
        travel.setDepartureDateTime(travelRequest.getDepartureDateTime());
        travel.setReturnDateTime(travelRequest.getReturnDateTime());
        travel.setNumberOfNights((int) travelRequest.getDepartureDateTime().until(travelRequest.getReturnDateTime(), ChronoUnit.DAYS));
        travel.setTotalSeats(travelRequest.getTotalSeats());
        travel.setAvailableSeats(travelRequest.getTotalSeats());
        travel.setCategory(categoryService.findById(priceDTO.getCategoryID()));

        travelRequest.setTypeUser(Role.ADMIN);

        travel.setPrice(priceDTO.getPrice());
        travel.setCreateFor(travelRequest.getCreateUser());

        travel = travelRepository.save(travel);
        travelRequestRepository.save(travelRequest);

        return travel;
    }

    public List<Travel> findIndividualTravelsForUser(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return travelRepository.findAllByUser(user);
    }

    public boolean responseForNewTravel(Long travelID, String answer, String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        Travel travel = travelService.findById(travelID);
        if(answer.equals("accept")){
            Reservation reservation = new Reservation();
            reservation.setTravel(travel);
            reservation.setNumberOfPassengers(travel.getAvailableSeats());
            reservation.setUser(user);
            travel.setAvailableSeats(0);
            travelRepository.save(travel);
            reservationRepository.save(reservation);
            return true;
        }

        travelRepository.delete(travel);
        return false;
    }
}
