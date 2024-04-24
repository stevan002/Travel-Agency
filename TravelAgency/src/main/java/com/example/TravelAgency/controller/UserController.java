package com.example.TravelAgency.controller;

import com.example.TravelAgency.model.dto.user.ChangePasswordDTO;
import com.example.TravelAgency.service.AuthenticationService;
import com.example.TravelAgency.service.JwtService;
import com.example.TravelAgency.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    @GetMapping("/profile")
    public ResponseEntity<?> getUserProfile(HttpServletRequest request) {

        try {
            String token = authenticationService.extractTokenFromRequest(request);
            String username = jwtService.extractUsername(token);
            return ResponseEntity.ok(userService.getUserProfile(username));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO, HttpServletRequest request) {
        try {
            String token = authenticationService.extractTokenFromRequest(request);
            String username = jwtService.extractUsername(token);
            userService.changePassword(username, changePasswordDTO);
            return ResponseEntity.ok("Successfully changed password");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
