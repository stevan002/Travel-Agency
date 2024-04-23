package com.example.TravelAgency.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/demo")
    public ResponseEntity<String> demo(){
        return ResponseEntity.ok("Hello World");
    }

    @GetMapping("/admninOnly")
    public ResponseEntity<String> adminOnly(){
        return ResponseEntity.ok("Hello World for admin");
    }
}
