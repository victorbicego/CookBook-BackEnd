package com.cookbook.cookbookbackend.repositories;

import com.cookbook.cookbookbackend.models.Ingredient;
import com.cookbook.cookbookbackend.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllIngredientsByNameContains(String name);

    @Transactional
    void deleteAllIngredientsByRecipe(Recipe recipe);
}
