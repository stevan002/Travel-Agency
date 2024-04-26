package com.example.TravelAgency.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number_of_passenger", nullable = false)
    private int numberOfPassengers;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "travel_id", nullable = false)
    private Travel travel;

}
