package com.example.TravelAgency.model.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReservationDTO {

    private int numberOfPassengers;
    private Long travelID;
}
