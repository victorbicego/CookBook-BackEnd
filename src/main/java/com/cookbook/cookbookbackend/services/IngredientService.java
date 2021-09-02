package com.cookbook.cookbookbackend.services;

import com.cookbook.cookbookbackend.models.Ingredient;
import com.cookbook.cookbookbackend.models.Recipe;
import com.cookbook.cookbookbackend.repositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> findIngredientsByName(String name) {
        return ingredientRepository.findAllIngredientsByNameContains(name);
    }

    public void deleteAllIngredientsByRecipe(Recipe recipe) {
        ingredientRepository.deleteAllIngredientsByRecipe(recipe);
    }

}
