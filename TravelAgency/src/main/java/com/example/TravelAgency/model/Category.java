package com.example.TravelAgency.model;

import com.example.TravelAgency.model.enums.ECategory;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_category", nullable = false)
    private ECategory nameCategory;

    @Column(name = "description", nullable = false)
    private String description;
}
