package com.example.TravelAgency.model;

import com.example.TravelAgency.model.enums.AccommodationUnit;
import com.example.TravelAgency.model.enums.Vehicle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle", nullable = false)
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column(name = "accommodation_unit", nullable = false)
    private AccommodationUnit accommodationUnit;

    @Column(name = "destination")
    private String destination;

    @Column(name = "departure_datetime", nullable = false)
    private LocalDateTime departureDateTime;

    @Column(name = "return_datetime", nullable = false)
    private LocalDateTime returnDateTime;

    @Column(name = "number_of_nights", nullable = false)
    private int numberOfNights;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "total_seats", nullable = false)
    private int totalSeats;

    @Column(name = "available_seats", nullable = false)
    private int availableSeats;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User createFor;
}
