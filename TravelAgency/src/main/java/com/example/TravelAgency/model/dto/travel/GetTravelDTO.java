package com.example.TravelAgency.model.dto.travel;

import com.example.TravelAgency.model.enums.AccommodationUnit;
import com.example.TravelAgency.model.enums.ECategory;
import com.example.TravelAgency.model.enums.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTravelDTO {
    private Vehicle vehicle;
    private AccommodationUnit accommodationUnit;
    private String destination;
    private LocalDateTime departureDateTime;
    private LocalDateTime returnDateTime;
    private int numberOfNights;
    private BigDecimal price;
    private int totalSeats;
    private int availableSeats;
    private ECategory category;
    private String promotional;
}
