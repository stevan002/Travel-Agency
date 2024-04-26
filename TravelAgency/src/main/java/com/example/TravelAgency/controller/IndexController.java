package com.example.TravelAgency.controller;

import com.example.TravelAgency.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/index")
public class IndexController {

    private final TravelService travelService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTravelForUser() {
        try {
            LocalDateTime timeNow = LocalDateTime.now();
            return ResponseEntity.ok(travelService.findAllForUsers(timeNow));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
