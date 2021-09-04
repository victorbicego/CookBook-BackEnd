package com.cookbook.cookbookbackend.controllers;

import com.cookbook.cookbookbackend.models.Cuisine;
import com.cookbook.cookbookbackend.services.CuisineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cuisine")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CuisineController {

    @Autowired
    private CuisineService cuisineService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCuisines() {
        return cuisineService.getAllCuisines();
    }

    // Used just to Pre-populate!
    @GetMapping("/get/{id}")
    public ResponseEntity<?> findCuisineById(@PathVariable Long id) {
        return cuisineService.findCuisineById(id);
    }

    // Used just to Pre-populate!
    @PostMapping("/post")
    public ResponseEntity<?> saveCuisine(@RequestBody @Valid Cuisine cuisine) {
        return cuisineService.saveCuisine(cuisine);
    }

    // Used just to Pre-populate!
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCuisine(@PathVariable Long id) {
        return cuisineService.deleteCuisine(id);
    }

    // Used just to Pre-populate!
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateCuisine(@PathVariable Long id, @RequestBody @Valid Cuisine cuisine) {
        return cuisineService.updateCuisine(id, cuisine);
    }

}
