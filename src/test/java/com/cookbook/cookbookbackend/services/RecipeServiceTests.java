package com.cookbook.cookbookbackend.services;

import com.cookbook.cookbookbackend.enums.Category;
import com.cookbook.cookbookbackend.enums.DifficultyGrade;
import com.cookbook.cookbookbackend.models.Cuisine;
import com.cookbook.cookbookbackend.models.HashTag;
import com.cookbook.cookbookbackend.models.Ingredient;
import com.cookbook.cookbookbackend.models.Recipe;
import com.cookbook.cookbookbackend.repositories.HashTagRepository;
import com.cookbook.cookbookbackend.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RecipeServiceTests {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private HashTagService hashTagService;

    @Mock
    private CuisineService cuisineService;

    @InjectMocks
    private RecipeService recipeService;

//    private final Recipe recipeTest1 = new Recipe();
//    private final Recipe recipeTest2 = new Recipe();
//    private final Cuisine cuisineTest1 = new Cuisine();
//    private final Ingredient ingredientTest1 = new Ingredient();
//    private final Ingredient ingredientTest2 = new Ingredient();
//    private final List<Ingredient> ingredientList = new ArrayList<>(List.of(ingredientTest1, ingredientTest2));

    @BeforeEach
    public void init() {
//        recipeTest1.setName("Test1");
//        recipeTest1.setAuthor("Tester1");
//        recipeTest1.setCategory(Category.MAIN);
//        recipeTest1.setCuisine(cuisineTest1);
//        recipeTest1.setDateOfPost("00/00/0000");
//        recipeTest1.setDifficultyGrade(DifficultyGrade.EASY);
//        recipeTest1.setIngredientList(ingredientList);
//        recipeTest1.setInstruction("InstructionTest1");
//        recipeTest1.setPortion(6);
//        recipeTest1.setPreparationTime(60);
//        recipeTest1.setRating(3D);
//        recipeTest1.setId(5L);
    }

    @Test
    @DisplayName("Test mappedCategory - OK")
    public void testMappedCategoryOK() {
        assertEquals(Category.MAIN, recipeService.mappedCategory("main"));
    }

    @Test
    @DisplayName("Test mappedCategory - null")
    public void testMappedCategoryNull() {
        assertNull(recipeService.mappedCategory("test"));
    }

    @Test
    @DisplayName("Test mappedDifficultyGrade - OK")
    public void testMappedDifficultyGradeOK() {
        assertEquals(DifficultyGrade.EASY, recipeService.mappedDifficultyGrade("easy"));
    }

    @Test
    @DisplayName("Test mappedCategory - null")
    public void testMappedDifficultyGradeNull() {
        assertNull(recipeService.mappedDifficultyGrade("test"));
    }

    @Test
    @DisplayName("Test mappedHashTag - OK")
    public void testMappedHashTagOK() {
        HashTag hashTagTest1 = new HashTag();
        hashTagTest1.setText("#lovetest1");

        when(hashTagService.findHashTagByText("#lovetest1")).thenReturn(Optional.of(hashTagTest1));
        assertEquals(hashTagTest1, recipeService.mappedHashTags("#lovetest1"));
    }

    @Test
    @DisplayName("Test mappedHashTag - null")
    public void testMappedHashTagNull() {

        when(hashTagService.findHashTagByText("#notexists")).thenReturn(Optional.empty());
        assertNull(recipeService.mappedDifficultyGrade("#notexists"));
    }

    @Test
    @DisplayName("Test mappedCuisine - OK")
    public void testMappedCuisineOK() {
        Cuisine cuisineTest1 = new Cuisine();

        cuisineTest1.setCountry("countryTest1");
        cuisineTest1.setContinent("continentTest1");
        cuisineTest1.setId(5L);

        when(cuisineService.findCuisineByCountry("countryTest1")).thenReturn(Optional.of(cuisineTest1));
        assertEquals(cuisineTest1, recipeService.mappedCuisine("countryTest1"));
    }

    @Test
    @DisplayName("Test mappedCuisine - null")
    public void testMappedCuisineNull() {

        when(cuisineService.findCuisineByCountry("notfound")).thenReturn(Optional.empty());
        assertNull(recipeService.mappedDifficultyGrade("#notexists"));
    }

}
