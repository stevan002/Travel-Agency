package com.example.TravelAgency.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user_table")
@Getter
@Setter
//@RequiredArgsConstructor
//@NoArgsConstructor
//@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "date_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "jmbg", unique = true, nullable = false)
    @Size(min = 13, max = 13)
    @Pattern(regexp="\\d{13}", message = "Number only in JMBG")
    private String JMBG;

    @Column(name = "phone_number", unique = true, nullable = false)
    @Pattern(regexp="\\d+", message="Number only in phone number")
    private String phoneNumber;

    @Column(name = "time_reservation", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp dateOfRegistration = new Timestamp(System.currentTimeMillis());

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
