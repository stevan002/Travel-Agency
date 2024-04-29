package com.example.TravelAgency.controller;

import com.example.TravelAgency.model.dto.request.CreateRequestDTO;
import com.example.TravelAgency.model.dto.travel.PriceTravelDTO;
import com.example.TravelAgency.service.AuthenticationService;
import com.example.TravelAgency.service.JwtService;
import com.example.TravelAgency.service.TravelRequestService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TravelRequestController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final TravelRequestService travelRequestService;

    // FOR USER
    @PostMapping("/user/createRequest")
    public ResponseEntity<?> createRequestForNewTravel(@RequestBody CreateRequestDTO createRequestDTO, HttpServletRequest request){
        try{
            String token = authenticationService.extractTokenFromRequest(request);
            String username = jwtService.extractUsername(token);
            return ResponseEntity.ok(travelRequestService.createTravelRequest(createRequestDTO, username));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //FOR ADMIN
    @GetMapping("/admin/getAllRequest")
    public ResponseEntity<?> findAllRequestForAdmin(){
        try {
            return ResponseEntity.ok(travelRequestService.getAllRequestForAdmin());
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //FOR ADMIN
    @DeleteMapping("/admin/request/{id}/decline")
    public ResponseEntity<?> declineRequest(@PathVariable Long id){
        try {
            return ResponseEntity.ok(travelRequestService.declineRequest(id));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // FOR ADMIN
    @PostMapping("/admin/request/{id}/createTravel")
    public ResponseEntity<?> createNewTravel(@PathVariable Long id, @RequestBody PriceTravelDTO travelDTO){
        try {
            return ResponseEntity.ok(travelRequestService.createTravelForUser(id, travelDTO));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // FOR USER
    @GetMapping("/user/travel/bids")
    public ResponseEntity<?> findTravelsBid(HttpServletRequest request){
        try {
            String token = authenticationService.extractTokenFromRequest(request);
            String username = jwtService.extractUsername(token);
            return ResponseEntity.ok(travelRequestService.findIndividualTravelsForUser(username));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // FOR USER
    @PostMapping("/user/travel/{id}/{answerBid}")
    public ResponseEntity<?> answerBid(@PathVariable Long id, @PathVariable String answerBid, HttpServletRequest request){
        try{
            String token = authenticationService.extractTokenFromRequest(request);
            String username = jwtService.extractUsername(token);
            return ResponseEntity.ok(travelRequestService.responseForNewTravel(id, answerBid, username));
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
