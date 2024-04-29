package com.example.TravelAgency.model.dto.request;

import com.example.TravelAgency.model.enums.AccommodationUnit;
import com.example.TravelAgency.model.enums.Role;
import com.example.TravelAgency.model.enums.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequestDTO {

    private String destination;
    private LocalDateTime departureDateTime;
    private LocalDateTime returnDateTime;
    private Vehicle vehicle;
    private AccommodationUnit accommodationUnit;
    private int totalSeats;
}
