package com.cookbook.cookbookbackend.services;

import com.cookbook.cookbookbackend.models.Cuisine;
import com.cookbook.cookbookbackend.repositories.CuisineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CuisineService {

    @Autowired
    private CuisineRepository cuisineRepository;

    public ResponseEntity<?> getAllCuisines() {
        return new ResponseEntity<>(cuisineRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> findCuisineById(Long id) {
        Optional<Cuisine> cuisineOptional = cuisineRepository.findById(id);

        if (cuisineOptional.isPresent()) {
            return new ResponseEntity<>(cuisineOptional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> saveCuisine(Cuisine cuisine) {
        return new ResponseEntity<>(cuisineRepository.save(cuisine), HttpStatus.CREATED);
    }

    public ResponseEntity<?> deleteCuisine(Long id) {
        Optional<Cuisine> cuisineOptional = cuisineRepository.findById(id);

        if (cuisineOptional.isPresent()) {
            cuisineRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> updateCuisine(Long id, Cuisine cuisine) {
        Optional<Cuisine> cuisineOptional = cuisineRepository.findById(id);

        if (cuisineOptional.isPresent()) {
            Cuisine updatedCuisine = cuisineOptional.get();
            updatedCuisine.setContinent(cuisine.getContinent());
            updatedCuisine.setCountry(cuisine.getCountry());
            return new ResponseEntity<>(cuisineRepository.save(updatedCuisine), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public Optional<Cuisine> findCuisineByCountry(String country) {
        return cuisineRepository.findFirstCuisineByCountry(country);
    }


}
