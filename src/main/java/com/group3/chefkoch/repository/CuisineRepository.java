package com.group3.chefkoch.repository;

import com.group3.chefkoch.model.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuisineRepository extends JpaRepository<Cuisine,Long> {
}
