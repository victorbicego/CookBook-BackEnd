package com.group3.chefkoch.repository;

import com.group3.chefkoch.model.Ingredient_Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Ingredient_RecipeRepository extends JpaRepository<Ingredient_Recipe,Long> {
}
