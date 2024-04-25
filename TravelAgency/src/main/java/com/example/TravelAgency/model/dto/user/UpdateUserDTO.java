package com.example.TravelAgency.model.dto.user;

import lombok.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String address;
    private Date dateOfBirth;
    private String JMBG;
    private String phoneNumber;
}
