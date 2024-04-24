package com.example.TravelAgency.controller;

import com.example.TravelAgency.model.Category;
import com.example.TravelAgency.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/addCategory")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        try {
            categoryService.save(category);
            return ResponseEntity.ok().body("Category added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/getAllCategory")
    public ResponseEntity<?> getAllCategory(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/getCategory/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.findById(id));
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.deleteById(id));
    }
}
