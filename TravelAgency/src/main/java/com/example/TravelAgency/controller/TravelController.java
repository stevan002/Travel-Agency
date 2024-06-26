package com.example.TravelAgency.controller;

import com.example.TravelAgency.model.dto.travel.CreateTravelDTO;
import com.example.TravelAgency.service.TravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/travels")
public class TravelController {

    private final TravelService travelService;

    @PostMapping("/addTravel")
    public ResponseEntity<?> saveTravel(@RequestBody CreateTravelDTO travel) {
        try {
            return ResponseEntity.ok(travelService.save(travel));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTravel() {
        try {
            return ResponseEntity.ok(travelService.findAll());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateTravel/{id}")
    public ResponseEntity<?> updateTravel(@RequestBody CreateTravelDTO travel, @PathVariable Long id) {
        try {
            return ResponseEntity.ok(travelService.update(travel, id));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteTravel/{id}")
    public ResponseEntity<?> deleteTravel(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(travelService.deleteTravel(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
