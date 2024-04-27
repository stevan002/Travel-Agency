package com.example.TravelAgency.model;

import com.example.TravelAgency.model.enums.AccommodationUnit;
import com.example.TravelAgency.model.enums.Role;
import com.example.TravelAgency.model.enums.Vehicle;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "travel_request")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "departure_datetime", nullable = false)
    private LocalDateTime departureDateTime;

    @Column(name = "return_datetime", nullable = false)
    private LocalDateTime returnDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle", nullable = false)
    private Vehicle vehicle;

    @Enumerated(EnumType.STRING)
    @Column(name = "accommodation_unit", nullable = false)
    private AccommodationUnit accommodationUnit;

    @Column(name = "total_seats", nullable = false)
    private int totalSeats;

    @Column(name = "comment_admin")
    private String commentAdmin;

    @Column(name = "approved")
    private Boolean approved;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "create_user_id")
    private User createUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_user", nullable = false)
    private Role typeUser;

}
