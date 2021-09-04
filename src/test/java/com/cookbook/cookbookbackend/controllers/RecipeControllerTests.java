package com.cookbook.cookbookbackend.controllers;

import com.cookbook.cookbookbackend.enums.Category;
import com.cookbook.cookbookbackend.enums.DifficultyGrade;
import com.cookbook.cookbookbackend.models.Cuisine;
import com.cookbook.cookbookbackend.models.Ingredient;
import com.cookbook.cookbookbackend.models.Recipe;
import com.cookbook.cookbookbackend.services.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;

@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTests {

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private final Recipe recipeTest1 = new Recipe();
    private final Recipe recipeTest2 = new Recipe();
    private final Cuisine cuisineTest1 = new Cuisine();
    private final Ingredient ingredientTest1 = new Ingredient();
    private final Ingredient ingredientTest2 = new Ingredient();
    private final List<Ingredient> ingredientList = new ArrayList<>(List.of(ingredientTest1, ingredientTest2));

    @BeforeEach
    public void init() {
        recipeTest1.setName("Test1");
        recipeTest1.setAuthor("Tester1");
        recipeTest1.setCategory(Category.MAIN);
        recipeTest1.setCuisine(cuisineTest1);
        recipeTest1.setDateOfPost("00/00/0000");
        recipeTest1.setDifficultyGrade(DifficultyGrade.EASY);
        recipeTest1.setIngredientList(ingredientList);
        recipeTest1.setInstruction("InstructionTest1");
        recipeTest1.setPortion(6);
        recipeTest1.setPreparationTime(60);
        recipeTest1.setRating(3D);
        recipeTest1.setId(5L);
    }

    @Test
    @DisplayName("Test Get All Success")
    public void testGetAllSuccess() throws Exception {
        List<Recipe> recipeList = new ArrayList<>(List.of(recipeTest1, recipeTest2));

        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(recipeList, HttpStatus.OK);
        doReturn(responseEntityAnswer).when(recipeService).getAllRecipes();

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/recipe/all")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(recipeList)));
    }

    @Test
    @DisplayName("Test Post - Invalid because name is empty")
    public void testPostInvalidNameEmpty() throws Exception {
        recipeTest1.setName("");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because name is longer than 50")
    public void testPostInvalidNameToLong() throws Exception {
        recipeTest1.setName("Lorem ipsum dolor sit amet, consectetur adipiscing elit nec.");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because author is empty")
    public void testPostInvalidAuthorEmpty() throws Exception {
        recipeTest1.setAuthor("");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because author is longer than 30")
    public void testPostInvalidAuthorToLong() throws Exception {
        recipeTest1.setAuthor("Lorem ipsum dolor sit amet gravida.");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because instruction is empty")
    public void testPostInvalidInstructionEmpty() throws Exception {
        recipeTest1.setInstruction("");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because instruction is longer than 800")
    public void testPostInvalidInstructionToLong() throws Exception {
        recipeTest1.setInstruction("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent id convallis mi. Duis eu finibus nulla. Aliquam eget eleifend lorem. Etiam cursus ultrices bibendum. Etiam a accumsan elit, vel rutrum purus. Ut quis orci nec libero rutrum tempor at rutrum ipsum. Ut id dolor aliquet, tempus dui sed, bibendum sem. Donec blandit quam in libero condimentum bibendum. Mauris sit amet turpis libero. Mauris eu metus laoreet, mattis neque quis, consectetur ligula. Maecenas et sapien mi. Nullam ultrices, erat eu volutpat ultricies, ante nibh fringilla arcu, at commodo nunc nisi aliquam velit. Curabitur rutrum condimentum urna, vitae posuere magna dapibus eget. Sed lobortis, eros condimentum aliquam laoreet, nulla tortor lacinia felis, dapibus cursus elit tortor at mauris. Morbi fringilla risus sit amet metus.");

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because portion is smaller than 1")
    public void testPostInvalidPortionToSmall() throws Exception {
        recipeTest1.setPortion(0);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    @DisplayName("Test Post - Invalid because preparationTime is smaller than 1")
    public void testPostInvalidPreparationTimeToSmall() throws Exception {
        recipeTest1.setPreparationTime(0);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because difficultyGrade is empty")
    public void testPostInvalidDifficultyGradeEmpty() throws Exception {
        recipeTest1.setDifficultyGrade(null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because category is empty")
    public void testPostInvalidCategoryEmpty() throws Exception {
        recipeTest1.setCategory(null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because cuisine is empty")
    public void testPostInvalidCuisineEmpty() throws Exception {
        recipeTest1.setCuisine(null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because ingredientList is empty")
    public void testPostInvalidIngredientListEmpty() throws Exception {
        recipeTest1.setIngredientList(null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because dateOfPost is empty")
    public void testPostInvalidDateOfPostEmpty() throws Exception {
        recipeTest1.setDateOfPost(null);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because rating is smaller than 0")
    public void testPostInvalidRatingToSmall() throws Exception {
        recipeTest1.setRating(-1D);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Post - Invalid because rating is bigger than 5")
    public void testPostInvalidRatingToBig() throws Exception {
        recipeTest1.setRating(7D);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/recipe/post")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(recipeTest1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Test Delete - OK")
    public void testDeleteOK() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.OK);

        doReturn(responseEntityAnswer).when(recipeService).deleteRecipeById(20L);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/recipe/delete/{id}", 20L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test Delete - NOTFOUND")
    public void testDeleteNOTFOUND() throws Exception {
        ResponseEntity<?> responseEntityAnswer = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(responseEntityAnswer).when(recipeService).deleteRecipeById(21L);

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/recipe/delete/{id}", 21L)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
