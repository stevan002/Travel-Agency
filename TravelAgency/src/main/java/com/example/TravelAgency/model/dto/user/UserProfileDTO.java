package com.example.TravelAgency.model.dto.user;

import com.example.TravelAgency.model.enums.Role;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDTO {
    private String firstName;
    private String lastName;
    private String username;
    private String address;
    private Date dateOfBirth;
    private String JMBG;
    private String phoneNumber;
    private Timestamp dateOfRegistration;
    private Role role;
}
