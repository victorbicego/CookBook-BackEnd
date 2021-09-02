package com.cookbook.cookbookbackend.services;

import com.cookbook.cookbookbackend.enums.Category;
import com.cookbook.cookbookbackend.enums.DifficultyGrade;
import com.cookbook.cookbookbackend.models.Comment;
import com.cookbook.cookbookbackend.models.Cuisine;
import com.cookbook.cookbookbackend.models.HashTag;
import com.cookbook.cookbookbackend.models.Recipe;
import com.cookbook.cookbookbackend.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CuisineService cuisineService;

    @Autowired
    private HashTagService hashTagService;

    @Autowired
    private IngredientService ingredientService;

    public ResponseEntity<?> getAllRecipes() {
        return new ResponseEntity<>(recipeRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<?> findRecipeById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isPresent()) {
            return new ResponseEntity<>(recipeOptional.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> saveRecipe(Recipe recipe) {
        return new ResponseEntity<>(recipeRepository.save(recipe), HttpStatus.CREATED);
    }

    public ResponseEntity<?> updateRecipe(Long id, Recipe recipe) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isPresent()) {
            Recipe updatedRecipe = recipeOptional.get();
            updatedRecipe.setName(recipe.getName());
            updatedRecipe.setAuthor(recipe.getAuthor());
            updatedRecipe.setInstruction(recipe.getInstruction());
            updatedRecipe.setDateOfPost(recipe.getDateOfPost());
            updatedRecipe.setPortion(recipe.getPortion());
            updatedRecipe.setPreparationTime(recipe.getPreparationTime());
            updatedRecipe.setDifficultyGrade(recipe.getDifficultyGrade());
            updatedRecipe.setCategory(recipe.getCategory());
            updatedRecipe.setCuisine(recipe.getCuisine());
            updatedRecipe.setHashTagList(recipe.getHashTagList());
            ingredientService.deleteAllIngredientsByRecipe(updatedRecipe);
            updatedRecipe.setIngredientList(recipe.getIngredientList());
            return new ResponseEntity<>(recipeRepository.save(updatedRecipe), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> deleteRecipeById(Long id) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isPresent()) {
            commentService.deleteAllCommentsByRecipe(id);
            recipeRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> countTotalOfRecipesByInputAndFilter(String input,
                                                                 Category filterCategory,
                                                                 DifficultyGrade filterDifficultyGrade,
                                                                 Double filterRating,
                                                                 Integer filterPreparationTime,
                                                                 String filterCuisine) {
        Category categoryMapped = mappedCategory(input);
        Cuisine cuisineMapped = mappedCuisine(input);
        HashTag hashTagMapped = mappedHashTags(input);
        DifficultyGrade difficultyGradeMapped = mappedDifficultyGrade(input);

        Cuisine filterCuisineMapped = mappedCuisine(filterCuisine);

        Integer totalNumberOfRecipes = recipeRepository.countAllRecipesByInputAndFilter(input, categoryMapped, cuisineMapped, hashTagMapped, difficultyGradeMapped, filterCategory, filterDifficultyGrade, filterRating, filterPreparationTime, filterCuisineMapped);

        return new ResponseEntity<>(totalNumberOfRecipes, HttpStatus.OK);
    }

    public ResponseEntity<?> findRecipeByInputAndFilter(String inputText,
                                                        Category filterCategory,
                                                        DifficultyGrade filterDifficultyGrade,
                                                        Double filterRating,
                                                        Integer filterPreparationTime,
                                                        String filterCuisine,
                                                        Integer paginatorPage,
                                                        Integer paginatorQuantity) {

        List<Recipe> foundRecipes = getRecipesByMatch(inputText, filterCategory, filterDifficultyGrade, filterRating, filterPreparationTime, filterCuisine, paginatorPage, paginatorQuantity);

        if (foundRecipes.size() > 0) {
            return new ResponseEntity<>(foundRecipes, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public List<Recipe> getRecipesByMatch(String inputText,
                                          Category filterCategory,
                                          DifficultyGrade filterDifficultyGrade,
                                          Double filterRating,
                                          Integer filterPreparationTime,
                                          String filterCuisine,
                                          Integer page,
                                          Integer quantity) {
        Category categoryMapped = mappedCategory(inputText);
        Cuisine cuisineMapped = mappedCuisine(inputText);
        HashTag hashTagMapped = mappedHashTags(inputText);
        DifficultyGrade difficultyGradeMapped = mappedDifficultyGrade(inputText);

        Cuisine cuisineFilter = mappedCuisine(filterCuisine);

        PageRequest pageNumberWithElementsProPage = PageRequest.of(page, quantity);

        return recipeRepository.findAllRecipesByInputAndFilter(inputText, categoryMapped, cuisineMapped, hashTagMapped, difficultyGradeMapped, filterCategory, filterDifficultyGrade, filterRating, filterPreparationTime, cuisineFilter, pageNumberWithElementsProPage);
    }

    public Category mappedCategory(String categoryName) {
        Category[] categories = Category.values();

        for (Category category : categories) {
            if (category.toString().equals(categoryName.toUpperCase())) {
                return category;
            }
        }

        return null;
    }

    public Cuisine mappedCuisine(String country) {
        Optional<Cuisine> cuisineOptional = cuisineService.findCuisineByCountry(country);
        return cuisineOptional.orElse(null);
    }

    public HashTag mappedHashTags(String text) {
        Optional<HashTag> hashTagOptional = hashTagService.findHashTagByText(text);
        return hashTagOptional.orElse(null);
    }

    public DifficultyGrade mappedDifficultyGrade(String difficultyGrade) {
        DifficultyGrade[] difficulties = DifficultyGrade.values();

        for (DifficultyGrade difficulty : difficulties) {
            if (difficulty.toString().equals(difficultyGrade.toUpperCase())) {
                return difficulty;
            }
        }

        return null;
    }

    public void updateRating(Recipe recipe) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipe.getId());

        if (recipeOptional.isPresent()) {
            Recipe editedRecipe = recipeOptional.get();
            List<Comment> commentList = commentService.findAllCommentsByRecipe(recipe);

            if (commentList.size() == 0) {
                editedRecipe.setRating(0.0);
            } else {
                double sumOfRatings = commentList.stream().mapToDouble(Comment::getRating).sum();
                double averageRating = sumOfRatings / commentList.size();
                editedRecipe.setRating(averageRating);
            }

            this.recipeRepository.save(editedRecipe);
        }

    }

    public ResponseEntity<?> findTop3RecipesByRating() {
        return new ResponseEntity<>(recipeRepository.findTop3RecipesByOrderByRatingDesc(), HttpStatus.OK);
    }

    public ResponseEntity<?> findTop5RecipesByDateOfPost() {
        return new ResponseEntity<>(recipeRepository.findTop5RecipesByOrderByDateOfPostDesc(), HttpStatus.OK);
    }

}
