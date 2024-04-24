package com.example.TravelAgency.model.dto.user;

import com.example.TravelAgency.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String address;
    private Date dateOfBirth;
    private String JMBG;
    private String phoneNumber;
}
