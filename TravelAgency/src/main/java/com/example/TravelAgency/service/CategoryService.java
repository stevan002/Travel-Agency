package com.example.TravelAgency.service;

import com.example.TravelAgency.model.Category;
import com.example.TravelAgency.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    public final CategoryRepository categoryRepository;

    public void save(Category category) {
        if(category.getNameCategory() == null && category.getDescription() == null){
            throw new IllegalArgumentException("Category name and description are required");
        }
        categoryRepository.save(category);
    }

    public Category deleteById(Long id) {
        Category category = findById(id);
        categoryRepository.delete(category);
        return category;
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with ID " + id + " not found."));
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
