package com.cookbook.cookbookbackend.controllers;

import com.cookbook.cookbookbackend.enums.Category;
import com.cookbook.cookbookbackend.enums.DifficultyGrade;
import com.cookbook.cookbookbackend.models.Recipe;
import com.cookbook.cookbookbackend.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/recipe")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/result")
    public ResponseEntity<?> findRecipeByInputAndFilter
            (@RequestParam String input,
             @RequestParam(required = false) Category category,
             @RequestParam(required = false) DifficultyGrade difficultyGrade,
             @RequestParam(required = false) Double rating,
             @RequestParam(required = false) Integer preparationTime,
             @RequestParam(required = false) String cuisine,
             @RequestParam Integer page,
             @RequestParam Integer quantity) {
        return recipeService.findRecipeByInputAndFilter(input, category, difficultyGrade, rating, preparationTime, cuisine, page, quantity);
    }

    @GetMapping("/result/total")
    public ResponseEntity<?> countRecipeByInputAndFilter
            (@RequestParam String input,
             @RequestParam(required = false) Category category,
             @RequestParam(required = false) DifficultyGrade difficultyGrade,
             @RequestParam(required = false) Double rating,
             @RequestParam(required = false) Integer preparationTime,
             @RequestParam(required = false) String cuisine) {
        return recipeService.countTotalOfRecipesByInputAndFilter(input, category, difficultyGrade, rating, preparationTime, cuisine);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> findRecipeById(@PathVariable Long id) {
        return recipeService.findRecipeById(id);
    }

    @PostMapping("/post")
    public ResponseEntity<?> saveRecipe(@RequestBody @Valid Recipe recipe) {
        return recipeService.saveRecipe(recipe);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id, @RequestBody @Valid Recipe recipe) {
        return recipeService.updateRecipe(id, recipe);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable Long id) {
        return recipeService.deleteRecipeById(id);
    }

    @GetMapping("/top3rated")
    public ResponseEntity<?> find3TopRated() {
        return recipeService.findTop3RecipesByRating();
    }

    @GetMapping("/top5newest")
    public ResponseEntity<?> find5Newest() {
        return recipeService.findTop5RecipesByDateOfPost();
    }

}
