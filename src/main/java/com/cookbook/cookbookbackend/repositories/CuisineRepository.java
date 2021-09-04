package com.cookbook.cookbookbackend.repositories;

import com.cookbook.cookbookbackend.models.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine, Long> {

    Optional<Cuisine> findFirstCuisineByCountry(String country);

}
