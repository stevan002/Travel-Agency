package com.example.TravelAgency.model.dto.travel;

import com.example.TravelAgency.model.enums.AccommodationUnit;
import com.example.TravelAgency.model.enums.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTravelDTO {
    private Vehicle vehicle;
    private AccommodationUnit accommodationUnit;
    private String destination;
    private LocalDateTime departureDateTime;
    private LocalDateTime returnDateTime;
    private int numberOfNights;
    private BigDecimal price;
    private BigDecimal promotionalPrice;
    private LocalDateTime promotionalDateTime;
    private int totalSeats;
    private int availableSeats;
    private Long categoryId;
}
