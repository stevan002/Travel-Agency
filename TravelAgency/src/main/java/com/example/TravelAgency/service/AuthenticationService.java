package com.example.TravelAgency.service;

import com.example.TravelAgency.model.dto.user.AuthenticationResponse;
import com.example.TravelAgency.model.enums.Role;
import com.example.TravelAgency.model.User;
import com.example.TravelAgency.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(User request){

        if (request == null || request.getFirstName() == null || request.getLastName() == null ||
                request.getUsername() == null || request.getPassword() == null ||
                request.getDateOfBirth() == null || request.getJMBG() == null ||
                request.getPhoneNumber() == null) {
            throw new IllegalArgumentException("Must input all data for user.");
        }

        if (repository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username must be unique.");
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAddress(request.getAddress());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setJMBG(request.getJMBG());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(Role.USER);

        user = repository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(User request){

        User user = repository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Wrong username or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong username or password.");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String token = jwtService.generateToken(user);

        return new AuthenticationResponse(token);
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        throw new IllegalArgumentException("Not found JWT token in header 'Authorization'.");
    }

}
